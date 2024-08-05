package com.luyifan.cars.model.vo.menu;

import lombok.Data;

import java.util.List;

@Data
public class SaveMenuParm {
    private Long roleId;
    private List<Long> list;
}