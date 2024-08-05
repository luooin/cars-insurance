import router from "./router";
// @ts-ignore
import { loginStore } from '@/store/login/index'
import { menuStore } from "./store/menu";
//白名单
const whiteList = ['/login']
//全局守卫路由
router.beforeEach(async(to, from, next) => {
    //用户store
    const ustore = loginStore()
    //菜单store
    const mstore = menuStore()
    //获取token
    const token = ustore.getToken
    //判断token是否存在
    if (token) {
        if (to.path === '/login' || to.path === '/') {
            next({ path: '/' })
        } else {
            //从store里面获取菜单数据
            const hasRoles = ustore.codeList.length > 0
            if (hasRoles) {
                next()
            } else {
                try {
                    //获取用户信息
                    await ustore.getInfo(ustore.getUserId)
                    //从服务器获取菜单数据
                    await mstore.getMenuRouter(router,ustore.getUserId)
                    //等待路由全部挂载
                    next({ ...to, replace: true })
                } catch (error) {
                    localStorage.clear()
                    next({ path: '/login' })
                }
            }
        }
    } else { //token不存在
        //判断是否在白名单中
        if (whiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            next({ path: '/login' })
        }
    }
})
