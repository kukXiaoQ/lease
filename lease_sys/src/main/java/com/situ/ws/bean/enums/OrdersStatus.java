package com.situ.ws.bean.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 订单状态
 */
@Getter
public enum OrdersStatus {
    pending(1,"待确认"),confirmed(2,"已确认"),cancelled(3,"已取消"),completed(4,"已完成");

    private final Integer status;

    @JsonValue
    private final String name;
    OrdersStatus(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    @Override
    public String toString() {
        return "OrdersStatus{" +
                "status=" + status +
                ", name='" + name + '\'' +
                '}';
    }

}
