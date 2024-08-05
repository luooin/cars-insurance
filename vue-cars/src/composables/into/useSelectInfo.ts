// @ts-nocheck
import { nextTick, onMounted, reactive, ref } from "vue"
import { AddInfoType, SelectMaterial } from "@/api/into/IntoModel"
import { getSelectListApi } from "@/api/into"
import { MaterialType } from "@/api/material/MaterialModel";
import {ElMessage, ElNotification} from "element-plus";
export default function useSelectInfo() {
    const selectTableHeight = ref(0)
    //表格数据
    const selectTable = reactive({
        list: []
    })
    //表格查询参数
    const selectParm = reactive<SelectMaterial>({
        categoryId: '',
        currentPage: 1,
        pageSize: 10,
        infoName: '',
        total: 0
    })
    //获取列表
    const getSelectList = async () => {
        let res = await getSelectListApi(selectParm)
        if (res && res.code == 200) {
            selectTable.list = res.data.records;
            selectParm.total = res.data.total;
        }
    }
    //物资查询
    const searchSelect = () => {
        getSelectList()
    }
    //重置
    const resetSelect = () => {
        selectParm.categoryId = ''
        selectParm.infoName = ''
        getSelectList()
    }
    //页容量改变时触发
    const sizeChange = (size: number) => {
        selectParm.pageSize = size;
        getSelectList()
    }
    //页数改变触发
    const currentChange = (page: number) => {
        selectParm.currentPage = page;
        getSelectList()
    }
    //加入的数据
    const addTableData = reactive<AddInfoType>({
        list:[]
    })
    //加入
    const addBtn = (row: MaterialType) => {
        console.log(row)
        const flag = addTableData.list.some(item => item.infoId === row.infoId)
        if(flag){
            ElNotification({
                title: "Warning",
                message: "该物资已经选择",
                type: "warning"
            })
            return;
        }
        //数量默认是1
        row.num = 1;
        addTableData.list.push(row)
        ElNotification({
            title: "Success",
            message: "加入成功",
            type: "success"
        })
    }
    onMounted(() => {
        nextTick(() => {
            selectTableHeight.value = window.innerHeight - 130
        })
    })
    return {
        selectTable,
        selectParm,
        getSelectList,
        searchSelect,
        resetSelect,
        sizeChange,
        currentChange,
        selectTableHeight,
        addBtn,
        addTableData
    }
}
