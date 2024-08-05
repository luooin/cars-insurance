// @ts-nocheck
import http from "@/http/index.ts";
//计算结果
export const getInsurancePrice = (parm) => {
    return http.post("/api/insurance/cal",parm)
}
//新增
export const addApi = (parm) => {
    return http.post("/api/insurance",parm)
}

//列表
export const getListApi = (parm)=>{
    return http.get("/api/insurance/list",parm)
}
//编辑
export const editApi = (parm)=>{
    return http.put("/api/insurance",parm)
}
//删除
export const deleteApi = (infoId)=>{
    return http.delete(`/api/insurance/${infoId}`)
}

