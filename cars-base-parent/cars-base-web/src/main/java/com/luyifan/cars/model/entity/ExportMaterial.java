package com.luyifan.cars.model.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ExportMaterial {
    @Excel(name = "图片", type = 2, orderNum = "1", width = 15,height = 30,imageType = 2)
    private byte[] imgFile;
    @Excel(name = "车辆品牌",orderNum = "2", width=30)
    private String carName;
    @Excel(name = "购置价格", orderNum = "3", width=50)
    private BigDecimal purchasePrice;
    @Excel(name = "折扣系数", orderNum = "4", width=30)
    private String discountFactor;
    @Excel(name = "初登日期", orderNum = "4", width=30,format = "yyyy-MM-dd")
    private Date initRegisterDate;
    @Excel(name = "起保日期", orderNum = "4", width=30,format = "yyyy-MM-dd")
    private Date insuranceStartDate;
}