package com.situ.ws.controller;


import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.common.PageResult;
import com.situ.ws.bean.dto.OrderDto;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.service.IOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 订单管理模块 前端控制器
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class OrdersController {

    private final IOrdersService ordersService;


    /**
     * 分页+条件查询
     * @param dto 订单条件
     * @param page 当前页码
     * @return 统一结果
     */
    @GetMapping
    public CommonResult PageQuery(OrderDto dto, Page page){
        dto.setIsDel(0);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<OrderDto> list = ordersService.PageQuery(dto,page);
        int count = ordersService.getCount(dto);

        return CommonResult.success(new PageResult<>(list, count));
    }

    /**
     * 分页+条件查询
     * @param dto 订单条件
     * @param page 当前页码
     * @return 统一结果
     */
    @GetMapping("/is")
    public CommonResult PageQueryIsDel(OrderDto dto, Page page){
        log.error("是否删除=====>{}",dto.getIsDel());

        dto.setIsDel(1);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<OrderDto> list = ordersService.PageQuery(dto,page);
        int count = ordersService.getCount(dto);
        return CommonResult.success(new PageResult<>(list, count));
    }

    /**
     * 批量删除（逻辑删除）
     * @param ids id数组
     * @return 统一结果
     */
    @DeleteMapping
    public CommonResult logicalDeletion(@RequestBody Long[] ids){
        int count = ordersService.logicalDeletion(ids);
        return CommonResult.success(count);
    }


    /**
     * 恢复数据
     * @param ids id数组
     * @return 恢复条数
     */
    @DeleteMapping("/n_is")
    public CommonResult restoreData(@RequestBody Long[] ids){

        int count = ordersService.restoreData(ids);
        return CommonResult.success(count);
    }


    /**
     * 真实删除
     * @param ids id数组
     * @return 修改行数
     */
    @DeleteMapping("/is")
    public CommonResult removeByIds(@RequestBody Long[] ids){

        int count = ordersService.removeByIds(ids);
        return CommonResult.success(count);
    }

    /**
     * 修改订单
     * @param orders 修改信息
     * @return 统一格式
     */
    @PutMapping
    public CommonResult updateById(@RequestBody Orders orders){
        int count = ordersService.updateById(orders);
        return CommonResult.success(count);
    }

    /**
     * 保存订单
     * @param orders 订单信息
     * @return 修改行数
     */
    @PostMapping
    public CommonResult addOrder(@RequestBody Orders orders){
        int count = ordersService.addOrder(orders);

        return CommonResult.success(count);
    }
}
