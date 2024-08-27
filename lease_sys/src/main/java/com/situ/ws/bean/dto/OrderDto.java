package com.situ.ws.bean.dto;

import com.situ.ws.bean.entries.Orders;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDto extends Orders {
    private String username;
    private String phone;
    private String plateNumber;
    private String nickname;
}
