import { defineStore } from "pinia";
// @ts-ignore
import { getMenuListApi } from '@/api/login/index.ts'
import { RouteRecordRaw } from "vue-router";
import Layout from '@/layout/index.vue'
import Center from '@/layout/center/center.vue'
const modules = import.meta.glob('../../views/**/*.vue')
//定义store
export const menuStore = defineStore('menuStore', {
    state: () => {
        return {
            menuList: [
                {
                    path: "/dashboard",
                    component: "Layout",
                    name: "dashboard",
                    meta: {
                        title: "首页",
                        icon: "HomeFilled",
                        roles: ["sys:dashboard"],
                    },
                    children: []
                } as any
            ]
        }
    },
    getters: {
        getMenuList(state) {
            return state.menuList
        }
    },
    actions: {
        getMenuRouter(router: any,userId:string) {
            return new Promise((resolve, reject) => {
                getMenuListApi(userId).then((res) => {
                    let accessRoute;
                    if (res && res.code == 200) {
                        //动态生成路由
                        accessRoute = generateRoutes(res.data, router)
                        this.menuList = this.menuList.concat(accessRoute)
                    }
                    resolve(this.menuList)
                }).catch((error)=>{
                    reject(error)
                })
            })
        }
    }
})
export function generateRoutes(routes: RouteRecordRaw[], router: any) {
    const res: Array<RouteRecordRaw> = [];
    routes.forEach((route: any) => {
        const tmp = { ...route }
        const component = tmp.component;
        if (route.component) {
            if (component == 'Layout') {
                tmp.component = Layout;
            } else {
                tmp.component = modules[`../../views${component}.vue`]
            }
        }
        if (tmp.children && tmp.children.length > 0) {
            //如果有下级，并且component != 'Layout'
            if (route.component != 'Layout') {
                tmp.component = Center
            }
            //递归
            tmp.children = generateRoutes(tmp.children, router)
        }
        router.addRoute(tmp)
        res.push(tmp)
    })
    return res;
}
