package com.luyifan.cars.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyifan.cars.model.entity.CarsCategory;
import com.luyifan.cars.model.vo.home.EchartsItem;

import java.util.List;

public interface CarsCategoryMapper extends BaseMapper<CarsCategory> {
    List<EchartsItem> getTotalCategory();
}