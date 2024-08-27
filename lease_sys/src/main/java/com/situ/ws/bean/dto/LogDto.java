package com.situ.ws.bean.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.situ.ws.bean.entries.SystemLogs;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class LogDto extends SystemLogs {

    @ExcelProperty("操作人员")
    private String username;
    @ExcelProperty("手机号")
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelIgnore
    private LocalDate localDate;

    @ExcelIgnore
    private String time;
}
