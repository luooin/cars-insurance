<template>
    <el-breadcrumb class="bred" separator="/">
        <el-breadcrumb-item v-for="item in tabs">{{ item.meta.title }}</el-breadcrumb-item>
    </el-breadcrumb>
</template>

<script setup lang="ts">
import {ref, Ref, watch,RouteLocationMatched} from 'vue'
import { useRoute } from "vue-router";
// 获取当前路由
const route = useRoute()

// 定义面包屑导航数据
const tabs: Ref<RouteLocationMatched[]> = ref([]);
// 监听路由变化的操作
const getBredcrumb = ()=> {
    // 获取菜单的数据 并且存在title
    let matched = route.matched.filter(item => item.meta && item.meta.title)
    //  判断是否是首页,不是 构造一个
    const first = matched[0]
    if (first.path !== "/dashboard") {
        matched = [{ path: "/dashboard", meta: { title: "首页" } } as any].concat(matched);
    }
    tabs.value =  matched;
}

// 监听路由变化
watch(
    ()=> route.path,
    ()=>  getBredcrumb()
)
</script>

<style lang="scss" scoped>
//修改字体颜色
:deep(.el-breadcrumb__inner) {
  color: #fff !important;
}

:deep(.el-breadcrumb__inner a) {
  color: #fff !important;
}
.bred {
  margin-left: 20px;
}
</style>
