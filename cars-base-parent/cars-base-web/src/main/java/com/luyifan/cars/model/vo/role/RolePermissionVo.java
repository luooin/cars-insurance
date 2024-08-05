package com.luyifan.cars.model.vo.role;

import com.luyifan.cars.model.entity.SysMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RolePermissionVo {
    //当前登录系统用户的菜单数据
    List<SysMenu> listmenu = new ArrayList<>();
    //角色原来分配的菜单
    private Object[] checkList;
}