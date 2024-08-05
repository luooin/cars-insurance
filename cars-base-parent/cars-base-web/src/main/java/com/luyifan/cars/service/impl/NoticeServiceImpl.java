package com.luyifan.cars.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luyifan.cars.mapper.NoticeMapper;
import com.luyifan.cars.model.entity.Notice;
import com.luyifan.cars.service.NoticeService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}