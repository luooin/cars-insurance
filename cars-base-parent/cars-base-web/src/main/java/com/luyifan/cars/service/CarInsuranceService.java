package com.luyifan.cars.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luyifan.cars.model.entity.CarInsurance;
import com.luyifan.cars.model.vo.home.EchartsItem;

import java.util.List;


public interface CarInsuranceService extends IService<CarInsurance> {
    List<EchartsItem> getPriceRank();
}
