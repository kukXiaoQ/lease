package com.situ.ws.mapper;

import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.dto.OrderDto;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.enums.OperationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mockito.internal.matchers.Or;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * <p>
 * 订单管理模块 Mapper 接口
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Mapper
public interface OrdersMapper{

    /**
     * 分页查询
     * @param orders 订单查询条件
     * @param page  分页查询条件
     * @return 符合条件订单集合
     */
    List<Orders> selectPage(@Param("orders") Orders orders, @Param("page") Page page);

    /**
     * 获取总条数
     * @return 总条数
     */
    int getCount(@Param("dto") OrderDto dto);

    /**
     * 删除
     * @param ids id数组
     * @return 删除条数
     */
    @Operation(value = OperationStatus.DELETE)
    int deleteByIds(@Param("ids") Long[] ids);

    /**
     * 逻辑删除
     * @return 删除条数
     */
    @Operation(value = OperationStatus.LOGICAL)
    int logicalDeletion(@Param("ids") Long[] ids);


    /**
     * 恢复数据
     * @param ids id数组
     * @return 恢复条数
     */
    @Operation(value = OperationStatus.RESTORE)
    int restoreData(@Param("ids") Long[] ids);

    /**
     * 修改订单记录
     * @param orders 修改数据
     * @return 修改行数
     */
    @Operation(value = OperationStatus.UPDATE)
    int update(@Param("orders") Orders orders);

    /**
     * 保存订单
     * @param orders 订单信息
     * @return 修改行数
     */
    @Operation(value = OperationStatus.INSERT)
    int insert(@Param("orders") Orders orders);

    /**
     * 根据订单号，查询车辆
     */
    List<Integer> getVehiclesIdListForIds(@Param("ids") List<Integer> ids);

    List<OrderDto> selectPageByNewWhere(@Param("dto") OrderDto dto,@Param("page") Page page);
}
