package com.luyifan.cars.model.dto;

import lombok.Data;

@Data
public class UpPasswordParm {
    private Long userId;
    private String password;
    private String oldPassword;
}