package com.luyifan.cars.utils;

import com.luyifan.cars.model.entity.SysMenu;
import com.luyifan.cars.model.vo.role.RouterVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MakeMenuTree {
    // 组装树的一个数据
    public static List<SysMenu> makeTree(List<SysMenu> menuList, Long pid) {
        List<SysMenu> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    SysMenu menu = new SysMenu();
                    BeanUtils.copyProperties(item, menu);
                    //递归查找下级,自己调用自己
                    List<SysMenu> children = makeTree(menuList, item.getMenuId());
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }
    //构造路由数据
    public static List<RouterVO> makeRourer(List<SysMenu> menuList, Long pid){
        //构建存放路由数据的容器
        List<RouterVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item ->{
                    RouterVO router = new RouterVO();
                    router.setName(item.getName());
                    router.setPath(item.getPath());
                    //设置children 递归调用：自己调用自己
                    List<RouterVO> children = makeRourer(menuList, item.getMenuId());
                    router.setChildren(children);
                    if(item.getParentId() == 0L){//如果是上级是0,那么他的component是Layout
                        router.setComponent("Layout");
                        //判断该数据是目录还是菜单
                        if(item.getType().equals("1")){ //如果一级菜单是 菜单类型，单独处理
                            router.setRedirect(item.getPath());
                            //菜单需要设置children
                            List<RouterVO> listChild = new ArrayList<>();
                            RouterVO child  = new RouterVO();
                            child.setName(item.getName());
                            child.setPath(item.getPath());
                            child.setComponent(item.getUrl());
                            child.setMeta(child.new Meta(
                                    item.getTitle(),
                                    item.getIcon(),
                                    item.getCode().split(",")
                            ));
                            listChild.add(child);
                            router.setChildren(listChild);
                            router.setPath(item.getPath() +"parent");
                            router.setName(item.getName()+"parent");
                        }
                    }else{
                        router.setComponent(item.getUrl());
                    }
                    router.setMeta(router.new Meta(
                            item.getTitle(),
                            item.getIcon(),
                            item.getCode().split(",")
                    ));
                    list.add(router);
                });
        return  list;
    }
}