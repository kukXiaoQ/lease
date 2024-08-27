package com.situ.ws.mapper;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.dto.ReportTwoDto;
import com.situ.ws.bean.entries.Payments;
import com.situ.ws.bean.enums.OperationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 支付管理模块 Mapper 接口
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Mapper
public interface PaymentsMapper {

    /**
     * 分页查询
     * @param payments 支付查询条件
     * @param page  分页查询条件
     * @return 符合条件支付集合
     */
    List<Payments> selectPage(@Param("payments") Payments payments, @Param("page") Page page);

    /**
     * 获取总条数
     * @return 总条数
     */
    int getCount(@Param("payments") Payments payments);

    /**
     * 保存支付信息
     * @param payments 支付信息
     * @return 修改行数
     */
    @Operation(value = OperationStatus.INSERT)
    int insert(@Param("payments") Payments payments);

    /**
     * 批量删除（逻辑删除）
     * @param ids id数组
     * @return 统一结果
     */
    @Operation(value = OperationStatus.LOGICAL)
    int logicalDeletion(@Param("ids") Long[] ids);

    /**
     * 修改支付信息
     * @param payments 修改信息
     * @return 统一格式
     */
    @Operation(value = OperationStatus.UPDATE)
    int update(@Param("payments") Payments payments);

    /**
     * 恢复数据
     * @param ids id数组
     * @return 恢复条数
     */
    @Operation(value = OperationStatus.RESTORE)
    int restoreData(@Param("ids") Long[] ids);
    /**
     * 真实删除
     * @param ids id数组
     * @return 修改行数
     */
    @Operation(value = OperationStatus.DELETE)
    int deleteByIds(@Param("ids") Long[] ids);

    ReportTwoDto getAmountForSeason();

    Integer getPayCount(LocalDate localDate);
}
