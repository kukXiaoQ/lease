package com.situ.ws.bean.entries;

import com.situ.ws.bean.enums.VehiclesStatus;
import lombok.Data;

@Data
public class ReportForm {

    private Integer count;
    private VehiclesStatus status;
}
