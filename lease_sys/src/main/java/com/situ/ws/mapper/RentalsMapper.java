package com.situ.ws.mapper;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.entries.Rentals;
import com.situ.ws.bean.enums.OperationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 租赁管理模块 Mapper 接口
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Mapper
public interface RentalsMapper {

    /**
     * 分页+条件查询
     * @param rentals 条件
     * @param page 当前页码
     * @return 统一结果
     */
    List<Rentals> selectPage(@Param("rentals") Rentals rentals,@Param("page") Page page);

    int getCount(@Param("rentals") Rentals rentals);

    /**
     * 保存租赁信息
     * @param rentals 租赁信息
     * @return 修改行数
     */
    @Operation(OperationStatus.INSERT)
    int insert(@Param("rentals") Rentals rentals);


    /**
     * 修改租赁信息
     * @param rentals 修改租赁信息
     * @return 统一格式
     */
    @Operation(OperationStatus.UPDATE)
    int update(@Param("rentals")Rentals rentals);

    /**
     * 批量删除（逻辑删除）
     * @param ids id数组
     * @return 统一结果
     */
    @Operation(OperationStatus.LOGICAL)
    int logicalDeletion(@Param("ids")Long[] ids);

    /**
     * 恢复数据
     * @param ids id数组
     * @return 恢复条数
     */
    @Operation(OperationStatus.RESTORE)
    int restoreData(@Param("ids")Long[] ids);
    /**
     * 真实删除
     * @param ids id数组
     * @return 删除条数
     */
    @Operation(OperationStatus.DELETE)
    int deleteByIds(@Param("ids")Long[] ids);

    List<Rentals> getRentalsByOrderId(@Param("orders") Orders orders);

}
