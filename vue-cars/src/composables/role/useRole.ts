//@ts-nocheck
import { AddRoleModel } from "@/api/role/RoleModel"
import { EditType } from "@/type/BaseEnum"
import { ref } from "vue"
import { deleteApi } from "@/api/role/index.ts"
import { ElMessage } from "element-plus";
import { FuncList } from "@/type/BaseType";
import useInstance from "@/hooks/useInstance";
export default function useRole(getList: FuncList) {
    //分配权限弹框ref属性
    const assignRoleRef = ref<{ show: (roleId: string, name: string) => void }>()
    const { global } = useInstance()
    //type: 0 新增  1 编辑  ； row:要编辑的数据
    const addRef = ref<{ show: (type: string, row?: AddRoleModel) => void }>()
    //新增
    const addBtn = () => {
        console.log(addRef.value)
        //父组件调用子组件的方法
        addRef.value?.show(EditType.ADD)
    }
    //编辑
    const editBtn = (row: AddRoleModel) => {
        console.log(row)
        //显示弹框：父组件调用子组件
        addRef.value?.show(EditType.EDIT, row)
    }
    //删除
    const deleteBtn = async (row: AddRoleModel) => {
        //信息确定
        let confirm = await global.$myconfirm('确定删除该数据吗?')
        if (confirm) {
            let res = await deleteApi(row.roleId)
            if (res && res.code == 200) {
                //信息提示
                ElMessage.success(res.msg);
                //刷新列表
                getList()
            }
        }
    }
    //分配权限按钮
    const assignBtn = (row: AddRoleModel) => {
        assignRoleRef.value?.show(row.roleId,row.roleName)
    }
    return {
        addBtn,
        editBtn,
        deleteBtn,
        addRef,
        assignBtn,
        assignRoleRef
    }
}
