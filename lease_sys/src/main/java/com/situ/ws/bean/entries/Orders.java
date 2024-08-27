package com.situ.ws.bean.entries;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.situ.ws.bean.enums.OrdersStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 订单管理模块
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单唯一标识
     */

    private Long id;

    /**
     * 用户ID
     */
    @NotNull(message = "当前用户状态异常！")
    private Integer userId;

    /**
     * 车辆ID
     */
    @NotNull(message = "请选择车辆！")
    private Integer vehicleId;

    /**
     * 订单开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    /**
     * 订单结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

    /**
     * 订单状态（待确认、已确认、已取消、已完成）
     */
    private OrdersStatus status;

    /**
     * 订单更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 实租价格
     */
    private BigDecimal actualRentalPrice;

    /**
     * 押金
     */
    private BigDecimal deposit;

}
