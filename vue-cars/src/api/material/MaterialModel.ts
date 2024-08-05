//定义列表参数类型
export type MaterialParm = {
    currentPage:number,
    pageSize:number,
    carName:string,
    total:number
}
//定义物资信息数据类型
export type MaterialType = {
    carId:string,
    categoryId:string,
    carName:string,
    image:string,
    purchasePrice:Number,
    discountFactor:string,
    initRegisterDate:string,
    insuranceDate:string
}
