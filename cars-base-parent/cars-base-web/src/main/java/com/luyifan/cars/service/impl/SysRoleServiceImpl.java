package com.luyifan.cars.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luyifan.cars.mapper.SysRoleMapper;
import com.luyifan.cars.model.dto.RoleAssignParm;
import com.luyifan.cars.model.dto.RoleParm;
import com.luyifan.cars.model.entity.SysMenu;
import com.luyifan.cars.model.entity.SysRole;
import com.luyifan.cars.model.entity.SysUser;
import com.luyifan.cars.model.vo.role.RolePermissionVo;
import com.luyifan.cars.service.SysMenuService;
import com.luyifan.cars.service.SysRoleService;
import com.luyifan.cars.service.SysUserService;
import com.luyifan.cars.utils.MakeMenuTree;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public IPage<SysRole> list(RoleParm parm) {
        //构造分页对象
        IPage<SysRole> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        //构造查询条件
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getRoleName())){
            query.lambda().like(SysRole::getRoleName,parm.getRoleName());
        }
        return baseMapper.selectPage(page,query);
    }

    @Override
    public RolePermissionVo getAssignTree(RoleAssignParm parm) {
        //查询当前用户信息
        SysUser user = sysUserService.getById(parm.getUserId());
        //查询当前用户的权限 如果是超级管理员，全查
        List<SysMenu> list = null;
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")){
            list = sysMenuService.list();
        }else{
            list = sysMenuService.getMenuByUserId(parm.getUserId());
        }
        List<SysMenu> menuList = MakeMenuTree.makeTree(list, 0L);
        //查询角色原来的权限
        List<SysMenu> roleMenu = sysMenuService.getMenuByRoleId(parm.getRoleId());
        List<Long> ids = new ArrayList<>();
        Optional.ofNullable(roleMenu).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null)
                .forEach(item ->{
                    ids.add(item.getMenuId());
                });
        RolePermissionVo vo = new RolePermissionVo();
        vo.setCheckList(ids.toArray());
        vo.setListmenu(menuList);
        return vo;
    }
}

