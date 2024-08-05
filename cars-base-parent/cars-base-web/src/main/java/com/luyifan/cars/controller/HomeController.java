package com.luyifan.cars.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyifan.cars.model.entity.Notice;
import com.luyifan.cars.model.vo.home.Echart;
import com.luyifan.cars.model.vo.home.EchartTotal;
import com.luyifan.cars.model.vo.home.EchartsItem;
import com.luyifan.cars.service.CarInsuranceService;
import com.luyifan.cars.service.CarsInfoService;
import com.luyifan.cars.service.CarsCategoryService;
import com.luyifan.cars.service.impl.NoticeServiceImpl;
import com.luyifan.utils.ResultUtils;
import com.luyifan.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private CarsInfoService carsInfoService;
    @Autowired
    private CarInsuranceService carInsuranceService;
    @Autowired
    private CarsCategoryService carsCategoryService;

    @Autowired
    private NoticeServiceImpl noticeService;
    //统计
    @GetMapping("/getTotal")
    public ResultVo getTotal(){
        EchartTotal total = new EchartTotal();
        //车辆信息数量
        int count = carsInfoService.count();
        total.setCarInfoTotal(count);
        // 保单数量
        int count1 = carInsuranceService.count();
        total.setInsuranceTotal(count1);
        // 车辆分类数量
        int count2 = carsCategoryService.count();
        total.setCategoryTotal(count2);
        return ResultUtils.success("查询成功",total);
    }

    //物资分类
    @GetMapping("/getTotalCategory")
    public ResultVo getTotalCategory(){
        List<EchartsItem> category = carsCategoryService.getTotalCategory();
        //组装数据
        Echart echart = new Echart();
        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        if(category.size()>0){
            for (int i=0;i<category.size();i++){
                names.add(category.get(i).getName());
                values.add(category.get(i).getValue());
            }
        }
        echart.setNames(names);
        echart.setValues(values);
        return ResultUtils.success("查询成功",echart);
    }

    //价格系数
    @GetMapping("/getDiscountRank")
    public ResultVo getDiscountRank(){
        List<EchartsItem> itemList =carsInfoService.discountRank();
        return ResultUtils.success("查询成功",itemList);
    }

    //保单价格
    @GetMapping("/getPriceRank")
    public ResultVo getPriceRank(){
        List<EchartsItem> itemList =carInsuranceService.getPriceRank();
        return ResultUtils.success("查询成功",itemList);
    }

    //公告
    @GetMapping("/getNoticeList")
    public ResultVo getNoticeList(){
        QueryWrapper<Notice> query = new QueryWrapper<>();
        query.lambda().orderByDesc(Notice::getCreateTime).last(" limit 3");
        List<Notice> list = noticeService.list(query);
        return ResultUtils.success("查询成功",list);
    }
}