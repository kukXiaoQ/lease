package com.situ.ws.bean.entries;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评价管理模块
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评价唯一标识
     */
    private Integer id;

    /**
     * 评分
     */
    private Integer rating;

    /**
     * 等级状态
     */
    private String comment;

    /**
     * 评价创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 评价更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 是否删除
     */
    private Integer isDel;

    private Integer count;

}
