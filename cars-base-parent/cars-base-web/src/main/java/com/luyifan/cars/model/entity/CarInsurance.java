package com.luyifan.cars.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName car_insurance
 */
@TableName(value ="car_insurance")
@Data
public class CarInsurance implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer insuranceId;

    /**
     * 
     */
    private Integer carId;

    /**
     * 
     */
    private String newPrice;

    /**
     * 
     */
    private  String forcePrice;

    /**
     * 
     */
    private  String threePrice;

    /**
     * 
     */
    private  String lossPrice;

    /**
     * 
     */
    private  String thiefPrice;

    /**
     * 
     */
    private  String bornPrice;

    /**
     * 
     */
    private  String glassPrice;

    /**
     * 
     */
    private  String resPrice;

    /**
     * 
     */
    private  String scratchPrice;
    private  String realPrice;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}