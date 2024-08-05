// @ts-ignore
import http from "@/http/index.ts";
import { AddUserModel, ListParm, UpdatePass,UpParm  } from "./UserModel";
//列表
export const getSelectApi =()=>{
    return http.get("/api/role/getListSelect")
}
//新增
export const addApi = (parm:AddUserModel)=>{
    return http.post("/api/user",parm)
}
//列表
export const getListApi = (parm:ListParm)=>{
    return http.get("/api/user/list",parm)
}
//编辑
export const editApi = (parm:AddUserModel)=>{
    return http.put("/api/user",parm)
}
//删除
export const deleteApi = (userId:string)=>{
    return http.delete(`/api/user/${userId}`)
}
//根据用户id查询角色id
export const getRoleByUserIdApi = (userId:string)=>{
    return http.get("/api/user/getRoleByUserId",{userId:userId})
}

//重置密码
export const upPasswordApi = (parm:UpdatePass)=>{
    return http.post("/api/user/resetPassword",parm)
}

//修改密码
export const updatePasswordApi = (parm:UpParm)=>{
    return http.post("/api/user/upPassword",parm)
}
