package com.luyifan.cars.model.vo.home;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Echart {
    private List<String> names = new ArrayList<>();
    private List<Integer> values = new ArrayList<>();
}