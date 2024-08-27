package com.situ.ws.bean.dto;

import com.situ.ws.bean.enums.PayMethodStatus;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CarRentalDto {

    @NotNull(message = "请选择车辆！")
    private Integer vehicleId;
    @NotNull(message = "当前用户状态异常！")
    private Integer userId;
    @NotNull(message = "预租价格状态！")
    @DecimalMin(value = "0",message = "请输入正确的预租价格！")
    private BigDecimal preLeasePrice;
    @DecimalMin(value = "0",message = "请输入正确的实租价格！")
    @NotNull(message = "请输入实租价格！")
    private BigDecimal actualRentalPrice;
    @NotNull(message = "请输入押金！")
    @DecimalMin(value = "0",message = "请输入正确的押金价格！")
    private BigDecimal deposit;
    @NotNull(message = "请输入支付金额！")
    @DecimalMin(value = "0",message = "请输入正确的支付金额价格！")
    private BigDecimal amount;

    @NotNull(message = "请选择支付方式！")
    private PayMethodStatus paymentMethod;
    @NotNull(message = "请输入还车日期！")
    @Future(message = "请选择正确的未来时间！")
    private LocalDate preReturnTime;
}
