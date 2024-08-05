package com.luyifan.cars.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luyifan.cars.mapper.CarsCategoryMapper;
import com.luyifan.cars.model.entity.CarsCategory;
import com.luyifan.cars.model.vo.home.EchartsItem;
import com.luyifan.cars.service.CarsCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarsCategoryServiceImpl extends ServiceImpl<CarsCategoryMapper, CarsCategory> implements CarsCategoryService {
    @Override
    public List<EchartsItem> getTotalCategory() {
        return this.baseMapper.getTotalCategory();
    }
}