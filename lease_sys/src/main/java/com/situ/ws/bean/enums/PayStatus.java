package com.situ.ws.bean.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 支付状态
 */
@Getter
public enum PayStatus {

    pending(1,"待处理"),completed(2,"已完成"),refunded(3,"已退款");

    private final Integer status;
    @JsonValue
    private final String name;
    PayStatus(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    @Override
    public String toString() {
        return "PayStatus{" +
                "status=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
