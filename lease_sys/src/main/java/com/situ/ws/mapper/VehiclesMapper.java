package com.situ.ws.mapper;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.dto.ReportThreeDto;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.entries.ReportForm;
import com.situ.ws.bean.entries.Vehicles;
import com.situ.ws.bean.enums.OperationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 车辆管理模块 Mapper 接口
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */

@Mapper
public interface VehiclesMapper {

    /**
     * 分页查询
     * @param vehicles 查询条件
     * @param page  分页查询条件
     * @return 符合条件集合
     */
    List<Vehicles> selectPage(@Param("vehicles") Vehicles vehicles, @Param("page") Page page);

    /**
     * 获取总条数
     * @return 总条数
     */
    int getCount(@Param("vehicles") Vehicles vehicles);

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
     * @param vehicles 修改数据
     * @return 修改行数
     */
    @Operation(value = OperationStatus.UPDATE)
    int update(@Param("vehicles") Vehicles vehicles);

    /**
     * 保存订单
     * @param vehicles 订单信息
     * @return 修改行数
     */
    @Operation(value = OperationStatus.INSERT)
    int insert(@Param("vehicles") Vehicles vehicles);

    List<Vehicles> getVehiclesForNumber(@Param("id") Integer id);

    /**
     * 查询订单id
     */
    List<Integer> getListForUserId(@Param("userId") int userId);

    List<Vehicles> getListByIds(@Param("ids") List<Integer> ids);

    List<ReportForm> getCountByStatus();

    List<ReportThreeDto> getSalesChart();

    List<Integer> getCountByGroup(@Param("ids") Long[] ids);
}
