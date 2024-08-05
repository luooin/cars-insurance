<template>
    <menu-logo :isCollapsed="isCollapse"></menu-logo>
    <el-menu
            :collapse="isCollapse"
            :default-active="activeIndex"
            class="el-menu-vertical-demo"
            unique-opened
            background-color="#304156"
            router
    >
        <menu-item :menuList="menuList"></menu-item>
    </el-menu>
</template>

<script setup>
import MenuLogo from "@/layout/menu/MenuLogo.vue";
import MenuItem from "@/layout/menu/MenuItem.vue";
import { menuStore } from "@/store/menu"
import { reactive, computed } from "vue"
import { useRoute } from 'vue-router';
import { collapseStore } from "@/store/collapse/index";


const route = useRoute();
const mstore = menuStore()


const colstore = collapseStore();
const isCollapse = computed(() => {
  return colstore.getCollapse;
});



//获取激活的菜单
const activeIndex = computed(()=>{
  const { path } = route;
  return path;
})

const menuList = computed(()=> {
  return mstore.getMenuList
})
// 菜单文字数据
// let menuList = reactive([
//   {
//     path: "/dashboard",
//     component: "Layout",
//     name: "dashboard",
//     meta: {
//       title: "首页",
//       icon: "HomeFilled",
//       roles: ["sys:dashboard"],
//     },
//   },
//   {
//     path: "/system",
//     component: "Layout",
//     name: "system",
//     meta: {
//       title: "系统管理",
//       icon: "Setting",
//       roles: ["sys:manage"],
//     },
//     children: [
//       {
//         path: "/userList",
//         component: "/system/user/UserList",
//         name: "userList",
//         meta: {
//           title: "用户管理",
//           icon: "UserFilled",
//           roles: ["sys:user"],
//         },
//       },
//       {
//         path: "/roleList",
//         component: "/system/role/RoleList",
//         name: "roleList",
//         meta: {
//           title: "角色管理",
//           icon: "Wallet",
//           roles: ["sys:role"],
//         },
//       },
//       {
//         path: "/menuList",
//         component: "/system/menu/MenuList",
//         name: "menuList",
//         meta: {
//           title: "菜单管理",
//           icon: "Menu",
//           roles: ["sys:menu"],
//         },
//       },
//     ],
//   },
//   {
//     path: "/goodsRoot",
//     component: "Layout",
//     name: "goodsRoot",
//     meta: {
//       title: "物资管理",
//       icon: "Setting",
//       roles: ["sys:goodsRoot"],
//     },
//     children: [
//       {
//         path: "/goodsType",
//         component: "/goods/GoodsType",
//         name: "goodsType",
//         meta: {
//           title: "物资类型",
//           icon: "UserFilled",
//           roles: ["sys:goodsType"],
//         },
//       },
//       {
//         path: "/goodsList",
//         component: "/goods/GoodsList",
//         name: "goodsList",
//         meta: {
//           title: "物资信息",
//           icon: "Wallet",
//           roles: ["sys:goodsList"],
//         },
//       },
//
//     ]
//   }
//   ,
//   {
//     path: "/sendRoot",
//     component: "Layout",
//     name: "sendRoot",
//     meta: {
//       title: "物资发放",
//       icon: "Wallet",
//       roles: ["sys:sendRoot"],
//     },
//     children: [
//       {
//         path: "/sendInfo",
//         component: "/send/SendInfo",
//         name: "sendInfo",
//         meta: {
//           title: "物资发放",
//           icon: "UserFilled",
//           roles: ["sys:sendInfo"],
//         },
//       },
//       {
//         path: "/sendDetail",
//         component: "/send_detail/SendDetail",
//         name: "sendDetail",
//         meta: {
//           title: "发放记录",
//           icon: "Wallet",
//           roles: ["sys:sendDetail"],
//         },
//       },
//     ],
//   },
//   {
//     path: "/intoRoot",
//     component: "Layout",
//     name: "intoRoot",
//     meta: {
//       title: "物资入库",
//       icon: "Wallet",
//       roles: ["sys:intoRoot"],
//     },
//     children: [
//       {
//         path: "/intoInfo",
//         component: "/into/IntoInfo",
//         name: "sendInfo",
//         meta: {
//           title: "物资入库",
//           icon: "UserFilled",
//           roles: ["sys:intoInfo"],
//         },
//       },
//       {
//         path: "/intoDetail",
//         component: "/into_detail/IntoDetail",
//         name: "intoDetail",
//         meta: {
//           title: "入库记录",
//           icon: "Wallet",
//           roles: ["sys:intoDetail"],
//         },
//       },
//     ],
//   }
// ]);
</script>

<style lang="scss" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 230px;
  min-height: 400px;
}
.el-menu {
  border-right: none;
}

:deep(.el-sub-menu .el-sub-menu__title) {
  color: #f4f4f5 !important;
}

:deep(.el-menu .el-menu-item) {
  color: #bfcbd9;
}
/* 菜单点中文字的颜色 */

:deep(.el-menu-item.is-active) {
  color: #409eff !important;
}
/* 当前打开菜单的所有子菜单颜色 */

:deep(.is-opened .el-menu-item) {
  background-color: #1f2d3d !important;
}
/* 鼠标移动菜单的颜色 */

:deep(.el-menu-item:hover) {
  background-color: #001528 !important;
}
</style>
