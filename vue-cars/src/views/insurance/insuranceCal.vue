<template>
    <el-container style="margin-left: 20px">
        <el-aside width="200px">
            <div style="display: flex;justify-content: center;align-items: center;margin-top: 30px;">
                <el-button type="primary" @click="handleCal">开始计算</el-button>
                <el-button type="primary"  @click="drawer =true">生成保单</el-button>
            </div>
            <el-card class="box-card" style="margin-top: 170px">
                <template #header>
                    <div class="card-header">
                        <span>保险费用合计</span><br><br>
                        本计算结果不含折扣，仅供参考
                    </div>

                </template>
                <el-input v-model="insurance_result" style="width: 100px" placeholder="计算结果" />&nbsp;元
            </el-card>
        </el-aside>
        <el-main >
            <el-table  style="width: 100%" :height="tableHeight" :data="tableData"  class="calculator print">
            <el-table-column fixed prop="name" label="保险费项目" width="300" />
            <el-table-column prop="choice" label="选择" width="300" >
                <template #default="scope">
                    <div v-if="scope.row.id===1">
                        <el-input  v-model="listParm2.newPrice" placeholder="请输入新车购置价格" style="width: 200px; margin-right: 10px" />万元
                    </div>
                    <div v-if="scope.row.id===2">
                        <el-radio-group v-model="listParm2.forcePrice" class="ml-4">
                            <el-radio label="1100" size="large" >家用六座以上</el-radio>
                            <el-radio label="950" size="large">家用六座以下</el-radio>
                        </el-radio-group>
                    </div>
                    <div v-if="scope.row.id===3">
                        <el-radio-group  v-model="listParm2.threePrice" class="ml-4">
                            <el-radio label="607" size="large" >5万</el-radio>
                            <el-radio label="877" size="large" >10万</el-radio>
                            <el-radio label="999" size="large" >15万</el-radio>
                            <el-radio label="1087" size="large" >20万</el-radio>
                            <el-radio label="1226" size="large" >30万</el-radio>
                            <el-radio label="1472" size="large" >50万</el-radio>
                            <el-radio label="1917" size="large" >100万</el-radio>
                        </el-radio-group>
                    </div>
                    <div v-if="scope.row.id===4">
                        <el-radio-group  v-model="listParm2.lossPrice" class="ml-4">
                            <el-radio label="1960" size="large" >2-6年</el-radio>
                        </el-radio-group>
                    </div>
                    <div v-if="scope.row.id===7">
                        <el-radio-group  v-model="listParm2.glassPrice" class="ml-4">
                            <el-radio label="607" size="large" >国产玻璃</el-radio>
                            <el-radio label="877" size="large" >进口玻璃</el-radio>
                        </el-radio-group>
                    </div>
                    <div v-if="scope.row.id===8">
                        <div> 驾驶员
                            <el-select v-model="resPrice.driverPrice" class="m-2" placeholder="选择金额">
                                <el-option
                                        v-for="item in driverOptions"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                />
                            </el-select></div>
                        <div> 乘员&nbsp;&nbsp;&nbsp;
                            <el-select v-model="resPrice.passengerPrice" class="m-2" placeholder="选择金额">
                                <el-option
                                        v-for="item in passengerOptions"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                />
                            </el-select></div>
                        <div> 人数&nbsp;&nbsp;&nbsp;
                            <el-select v-model="resPrice.peopleCount" class="m-2" placeholder="选择人数">
                                <el-option
                                        v-for="item in peopleCountOptions"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                />
                            </el-select></div>
                    </div>
                    <div v-if="scope.row.id===9">
                        <el-radio-group  v-model="listParm2.scratchPrice" class="ml-4">
                            <el-radio label="400" size="large" >2000元</el-radio>
                            <el-radio label="570" size="large" >5000元</el-radio>
                            <el-radio label="760" size="large" >10000元</el-radio>
                            <el-radio label="1140" size="large" >20000元</el-radio>
                        </el-radio-group>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="state" label="保险金额" width="300" >
                <template #default="scope">
                    <div v-if="scope.row.id===2">
                        <el-input  v-model="listParm2.forcePrice" placeholder="价格" style="width: 200px; margin-right: 10px" />
                    </div>
                    <div v-if="scope.row.id===3">
                        <el-input  v-model="listParm2.threePrice" placeholder="价格" style="width: 200px; margin-right: 10px" />
                        <el-radio-group v-model="listParm2.threePrice" class="ml-4">
                            <el-radio label="0" size="large">取消此险</el-radio>
                        </el-radio-group>
                    </div>
                    <div v-if="scope.row.id===4">
                        <el-input  v-model="listParm2.lossPrice" placeholder="价格" style="width: 200px; margin-right: 10px" />
                        <el-radio-group v-model="listParm2.lossPrice" class="ml-4">
                            <el-radio label="0" size="large">取消此险</el-radio>
                        </el-radio-group>
                    </div>
                    <div v-if="scope.row.id===5">
                        <el-input  v-model="listParm2.thiefPrice" placeholder="价格" style="width: 200px; margin-right: 10px" />
                        <el-radio-group v-model="listParm2.thiefPrice" class="ml-4">
                            <el-radio label="0" size="large">取消此险</el-radio>
                        </el-radio-group>
                    </div>
                    <div v-if="scope.row.id===6">
                        <el-input  v-model="listParm2.bornPrice" placeholder="价格" style="width: 200px; margin-right: 10px" />
                        <el-radio-group v-model="listParm2.bornPrice" class="ml-4">
                            <el-radio label="0" size="large">取消此险</el-radio>
                        </el-radio-group>
                    </div>
                    <div v-if="scope.row.id===7">
                        <el-input  v-model="listParm2.glassPrice" placeholder="价格" style="width: 200px; margin-right: 10px" />
                        <el-radio-group v-model="listParm2.glassPrice" class="ml-4">
                            <el-radio label="0" size="large">取消此险</el-radio>
                        </el-radio-group>
                    </div>
                    <div v-if="scope.row.id===8">
                        <el-input  v-model="totalCountPrice" placeholder="价格" style="width: 200px; margin-right: 10px" />
                        <el-radio-group v-model="resPrice.count" class="ml-4">
                            <el-radio label="0" size="large">取消此险</el-radio>
                        </el-radio-group>
                    </div>
                    <div v-if="scope.row.id===9">
                        <el-input  v-model="listParm2.scratchPrice" placeholder="价格" style="width: 200px; margin-right: 10px" />
                        <el-radio-group v-model="listParm2.scratchPrice" class="ml-4">
                            <el-radio label="0" size="large">取消此险</el-radio>
                        </el-radio-group>
                    </div>
                </template>
            </el-table-column>
        </el-table>
           </el-main>
    </el-container>

  <!--  抽屉-->
    <el-drawer
            size="60%"
            v-model="drawer"
            title="车辆选择"
            :direction="direction"
            :before-close="handleClose"
    > <!-- 表格 -->
        <el-table  :height="tableHeight" :data="tableList.list" border stripe>
            <el-table-column prop="image" width="90" label="车辆图片" align="center">
                <template #default="scope">
                    <el-image
                            style="width: 60px; height: 60px; border-radius: 50%"
                            :src="scope.row.image"
                    ></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="carName" label="车辆品牌"></el-table-column>
            <el-table-column prop="categoryName" label="车辆分类"></el-table-column>
            <el-table-column prop="purchasePrice" label="购置价格"></el-table-column>
            <el-table-column prop="discountFactor" label="折扣系数"></el-table-column>
            <el-table-column prop="initRegisterDate" label="初登日期"></el-table-column>
            <el-table-column prop="insuranceStartDate" label="起保日期"></el-table-column>
            <el-table-column  label="操作" align="center" width="200">
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" size="default" @click="editBtn(scope.row)">编辑</el-button>
                    <el-button type="primary" :icon="Edit" size="default" @click="choiceBtn(scope.row)">选择</el-button>
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
    </el-drawer>
    <!-- 新增弹框 -->
    <AddInfo ref="addRef" @reFresh="getList"></AddInfo>
