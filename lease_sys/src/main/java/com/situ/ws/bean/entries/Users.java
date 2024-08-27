package com.situ.ws.bean.entries;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户管理模块
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码（应加密存储）
     */
    private String password;
    /**
     * 电子邮件地址
     */
    private String email;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 用户创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    /**
     * 用户信息更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;
    /**
     * 是否删除
     */
    private Integer isDel;

    private String url;

    private String nickname;
}
