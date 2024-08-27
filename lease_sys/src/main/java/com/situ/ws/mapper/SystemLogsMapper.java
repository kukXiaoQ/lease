package com.situ.ws.mapper;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.dto.LogDto;
import com.situ.ws.bean.entries.SystemLogs;
import com.situ.ws.bean.enums.OperationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统管理模块 Mapper 接口
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Mapper
public interface SystemLogsMapper {


    int insert(@Param("logs") SystemLogs logs);

    List<LogDto> selectPageByNewWhere(@Param("dto") LogDto dto,@Param("page") Page page);

    int getCount(@Param("dto") LogDto dto);

    int logicalDeletion(@Param("ids") Long[] ids);
}
