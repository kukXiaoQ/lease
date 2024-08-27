package com.situ.ws.controller;


import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.common.PageResult;
import com.situ.ws.bean.dto.LogDto;
import com.situ.ws.bean.dto.OrderDto;
import com.situ.ws.bean.entries.SystemLogs;
import com.situ.ws.service.ISystemLogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 系统管理模块 前端控制器
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@RestController
@RequestMapping("/system-logs")
@RequiredArgsConstructor
public class SystemLogsController {

    private final ISystemLogsService systemLogsService;

    @GetMapping
    public CommonResult PageQuery(LogDto dto, Page page){

        System.out.println(dto);


        dto.setIsDel(0);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<LogDto> list = systemLogsService.PageQuery(dto, page);

        for (LogDto logDto : list) {
            LocalDateTime createdAt = logDto.getCreatedAt();
            logDto.setTime(createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        int count = systemLogsService.getCount(dto);

        return CommonResult.success(new PageResult<>(list, count));
    }


    @DeleteMapping
    public CommonResult logicalDeletion(@RequestBody Long[] ids){
        int count = systemLogsService.logicalDeletion(ids);
        return CommonResult.success(count);
    }

}
