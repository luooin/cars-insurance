<template>
    <el-main>
        <!-- 搜索栏 -->
        <el-form :model="listParm" :inline="true" size="default">
            <el-form-item>
                <el-input
                        v-model="listParm.categoryName"
                        placeholder="请输入分类名称"
                ></el-input>
            </el-form-item>
            <el-form-item>
                <el-button :icon="Search" @click="searchBtn">搜索</el-button>
                <el-button :icon="Close" type="danger" plain @click="resetBtn">重置</el-button>
                <el-button :icon="Plus" type="primary" @click="addBtn"
                >新增</el-button
                >
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :height="tableHeight" :data="tableList.list" border stripe>
            <el-table-column prop="categoryName" label="分类名称"></el-table-column>
            <el-table-column prop="orderNum" label="序号"></el-table-column>
            <el-table-column  label="操作" width="200" align="center">
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" size="default" @click="editBtn(scope.row)">编辑</el-button>
                    <el-button type="danger" :icon="Delete" size="default" @click="deleteBtn(scope.row)">删除</el-button>
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

        <!-- 新增弹框 -->
        <AddCatgory ref="addRef" @reFresh="getList"></AddCatgory>
    </el-main>
</template>

<script setup lang="ts">
import AddCatgory from "@/views/goods/AddCategory.vue";
import { Search, Edit, Plus, Close, Delete } from "@element-plus/icons-vue";
import useTable from "@/composables/category/useTable";
import useCategory from "@/composables/category/useCategory";

//表格
const { listParm, getList, resetBtn, searchBtn,tableList,sizeChange,currentChange,tableHeight } = useTable();
//新增
const { addBtn, editBtn, deleteBtn,addRef } = useCategory(getList);
</script>

<style scoped></style>
