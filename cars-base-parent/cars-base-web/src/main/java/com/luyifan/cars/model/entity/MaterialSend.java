package com.luyifan.cars.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName("material_send")
public class MaterialSend {
    @TableId(type = IdType.AUTO)
    private Long sendId;
    private String province;
    private String detailSource;
    private String name;
    private String phone;
    private String email;
    private String type;
    private String status;
    private String doType;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;
    //记录的物资明细
    @TableField(exist = false)
    private List<MaterialSendDetail> children = new ArrayList<>();
}