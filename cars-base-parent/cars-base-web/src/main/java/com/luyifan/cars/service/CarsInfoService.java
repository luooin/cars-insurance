package com.luyifan.cars.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luyifan.cars.model.dto.InfoParm;
import com.luyifan.cars.model.entity.CarsInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luyifan.cars.model.vo.home.EchartsItem;

import java.util.List;

/**
* @author luyifan
* @description 针对表【cars_info】的数据库操作Service
* @createDate 2023-07-05 22:33:17
*/
public interface CarsInfoService extends IService<CarsInfo> {
    IPage<CarsInfo> getList(InfoParm parm);
    List<EchartsItem> discountRank();
}
