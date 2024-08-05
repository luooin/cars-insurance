package com.luyifan.cars.model.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ExportUser {
    @Excel(name = "姓名", orderNum = "1", width=30)
    private String nickName;
    @Excel(name = "性别",replace = { "男_0", "女_1" }, orderNum = "2", width=30)
    private String sex;
    @Excel(name = "电话", orderNum = "3", width=50)
    private String phone;
    @Excel(name = "邮箱", orderNum = "4", width=30)
    private Integer email;
}