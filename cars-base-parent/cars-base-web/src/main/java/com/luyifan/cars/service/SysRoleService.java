package com.luyifan.cars.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luyifan.cars.model.dto.RoleAssignParm;
import com.luyifan.cars.model.dto.RoleParm;
import com.luyifan.cars.model.entity.SysRole;
import com.luyifan.cars.model.vo.role.RolePermissionVo;

public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> list(RoleParm parm);
    RolePermissionVo getAssignTree(RoleAssignParm parm);
}