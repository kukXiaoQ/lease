package com.situ.ws.bean.common;

import lombok.Data;

@Data
public class Page {
    // 当前页
    private int page;
    // 每页条数
    private int size;

    // 分页的起始位置
    private int start;

    public int getStart() {
        return (page - 1) * size;
    }
}
