// @ts-nocheck
import http from "@/http/index.ts";
import { CategoryParm, CategoryType } from "./CategoryModel";
//新增
export const addApi = (parm:CategoryType)=>{
    return http.post("/api/category",parm)
}
//列表
export const getListApi = (parm:CategoryParm)=>{
    return http.get("/api/category/list",parm)
}
//编辑
export const editApi = (parm:CategoryType)=>{
    return http.put("/api/category",parm)
}
//删除
export const deleteApi = (categoryId:string)=>{
    return http.delete(`/api/category/${categoryId}`)
}
//列表
export const getSelectApi = ()=>{
    return http.get("/api/category/getSelect")
}
