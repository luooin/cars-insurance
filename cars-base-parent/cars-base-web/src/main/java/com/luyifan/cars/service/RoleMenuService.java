package com.luyifan.cars.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luyifan.cars.model.entity.RoleMenu;
import com.luyifan.cars.model.vo.menu.SaveMenuParm;

public interface RoleMenuService extends IService<RoleMenu> {
    //保存角色权限
    void saveMenu(SaveMenuParm parm);
}