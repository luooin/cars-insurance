package com.luyifan.cars.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luyifan.cars.model.dto.InsuranceParm;
import com.luyifan.cars.model.entity.CarInsurance;
import com.luyifan.cars.model.entity.CarsInfo;
import com.luyifan.cars.service.CarInsuranceService;
import com.luyifan.cars.service.CarsInfoService;
import com.luyifan.cars.service.SysUserService;
import com.luyifan.utils.ResultUtils;
import com.luyifan.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author lyf
 * @version 1.0
 * @date 2023/7/6 14:29
 */
@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    @Autowired
    private CarInsuranceService carInsuranceService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CarsInfoService carsInfoService;
    @PostMapping("/cal")
    public ResultVo getInsurancePrice(@RequestBody CarInsurance carInsurance) {
        BigDecimal result = getInsuranceResult(carInsurance);
        System.out.println(result.toString());
        System.out.println(carInsurance);
        return  ResultUtils.success("计算成功", result);
    }

    private static BigDecimal getInsuranceResult(CarInsurance carInsurance) {
        BigDecimal newPrice = new BigDecimal(carInsurance.getNewPrice()).multiply(new BigDecimal(10000)).multiply(new BigDecimal("0.01"));
        BigDecimal forcePrice = new BigDecimal(carInsurance.getForcePrice());
        BigDecimal threePrice = new BigDecimal(carInsurance.getThreePrice());
        BigDecimal lossPrice = new BigDecimal(carInsurance.getLossPrice());
        BigDecimal thiefPrice = new BigDecimal(carInsurance.getThiefPrice());
        BigDecimal bornPrice = new BigDecimal(carInsurance.getBornPrice());
        BigDecimal glassPrice = new BigDecimal(carInsurance.getGlassPrice());
        BigDecimal resPrice = new BigDecimal(carInsurance.getResPrice());
        BigDecimal scratchPrice= new BigDecimal(carInsurance.getScratchPrice());
        BigDecimal result = newPrice.add(forcePrice).add(threePrice).add(lossPrice).add(thiefPrice)
                .add(bornPrice).add(glassPrice).add(resPrice).add(scratchPrice);
        return result;
    }


    //新增
    @PostMapping
    public ResultVo add(@RequestBody CarInsurance carInsurance){
        CarsInfo carsInfo = carsInfoService.getById(carInsurance.getCarId());
        BigDecimal result = getInsuranceResult(carInsurance);
        BigDecimal realPrice = new BigDecimal(carsInfo.getDiscountFactor()).multiply(result);
        carInsurance.setRealPrice(realPrice.toString());
        if(carInsuranceService.save(carInsurance)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody CarInsurance carInsurance){
        if(carInsuranceService.updateById(carInsurance)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{insuranceId}")
    public ResultVo delete(@PathVariable("insuranceId") Long insuranceId){
        if(carInsuranceService.removeById(insuranceId)){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(InsuranceParm insuranceParm){
        //构造分页对象
        IPage<CarInsurance> page = new Page<>(insuranceParm.getCurrentPage(),insuranceParm.getPageSize());
        //构造查询条件
        IPage<CarInsurance> list = carInsuranceService.page(page);
        return ResultUtils.success("查询成功",list );
    }
}
