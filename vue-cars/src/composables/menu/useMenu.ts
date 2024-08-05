// @ts-ignore
import { MenuType } from "@/api/menu/MenuModel.ts"
// @ts-ignore
import { EditType } from "@/type/BaseEnum.ts"
import { ref } from "vue"
// @ts-ignore
import useInstance from "@/hooks/useInstance.ts"
// @ts-ignore
import { deleteApi } from "@/api/menu/index.ts"
// @ts-ignore
import {ElMessage, ElNotification} from "element-plus"
// @ts-ignore
import { FuncList } from "@/type/BaseType.ts"


export default function useMenu(getList:FuncList) {
    const { global } = useInstance()
    //新增弹框的ref属性
    const addRef = ref<{ show: (type: string, row?: MenuType) => void }>()
    //新增
    const addBtn = () => {
        addRef.value?.show(EditType.ADD)
        //刷新列表
        getList()
    }
    //编辑
    const editBtn = (row: MenuType) => {

        addRef.value?.show(EditType.EDIT, row)
        //刷新列表
        getList()
    }
    //删除
    const deleteBtn = async(row: MenuType) => {
        const confirm = await global.$myconfirm('确定删除该数据吗?')
        if(confirm){
            let res = await deleteApi(row.menuId)
            if(res && res.code == 200){
                // 信息提示
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                //刷新列表
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
