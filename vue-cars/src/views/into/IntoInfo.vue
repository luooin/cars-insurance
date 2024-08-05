<template>
    <el-container class="mainclass" :style="{ height: mainHeight + 'px' }">
        <el-aside width="30%" class="left-side">
            <div class="title">入库信息</div>
            <el-divider />
            <el-form
                    style="padding: 20px"
                    :model="addModel"
                    ref="addRef"
                    :rules="rules"
                    label-width="80px"
                    size="default"
            >
                <el-form-item prop="type" label="入库类型">
                    <el-radio-group v-model="addModel.type">
                        <el-radio :label="1">捐赠</el-radio>
                        <el-radio :label="2">下拨</el-radio>
                        <el-radio :label="3">采购</el-radio>
                        <el-radio :label="4">借用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="province" label="省份" size="default">
                    <elui-china-area-dht
                            v-model="address"
                            isall
                            @change="onChange"
                    ></elui-china-area-dht>
                </el-form-item>
                <el-form-item prop="detailSource" label="详细来源" size="default">
                    <el-input v-model="addModel.detailSource"></el-input>
                </el-form-item>
                <el-form-item prop="name" label="联系人" size="default">
                    <el-input v-model="addModel.name"></el-input>
                </el-form-item>
                <el-form-item prop="phone" label="联系电话" size="default">
                    <el-input v-model="addModel.phone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" size="default">
                    <el-input v-model="addModel.email"></el-input>
                </el-form-item>
                <el-form-item label="选择物资" prop="email" size="default">
                    <el-button size="default" :icon="Edit" @click="selectInfo"
                    >物资选择</el-button
                    >
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="commit">入库</el-button>
                    <el-button>取消</el-button>
                </el-form-item>
            </el-form>
        </el-aside>
        <el-main class="right-main">
            <div class="title">
                物资信息
            </div>
            <el-divider />
            <el-table :data="addTableData.list" border stripe>
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
                <el-table-column prop="num" label="数量" width="180">
                    <template #default="scope">
                        <el-input-number
                                @change="change"
                                size="default"
                                v-model="scope.row.num"
                                :min="1"
                                label="数量"
                        ></el-input-number>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="110">
                    <template #default="scope">
                        <el-button
                                @click="deleteAddBtn(scope.row)"
                                type="danger"
                                :icon="Delete"
                                size="default"
                        >删除</el-button
                        >
                    </template>
                </el-table-column>
            </el-table>
        </el-main>
    </el-container>
    <el-drawer
            size="50%"
            custom-class="faDrawer"
            v-model="drawer"
            direction="rtl"
    >
        <template #title>
            <el-select
                    v-model="selectParm.categoryId"
                    placeholder="请选择分类"
                    size="default"
            >
                <el-option
                        v-for="item in categoryData.list"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                />
            </el-select>
            <el-input
                    v-model="selectParm.infoName"
                    style="width: 30%; margin-left: 20px"
                    placeholder="请输入物资名称"
            ></el-input>
            <el-button
                    style="margin: 0px 20px"
                    :icon="Search"
                    size="default"
                    @click="searchSelect"
            >搜索</el-button
            >
            <el-button
                    style="margin-left: 0px"
                    type="danger"
                    plain
                    :icon="Close"
                    size="default"
                    @click="resetSelect"
            >重置</el-button
            >
        </template>
        <template #default>
            <el-table
                    :height="selectTableHeight"
                    :data="selectTable.list"
                    border
                    stripe
            >
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
                <el-table-column label="操作" align="center" width="110">
                    <template #default="scope">
                        <el-button
                                type="primary"
                                :icon="Plus"
                                size="default"
                                @click="addBtn(scope.row)"
                        >加入</el-button
                        >
                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <el-pagination
                    @size-change="sizeChange"
                    @current-change="currentChange"
                    :current-page.sync="selectParm.currentPage"
                    :page-sizes="[10, 20, 40, 80, 100]"
                    :page-size="selectParm.pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="selectParm.total"
                    background
            >
            </el-pagination>
        </template>
    </el-drawer>
