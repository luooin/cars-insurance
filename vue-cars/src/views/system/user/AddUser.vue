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
                    ref="addRormRef"
                    :rules="rules"
                    label-width="80px"
                    size="default"
            >
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="nickName" label="姓名">
                            <el-input v-model="addModel.nickName"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="sex" label="性别">
                            <el-radio-group v-model="addModel.sex">
                                <el-radio :label="'0'">男</el-radio>
                                <el-radio :label="'1'">女</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="phone" label="电话">
                            <el-input v-model="addModel.phone"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="email" label="邮箱">
                            <el-input v-model="addModel.email"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="roleId" label="角色">
                            <el-select
                                    style="width: 100%"
                                    v-model="addModel.roleId"
                                    placeholder="请选择角色"
                                    size="default"
                            >
                                <el-option
                                        v-for="item in roleData.list"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="enabled" label="状态">
                            <el-radio-group v-model="addModel.enabled">
                                <el-radio :label="true">启用</el-radio>
                                <el-radio :label="false">停用</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="username" label="账户">
                            <el-input v-model="addModel.username"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col v-if="addModel.type == '0'" :span="12" :offset="0">
                        <el-form-item prop="password" label="密码">
                            <el-input v-model="addModel.password"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </template>
    </SysDialog>
</template>

<script setup lang="ts">
import { AddUserModel } from "@/api/user/UserModel";
import SysDialog from "@/components/SysDialog.vue";
import useDialog from "@/hooks/useDialog";
import {ElMessage, ElNotification, FormInstance} from "element-plus";
import { nextTick, reactive, ref } from "vue";
import useSelectRole from "@/composables/user/useSelectRole";
import { addApi, editApi } from "@/api/user/index.ts";
import { EditType, Title } from "@/type/BaseEnum.ts";
import useInstance from "../../../hooks/useInstance.ts";
const { global} = useInstance()
//角色数据
const { roleData, listRole, roleId, getRoleByUserId } = useSelectRole();
//表单的ref属性
const addRormRef = ref<FormInstance>();
//弹框属性
const { dialog, onClose, onConfirm, onShow } = useDialog();
//显示弹框
const show = async (type: string, row?: AddUserModel) => {
    //获取角色数据
    await listRole();
    //弹框属性设置
    dialog.height = 220;
    type == EditType.ADD
        ? (dialog.title = Title.ADD)
        : (dialog.title = Title.EDIT);
    //设置编辑的属性到表单绑定的数据对象
    // 如果是编辑需要设置编辑的数据到表单显示
    onShow();
    if(row) {
        // 设置回显的数据  解决先点编辑,再点新增表单不能清空的问题
        nextTick(async ()=> {
           // 设置回显的数据类
            await getRoleByUserId(row.userId)
            //备拷
            Object.assign(addModel, row);
            addModel.roleId = roleId.value
            addModel.password = ''
        })
    }
    addModel.type = type;
    addRormRef.value?.resetFields();

};
//暴露出去，给父组件使用
defineExpose({
    show,
});
//表单绑定的对象
const addModel = reactive<AddUserModel>({
    type: "", //主要是用了区分 新增还是编辑 0：新增 1：编辑
    userId: "",
    roleId: "",
    username: "",
    password: "",
    phone: "",
    email: "",
    sex: "",
    enabled: true,
    nickName: "",
});
//表单验证规则
const rules = reactive({
    nickName: [
        {
            required: true,
            trigger: "blur",
            message: "请填写姓名",
        },
    ],
    phone: [
        {
            required: true,
            trigger: "change",
            message: "请输入电话",
        },
    ],
    sex: [
        {
            required: true,
            trigger: "change",
            message: "请输选择性别",
        },
    ],
    enabled: [
        {
            required: true,
            trigger: "change",
            message: "请输选择状态",
        },
    ],
    username: [
        {
            required: true,
            trigger: "change",
            message: "请输入账户",
        },
    ],
    password: [
        {
            required: true,
            trigger: "change",
            message: "请输入密码",
        },
    ],
    roleId: [
        {
            required: true,
            trigger: "change",
            message: "请选择角色",
        },
    ],
});
//注册事件
const emits = defineEmits(["reFresh"]);
//表单提交
const commit = () => {
    addRormRef.value?.validate(async (valid) => {
        if (valid) {
            let res = null;
            if (addModel.type == EditType.ADD) {
                //新增
                res = await addApi(addModel);
            } else {
                res = await editApi(addModel);
            }
            if (res && res.code == 200) {
                //信息提示
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                //刷新表格
                emits("reFresh");
                onClose();
            }
        }
    });
};
</script>

<style scoped></style>
