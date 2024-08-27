package com.situ.ws.service.impl;

import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.OrderDto;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.enums.OrdersStatus;
import com.situ.ws.mapper.OrdersMapper;
import com.situ.ws.mapper.UsersMapper;
import com.situ.ws.mapper.VehiclesMapper;
import com.situ.ws.service.IOrdersService;
import com.situ.ws.util.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 订单管理模块 服务实现类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements IOrdersService {

    private final OrdersMapper mapper;
    private final UsersMapper usersMapper;
    private final VehiclesMapper vehiclesMapper;

    @Override
    public List<Orders> PageQuery(Orders orders, Page page) {
        return mapper.selectPage(orders, page);
    }


    @Override
    public List<OrderDto> PageQuery(OrderDto dto, Page page) {

        return mapper.selectPageByNewWhere(dto,page);
    }

    @Override
    public int getCount(OrderDto orders) {
        return mapper.getCount(orders);
    }

    @Override
    public int logicalDeletion(Long[] ids) {
        return mapper.logicalDeletion(ids);
    }

    @Override
    public int removeByIds(Long[] ids) {
        return mapper.deleteByIds(ids);
    }

    @Override
    public int updateById(Orders orders) {

        // 如果订单状态为已完成
        if (orders.getStatus() == OrdersStatus.completed){
            // 设置结束时间
            orders.setEndTime(LocalDate.now());
        }

        return mapper.update(orders);
    }

    @Override
    public int addOrder(Orders orders) {
        // 生成订单编号
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1, 1);
        long id = idGenerator.nextId();
        orders.setId(id);
        return mapper.insert(orders);
    }

    @Override
    public int restoreData(Long[] ids) {
        return mapper.restoreData(ids);
    }
}
