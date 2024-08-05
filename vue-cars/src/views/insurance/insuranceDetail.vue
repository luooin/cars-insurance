<template>
    <el-main  >
        <!-- 表格 -->
        <el-table :height="tableHeight" :data="tableList.list" border stripe>
            <el-table-column prop="newPrice" label="购置价格(万元)"></el-table-column>
            <el-table-column prop="forcePrice" label="强制险(元)"></el-table-column>
            <el-table-column prop="threePrice" label="第三责任险(元)"></el-table-column>
            <el-table-column prop="lossPrice" label="车辆损失险(元)"></el-table-column>
            <el-table-column prop="thiefPrice" label="机动车盗抢险(元)"></el-table-column>
            <el-table-column prop="bornPrice" label="自燃险(元)"></el-table-column>
            <el-table-column prop="glassPrice" label="玻璃单独破碎险(元)"></el-table-column>
            <el-table-column prop="resPrice" label="车上人员责任险(元)"></el-table-column>
            <el-table-column prop="scratchPrice" label="车身划痕损失险(元)"></el-table-column>
            <el-table-column prop="realPrice" label="实际费用(元)"></el-table-column>
            <el-table-column  label="操作" align="center" width="200">
                <template #default="scope">
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



    </el-main>
</template>

<script setup lang="ts">
import {Edit, Delete, Plus} from "@element-plus/icons-vue";
import useTable from "@/composables/insurance/useTable";
import useInsurance from "@/composables/insurance/useInsurance";
import useDialog from "@/hooks/useDialog";
import {reactive, ref} from "vue";
//表格
const { tableHeight,listParm, getList, resetBtn, searchBtn, tableList,sizeChange ,currentChange} = useTable();
//新增
const { addBtn, editBtn, deleteBtn, addRef } = useInsurance(getList);

const { dialog, onClose, onConfirm, onShow } = useDialog();
//导出
const exportBtn = () => {
    const abtn = document.createElement("a");
    abtn.href = process.env.BASE_API + "/api/carsInfo/exporMaterial";
    abtn.click();
};





</script >

<style scoped  >

</style>
