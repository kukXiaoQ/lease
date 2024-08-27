package com.situ.ws.controller;


import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.common.PageResult;
import com.situ.ws.bean.dto.VehiclesDto;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.entries.Vehicles;
import com.situ.ws.service.IOrdersService;
import com.situ.ws.service.IVehiclesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 车辆管理模块 前端控制器
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@RestController
@RequestMapping("/vehicles")
@Slf4j
@RequiredArgsConstructor
public class VehiclesController {


    private final IVehiclesService vehiclesService;


    /**
     * 分页+条件查询
     * @param vehicles 条件
     * @param page 当前页码
     * @return 统一结果
     */
    @GetMapping
    public CommonResult PageQuery(Vehicles vehicles, Page page){
        log.error("vehicles=====>{}",vehicles);
        vehicles.setIsDel(0);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<VehiclesDto> list = vehiclesService.PageQuery(vehicles, page);
        int count = vehiclesService.getCount(vehicles);

        System.out.println(list);

        return CommonResult.success(new PageResult<>(list, count));
    }

    /**
     * 分页+条件查询
     * @param vehicles 条件
     * @param page 当前页码
     * @return 统一结果
     */
    @GetMapping("/is")
    public CommonResult PageQueryIsDel(Vehicles vehicles, Page page){
        log.error("是否删除=====>{}",vehicles.getIsDel());

        vehicles.setIsDel(1);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<VehiclesDto> list = vehiclesService.PageQuery(vehicles, page);
        int count = vehiclesService.getCount(vehicles);

        return CommonResult.success(new PageResult<>(list, count));
    }

    /**
     * 批量删除（逻辑删除）
     * @param ids id数组
     * @return 统一结果
     */
    @DeleteMapping
    public CommonResult logicalDeletion(@RequestBody Long[] ids){
        int count = vehiclesService.logicalDeletion(ids);
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

        int count = vehiclesService.restoreData(ids);
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

        int count = vehiclesService.removeByIds(ids);
        return CommonResult.success(count);
    }

    /**
     * 修改车辆
     * @param vehicles 修改信息
     * @return 统一格式
     */
    @PutMapping
    public CommonResult updateById(@RequestBody Vehicles vehicles){
        log.error("修改的车辆信息为=====>{}",vehicles);

        int count = vehiclesService.updateById(vehicles);
        return CommonResult.success(count);
    }

    /**
     * 保存
     * @param vehicles 订单信息
     * @return 修改行数
     */
    @PostMapping
    public CommonResult addVehicles(@RequestBody Vehicles vehicles){
        log.error("新增订单=====>{}",vehicles);

        int count = vehiclesService.addVehicles(vehicles);

        return CommonResult.success(count);
    }


    @GetMapping("/number/{id}")
    public CommonResult getVehiclesFromNumber(@PathVariable("id") Integer id){
        log.info("品牌id====================》{}",id);
        List<Vehicles> vehicles = vehiclesService.getVehiclesFromNumber(id);
        return CommonResult.success(vehicles);
    }

}
