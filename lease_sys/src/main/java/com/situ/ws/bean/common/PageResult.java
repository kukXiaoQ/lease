package com.situ.ws.bean.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    // 返回数据
    private List<T> data;

    // 返回总条数
    private int total;


}
