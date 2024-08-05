package com.luyifan.cars;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lyf
 * @version 1.0
 * @date 2023/7/2 19:04
 */
@SpringBootApplication
@MapperScan("com.luyifan.cars.mapper")
public class CarsApplication
{
    public static void main(String[] args) {
        SpringApplication.run(CarsApplication.class,args);
    }
}
