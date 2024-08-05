package com.luyifan.cars.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luyifan.cars.mapper.SysUserMapper;
import com.luyifan.cars.model.entity.SysUser;
import com.luyifan.cars.model.entity.SysUserRole;
import com.luyifan.cars.service.SysUserRoleService;
import com.luyifan.cars.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Transactional
    @Override
    public void addUser(SysUser sysUser) {
        //新增用户
        int insert = this.baseMapper.insert(sysUser);
        //设置用户的角色
        if(insert > 0){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRole.setRoleId(sysUser.getRoleId());
            sysUserRoleService.save(sysUserRole);
        }
    }

    @Override
    @Transactional
    public void editUser(SysUser sysUser) {
        int i = this.baseMapper.updateById(sysUser);
        //设置用户的角色：判断角色是否发生变化
        if(i > 0){
            //先删除，再插入
            QueryWrapper<SysUserRole> query = new QueryWrapper<>();
            query.lambda().eq(SysUserRole::getUserId,sysUser.getUserId());
            sysUserRoleService.remove(query);
            //插入
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRole.setRoleId(sysUser.getRoleId());
            sysUserRoleService.save(sysUserRole);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        int i = this.baseMapper.deleteById(userId);
        if(i > 0){
            QueryWrapper<SysUserRole> query = new QueryWrapper<>();
            query.lambda().eq(SysUserRole::getUserId,userId);
            sysUserRoleService.remove(query);
        }
    }
}