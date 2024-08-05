package com.luyifan.cars.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luyifan.cars.model.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    //新增用户
    void addUser(SysUser sysUser);
    //编辑用户
    void editUser(SysUser sysUser);
    //删除用户
    void deleteUser(Long userId);
}
