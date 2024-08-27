package com.situ.ws.mapper;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.dto.NotificationsDto;
import com.situ.ws.bean.entries.Notifications;
import com.situ.ws.bean.enums.OperationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 通知管理模块 Mapper 接口
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Mapper
public interface NotificationsMapper {

    List<NotificationsDto> selectPageByNewWhere(@Param("dto") NotificationsDto dto,@Param("page") Page page);

    int getCount(@Param("dto") NotificationsDto dto);

    @Operation(value = OperationStatus.LOGICAL)
    int logicalDeletion(@Param("ids") Long[] ids);

    @Operation(value = OperationStatus.INSERT)
    int insert(@Param("not") Notifications notificationsService);

}
