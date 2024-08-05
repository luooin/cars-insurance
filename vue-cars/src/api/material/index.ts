// @ts-ignore
import http from "@/http/index.ts"
import {MaterialType,MaterialParm} from "./MaterialModel.ts";
//图片上传
export const uploadImageApi = (parm: object) => {
    return http.upload("/api/upload/uploadImage", parm)
}


//新增
export const addApi = (parm: MaterialType) => {
    return http.post("/api/carsInfo",parm)
}

//列表
export const getListApi = (parm:MaterialParm)=>{
    return http.get("/api/carsInfo/list",parm)
}
//编辑
export const editApi = (parm:MaterialType)=>{
    return http.put("/api/carsInfo",parm)
}
//删除
export const deleteApi = (infoId:string)=>{
    return http.delete(`/api/carsInfo/${infoId}`)
}
