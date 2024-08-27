package com.situ.ws.service;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.OrderDto;
import com.situ.ws.bean.entries.Orders;

import java.util.List;

/**
 * <p>
 * 订单管理模块 服务类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
public interface IOrdersService{

    /**
     * 分页查询
     * @param orders 订单查询条件
     * @param page  分页查询条件
     * @return 符合条件订单集合
     */
    List<Orders> PageQuery(Orders orders, Page page);

    List<OrderDto> PageQuery(OrderDto dto, Page page);

    /**
     * 获取总条数
     * @return 总条数
     */
    int getCount(OrderDto orders);


    /**
     * 逻辑删除
     * @param ids id数组
     * @return 删除条数
     */
    int logicalDeletion(Long[] ids);


    /**
     * 真实删除
     * @param ids id数组
     * @return 删除条数
     */
    int removeByIds(Long[] ids);

    /**
     * 修改订单记录
     * @param orders 修改数据
     * @return 修改行数
     */
    int updateById(Orders orders);

    /**
     * 保存订单
     * @param orders 订单信息
     * @return 修改行数
     */
    int addOrder(Orders orders);

    /**
     * 恢复数据
     * @param ids id数组
     * @return 恢复条数
     */
    int restoreData(Long[] ids);
}
