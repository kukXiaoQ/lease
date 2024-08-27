package com.situ.ws.controller;


import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.common.PageResult;
import com.situ.ws.bean.dto.NotificationsDto;
import com.situ.ws.bean.dto.OrderDto;
import com.situ.ws.service.INotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 通知管理模块 前端控制器
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationsController {

    private final INotificationsService notificationsService;

    @GetMapping
    public CommonResult PageQuery(NotificationsDto dto, Page page){
        dto.setIsDel(0);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<NotificationsDto> list = notificationsService.PageQuery(dto,page);
        int count = notificationsService.getCount(dto);

        return CommonResult.success(new PageResult<>(list, count));
    }


    /**
     * 批量删除（逻辑删除）
     * @param ids id数组
     * @return 统一结果
     */
    @DeleteMapping
    public CommonResult logicalDeletion(@RequestBody Long[] ids){
        int count = notificationsService.logicalDeletion(ids);
        return CommonResult.success(count);
    }


}
