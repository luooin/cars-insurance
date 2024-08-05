package com.luyifan.cars.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luyifan.cars.mapper.CarInsuranceMapper;
import com.luyifan.cars.model.entity.CarInsurance;
import com.luyifan.cars.model.vo.home.EchartsItem;
import com.luyifan.cars.service.CarInsuranceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author luyifan
* @description 针对表【car_insurance】的数据库操作Service实现
* @createDate 2023-07-06 13:50:53
*/
@Service
public class CarInsuranceServiceImpl extends ServiceImpl<CarInsuranceMapper, CarInsurance>
        implements CarInsuranceService {

    @Override
    public List<EchartsItem> getPriceRank() {
        return this.baseMapper.getPriceRank();
    }
}





