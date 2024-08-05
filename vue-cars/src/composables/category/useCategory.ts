// @ts-nocheck
import { CategoryType } from "@/api/category/CategoryModel"
import { EditType } from "@/type/BaseEnum"
import { FuncList } from "@/type/BaseType"
import { ref } from "vue"
import useInstance from "@/hooks/useInstance"
import { deleteApi } from "@/api/category/index.ts"
import {ElMessage, ElNotification} from "element-plus"
export default function useCategory(getList: FuncList) {
    const { global } = useInstance()
    //弹框属性
    const addRef = ref<{ show: (type: string, row?: CategoryType) => void }>()
    //新增
    const addBtn = () => {
        addRef.value?.show(EditType.ADD)
    }
    //编辑
    const editBtn = (row: CategoryType) => {
        addRef.value?.show(EditType.EDIT, row)
    }
    //删除
    const deleteBtn = async(row: CategoryType) => {
        const confirm = await global.$myconfirm('确定删除该数据吗?')
        if(confirm){
            let res = await deleteApi(row.categoryId)
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
    return {
        addBtn,
        editBtn,
        deleteBtn,
        addRef
    }
}
