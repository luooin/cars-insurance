package com.luyifan.cars.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyifan.cars.model.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    //保存
    boolean saveRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}