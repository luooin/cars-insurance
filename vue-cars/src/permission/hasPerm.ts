// @ts-ignore
import { loginStore } from '@/store/login/index'
export default function hasPerm(value:any) {
    //用户的store
    const store = loginStore()
    //获取用户的按钮权限字段
    const permissions = store.codeList;
    //判断传递进来的按钮权限是否正确
    if (value && value instanceof Array && value.length > 0) {
        const permissionRoles = value;
        //查看按钮绑定的权限是否在用户的权限字段里面
        const hasPermission = permissions.some((role) => {
            return permissionRoles.includes(role)
        })
        return hasPermission
    } else {
        throw new Error('need roles! Like v-permission="[\'add\',\'edit\']"')
    }
}
