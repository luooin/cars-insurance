// @ts-ignore
import http from "@/http/index.ts";
import { SelectMaterial,MaterialIntoParm, Apply } from "./IntoModel";
//物资列表
export const getSelectListApi = (parm:SelectMaterial)=>{
    return http.get("/api/materialInfo/selectList",parm)
}
//物资入库
export const intoSaveApi = (parm:any)=>{
    return http.post("/api/into/intoSave",parm)
}
//物资记录列表
export const getIntoListApi = (parm:MaterialIntoParm)=>{
    return http.get("/api/into/getIntoList",parm)
}
//入库审核
export const intoApplyApi = (parm:Apply)=>{
    return http.post("/api/into/apply",parm)
}
