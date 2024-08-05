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
                <el-form-item prop="categoryName" label="分类名称">
                    <el-input v-model="addModel.categoryName"></el-input>
                </el-form-item>
                <el-form-item prop="orderNum" label="序号">
                    <el-input v-model="addModel.orderNum"></el-input>
                </el-form-item>
            </el-form>
        </template>
    </SysDialog>
</template>

<script setup lang="ts">
import { CategoryType } from "@/api/category/CategoryModel";
import SysDialog from "@/components/SysDialog.vue";
import useDialog from "@/hooks/useDialog";
import {ElMessage, ElNotification, FormInstance} from "element-plus";
import { nextTick, reactive, ref } from "vue";
import { addApi, editApi } from "@/api/category/index";
import { EditType, Title } from "@/type/BaseEnum";
//表单ref属性
const addFormRef = ref<FormInstance>();
//弹框属性
const { dialog, onClose, onConfirm, onShow } = useDialog();
//显示弹框
const show = (type: string, row?: CategoryType) => {
    //设置弹框属性
    type == EditType.ADD
        ? (dialog.title = Title.ADD)
        : (dialog.title = Title.EDIT);
    //编辑数据回显
    if (row) {
        nextTick(() => {
            Object.assign(addModel, row);
        });
    }
    onShow();
    //清空表单
    addFormRef.value?.resetFields();
    addModel.type = type;
};
//暴露出去
defineExpose({
    show,
});
//表单对象
const addModel = reactive<CategoryType>({
    categoryId: "",
    categoryName: "",
    orderNum: "",
    type: "",
});
//表单验证规则
const rules = reactive({
    categoryName: [
        {
            required: true,
            trigger: "blur",
            message: "请输入分类名称",
        },
    ],
});
//注册事件
const emits = defineEmits(["reFresh"]);
//表单提交
const commit = () => {
    addFormRef.value?.validate(async (valid) => {
        if (valid) {
            let res = null;
            if (addModel.type == EditType.ADD) {
                res = await addApi(addModel);
            } else {
                res = await editApi(addModel);
            }
            if (res && res.code == 200) {
                emits("reFresh");
                //信息提示
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                //刷新表格
                onClose();
            }
        }
    });
};
</script>

<style scoped></style>
