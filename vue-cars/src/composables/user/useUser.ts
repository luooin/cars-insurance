import { ref } from "vue"
import {EditType} from "../../type/BaseEnum.ts";
import {AddUserModel,UpdatePass} from "../../api/user/UserModel.ts";
import {deleteApi,upPasswordApi} from "../../api/user";
// @ts-ignore
import {ElNotification} from "element-plus";
import {FuncList} from "../../type/BaseType.ts";
import useInstance from "../../hooks/useInstance.ts";
export default function useUser(getList:FuncList) {
    const {global} = useInstance()
    //新增弹框的ref属性
    const addRef = ref<{ show: (type:EditType,row?: AddUserModel) => void }>()
    //新增
    const addBtn = (row:AddUserModel) => {
        addRef.value?.show(EditType.ADD)
    }
    //编辑
    const editBtn = (row: AddUserModel) => {
        addRef.value?.show(EditType.EDIT,row)
    }
    //删除
    const deleteBtn =async (row:AddUserModel) => {
         let res =  await  deleteApi(row.userId)
        if (res && res.code == 200) {
            ElNotification({
                title: "Success",
                message: res.msg,
                type: "success"
            })
            // 刷新
            getList()
        }
    }

    // 重置密码
    const resetPassword =async (row:AddUserModel)=> {
        let confirm = await global.$myconfirm('确定重置密码吗?【重置后密码为666666】')
        if (confirm) {
            let parm = {
                userId: row.userId,
                password: '6666666'
            }
            let res = await upPasswordApi(parm)
            if (res && res.code == 200) {
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                // 刷新
                getList()
            }
        }
    }
    return {
        addBtn,
        deleteBtn,
        editBtn,
        addRef,
        resetPassword
    }
}
