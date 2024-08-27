package com.situ.ws.bean.dto;

import com.situ.ws.bean.enums.ReviewsStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ReviewsDto {

    private String username;
    private String nickname;
    private String phone;
    private Integer rating;
    private ReviewsStatus status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateAt;
    private Integer id;
    private Integer count;
}
