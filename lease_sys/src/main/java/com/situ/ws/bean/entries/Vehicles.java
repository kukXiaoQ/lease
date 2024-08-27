package com.situ.ws.bean.entries;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.situ.ws.bean.enums.VehiclesStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 车辆管理模块
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Vehicles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆唯一标识
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 车辆状态（可用、已租、维修中）
     */
    private VehiclesStatus status;

    /**
     * 车辆信息创建时间
     */
    private LocalDate createdAt;

    /**
     * 车辆信息更新时间
     */
    private LocalDate updatedAt;

    /**
     * 预租价格
     */
    private BigDecimal preLeasePrice;

    /**
     * 品牌ID
     */
    private Integer brandId;

    private Integer isDel;

    private String url;

}
