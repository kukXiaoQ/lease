package com.situ.ws.bean.entries;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.situ.ws.LeaseSpringApplication;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 通知管理模块
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Notifications implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通知唯一标识
     */
    @ExcelIgnore
    private Integer id;

    /**
     * 用户ID
     */
    @ExcelIgnore
    private Integer userId;

    /**
     * 通知消息，类型+信息
     * （忘记密码-验证码-code）
     * （租车成功-msg）
     */
    @ExcelProperty("发送信息")
    private String message;

    /**
     * 通知发送时间
     */
    @ExcelProperty("发送时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sentAt;

    @ExcelIgnore
    private Integer isDel;

}
