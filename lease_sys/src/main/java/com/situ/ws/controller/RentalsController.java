package com.situ.ws.controller;


import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.common.PageResult;
import com.situ.ws.bean.entries.Payments;
import com.situ.ws.bean.entries.Rentals;
import com.situ.ws.service.IRentalsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 租赁管理模块 前端控制器
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
@Slf4j
public class RentalsController {

    private final IRentalsService rentalsService;

    /**
     * 分页+条件查询
     * @param rentals 条件
     * @param page 当前页码
     * @return 统一结果
     */
    @GetMapping
    public CommonResult PageQuery(Rentals rentals, Page page){

        rentals.setIsDel(0);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Rentals> list = rentalsService.PageQuery(rentals,page);
        int count = rentalsService.getCount(rentals);

        return CommonResult.success(new PageResult<>(list, count));
    }



    /**
     * 分页+条件查询
     * @param rentals 条件
     * @param page 当前页码
     * @return 统一结果
     */
    @GetMapping("/is")
    public CommonResult PageQueryIsDel(Rentals rentals, Page page){
        rentals.setIsDel(1);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Rentals> list = rentalsService.PageQuery(rentals,page);
        int count = rentalsService.getCount(rentals);

        return CommonResult.success(new PageResult<>(list, count));
    }

    /**
     * 保存租赁信息
     * @param rentals 租赁信息
     * @return 修改行数
     */
    @PostMapping
    public CommonResult addPayments(@RequestBody Rentals rentals){
        log.error("新增支付信息=====>{}",rentals);

        int count = rentalsService.addPayments(rentals);

        return CommonResult.success(count);
    }

    /**
     * 批量删除（逻辑删除）
     * @param ids id数组
     * @return 统一结果
     */
    @DeleteMapping
    public CommonResult logicalDeletion(@RequestBody Long[] ids){
        int count = rentalsService.logicalDeletion(ids);
        return CommonResult.success(count);
    }

    /**
     * 修改租赁信息
     * @param rentals 修改租赁信息
     * @return 统一格式
     */
    @PutMapping
    public CommonResult updateById(@RequestBody Rentals rentals){
        log.error("修改的租赁信息为=====>{}",rentals);

        int count = rentalsService.updateById(rentals);
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

        int count = rentalsService.restoreData(ids);
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

        int count = rentalsService.removeByIds(ids);
        return CommonResult.success(count);
    }

}
