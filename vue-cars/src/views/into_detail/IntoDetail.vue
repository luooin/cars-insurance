<template>
    <el-main>
        <!-- 表格 -->
        <el-table :height="tableHeight" :data="tableList.list" border stripe>
            <el-table-column prop="image" width="90" label="车辆图片" align="center">
                <template #default="scope">
                    <el-image
                            style="width: 60px; height: 60px; border-radius: 50%"
                            :src="scope.row.image"
                    ></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="newPrice" label="新车购置价格"></el-table-column>
            <el-table-column prop="forcePrice" label="强制险"></el-table-column>
            <el-table-column prop="threePrice" label="第三责任险"></el-table-column>
            <el-table-column prop="lossPrice" label="车辆损失险"></el-table-column>
            <el-table-column prop="thiefPrice" label="机动车盗抢险"></el-table-column>
            <el-table-column prop="bornPrice" label="自燃险"></el-table-column>
            <el-table-column prop="glassPrice" label="玻璃单独破碎险"></el-table-column>
            <el-table-column prop="resPrice" label="车上人员责任险"></el-table-column>
            <el-table-column prop="scratchPrice" label="车身划痕损失险"></el-table-column>
            <el-table-column  label="操作" align="center" width="200">
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
        <AddInfo ref="addRef" @reFresh="getList"></AddInfo>
    </el-main>
</template>

<script setup lang="ts">
import AddInfo from "./AddInfo.vue";
import { Search, Edit, Plus, Close, Delete } from "@element-plus/icons-vue";
import useTable from "@/composables/insurance/useTable";
import useInsurance from "@/composables/insurance/useInsurance";
//表格
const { tableHeight,listParm, getList, resetBtn, searchBtn, tableList,sizeChange ,currentChange} = useTable();
//新增
const { addBtn, editBtn, deleteBtn, addRef } = useInsurance(getList);
//导出
const exportBtn = () => {
    const abtn = document.createElement("a");
    abtn.href = process.env.BASE_API + "/api/carsInfo/exporMaterial";
    abtn.click();
};
</script>

<style scoped></style>
