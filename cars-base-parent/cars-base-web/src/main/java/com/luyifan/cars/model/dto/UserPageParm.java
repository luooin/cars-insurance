package com.luyifan.cars.model.dto;

import lombok.Data;

@Data
public class UserPageParm {
    //当前第几页
    private Long currentPage;
    //每页查询的条数
    private Long pageSize;
    //查询关键字
    private String nickName;
    private String phone;
}