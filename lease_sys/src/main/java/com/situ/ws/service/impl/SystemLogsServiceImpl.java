package com.situ.ws.service.impl;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.LogDto;
import com.situ.ws.bean.entries.SystemLogs;
import com.situ.ws.mapper.SystemLogsMapper;
import com.situ.ws.service.ISystemLogsService;
import com.situ.ws.util.ExcelUtil;
import com.situ.ws.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 系统管理模块 服务实现类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SystemLogsServiceImpl implements ISystemLogsService {

    private final SystemLogsMapper mapper;

    @Override
    public int insert(SystemLogs logs) {
        log.error("记录全局日志===============>{}",logs);

        return mapper.insert(logs);
    }

    @Override
    public List<LogDto> PageQuery(LogDto dto, Page page) {
        return mapper.selectPageByNewWhere(dto,page);
    }

    @Override
    public int getCount(LogDto dto) {
        return mapper.getCount(dto);
    }

    @Override
    public int logicalDeletion(Long[] ids) {
        return mapper.logicalDeletion(ids);
    }

    @Override
    public void downLoadWithSysLog() {
        // 生成文件名
        String name = FileUtils.createFileName("log");
        // 获取所有数据
        List<LogDto> dtos = this.PageQuery(new LogDto(), FileUtils.page);
        // 写入文件
        ExcelUtil.exportData(name, LogDto.class, FileUtils.sheet,dtos);
    }
}
