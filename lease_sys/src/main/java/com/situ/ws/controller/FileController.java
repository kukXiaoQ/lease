package com.situ.ws.controller;

import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.LogDto;
import com.situ.ws.config.LeaseWebConfigurer;
import com.situ.ws.service.INotificationsService;
import com.situ.ws.service.ISystemLogsService;
import com.situ.ws.service.MsgService;
import com.situ.ws.util.ExcelUtil;
import com.situ.ws.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/loading")
public class FileController {

    private final ISystemLogsService systemLogsService;
    private final INotificationsService notificationsService;


    /**
     * 上传头像
     * @param file 上传头像信息
     * @return CommonResult
     */
    @PostMapping
    public CommonResult upload(@RequestParam("file") MultipartFile file) {
        log.info("... ...开始上传... ...");

        String newFileName = FileUtils.uploadFile(file);

        return CommonResult.success(LeaseWebConfigurer.IMG +newFileName);
    }

    @GetMapping("/up_logs")
    public CommonResult uploadLogs(){
        systemLogsService.downLoadWithSysLog();
        return CommonResult.success(200,"文件已成功下载！");
    }

    @GetMapping("/up_msgs")
    public CommonResult uploadMsg(){
        notificationsService.downLoadWithSysMsg();
        return CommonResult.success(200,"文件已成功下载！");
    }
}
