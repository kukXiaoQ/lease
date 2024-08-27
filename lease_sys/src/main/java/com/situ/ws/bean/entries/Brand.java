package com.situ.ws.bean.entries;

import java.time.LocalDate;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author ws
 * @since 2024-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 所属国家
     */
    private String country;

    /**
     * 成立时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foundedYear;

    private Integer isDel;
    private String url;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateTime;
}
