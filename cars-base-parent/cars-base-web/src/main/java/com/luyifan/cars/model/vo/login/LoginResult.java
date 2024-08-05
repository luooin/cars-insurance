package com.luyifan.cars.model.vo.login;

import lombok.Data;

/**
 * @author lyf
 * @version 1.0
 * @date 2023/7/5 8:36
 */
@Data
public class LoginResult {
    private Long userId;
    private String token;
}
