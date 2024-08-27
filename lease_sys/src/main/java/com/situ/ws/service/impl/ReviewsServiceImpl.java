package com.situ.ws.service.impl;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.ReviewsDto;
import com.situ.ws.bean.enums.ReviewsStatus;
import com.situ.ws.mapper.ReviewsMapper;
import com.situ.ws.service.IReviewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 评价管理模块 服务实现类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewsServiceImpl implements IReviewsService {

    private final ReviewsMapper mapper;

    @Override
    public List<ReviewsDto> PageQuery(ReviewsDto dto, Page page) {
        return mapper.selectPageByNewWhere(dto,page);
    }

    @Override
    public int getCount(ReviewsDto dto) {
        return mapper.getCount(dto);
    }

    @Override
    public int updateById(ReviewsDto dto) {
        log.error("=================>{}",dto);


        // 根据id获取，信誉积分，如果新的信誉积分，区间
        Integer rating = dto.getRating();

        status(dto, rating);

        return mapper.update(dto);
    }

    public static void status(ReviewsDto dto, Integer rating) {
        if (rating > 88){
            dto.setStatus(ReviewsStatus.excellent);
        } else if (rating > 70) {
            dto.setStatus(ReviewsStatus.merit);
        }else if (rating > 60) {
            dto.setStatus(ReviewsStatus.commonly);
        }else{
            dto.setStatus(ReviewsStatus.bad);
        }
    }
}
