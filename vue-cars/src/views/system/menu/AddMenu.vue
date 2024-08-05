<template>
    <SysDialog
            :title="dialog.title"
            :width="dialog.width"
            :height="dialog.height"
            :visible="dialog.visible"
            @onClose="onClose"
            @onConfirm="commit"
    >
        <template v-slot:content>
            <el-form
                    :model="addModel"
                    ref="addFormRef"
                    :rules="rules"
                    label-width="80px"
                    size="default"
            >
                <el-form-item label="菜单类型" prop="type">
                    <el-radio-group v-model="addModel.type">
                        <el-radio :label="'0'">目录</el-radio>
                        <el-radio :label="'1'">菜单</el-radio>
                        <el-radio :label="'2'">按钮</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="parentName" label="上级菜单">
                            <el-input
                                    @click="selectParent"
                                    readonly
                                    v-model="addModel.parentName"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="title" label="菜单名称">
                            <el-input v-model="addModel.title"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="addModel.type != '2'">
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="icon" label="菜单图标">
                            <el-input v-model="addModel.icon"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="name" label="路由名称">
                            <el-input v-model="addModel.name"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="orderNum" label="菜单序号">
                            <el-input v-model="addModel.orderNum"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="code" label="权限字段">
                            <el-input v-model="addModel.code"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col v-if="addModel.type != '2'" :span="12" :offset="0">
                        <el-form-item prop="path" label="路由地址">
                            <el-input v-model="addModel.path"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col v-if="addModel.type == '1'" :span="12" :offset="0">
                        <el-form-item prop="url" label="组件路径">
                            <el-input v-model="addModel.url"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </template>
    </SysDialog>
  <!-- 上级菜单 -->
    <ParentMenu ref="parentRef" @selectParentNode="selectParentNode"></ParentMenu>
</template>

<script setup lang="ts">
import ParentMenu from "./ParentMenu.vue";
import { MenuType, SelectNode } from "@/api/menu/MenuModel.ts";
import SysDialog from "@/components/SysDialog.vue";
import useDialog from "@/hooks/useDialog";
import {ElMessage, ElNotification, FormInstance} from "element-plus";
import { nextTick, reactive, ref } from "vue";
import { addApi, editApi } from "@/api/menu/index.ts";
import { EditType, Title } from "@/type/BaseEnum";
const parentRef = ref<{ showParent: () => void }>();
//表单的ref属性
const addFormRef = ref<FormInstance>();
//弹框属性
const { dialog, onClose, onConfirm, onShow } = useDialog();
//弹框显示
const show = (type: string, row?: MenuType) => {
    //设置弹框属性
    dialog.height = 260;
    type == EditType.ADD
        ? (dialog.title = Title.ADD)
        : (dialog.title = Title.EDIT);
    //设置编辑的数据
    if (row) {
        nextTick(() => {
            Object.assign(addModel, row);
        });
    }
    onShow();
    addFormRef.value?.resetFields();
    addModel.editType = type;
};
//暴露给外部组件使用
defineExpose({
    show,
});
//菜单数据
const addModel = reactive<MenuType>({
    editType: "",
    menuId: "",
    parentId: "",
    title: "",
    code: "",
    name: "",
    path: "",
    url: "",
    type: "",
    icon: "",
    parentName: "",
    orderNum: "",
});
//表单验证规则
const rules = reactive({
    type: [
        {
            required: true,
            trigger: "blur",
            message: "请选择菜单类型",
        },
    ],
    parentId: [
        {
            required: true,
            trigger: "change",
            message: "请选择上级菜单",
        },
    ],
    title: [
        {
            required: true,
            trigger: "change",
            message: "请填写菜单名称",
        },
    ],
    code: [
        {
            required: true,
            trigger: "change",
            message: "请填写权限字段",
        },
    ],
    name: [
        {
            required: true,
            trigger: "change",
            message: "请填写路由名称",
        },
    ],
    path: [
        {
            required: true,
            trigger: "change",
            message: "请填写路由地址",
        },
    ],
    url: [
        {
            required: true,
            trigger: "change",
            message: "请填写组件路径",
        },
    ],
});
//上级菜单点击事件
const selectParent = () => {
    parentRef.value?.showParent();
};
const selectParentNode = (node: SelectNode) => {
    addModel.parentId = node.parentId;
    addModel.parentName = node.parentName;
};
//注册事件
const emints = defineEmits(['refresh'])
//表单提交
const commit = () => {
    addFormRef.value?.validate(async (valid) => {
        if (valid) {
            let res = null;
            if (addModel.editType == EditType.ADD) {
                res = await addApi(addModel);
            } else {
                res = await editApi(addModel);
            }
            if (res && res.code == 200) {
                emints('refresh')
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                onClose();
            }
        }
    });
};
</script>

<style scoped></style>
