package com.situ.ws.service;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.NotificationsDto;
import com.situ.ws.bean.entries.Notifications;

import java.util.List;

/**
 * <p>
 * 通知管理模块 服务类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
public interface INotificationsService {

    List<NotificationsDto> PageQuery(NotificationsDto dto, Page page);

    int getCount(NotificationsDto dto);

    int logicalDeletion(Long[] ids);

    int insert(Notifications notifications);

    void downLoadWithSysMsg();

}
