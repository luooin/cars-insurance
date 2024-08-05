// @ts-ignore
import http from "@/http/index.ts"
import {AddRoleModel, ListParm,AssignParm,SaveAssignParm} from "./RoleModel.ts";
//新增角色
export const addApi = (param:AddRoleModel)=> {
    return http.post("/api/role",param)
}

// 列表
export const getListApi = (param: ListParm)=> {
    return    http.get("/api/role/list", param)

}

//编辑
export const editApi = (parm: AddRoleModel) => {
    return http.put("/api/role", parm)
}

//删除
export const deleteApi = (roleId:string)=>{
    return http.delete(`/api/role/${roleId}`)
}

//查询权限树数据
export const getAssignTreeApi =(parm:AssignParm)=>{
    return http.get('/api/role/getAssignTree',parm)
}

//保存权限
export const assignSaveApi = (parm:SaveAssignParm)=>{
    return http.post("/api/role/assignSave",parm)
}
