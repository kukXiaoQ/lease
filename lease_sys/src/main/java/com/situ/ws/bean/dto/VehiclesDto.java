package com.situ.ws.bean.dto;

import com.situ.ws.bean.entries.Vehicles;
import lombok.Data;

@Data
public class VehiclesDto {

    private Vehicles vehicles;
    private String brandName;
    private String url;
}
