<template>
    <el-main>
        <!-- 搜索栏 -->
        <el-form :model="listParm"  :inline="true" size="default">
            <el-form-item>
                <el-input v-model="listParm.title" placeholder="请输入标题"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button :icon="Search" @click="searchBtn">搜索</el-button>
                <el-button type="danger" plain :icon="Close" @click="resetBtn">重置</el-button>
                <el-button :icon="Plus" type="primary" @click="addBtn">新增</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :height="tableHeight" :data="tableList.list" border stripe>
            <el-table-column prop="title" label="标题"></el-table-column>
            <el-table-column prop="contentText" label="内容"></el-table-column>
            <el-table-column prop="createTime" label="时间"></el-table-column>
            <el-table-column  label="操作" align="center" width="200">
                <template #default="scope">
                    <el-button :icon="Edit" type="primary" size="default" @click="editBtn(scope.row)">编辑</el-button>
                    <el-button :icon="Delete" type="danger" size="default" @click="deleteBtn(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
                @size-change="sizeChange"
                @current-change="currentChange"
                :current-page.sync="listParm.currentPage"
                :page-sizes="[10,20, 40, 80, 100]"
                :page-size="listParm.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="listParm.total" background>
        </el-pagination>

        <!-- 弹框组件 -->
        <AddNotice ref="addRef" @reFresh="getList"></AddNotice>
    </el-main>

</template>

<script setup lang="ts">
import AddNotice from "./AddNotice.vue";
import { Search, Edit, Plus, Close, Delete } from "@element-plus/icons-vue";
import useTable from '@/composables/notice/useTable';
import useNotice from '@/composables/notice/useNotice'
//表格
const {listParm,getList,resetBtn,searchBtn,tableList,sizeChange,currentChange,tableHeight} = useTable()
//新增、编辑
const {addBtn,editBtn,deleteBtn,addRef} = useNotice(getList)
</script>

<style scoped>

</style>
