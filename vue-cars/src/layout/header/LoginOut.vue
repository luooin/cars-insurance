<template>
    <el-dropdown>
    <span class="el-dropdown-link">
      <img class="userimg" src="@/assets/user.jpg" />
    </span>
        <template #dropdown>
            <el-dropdown-menu>
                <el-dropdown-item @click="updateBtn">修改密码</el-dropdown-item>
                <el-dropdown-item @click="logoutBtn">退出登录</el-dropdown-item>
            </el-dropdown-menu>
        </template>
    </el-dropdown>
  <!-- 弹框组件 -->
    <sys-dialog
            :title="dialog.title"
            :height="dialog.height"
            :width="dialog.width"
            :visible="dialog.visible"
            @onClose="onClose"
            @onConfirm="commit"
    >
        <template v-slot:content>
            <el-form
                    :model="parm"
                    ref="upRef"
                    :rules="rules"
                    label-width="80px"
                    size="default"
            >
                <el-form-item prop="oldPassword" label="原密码">
                    <el-input v-model="parm.oldPassword"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="新密码">
                    <el-input v-model="parm.password"></el-input>
                </el-form-item>
            </el-form>
        </template>
    </sys-dialog>
</template>

<script setup lang="ts">
import SysDialog from "@/components/SysDialog.vue";
import useDialog from "@/hooks/useDialog";
import useInstance from "@/hooks/useInstance";
import { loginStore } from "@/store/login";
import { FormInstance } from "element-plus";
import { reactive, ref } from "vue";
import { updatePasswordApi } from "@/api/user/index";
//表单ref属性
const upRef = ref<FormInstance>();
//弹框属性
const { dialog, onClose, onConfirm, onShow } = useDialog();
const store = loginStore();
const { global } = useInstance();
const logoutBtn = async () => {
    const confirm = await global.$myconfirm("确定退出登录吗?");
    if (confirm) {
        //清空token和用户id
        store.setToken("");
        store.setUserId("");
        localStorage.clear();
        //跳转到登录
        window.location.href = "/login";
    }
};
//修改密码按钮
const updateBtn = () => {
    //设置标题
    dialog.title = "修改密码";
    onShow();
};
//密码修改数据
const parm = reactive({
    userId: store.getUserId,
    password: "",
    oldPassword: "",
});
//表单验证规则
const rules = reactive({
    oldPassword: [
        {
            required: true,
            trigger: "blur",
            message: "请录入原密码",
        },
    ],
    password: [
        {
            required: true,
            trigger: "blur",
            message: "请录入新密码",
        },
    ],
});
//提交修改
const commit = () => {
    upRef.value?.validate(async (valid) => {
        if (valid) {
            let res = await updatePasswordApi(parm);
            if (res && res.code == 200) {
                //清空token和用户id
                store.setToken("");
                store.setUserId("");
                localStorage.clear();
                onClose();
                //跳转到登录
                window.location.href = "/login";
            }
        }
    });
};
</script>

<style scoped>
.userimg {
    height: 28px;
    width: 28px;
    border-radius: 50%;
}
</style>
