package com.luyifan.cars.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luyifan.cars.mapper.RoleMenuMapper;
import com.luyifan.cars.model.entity.RoleMenu;
import com.luyifan.cars.model.vo.menu.SaveMenuParm;
import com.luyifan.cars.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Transactional
    @Override
    public void saveMenu(SaveMenuParm parm) {
        //先删除角色原来的权限
        QueryWrapper<RoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(RoleMenu::getRoleId,parm.getRoleId());
        this.baseMapper.delete(query);
        //重新保存
        this.baseMapper.saveRoleMenu(parm.getRoleId(),parm.getList());
    }
}