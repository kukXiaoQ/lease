package com.situ.ws.bean.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.situ.ws.bean.entries.Notifications;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotificationsDto extends Notifications {

    @ExcelProperty("用户名")
    private String username;
    @ExcelProperty("用户昵称")
    private String nickname;
    @ExcelProperty("电子邮箱")
    private String email;
}
