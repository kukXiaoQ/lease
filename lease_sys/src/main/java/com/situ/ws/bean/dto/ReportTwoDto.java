package com.situ.ws.bean.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReportTwoDto {

    private BigDecimal spring;
    private BigDecimal summer;
    private BigDecimal autumn;
    private BigDecimal winter;

}
