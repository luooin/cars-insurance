package com.luyifan.cars.model.vo.login;

import lombok.Data;

@Data
public class UserInfo {
    private Long userId;
    private String name;
    private Object[] permissons;
}