</template>

<script setup>
import {reactive, ref,computed} from "vue";
import useInsurance from "@/composables/material/useMaterial";
import {Delete, Edit} from "@element-plus/icons-vue";
import useTable from "@/composables/material/useTable";
import AddInfo from "@/views/goods/AddInfo.vue";
import useInstance from "@/hooks/useInstance"
import {ElNotification} from "element-plus";
import {addApi, getInsurancePrice} from "@/api/insurance/index.ts"
import { useRouter } from "vue-router";
import useMaterial from "@/composables/material/useMaterial"
const { global } = useInstance()
const router = useRouter();
const { tableHeight,listParm, getList, resetBtn, searchBtn, tableList,sizeChange ,currentChange} = useTable();

//新增
const { addBtn, editBtn, deleteBtn, addRef } = useMaterial(getList);
const tableData =reactive([
    {
      id:1,
      name: '新车购置价格',
    },
  {
    id:2,
    name: '交强险',
  },
  {
    id:3,
    name: '第三者责任险',
  },
  {
    id:4,
    name: '车辆损失险',
  },
  {
    id:5,
    name: '机动车盗抢险',
  },
  {
    id:6,
    name: '自燃险',
  },

  {
    id:7,
    name: '玻璃单独破碎险',
  },
  {
    id:8,
    name: '车上人员责任险',
  },

  {
    id:9,
    name: '车身划痕损失险',
  },

])

