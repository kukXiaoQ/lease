package com.situ.ws.controller;

import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.service.IPaymentsService;
import com.situ.ws.service.IVehiclesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reportforms")
public class ReportFormController {

    private final IVehiclesService vehiclesService;
    private final IPaymentsService paymentsService;

    /**
     * 获取车辆分布报表信息
     */
    @GetMapping
    public CommonResult getDrawProjectSigningOverview(){

        Integer[] arr = vehiclesService.getCountByStatus();

        return CommonResult.success(arr);
    }

    @GetMapping("/salesChart")
    public CommonResult getSalesChart(){
        Map<String,Object[]> map = vehiclesService.getSalesChart();
        return CommonResult.success(map);
    }

    @GetMapping("/monthlySalesChart")
    public CommonResult getMonthlySalesChart(){

        System.out.println("===========2=====");

        BigDecimal[] amounts = paymentsService.getAmount();


        System.out.println(Arrays.toString(amounts));

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return CommonResult.success(amounts);
    }

    @GetMapping("/oneSalesChart")
    public CommonResult getOneSalesChart(){
        System.out.println("==================================111");

        Map<String,Object> map = paymentsService.getOneSalesChart();
        return CommonResult.success(map);
    }
}
