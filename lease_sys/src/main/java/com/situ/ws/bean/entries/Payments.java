package com.situ.ws.bean.entries;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.situ.ws.bean.enums.PayMethodStatus;
import com.situ.ws.bean.enums.PayStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 支付管理模块
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付记录唯一标识
     */
    private Integer id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 支付方式（信用卡、PayPal、现金）
     */
    private PayMethodStatus paymentMethod;

    /**
     * 支付状态（待处理、已完成、已退款）
     */
    private PayStatus status;

    /**
     * 支付记录创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    /**
     * 支付记录更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;

    /**
     * 是否删除
     */
    private Integer isDel;

}
