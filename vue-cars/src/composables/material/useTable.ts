// @ts-nocheck
import { MaterialParm } from "@/api/material/MaterialModel";
import { nextTick, onMounted, reactive, ref } from "vue";
import { getListApi } from "@/api/material/index.ts";
export default function useTable(){
    //表格高度
    const tableHeight = ref(0)
    //定义表格数据
    const tableList = reactive({
        list:[]
    })
    //列表查询参数
    const listParm = reactive<MaterialParm>({
        currentPage:1,
        pageSize:10,
        carName:'',
        total:0
    })
    //列表
    const getList = async()=>{
        let res = await getListApi(listParm)
        if(res && res.code == 200){
            //设置表格数据
            tableList.list = res.data.records;
            //设置分页总条数
            listParm.total = res.data.total;
        }
    }
    //搜索
    const searchBtn = ()=>{
        getList()
    }
    //重置
    const resetBtn = ()=>{
        listParm.carName = ''
        getList()
    }
    //页容量改变时触发
    const sizeChange = (size:number)=>{
        listParm.pageSize = size;
        getList()
    }
    //页数改变时触发
    const currentChange = (page:number)=>{
        listParm.currentPage = page;
        getList()
    }
    onMounted(()=>{
        getList()
        nextTick(()=>{
            tableHeight.value = window.innerHeight - 230
        })
    })
    return{
        getList,
        searchBtn,
        resetBtn,
        listParm,
        tableList,
        sizeChange,
        currentChange,
        tableHeight
    }
}
