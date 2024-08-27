package com.situ.ws.bean.entries;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 租赁管理模块
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Rentals implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租赁记录唯一标识
     */
    private Integer id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 租车时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentTime;

    /**
     * 还车时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnTime;

    /**
     * 预还时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate preReturnTime;

    /**
     * 总费用
     */
    private BigDecimal totalFee;

    /**
     * 租赁记录创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    /**
     * 租赁记录更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;

    /**
     * 是否删除
     */
    private Integer isDel;

}
