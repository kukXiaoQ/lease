package com.situ.ws.mapper;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.dto.ReviewsDto;
import com.situ.ws.bean.entries.Reviews;
import com.situ.ws.bean.enums.OperationStatus;
import com.situ.ws.bean.enums.ReviewsStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Stack;

/**
 * <p>
 * 信誉管理模块 Mapper 接口
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Mapper
public interface ReviewsMapper {


    /**
     * 删除
     * @param ids id数组
     * @return 删除条数
     */
    @Operation(OperationStatus.DELETE)
    int deleteByIds(@Param("ids") Long[] ids);

    /**
     * 逻辑删除
     * @return 删除条数
     */
    @Operation(OperationStatus.LOGICAL)
    int logicalDeletion(@Param("ids") Long[] ids);


    /**
     * 恢复数据
     * @param ids id数组
     * @return 恢复条数
     */
    @Operation(OperationStatus.RESTORE)
    int restoreData(@Param("ids") Long[] ids);

    /**
     * 修改信誉记录
     * @param dto 修改数据
     * @return 修改行数
     */
    @Operation(OperationStatus.UPDATE)
    int update(@Param("dto") ReviewsDto dto);

    /**
     * 保存信誉
     * @param reviews 订单信息
     * @return 修改行数
     */
    @Operation(OperationStatus.INSERT)
    int insert(@Param("reviews") Reviews reviews);

    Reviews selectById(@Param("userId") Long userId);

    /**
     * 查询用户信誉
     */
    List<ReviewsDto> selectPageByNewWhere(@Param("dto") ReviewsDto dto,@Param("page") Page page);

    int getCount(@Param("dto") ReviewsDto dto);

    ReviewsStatus getStatus(@Param("userId")@NotNull(message = "当前用户状态异常！") Integer userId);

    /**
     * 根据用户id，和用户扣的信誉
     */
    @Operation(OperationStatus.UPDATE)
    int updateByCourse(@Param("reviews") Reviews reviews);

    Reviews getById(@Param("userId")@NotNull(message = "当前用户状态异常！") Integer userId);
}