// 新车购置价格
const newPrice = ref("")
// 强险
const forcePrice = ref("")
// 第三责任险
const threePrice = ref("")
// 车辆损失险
const lossPrice = ref("")
// 盗抢险
const thiefPrice = ref("")
// 自燃险
const bornPrice = ref("")
// 玻璃单独破碎险
const glassPrice = ref("")
// 车上人员责任险
const resPrice = reactive({
  driverPrice: "",
  passengerPrice: "",
  count:1,
  peopleCount: 1,
})
// 驾驶员费用
const driverOptions = [
  {
    value: '0',
    label: '不投',
  },
  {
    value: '41',
    label: '1万',
  },
  {
    value: '82',
    label: '2万',
  },
  {
    value: '123',
    label: '3万',
  },
  {
    value: '164',
    label: '4万',
  },
  {
    value: '205',
    label: '5万',
  },
  {
    value: '246',
    label: '6万',
  },
  {
    value: '287',
    label: '7万',
  },

]
// 乘车员
const passengerOptions = [
  {
    value: '0',
    label: '不投',
  },
  {
    value: '26',
    label: '1万',
  },
  {
    value: '78',
    label: '2万',
  },
  {
    value: '123',
    label: '3万',
  },
  {
    value: '164',
    label: '4万',
  },
  {
    value: '130',
    label: '5万',
  },
  {
    value: '246',
    label: '6万',
  },
  {
    value: '287',
    label: '7万',
  },

]
// 人数
const peopleCountOptions = [
  {
    value: '1',
    label: '1人',
  },
  {
    value: '2',
    label: '2人',
  },
  {
    value: '3',
    label: '3人',
  },
  {
    value: '4',
    label: '4人',
  },
]
// 车上人员责任险总费用
const totalCountPrice =computed(()=> {
  return (parseInt(resPrice.driverPrice)+parseInt(resPrice.passengerPrice)*parseInt(resPrice.peopleCount))*resPrice.count || 0
})
const insurance_result = ref("")

const listParm2 = reactive({
  carId: "",
  newPrice: 0,
  forcePrice: 0,
  threePrice: 0,
  lossPrice: 0,
  thiefPrice: 0,
  bornPrice: 0,
  glassPrice:0,
  scratchPrice: 0,
  resPrice :totalCountPrice
})
// 车上划痕险
const scratchPrice = ref("")
//抽屉显示
const drawer = ref(false)
const direction = ref('rtl')
const handleClose = (done) => {
      done()
}

const choiceBtn = async (row)=> {
    const cofirm = await global.$myconfirm('确定选择该车辆类型吗？')
    listParm2.carId = row.carId
    if (cofirm) {
      const res  =  await addApi(listParm2)
      console.log(row.id)
      router.push({name:"insuranceDetail"})
      if (res && res.code == 200){
        ElNotification({
          title: "Success",
          message:res.msg,
          type: "success"
        })
      }

    }
  }
const handleCal = async ()=> {
  const res = await getInsurancePrice(listParm2)
  if (res && res.code == 200){
    insurance_result.value = res.data
    ElNotification({
      title: "Success",
      message: "计算成功",
      type: "success"
    })
  }

}
</script>

<style scoped lang="scss">
  .calculator {
    //display: flex;
    //flex-direction: column;
    //align-items: center;
    //justify-content: center;
  }
</style>
