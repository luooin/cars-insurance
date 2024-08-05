<template>
    <el-main>
        <!-- 搜索栏 -->
        <el-form :model="listParm" :inline="true" size="default">
            <el-form-item>
                <el-input
                        v-model="listParm.nickName"
                        placeholder="请输入姓名"
                ></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="listParm.phone" placeholder="请输入电话"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button :icon="Search" @click="searchBtn">搜索</el-button>
                <el-button type="danger" plain :icon="Close" @click="resetBtn">重置</el-button>
                <el-button :icon="Plus" type="primary" @click="addBtn"
                >新增</el-button
                >
                <el-button
                        :icon="Plus"
                        type="success"
                        @click="exportBtn"
                >导出</el-button
                >
            </el-form-item>

        </el-form>

        <!-- 表格 -->
        <el-table :height="tableHeight"  :data="tableData.list" border stripe>
            <el-table-column prop="nickName" label="姓名"></el-table-column>
            <el-table-column prop="sex" label="性别">
                <template #default="scope">
                    <el-tag v-if="scope.row.sex == '0'" type="success"  size="default" @click="">男</el-tag>
                    <el-tag v-if="scope.row.sex == '1'" type="warning" size="default"  @click="">女</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="username" label="账户"></el-table-column>
            <el-table-column  label="操作" width="320" align="center">
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" size="default" @click="editBtn(scope.row)">编辑</el-button>
                    <el-button type="danger" :icon="Delete" size="default" @click="deleteBtn(scope.row)">删除</el-button>
                    <el-button type="warning" :icon="Edit" size="default" @click="resetPassword(scope.row)">重置密码</el-button>
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
        <!-- 新增、编辑 -->
        <AddUser ref="addRef" @reFresh="getList"></AddUser>
    </el-main>
</template>

<script setup lang="ts">
import AddUser from "./AddUser.vue";
import { Search, Edit, Plus, Close, Delete } from "@element-plus/icons-vue";
import useTable from "@/composables/user/useTable";
import useUser from "@/composables/user/useUser";
//表格
const { listParm, searchBtn, resetBtn,getList,sizeChange,currentChange ,tableData,tableHeight} = useTable();
//新增、编辑
const { addBtn, editBtn, deleteBtn,addRef,resetPassword } = useUser(getList);
//导出
const exportBtn = () => {
    const abtn = document.createElement("a");
    abtn.href = process.env.BASE_API + "/api/user/exportUser";
    abtn.click();
};
</script>

<style scoped></style>
