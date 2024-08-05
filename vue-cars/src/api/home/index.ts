//@ts-nocheck
import http from "@/http/index.ts";
//统计总条数
export const getTotalApi = ()=>{
    return http.get("/api/home/getTotal")
}

//车辆分类
export const getTotalCategoryApi = ()=>{
    return http.get("/api/home/getTotalCategory")
}


//折扣系数rank
export const getDiscountRankApi = ()=>{
    return http.get("/api/home/getDiscountRank")
}

// 保险价格
export const getPriceCountApi = ()=> {
    return http.get("/api/home/getPriceRank")
}


//公告列表
export const getNoticeListApi = ()=>{
    return http.get("/api/home/getNoticeList")
}
