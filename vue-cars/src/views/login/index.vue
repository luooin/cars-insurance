<template>

    <div class="logincontainer">
        <el-form
                style="margin-bottom: 210px"
                class="formstyle"
                :model="loginModel"
                ref="loginRef"
                :rules="rules"
                size="large"
        >
            <el-form-item>
                <span  class="login-title">系统登录</span>
            </el-form-item>
            <el-form-item prop="username">
                <el-input
                        v-model="loginModel.username"
                        placeholder="请输入账号"
                ></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input
                        type="password"
                        v-model="loginModel.password"
                        placeholder="请输入密码"
                ></el-input>
            </el-form-item>
            <el-form-item prop="code">
                <el-row :gutter="20">
                    <el-col :span="16" :offset="0">
                        <el-input
                                v-model="loginModel.code"
                                placeholder="请输入验证码"
                        ></el-input>
                    </el-col>
                    <el-col :span="8" :offset="0">
                        <img :src="imgSrc" class="image" @click="getImage" />
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item>
                <el-row style="width: 100%; margin-top: 20px">
                    <el-col
                            :span="12"
                            :offset="0"
                            style="padding-right: 10px; padding-left: 0px"
                    >
                        <el-button
                                style="width: 100%"
                                size="large"
                                type="primary"
                                @click="onSubmit"
                        >登录</el-button
                        >
                    </el-col>
                    <el-col
                            :span="12"
                            :offset="0"
                            style="padding-right: 0px; padding-left: 10px"
                    >
                        <el-button type="danger" plain style="width: 100%" size="large"
                        >重置</el-button
                        >
                    </el-col>
                </el-row>
            </el-form-item>
        </el-form>
    </div>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import useImage from "@/composables/login/useImage";
import {ElNotification, FormInstance} from "element-plus";
import { loginApi } from "@/api/login/index.ts";
import { loginStore } from "@/store/login/index.ts";
import { useRouter } from "vue-router";
//获取router
const router = useRouter()
//获取store
const store = loginStore()
const loginRef = ref<FormInstance>();
//验证码
const { imgSrc, getImage } = useImage();
//表单绑定数据对象性
const loginModel = reactive({
    username: "",
    password: "",
    code: "",
});
//表单验证规则
const rules = reactive({
    username: [
        {
            required: true,
            message: "请输入账号",
            trigger: "blur",
        },
    ],
    password: [
        {
            required: true,
            message: "请输入密码",
            trigger: "blur",
        },
    ],
    code: [
        {
            required: true,
            message: "请输入验证码",
            trigger: "blur",
        },
    ],
});
//登录提交
const onSubmit = () => {
    loginRef.value?.validate(async (valid) => {
        if (valid) {
            let res = await loginApi(loginModel);
            if (res && res.code == 200) {
                //存储token和userId
                console.log(res)
                store.setToken(res.data.token)
                store.setUserId(res.data.userId)
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                //跳转到首页
                router.push({path:'/'})
            }
        }
    });
};

const isPopUp= ref(false)
onMounted(()=> {
    setTimeout(()=> {
        isPopUp.value = true
    },1000)
})
</script>

<style scoped lang="scss">
.logincontainer {
  top: 0;
  left: 0;
  background-color: #fff;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background: url("src/assets/bgc.jpg") no-repeat ;
  background-size: 100% 100%;
  .formstyle {
    border-radius: 10px;
    background-color: #fff;
    width: 430px;
    height: 320px;
    padding: 20px 35px;
    .login-title {
      width: 100%;
      font-size: 24px;
      font-weight: 600;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .image {
      width: 100%;
      height: 37px;
    }
  }
}
</style>
