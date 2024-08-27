package com.situ.ws.service.impl;

import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.CarRentalDto;
import com.situ.ws.bean.dto.CodeDto;
import com.situ.ws.bean.dto.ReviewsDto;
import com.situ.ws.bean.dto.UpdDto;
import com.situ.ws.bean.entries.*;
import com.situ.ws.bean.enums.OrdersStatus;
import com.situ.ws.bean.enums.PayStatus;
import com.situ.ws.bean.enums.ReviewsStatus;
import com.situ.ws.bean.enums.VehiclesStatus;
import com.situ.ws.mapper.*;
import com.situ.ws.service.IUsersService;
import com.situ.ws.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 * 用户管理模块 服务实现类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements IUsersService {

    private final UsersMapper mapper;
    // 修改车辆状态
    private final VehiclesMapper vehiclesMapper;
    // 新增订单
    private final OrdersMapper ordersMapper;
    // 新增支付方式
    private final PaymentsMapper paymentsMapper;
    // 新增记录
    private final RentalsMapper rentalsMapper;
    private final BrandMapper brandMapper;
    private final ReviewsMapper reviewsMapper;

    private final StringRedisTemplate redisTemplate;



    @Override
    @Transactional
    public boolean createRentalRecords(CarRentalDto dto) {

        // 查询用户信誉是否足够
        ReviewsStatus status = reviewsMapper.getStatus(dto.getUserId());

        if (status == null || status == ReviewsStatus.bad)
            throw new RuntimeException("用户信誉过低！");

        Orders order = createOrder(dto);
        ordersMapper.insert(order);
        Payments payments = createPayment(dto,order.getId());
        paymentsMapper.insert(payments);
        Rentals rentals = createRentals(dto,order.getId());
        rentalsMapper.insert(rentals);
        Vehicles vehicles = createVehicles(dto);
        vehiclesMapper.update(vehicles);

        return true;
    }

    @Override
    @Transactional
    public Map<String,Object> getBrandForUSerId(int id) {

        // 根据用户id，查询跟用户有关订单，通过订单id，查询租赁信息，当前用户下，还车时间为null的
        // 通过userid，查询为还车的订单记录
        List<Integer> orderIds = vehiclesMapper.getListForUserId(id);

        // 根据订单id，获取车辆Id
        List<Integer> vehicleIds = ordersMapper.getVehiclesIdListForIds(orderIds);

        // 获取车辆id后，查询车辆数据，并查询品牌
        List<Vehicles> vehicles = vehiclesMapper.getListByIds(vehicleIds);

        Set<Integer> vehicleSet = new HashSet<>();
        // 获取品牌id，查询品牌
        for (Vehicles vehicle : vehicles) {
            vehicleSet.add(vehicle.getBrandId());
        }

        List<Brand> brands = brandMapper.getListByIds(vehicleSet);

        HashMap<String, Object> map = new HashMap<>();
        map.put("brands", brands);
        map.put("vehicles", vehicles);
        return map;
    }

    @Override
    @Transactional
    public boolean returnRental(Orders orders) {

        // 根据订单信息，查询租赁记录，将其还车时间赋值
        List<Rentals> rentals = rentalsMapper.getRentalsByOrderId(orders);

        // 众多租赁记录，只有一个还车时间时null
        for (Rentals rental : rentals) {
            if (rental.getReturnTime() == null){
                // 获取当前时间,与预还时间进行比较
                LocalDate now = LocalDate.now();
                LocalDate preReturnTime = rental.getPreReturnTime();

                // 在预还时间之前，则不扣分
                if (!now.isBefore(preReturnTime)){
                    // 根据用户id，获取当前信誉记录
                    Reviews reviews = reviewsMapper.getById(orders.getUserId());
                    if (reviews == null)
                        throw new RuntimeException("用户信誉异常！");
                    Integer rating = reviews.getRating();

                    System.out.println(rating + "==============");

                    rating = rating - 5 * (reviews.getCount() + 1);
                    ReviewsDto dto = new ReviewsDto();
                    dto.setRating(rating);
                    dto.setCount(reviews.getCount() + 1);
                    dto.setId(reviews.getId());

                    ReviewsServiceImpl.status(dto,rating);
                    int c = reviewsMapper.update(dto);

                    if (c <= 0)
                        throw new RuntimeException("用户信誉异常！");
                }
                // 用户目前是这个租赁记录
                rental.setReturnTime(now);

                // 修改数据库
                rentalsMapper.update(rental);
                break;
            }
        }
        // 修改当前车状态
        Vehicles vehicles = new Vehicles();
        vehicles.setId(orders.getVehicleId());
//        设置为维修中
        vehicles.setStatus(VehiclesStatus.maintenance);
        vehiclesMapper.update(vehicles);

        return true;
    }

    @Override
    public boolean forgotPassword(CodeDto dto) {

        // 比对验证码
        String code = redisTemplate.opsForValue().get("lease:msg:phone:" + dto.getPhone());

        if (code == null)
            throw new RuntimeException("验证码不存在！");

        if (!dto.getCode().equals(code))
            return false;


        System.out.println("=-================");
        System.out.println(dto.getCode());
        System.out.println(code);
        System.out.println("=-================");

        Users users = new Users();
        BeanUtils.copyProperties(dto,users);
        Users user = mapper.getUserByPhone(users);

        // 如果用户不存在，抛出异常
        if (user == null){
            throw new RuntimeException("用户状态异常！");
        }

        user.setPassword(DigestUtilsByMe.randomDigest("123456"));
        int count = mapper.updatePass(user);

        return count > 0;
    }

    @Override
    public boolean updPassword(UpdDto dto) {
        // 比对密码
        System.out.println(LeaseThreadLocal.get()+"===========================");
        Users users = new Users();
        users.setId(LeaseThreadLocal.get());
        Users user = mapper.getUserByPhone(users);

        if (user != null){
            // 比对密码
            if (DigestUtilsByMe.decode(dto.getOldPassword(), user.getPassword())){
                // 比对成功
                String newP = DigestUtilsByMe.randomDigest(dto.getNewPassword());
                user.setPassword(newP);
                mapper.updatePass(user);
                return true;
            }
        }
        return false;
    }

    private static Vehicles createVehicles(CarRentalDto dto) {
        Vehicles vehicles = new Vehicles();
        vehicles.setStatus(VehiclesStatus.rented);
        vehicles.setId(dto.getVehicleId());
        vehicles.setUpdatedAt(LocalDate.now());
        return vehicles;
    }

    /**
     * 创建租赁记录
     */
    private Rentals createRentals(CarRentalDto dto, Long orderId) {
        Rentals rentals = new Rentals();
        BeanUtils.copyProperties(dto, rentals);
        rentals.setOrderId(orderId);
        // 还车时间为null
        rentals.setRentTime(LocalDate.now());
        rentals.setTotalFee(dto.getActualRentalPrice().add(dto.getDeposit()));
        rentals.setCreatedAt(LocalDate.now());
        rentals.setUpdatedAt(LocalDate.now());
        System.out.println(rentals+"===============================");

        // 需要的总费用大于支付金额，抛出异常
        if (rentals.getTotalFee().compareTo(dto.getAmount()) > 0){
            throw new RuntimeException("支付金额过小！");
        }
        return rentals;
    }

    /**
     * 生成支付记录
     */
    private Payments createPayment(CarRentalDto dto, Long orderId) {
        Payments payments = new Payments();
        BeanUtils.copyProperties(dto, payments);
        payments.setCreatedAt(LocalDate.now());
        payments.setUpdatedAt(LocalDate.now());
        payments.setStatus(PayStatus.pending);
        payments.setOrderId(orderId);
        System.out.println(payments+"======================");
        return payments;
    }

    /**
     * 封装orders
     */
    private Orders createOrder(CarRentalDto dto) {
        Orders orders = new Orders();
        BeanUtils.copyProperties(dto,orders);
        orders.setStartTime(LocalDate.now());
        orders.setUpdatedAt(LocalDate.now());
        orders.setStatus(OrdersStatus.pending);
        long id = new SnowflakeIdGenerator(1, 1).nextId();
        orders.setId(id);
        return orders;
    }

    @Override
    public Users getUserByPhone(Users users) {
        return mapper.getUserByPhone(users);
    }

    @Override
    public List<Orders> PageQuery(Users users, Page page) {
        return mapper.selectPage(users,page);
    }

    @Override
    public int getCount(Users users) {
        return mapper.getCount(users);
    }

    @Override
    public int updateById(Users users) {
        users = (Users) FileUtils.getLastName(users);
        return mapper.update(users);
    }

    @Override
    public int addOrder(Users users) {

        // 如果密码为null，设置初始密码
        if (users.getPassword() == null){
            users.setPassword(DigestUtilsByMe.randomDigest("123456"));
        }

        users = (Users) FileUtils.getLastName(users);
        return mapper.insert(users);
    }

    @Override
    public int removeByIds(Long[] ids) {
        // 删除对应的信誉记录
        reviewsMapper.deleteByIds(ids);
        // 删除永久用户信息
        return mapper.deleteByIds(ids);
    }

    @Override
    public int restoreData(Long[] ids) {
        reviewsMapper.restoreData(ids);
        return mapper.restoreData(ids);
    }

    @Override
    public int logicalDeletion(Long[] ids) {
        // 逻辑删除信誉
        reviewsMapper.logicalDeletion(ids);
        return mapper.logicalDeletion(ids);
    }

    @Override
    public Users getSelectOne(Users users) {
        return mapper.selectOne(users);
    }


}
