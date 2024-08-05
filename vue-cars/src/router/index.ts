import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/index.vue'
export const constantRoutes: Array<RouteRecordRaw> = [
    {
        path: "/login",
        component: () => import('@/views/login/index.vue'),
        name: "login"
    },
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [
            {
                path: '/dashboard',
                component: () => import('@/layout/dashboard/index.vue'),
                name: 'dashboard',
                meta: {
                    title: '首页',
                    icon: '#icondashboard'
                }
            }
        ]
    }
]
const routes: Array<RouteRecordRaw> = [
    {
        path: "/login",
        component: () => import('@/views/login/Index.vue'),
        name: "login"
    },
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [
            {
                path: '/dashboard',
                component: () => import('@/layout/dashboard/Index.vue'),
                name: 'dashboard',
                meta: {
                    title: '首页',
                    icon: '#icondashboard'
                }
            }
        ]
    },
    {
        path: "/system",
        component: Layout,
        name: "system",
        meta: {
            title: "系统管理",
            icon: "el-icon-menu",
            roles: ["sys:manage"]
        },
        children: [
            {
                path: "/userList",
                component: () => import('@/views/system/user/UserList.vue'),
                name: "userList",
                meta: {
                    title: "用户管理",
                    icon: "el-icon-s-custom",
                    roles: ["sys:user"]
                },
            },
            {
                path: "/roleList",
                component: () => import('@/views/system/role/RoleList.vue'),
                name: "roleList",
                meta: {
                    title: "角色管理",
                    icon: "el-icon-s-tools",
                    roles: ["sys:role"]
                },
            },
            {
                path: "/menuList",
                component: () => import('@/views/system/menu/MenuList.vue'),
                name: "menuList",
                meta: {
                    title: "权限管理",
                    icon: "el-icon-document",
                    roles: ["sys:menu"]
                },
            },
        ]
    },
    {
        path: "/goodsRoot",
        component: Layout,
        name: "goodsRoot",
        meta: {
            title: "物资管理",
            icon: "Setting",
            roles: ["sys:goodsRoot"],
        },
        children: [
            {
                path: "/goodsType",
                component: () => import('@/views/goods/goodsType.vue'),
                name: "goodsType",
                meta: {
                    title: "物资类型",
                    icon: "UserFilled",
                    roles: ["sys:goodsType"],
                },
            },
            {
                path: "/goodsList",
                component: () => import('@/views/goods/GoodsList.vue'),
                name: "goodsList",
                meta: {
                    title: "物资信息",
                    icon: "Wallet",
                    roles: ["sys:goodsList"],
                },
            }
        ]
    },
    {
        path: "/sendRoot",
        component: Layout,
        name: "sendRoot",
        meta: {
            title: "发放管理",
            icon: "Setting",
            roles: ["sys:sendRoot"],
        },
        children: [
            {
                path: "/sendInfo",
                component: () => import('@/views/send/SendInfo.vue'),
                name: "sendInfo",
                meta: {
                    title: "物资发放",
                    icon: "UserFilled",
                    roles: ["sys:sendInfo"],
                },
            },
            {
                path: "/sendDetail",
                component: () => import('@/views/send_detail/SendDetail.vue'),
                name: "sendDetail",
                meta: {
                    title: "发放记录",
                    icon: "Wallet",
                    roles: ["sys:sendDetail"],
                },
            }
        ]
    },
    {
        path: "/intoRoot",
        component: Layout,
        name: "intoRoot",
        meta: {
            title: "物资入库",
            icon: "Wallet",
            roles: ["sys:intoRoot"],
        },
        children: [
            {
                path: "/intoInfo",
                component: () => import('@/views/into/IntoInfo.vue'),
                name: "intoInfo",
                meta: {
                    title: "物资入库",
                    icon: "UserFilled",
                    roles: ["sys:intoInfo"],
                },
            },
            {
                path: "/intoDetail",
                component: () => import('@/views/into_detail/IntoDetail.vue'),
                name: "intoDetail",
                meta: {
                    title: "入库记录",
                    icon: "Wallet",
                    roles: ["sys:intoDetail"],
                },
            },
        ],
    },

]

const router = createRouter({
    history: createWebHistory(),
    routes:constantRoutes
})

export default router
