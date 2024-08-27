package com.situ.ws.service.impl;

import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.LogDto;
import com.situ.ws.bean.dto.NotificationsDto;
import com.situ.ws.bean.entries.Notifications;
import com.situ.ws.mapper.NotificationsMapper;
import com.situ.ws.service.INotificationsService;
import com.situ.ws.util.ExcelUtil;
import com.situ.ws.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通知管理模块 服务实现类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Service
@RequiredArgsConstructor
public class NotificationsServiceImpl implements INotificationsService {

    private final NotificationsMapper mapper;

    @Override
    public List<NotificationsDto> PageQuery(NotificationsDto dto, Page page) {
        return mapper.selectPageByNewWhere(dto,page);
    }

    @Override
    public int getCount(NotificationsDto dto) {
        return mapper.getCount(dto);
    }

    @Override
    public int logicalDeletion(@Param("ids") Long[] ids) {
        return mapper.logicalDeletion(ids);
    }

    @Override
    public int insert(Notifications notifications) {
        return mapper.insert(notifications);
    }

    @Override
    public void downLoadWithSysMsg() {
        // 生成文件名
        String name = FileUtils.createFileName("msg");
        // 获取所有数据
        List<NotificationsDto> dtos = this.PageQuery(new NotificationsDto(), FileUtils.page);
        // 写入文件
        ExcelUtil.exportData(name, NotificationsDto.class, FileUtils.sheet,dtos);
    }
}
