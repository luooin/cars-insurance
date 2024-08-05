<template>
    <SysDialog
            :title="dialog.title"
            :height="dialog.height"
            :width="dialog.width"
            :visible="dialog.visible"
            @onClose="onClose"
            @onConfirm="commit"
    >
        <template v-slot:content>
            <el-tree
                    ref="assignTree"
                    :data="assignTreeData.list"
                    node-key="menuId"
                    :props="defaultProps"
                    empty-text="暂无数据"
                    :show-checkbox="true"
                    default-expand-all
                    :highlight-current="true"
                    :default-checked-keys="assignTreeData.assignTreeChecked"
            ></el-tree>
        </template>
    </SysDialog>
</template>

<script setup lang="ts">
import SysDialog from "@/components/SysDialog.vue";
import useDialog from "@/hooks/useDialog";
import useAssign from "@/composables/role/useAssign";
import { loginStore } from "@/store/login";
import { SaveAssignParm } from "@/api/role/RoleModel";
import { assignSaveApi } from "@/api/role/index";
import { reactive, ref } from "vue";
import {ElMessage, ElNotification, ElTree} from "element-plus";
//树的ref属性
const assignTree = ref<InstanceType<typeof ElTree>>();
const store = loginStore();
//树组件属性
const { defaultProps, getAssignTree, assignTreeData } = useAssign();
//弹框属性
const { dialog, onClose, onConfirm, onShow } = useDialog();
//显示弹框
const show = (roleId: string, name: string) => {
    saveParm.roleId = roleId;
    //清空数据
    saveParm.list = []
    assignTreeData.list = []
    assignTreeData.assignTreeChecked = []
    //获取树的数据
    getAssignTree({
        roleId: roleId,
        userId: store.getUserId,
    });
    dialog.title = "为【" + name + "】分配权限";
    dialog.width = 300;
    dialog.height = 450;
    onShow();
};
//暴露出去
defineExpose({
    show,
});
//提交的数据
const saveParm = reactive<SaveAssignParm>({
    list: [],
    roleId: "",
});
//提交权限保存
const commit = async() => {
    // console.log(assignTree.value)
    let checkedIds = assignTree.value?.getCheckedKeys(false) as string[];
    let hlfIds = assignTree.value?.getHalfCheckedKeys() as string[];
    let ids = checkedIds?.concat(hlfIds);
    saveParm.list = ids;
    //判断是否已经勾选权限
    if (saveParm.list.length == 0) {
        ElMessage.warning("请勾选权限");
        return;
    }
    console.log(saveParm);
    let res = await assignSaveApi(saveParm)
    if(res && res.code == 200){
        ElNotification({
            title: "Success",
            message: res.msg,
            type: "success"
        })
        onClose()
    }
};
</script>

<style scoped></style>
