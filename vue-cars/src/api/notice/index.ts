//@ts-nocheck
import http from "@/http/index.ts";
import { NoticeParm, NoticeType } from "./NoticeModel";
//新增
export const addApi = (parm:NoticeType)=>{
    return http.post("/api/notice",parm)
}
//列表
export const getListApi = (parm:NoticeParm)=>{
    return http.get("/api/notice/list",parm)
}
//编辑
export const editApi = (parm:NoticeType)=>{
    return http.put("/api/notice",parm)
}
//删除
export const deleteApi = (noticeId:string)=>{
    return http.delete(`/api/notice/${noticeId}`)
}
