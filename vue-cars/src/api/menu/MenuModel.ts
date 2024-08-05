//菜单数据类型
export type MenuType = {
    editType:string,
    menuId:string,
    parentId:string,
    title:string,
    code:string,
    name:string,
    path:string,
    url:string,
    type:string,
    icon:string,
    parentName:string,
    orderNum:string,
    open?:boolean
}


//选中上级菜单数据类型
export type SelectNode = {
    parentId: string,
    parentName: string
}

