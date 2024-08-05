package com.luyifan.cars.model.dto;

import lombok.Data;

/**
 * @author lyf
 * @version 1.0
 * @date 2023/7/6 16:05
 */
@Data
public class InsuranceParm {
    //当前第几页
    private Long currentPage;
    //每页查询的条数
    private Long pageSize;
}
