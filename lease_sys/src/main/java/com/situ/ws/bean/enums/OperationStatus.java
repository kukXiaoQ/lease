package com.situ.ws.bean.enums;


import lombok.Data;
import lombok.Getter;

@Getter
public enum OperationStatus {

    INSERT("新增"),
    UPDATE("修改"),
    DELETE("删除"),
    LOGICAL("逻辑删除"),
    RESTORE("恢复");

    private final String name;

    OperationStatus(String name) {
        this.name = name;
    }
}
