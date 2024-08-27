package com.situ.ws.bean.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ReviewsStatus {

    commonly(1,"一般"),merit(2,"良好"),excellent(3,"优秀"),bad(4,"差");
    private final Integer status;
    @JsonValue
    private final String name;
    ReviewsStatus(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReviewsStatus{" +
                "status=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
