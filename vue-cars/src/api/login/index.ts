// @ts-nocheck
import http from "@/http/index.ts";
import { LoginParm } from "./LoginModel";
//获取验证码
export const getImageApi = ()=>{
    return http.post("/api/login/image")
}
//登录
export const loginApi = (parm:LoginParm)=>{
    return http.post("/api/login/login",parm)
}

//获取菜单数据
export const getMenuListApi = (userId:string)=>{
    return http.get("/api/login/getMenuList",{userId:userId})
}


//获取用户信息
export const getInfoApi = (userId:string)=>{
    return http.get("/api/login/getInfo",{userId:userId})
}


