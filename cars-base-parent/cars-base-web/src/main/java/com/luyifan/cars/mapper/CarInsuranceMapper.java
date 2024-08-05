package com.luyifan.cars.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyifan.cars.model.entity.CarInsurance;
import com.luyifan.cars.model.vo.home.EchartsItem;

import java.util.List;


/**
* @author luyifan
* @description 针对表【car_insurance】的数据库操作Mapper
* @createDate 2023-07-06 13:50:53
* @Entity generator.domain.CarInsurance
*/
public interface CarInsuranceMapper extends BaseMapper<CarInsurance> {
    List<EchartsItem> getPriceRank();
}




