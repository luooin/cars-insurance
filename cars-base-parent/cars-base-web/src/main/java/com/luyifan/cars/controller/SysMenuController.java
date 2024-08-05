package com.luyifan.cars.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyifan.cars.model.entity.SysMenu;
import com.luyifan.cars.service.SysMenuService;
import com.luyifan.cars.utils.MakeMenuTree;
import com.luyifan.utils.ResultUtils;
import com.luyifan.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody SysMenu sysMenu){
        sysMenu.setCreateTime(new Date());
        if(sysMenuService.save(sysMenu)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody SysMenu sysMenu){
        sysMenu.setUpdateTime(new Date());
        if(sysMenuService.updateById(sysMenu)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{menuId}")
    public ResultVo delete(@PathVariable("menuId") Long menuId){
        if(sysMenuService.removeById(menuId)){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    // 查询菜单列表
    @GetMapping("/list")
    public ResultVo list(){
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> list = sysMenuService.list(query);
        //树形数据组装
        List<SysMenu> menuList = MakeMenuTree.makeTree(list, 0L);

        return ResultUtils.success("查询成功",menuList);
    }

    //查询上级树
    @GetMapping("/parent")
    public ResultVo getParent(){
        List<SysMenu> parent = sysMenuService.getParent();
        return ResultUtils.success("查询成功",parent);
    }

}