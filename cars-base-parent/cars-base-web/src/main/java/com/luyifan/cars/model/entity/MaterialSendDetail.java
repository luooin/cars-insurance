package com.luyifan.cars.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("material_send_detail")
public class MaterialSendDetail {
    @TableId(type = IdType.AUTO)
    private Long sendDetailId;
    private Long sendId;
    private Long infoId;
    private Long categoryId;
    private String infoName;
    private String image;
    private String specs;
    private String unit;
    private String infoDesc;
    private Integer num;
}
