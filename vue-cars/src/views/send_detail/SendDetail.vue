<template>
    <el-main>
        <!--搜索栏 -->
        <el-form :model="listParm" :inline="true" size="default">
            <el-form-item>
                <el-input
                        v-model="listParm.province"
                        placeholder="请输入省份"
                ></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="listParm.name" placeholder="请输入联系人"></el-input>
            </el-form-item>
            <el-form-item>
                <el-select
                        v-model="listParm.status"
                        placeholder="请选择审核状态"
                        size="default"
                >
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-select
                        v-model="listParm.type"
                        placeholder="请选择领取类型"
                        size="default"
                >
                    <el-option
                            v-for="item in optionsType"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button :icon="Search" @click="searchBtn">搜索</el-button>
                <el-button type="danger" plain :icon="Close" @click="resetBtn"
                >重置</el-button
                >
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :height="tableHeigth" :data="tableList.list" border stripe>
            <!-- 详细信息 给 el-table-column 加上 type="expand" -->
            <el-table-column type="expand">
                <template #default="scope">
                    <el-table :data="scope.row.children" border stripe>
                        <el-table-column
                                prop="image"
                                width="90"
                                label="物资图片"
                                align="center"
                        >
                            <template #default="scope">
                                <el-image
                                        style="width: 60px; height: 60px; border-radius: 50%"
                                        :src="scope.row.image"
                                ></el-image>
                            </template>
                        </el-table-column>
                        <el-table-column prop="infoName" label="物资名称"></el-table-column>
                        <el-table-column prop="specs" label="规格"></el-table-column>
                        <el-table-column prop="unit" label="单位"></el-table-column>
                        <el-table-column prop="num" label="数量"></el-table-column>
                    </el-table>
                </template>
            </el-table-column>
            <el-table-column prop="province" label="省份"></el-table-column>
            <el-table-column prop="detailSource" label="详细来源"></el-table-column>
            <el-table-column prop="name" label="联系人"></el-table-column>
            <el-table-column prop="phone" label="联系电话"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="type" label="领取类型" align="center">
                <template #default="scope">
                    <el-tag v-if="scope.row.type == '1'" size="default">政府</el-tag>
                    <el-tag v-if="scope.row.type == '2'" type="success" size="default"
                    >医院</el-tag
                    >
                    <el-tag v-if="scope.row.type == '3'" type="warning" size="default"
                    >小区</el-tag
                    >
                    <el-tag v-if="scope.row.type == '4'" type="danger" size="default"
                    >个人</el-tag
                    >
                    <el-tag v-if="scope.row.type == '5'" type="danger" size="default"
                    >其他</el-tag
                    >
                </template>
            </el-table-column>
            <el-table-column prop="doType" label="紧急程度" align="center">
                <template #default="scope">
                    <el-tag v-if="scope.row.doType == '1'" size="default">不急</el-tag>
                    <el-tag v-if="scope.row.doType == '2'" type="success" size="default"
                    >紧急</el-tag
                    >
                    <el-tag v-if="scope.row.doType == '3'" type="warning" size="default"
                    >特急</el-tag
                    >
                    <el-tag v-if="scope.row.doType == '4'" type="danger" size="default"
                    >超急</el-tag
                    >
                </template>
            </el-table-column>
            <el-table-column prop="status" label="审核状态" align="center">
                <template #default="scope">
                    <el-tag v-if="scope.row.status == '0'" size="default">未审核</el-tag>
                    <el-tag v-if="scope.row.status == '1'" type="success" size="default"
                    >审核通过</el-tag
                    >
                    <el-tag v-if="scope.row.status == '2'" type="danger" size="default"
                    >拒绝入库</el-tag
                    >
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column label="操作" align="center" width="260">
                <template #default="scope">
                    <el-button :icon="Edit" type="primary" size="default" @click="applyBtn(scope.row)"
                    >同意发放</el-button
                    >
                    <el-button :icon="Edit" type="danger" size="default" @click="rejectBtn(scope.row)"
                    >拒绝发放</el-button
                    >
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

    </el-main>
</template>

<script setup lang="ts">
import { Search, Edit, Plus, Close, Delete } from "@element-plus/icons-vue";
import useDetailTable from "@/composables/send/useDetailTable";
const options = [
    {
        value: "0",
        label: "未审核",
    },
    {
        value: "1",
        label: "已审核",
    },
    {
        value: "2",
        label: "审核失败",
    },
];
const optionsType = [
    {
        value: "1",
        label: "政府",
    },
    {
        value: "2",
        label: "医院",
    },
    {
        value: "3",
        label: "小区",
    },
    {
        value: "4",
        label: "个人",
    },
    {
        value: "5",
        label: "其他",
    }
];
//表格
const { listParm, tableHeigth, resetBtn, searchBtn, tableList,sizeChange,currentChange,rejectBtn,applyBtn } = useDetailTable();
</script>

<style scoped></style>
