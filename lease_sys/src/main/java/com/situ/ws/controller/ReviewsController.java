package com.situ.ws.controller;


import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.common.PageResult;
import com.situ.ws.bean.dto.CarRentalDto;
import com.situ.ws.bean.dto.CodeDto;
import com.situ.ws.bean.dto.ReviewsDto;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.entries.Users;
import com.situ.ws.service.IReviewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评价管理模块 前端控制器
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewsController {


    private final IReviewsService reviewsService;

    /**
     * 分页+条件查询
     * @param dto 订单条件
     * @param page 当前页码
     * @return 统一结果
     */
    @GetMapping
    public CommonResult PageQuery(ReviewsDto dto, Page page){
        log.error("reviewsDto=====>{}",dto);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<ReviewsDto> list = reviewsService.PageQuery(dto,page);
        int count = reviewsService.getCount(dto);

        return CommonResult.success(new PageResult<>(list, count));
    }

    @PutMapping
    public CommonResult updateById(@RequestBody ReviewsDto dto){

        int count = reviewsService.updateById(dto);
        return CommonResult.success(count);
    }

}
