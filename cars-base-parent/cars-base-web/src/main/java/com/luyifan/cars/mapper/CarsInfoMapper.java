package com.luyifan.cars.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luyifan.cars.model.dto.InfoParm;
import com.luyifan.cars.model.entity.CarsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyifan.cars.model.vo.home.EchartsItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author luyifan
* @description 针对表【cars_info】的数据库操作Mapper
* @createDate 2023-07-05 22:33:17
* @Entity generator.domain.CarsInfo
*/
public interface CarsInfoMapper extends BaseMapper<CarsInfo> {
    IPage<CarsInfo> getList(IPage<CarsInfo> page, @Param("parm") InfoParm parm);
    List<EchartsItem> discountRank();
}




