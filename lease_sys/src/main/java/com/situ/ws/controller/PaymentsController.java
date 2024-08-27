package com.situ.ws.controller;


import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.common.PageResult;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.entries.Payments;
import com.situ.ws.service.IPaymentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 支付管理模块 前端控制器
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentsController {

    private final IPaymentsService paymentsService;

    /**
     * 分页+条件查询
     * @param payments 支付条件
     * @param page 当前页码
     * @return 统一结果
     */
    @GetMapping
    public CommonResult PageQuery(Payments payments, Page page){
        payments.setIsDel(0);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Payments> list = paymentsService.PageQuery(payments,page);
        int count = paymentsService.getCount(payments);

        return CommonResult.success(new PageResult<>(list, count));
    }

    /**
     * 查询被删除的数据
     * @param payments
     * @param page
     * @return
     */
    @GetMapping("/is")
    public CommonResult PageQueryIsDel(Payments payments, Page page){
        payments.setIsDel(1);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Payments> list = paymentsService.PageQuery(payments,page);
        int count = paymentsService.getCount(payments);

        return CommonResult.success(new PageResult<>(list, count));
    }

    /**
     * 保存支付信息
     * @param payments 支付信息
     * @return 修改行数
     */
    @PostMapping
    public CommonResult addPayments(@RequestBody Payments payments){
        int count = paymentsService.addPayments(payments);

        return CommonResult.success(count);
    }

    /**
     * 批量删除（逻辑删除）
     * @param ids id数组
     * @return 统一结果
     */
    @DeleteMapping
    public CommonResult logicalDeletion(@RequestBody Long[] ids){
        int count = paymentsService.logicalDeletion(ids);
        return CommonResult.success(count);
    }

    /**
     * 修改支付信息
     * @param payments 修改信息
     * @return 统一格式
     */
    @PutMapping
    public CommonResult updateById(@RequestBody Payments payments){
        log.error("修改的订单信息为=====>{}",payments);
        int count = paymentsService.updateById(payments);
        return CommonResult.success(count);
    }



    /**
     * 恢复数据
     * @param ids id数组
     * @return 恢复条数
     */
    @DeleteMapping("/n_is")
    public CommonResult restoreData(@RequestBody Long[] ids){

        log.info("要恢复的数据===》{}", Arrays.toString(ids));

        int count = paymentsService.restoreData(ids);
        return CommonResult.success(count);
    }


    /**
     * 真实删除
     * @param ids id数组
     * @return 修改行数
     */
    @DeleteMapping("/is")
    public CommonResult removeByIds(@RequestBody Long[] ids){

        log.info("要永久删除的数据===》{}", Arrays.toString(ids));

        int count = paymentsService.removeByIds(ids);
        return CommonResult.success(count);
    }

}
