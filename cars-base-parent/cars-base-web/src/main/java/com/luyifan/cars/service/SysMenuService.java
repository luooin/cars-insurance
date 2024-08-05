package com.luyifan.cars.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luyifan.cars.model.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> getParent();
    //根据用户id查询菜单
    List<SysMenu> getMenuByUserId(Long userId);
    //根据角色id查询菜单
    List<SysMenu> getMenuByRoleId(Long roleId);
}