package com.luyifan.cars.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyifan.cars.model.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //根据用户id查询菜单
    List<SysMenu> getMenuByUserId(@Param("userId") Long userId);
    //根据角色id查询菜单
    List<SysMenu> getMenuByRoleId(@Param("roleId") Long roleId);
}