package com.luyifan.cars.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luyifan.cars.mapper.CarsInfoMapper;
import com.luyifan.cars.model.dto.InfoParm;
import com.luyifan.cars.model.entity.CarsInfo;
import com.luyifan.cars.model.vo.home.EchartsItem;
import com.luyifan.cars.service.CarsInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author luyifan
* @description 针对表【cars_info】的数据库操作Service实现
* @createDate 2023-07-05 22:33:17
*/
@Service
public class CarsInfoServiceImpl extends ServiceImpl<CarsInfoMapper, CarsInfo>
    implements CarsInfoService {

    @Override
    public IPage<CarsInfo> getList(InfoParm parm) {
        //构造分页对象
        IPage<CarsInfo> pages = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        System.out.println(baseMapper.getList(pages,parm));
        return baseMapper.getList(pages,parm);
    }

    @Override
    public List<EchartsItem> discountRank() {
        return this.baseMapper.discountRank();
    }
}