</template>

<script setup lang="ts">
import { MaterialType } from "@/api/material/MaterialModel";
import useSelectInfo from "@/composables/into/useSelectInfo";
import useSelectCategory from "@/composables/material/useSelectCategory";
import { Search, Edit, Plus, Close, Delete } from "@element-plus/icons-vue";
import {ElMessage, ElNotification, FormInstance} from "element-plus";
import { EluiChinaAreaDht } from "elui-china-area-dht";
import { ref, onMounted, nextTick, reactive } from "vue";
import { useRouter } from "vue-router";
import { intoSaveApi } from "@/api/into";
//物资信息
const {
    addTableData,
    addBtn,
    selectTableHeight,
    selectParm,
    selectTable,
    getSelectList,
    resetSelect,
    searchSelect,
    sizeChange,
    currentChange,
} = useSelectInfo();
const router = useRouter();
const chinaData = new EluiChinaAreaDht.ChinaArea().chinaAreaflat;
//物资分类
const { categoryData, getCateList } = useSelectCategory();
//控制抽屉布局的显示和影藏
const drawer = ref(false);
//表单ref属性
const addRef = ref<FormInstance>();
//省市区数据
const address = ref([]);
//定义容器的高度
const mainHeight = ref(0);
//表单对象
const addModel = reactive({
    type: "",
    province: "",
    detailSource: "",
    name: "",
    phone: "",
    email: "",
    infos:[]
});
//表单验证规则
const rules = reactive({
    type: [
        {
            required: true,
            trigger: "blur",
            message: "请选择入库类型",
        },
    ],
    province: [
        {
            required: true,
            trigger: "blur",
            message: "请选择省市区",
        },
    ],
    detailSource: [
        {
            required: true,
            trigger: "blur",
            message: "请填写详细来源",
        },
    ],
    name: [
        {
            required: true,
            trigger: "blur",
            message: "请填写联系人",
        },
    ],
    phone: [
        {
            required: true,
            trigger: "blur",
            message: "请填写联系电话",
        },
    ],
    email: [
        {
            required: true,
            trigger: "blur",
            message: "请填写邮箱",
        },
    ],
});
//计算容器高度
onMounted(() => {
    nextTick(() => {
        mainHeight.value = window.innerHeight - 100;
    });
});
//省市区切换事件
const onChange = (e: any) => {
    console.log(e);
    const one = chinaData[e[0]].label;
    const two = chinaData[e[1]].label;
    const three = chinaData[e[2]].label;
    addModel.province = one + "/" + two + "/" + three;
    console.log(one, two, three);
};
//选择物资点击事件
const selectInfo = async () => {
    //获取分类
    await getCateList();
    //获取物资信息
    await getSelectList();
    drawer.value = true;
};
//数量改变触发
const change = ()=>{

}
//删除
const deleteAddBtn = (row:MaterialType)=>{
    addTableData.list = addTableData.list.filter(item =>item.infoId != row.infoId)
}
//表单提交
const commit = () => {
    if(addTableData.list.length == 0){
        ElMessage.warning('请选择物资信息!');
        return;
    }
    addRef.value?.validate(async(valid) => {
        if (valid) {
            addModel.infos = addTableData.list as any;
            console.log(addModel)
            let res = await intoSaveApi(addModel)
            if(res && res.code == 200){
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                //跳转到入库记录
                router.push({ name: "intoDetail" });
            }
        }
    });
};
</script>

<style lang="scss">
.mainclass {
  padding: 20px 10px;
}
.left-side {
  border-right: 1px solid #dedfe6;
}
.right-main {
  padding: 0px 0px 0px 10px;
}
.title {
  display: flex;
  justify-content: center;
  align-items: center;
}
:deep(.el-cascader) {
  width: 100%;
}
.faDrawer {
  .el-drawer__header {
    font-size: 16px;
    padding: 9px;
    margin-bottom: 0px !important;
    background-color: #009688 !important;
    .el-drawer__close {
      color: #fff !important;
    }
  }
}
.el-cascader{
  width: 100%;
}
</style>
