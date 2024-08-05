package com.luyifan.cars.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("material_into")
public class MaterialInto {
    @TableId(type = IdType.AUTO)
    private Long intoId;
    private String province;
    private String detailSource;
    private String name;
    private String phone;
    private String email;
    private String type;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private String createTime;
    @TableField(exist = false)
    private List<MaterialIntoDetail> children = new ArrayList<>();
}