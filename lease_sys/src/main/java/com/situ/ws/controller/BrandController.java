package com.situ.ws.controller;


import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.common.PageResult;
import com.situ.ws.bean.dto.OrderDto;
import com.situ.ws.bean.entries.Brand;
import com.situ.ws.service.IBrandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ws
 * @since 2024-08-10
 */
@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandService brandService;

    @GetMapping
    public CommonResult selectAllByName(String name){
        List<Brand> list = brandService.getAllBrands(name);
        return CommonResult.success(list);
    }

    @GetMapping("/page")
    public CommonResult PageQuery(Brand brand, Page page){
        brand.setIsDel(0);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Brand> list = brandService.PageQuery(brand,page);
        int count = brandService.getCount(brand);

        return CommonResult.success(new PageResult<>(list, count));
    }


    @PutMapping
    public CommonResult update(@RequestBody Brand brand){
        System.out.println(brand);

        int count = brandService.update(brand);
        return CommonResult.success(count);
    }

    @PostMapping
    public CommonResult insert(@RequestBody Brand brand){
        System.out.println(brand);
        int count = brandService.insert(brand);
        return CommonResult.success(count);
    }

    @DeleteMapping
    public CommonResult logicalDeletion(@RequestBody Long[] ids){


        int count = brandService.logicalDeletion(ids);
        return CommonResult.success(count);
    }
}
