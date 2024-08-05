// @ts-nocheck
import { NoticeType } from "@/api/notice/NoticeModel"
import { EditType } from "@/type/BaseEnum"
import { ref } from "vue"
import useInstance from "@/hooks/useInstance"
import { deleteApi } from "@/api/notice/index.ts"
import {ElMessage, ElNotification} from "element-plus"
import { FuncList } from "@/type/BaseType"
export default function useNotice(getList:FuncList){
    const {global} = useInstance()
    //弹框组件属性
    const addRef = ref<{show:(type:string,row?:NoticeType)=>void}>()
    //新增
    const addBtn = ()=>{
        addRef.value?.show(EditType.ADD)
    }
    //编辑
    const editBtn = (row:NoticeType)=>{
        addRef.value?.show(EditType.EDIT,row)
    }
    //删除
    const deleteBtn = async(row:NoticeType)=>{
        const confirm = await global.$myconfirm('确定删除该数据吗?')
        if(confirm){
            let res = await deleteApi(row.noticeId)
            if(res && res.code == 200){
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                getList()
            }
        }
    }
    return{
        addBtn,
        deleteBtn,
        editBtn,
        addRef
    }
}
