//列表查询参数类型
export type NoticeParm = {
    currentPage: number,
    pageSize: number,
    title: string,
    total: number
}
//定义公告数据类型
export type NoticeType = {
    noticeId:string,
    title:string,
    contentText:string,
    type:string
}
