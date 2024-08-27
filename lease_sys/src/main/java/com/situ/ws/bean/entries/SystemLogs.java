package com.situ.ws.bean.entries;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统管理模块
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志唯一标识
     */
    @ExcelIgnore
    private Integer id;

    /**
     * 用户ID
     */
    @ExcelIgnore
    private Integer userId;

    /**
     * 操作描述
     */
    @ExcelProperty("操作描述")
    private String action;

    /**
     * 操作详情
     */
    @ExcelProperty("操作详情")
    private String details;

    /**
     * 日志创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd xx HH:mm:ss")
    @ExcelProperty("创建时间")
    private LocalDateTime createdAt;
    @ExcelIgnore
    private Integer isDel;
}
