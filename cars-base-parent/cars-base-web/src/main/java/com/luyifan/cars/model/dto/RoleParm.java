package com.luyifan.cars.model.dto;

import lombok.Data;

/**
 * 列表参数接收
 */
@Data
public class RoleParm {
    private Long currentPage;
    private Long pageSize;
    // 查询关键字
    private String roleName;
}