//定义列表的参数类型
export type CategoryParm ={
    currentPage:number,
    pageSize:number,
    categoryName:string
}
//分类数据类型
export type CategoryType = {
    type:string, //区分新增和编辑
    categoryId:string,
    categoryName:string,
    orderNum:string
}
