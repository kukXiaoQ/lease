package com.situ.ws.bean.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 支付状态方式
 */
@Getter
public enum PayMethodStatus {

    creditcard(1,"信用卡"),paypal(2,"支付宝"),cash(3,"现金");

    private final Integer status;
    @JsonValue
    private final String name;
    PayMethodStatus(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    @Override
    public String toString() {
        return "PayMethodStatus{" +
                "status=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
