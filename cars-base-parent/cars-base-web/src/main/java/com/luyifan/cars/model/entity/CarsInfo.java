package com.luyifan.cars.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 *
 * @TableName cars_info
 */
@TableName(value ="cars_info")
@Data
public class CarsInfo implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer carId;

    /**
     *
     */
    private Integer categoryId;

    @TableField(exist = false)
    private String categoryName;
    /**
     *
     */
    private String carName;

    /**
     *
     */
    private BigDecimal purchasePrice;

    /**
     *
     */
    private String discountFactor;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date initRegisterDate;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date insuranceStartDate;

    private String image;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}