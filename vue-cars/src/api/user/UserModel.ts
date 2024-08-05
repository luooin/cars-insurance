//定义角色下拉选择数据类型
export type SelectType = {
    value: string,
    label: string
}
//下拉数据列表
export type SelectTypeList = {
    list: SelectType[]
}
//列表查询参数类型
export type ListParm = {
    phone: string,
    nickName: string,
    currentPage: number,
    pageSize: number,
    total: number
}
//用户数据类型
export type AddUserModel = {
    type: string, // 主要区分新增还是编辑
    userId: string,
    roleId: string,
    username: string,
    password: string,
    phone: string,
    email: string,
    sex: string,
    enabled:boolean
    nickName: string
}
//定义角色类型
export type RoleType = {
    value: string,
    label: string
}
export type SelectRole = {
    list: RoleType[]
}

export type UpdatePass = {
    userId: string,
    password: string,
}

export type UpParm = {
    userId: string,
    password: string,
    oldPassword: string
}

