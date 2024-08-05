// 角色数据类型
export type AddRoleModel={
    type:string,
    roleId:string,
    roleName:string,
    remark:string
}

//列表查询参数类型
export type ListParm = {
    roleName:string,
    currentPage:number,
    pageSize:number,
    total:number
}

export type AssignParm = {
    roleId:string,
    userId:string
}
export type SaveAssignParm = {
    roleId:string,
    list:Array<string>
}
