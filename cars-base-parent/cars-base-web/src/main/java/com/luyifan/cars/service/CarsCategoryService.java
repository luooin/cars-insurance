package com.luyifan.cars.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luyifan.cars.model.entity.CarsCategory;
import com.luyifan.cars.model.vo.home.EchartsItem;

import java.util.List;

public interface CarsCategoryService extends IService<CarsCategory> {
    List<EchartsItem> getTotalCategory();
}