package com.situ.ws.bean.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 车辆状态
 */
@Getter
public enum VehiclesStatus {
    available(1,"可用"),rented(2,"已租"),maintenance(3,"维修中");

    private final Integer status;
    @JsonValue
    private final String name;
    VehiclesStatus(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    @Override
    public String toString() {
        return "VehiclesStatus{" +
                "status=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
