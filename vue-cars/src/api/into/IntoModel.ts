import { MaterialType } from "../material/MaterialModel"

export type IntoType = {
    
}
//定义选择物资参数类型
export type SelectMaterial = {
    categoryId:string,
    currentPage:number,
    pageSize:number,
    infoName:string,
    total:number
}
//加入的数据类型
export type AddInfoType = {
    list:MaterialType[]
}
//入库记录列表参数类型
export type MaterialIntoParm = {
    currentPage:number,
    pageSize:number,
    province:string,
    name:string,
    total:number,
    status:string,
    type:string
}
//审核参数类型
export type Apply = {
    intoId:string,
    status:string,
}