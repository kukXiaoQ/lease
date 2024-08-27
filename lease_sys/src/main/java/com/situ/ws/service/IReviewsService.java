package com.situ.ws.service;

import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.ReviewsDto;

import java.util.List;

/**
 * <p>
 * 评价管理模块 服务类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
public interface IReviewsService {

    List<ReviewsDto> PageQuery(ReviewsDto dto, Page page);

    int getCount(ReviewsDto dto);

    int updateById(ReviewsDto dto);
}
