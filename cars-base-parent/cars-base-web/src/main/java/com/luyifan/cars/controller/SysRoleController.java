package com.luyifan.cars.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luyifan.cars.model.dto.RoleAssignParm;
import com.luyifan.cars.model.dto.RoleParm;
import com.luyifan.cars.model.vo.material.SelectType;
import com.luyifan.cars.model.entity.SysRole;
import com.luyifan.cars.model.vo.menu.SaveMenuParm;
import com.luyifan.cars.model.vo.role.RolePermissionVo;
import com.luyifan.cars.service.RoleMenuService;
import com.luyifan.cars.service.SysRoleService;
import com.luyifan.utils.ResultUtils;
import com.luyifan.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private RoleMenuService roleMenuService;
    //新增角色
    @PostMapping
    public ResultVo addRole(@RequestBody SysRole role){
        role.setCreateTime(new Date());
        boolean save = sysRoleService.save(role);
        if(save){
            return ResultUtils.success("新增角色成功!");
        }
        return ResultUtils.error("新增角色成功!");
    }

    //编辑角色
    @PutMapping
    public ResultVo editRole(@RequestBody SysRole role){
        role.setUpdateTime(new Date());
        boolean save = sysRoleService.updateById(role);
        if(save){
            return ResultUtils.success("编辑角色成功!");
        }
        return ResultUtils.error("编辑角色失败!");
    }

    //删除角色
    @DeleteMapping("/{roleId}")
    public ResultVo deleteRole(@PathVariable("roleId") Long roleId){
        boolean b = sysRoleService.removeById(roleId);
        if(b){
            return ResultUtils.success("删除角色成功!");
        }
        return ResultUtils.error("删除角色失败!");
    }

    //角色列表
    @GetMapping("/list")
    public ResultVo getList(RoleParm parm){
        IPage<SysRole> list = sysRoleService.list(parm);
        return ResultUtils.success("查询角色成功",list);
    }


    //获取角色下拉数据
    @GetMapping("/getListSelect")
    public ResultVo getListSelect() {
        List<SysRole> list = sysRoleService.list();
        List<SelectType> roleList = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .forEach(item -> {
                    SelectType type = new SelectType();
                    type.setValue(item.getRoleId());
                    type.setLabel(item.getRoleName());
                    roleList.add(type);
                });
        return ResultUtils.success("查询成功", roleList);
    }


    //角色分配权限树回显
    @GetMapping("/getAssignTree")
    public ResultVo getAssignTree(RoleAssignParm parm){
        RolePermissionVo assignTree = sysRoleService.getAssignTree(parm);
        return ResultUtils.success("查询成功",assignTree);
    }

    //分配权限保存
    @PostMapping("/assignSave")
    public ResultVo assignSave(@RequestBody SaveMenuParm parm){
        roleMenuService.saveMenu(parm);
        return ResultUtils.success("分配成功");
    }
}