package com.luyifan.cars.model.dto;

import lombok.Data;

@Data
public class ApplyParm {
    //入库记录id
    private Long intoId;
    //审核状态
    private String status;
}