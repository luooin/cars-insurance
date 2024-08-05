package com.luyifan.cars.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luyifan.cars.model.dto.PageParm;
import com.luyifan.cars.model.vo.material.SelectType;
import com.luyifan.cars.model.entity.CarsCategory;
import com.luyifan.cars.service.CarsCategoryService;
import com.luyifan.utils.ResultUtils;
import com.luyifan.utils.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CarsCategoryController {
    @Autowired
    private CarsCategoryService materialCategoryService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody CarsCategory category){
        if(materialCategoryService.save(category)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody CarsCategory category){
        if(materialCategoryService.updateById(category)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{categoryId}")
    public ResultVo delete(@PathVariable("categoryId") Long categoryId){
        if(materialCategoryService.removeById(categoryId)){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(PageParm parm){
        //构造分页对象
        IPage<CarsCategory> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<CarsCategory> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getCategoryName())){
            query.lambda().like(CarsCategory::getCategoryName,parm.getCategoryName());
        }
        query.lambda().orderByDesc(CarsCategory::getOrderNum);
        IPage<CarsCategory> list = materialCategoryService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }


    //物资分类
    @GetMapping("/getSelect")
    public ResultVo getSelect(){
        List<CarsCategory> list = materialCategoryService.list();
        List<SelectType> roleList = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .forEach(item -> {
                    SelectType type = new SelectType();
                    type.setValue(item.getCategoryId());
                    type.setLabel(item.getCategoryName());
                    roleList.add(type);
                });
        return ResultUtils.success("查询成功", roleList);
    }
}