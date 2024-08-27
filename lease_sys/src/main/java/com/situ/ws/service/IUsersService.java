package com.situ.ws.service;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.CarRentalDto;
import com.situ.ws.bean.dto.CodeDto;
import com.situ.ws.bean.dto.UpdDto;
import com.situ.ws.bean.entries.Brand;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.entries.Users;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户管理模块 服务类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
public interface IUsersService {

    /**
     * 根据用户名、手机号查询用户
     * @param users 输入信息
     * @return 用户
     */
    Users getUserByPhone(Users users);

    List<Orders> PageQuery(Users users, Page page);

    int getCount(Users users);

    int updateById(Users users);

    int addOrder(Users users);

    int removeByIds(Long[] ids);

    int restoreData(Long[] ids);

    int logicalDeletion(Long[] ids);

    Users getSelectOne(Users users);

    /**
     * 创建订单记录
     * @param dto 封装类
     * @return 是否成功
     */
    boolean createRentalRecords(CarRentalDto dto);

    /**
     * 根据用户id查询，它租过的所有车辆
     * @param id
     * @return
     */
    Map<String,Object> getBrandForUSerId(int id);

    /**
     * 用户还车
     * @param orders
     * @return
     */
    boolean returnRental(Orders orders);

    /**
     * 根据手机号、邮箱
     */
    boolean forgotPassword(CodeDto dto);

    boolean updPassword(UpdDto dto);
}
