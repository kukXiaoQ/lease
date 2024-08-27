package com.situ.ws.service;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.LogDto;
import com.situ.ws.bean.dto.OrderDto;
import com.situ.ws.bean.entries.SystemLogs;

import java.util.List;

/**
 * <p>
 * 系统管理模块 服务类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
public interface ISystemLogsService {


    /**
     * 新增
     */
    int insert(SystemLogs logs);


    List<LogDto> PageQuery(LogDto dto, Page page);

    /**
     * 获取总条数
     * @return 总条数
     */
    int getCount(LogDto dto);

    int logicalDeletion(Long[] ids);

    void downLoadWithSysLog();

}
