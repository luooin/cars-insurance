//@ts-nocheck
import { NoticeParm } from "@/api/notice/NoticeModel";
import { nextTick, onMounted, reactive, ref } from "vue";
import { getListApi } from "@/api/notice/index.ts";
export default function useTable() {
    //表格高度
    const tableHeight = ref(0)
    //表格数据
    const tableList = reactive({
        list:[]
    })
    //列出查询的参数
    const listParm = reactive<NoticeParm>({
        currentPage: 1,
        pageSize: 10,
        title: '',
        total: 0
    })
    //列表
    const getList = async()=>{
        let res = await getListApi(listParm)
        if(res && res.code == 200){
            tableList.list = res.data.records;
            listParm.total = res.data.total;
        }
    }
    //搜索
    const searchBtn = ()=>{
        getList()
    }
    //重置
    const resetBtn = ()=>{
        listParm.title = ''
        getList()
    }
    //页容量改变触发
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
        listParm,
        getList,
        resetBtn,
        searchBtn,
        tableList,
        sizeChange,
        currentChange,
        tableHeight
    }
}
