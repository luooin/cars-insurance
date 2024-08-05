// @ts-ignore
import { CategoryParm } from "@/api/category/CategoryModel";
import { nextTick, onMounted, reactive, ref } from "vue";
// @ts-ignore
import { getListApi } from "@/api/category/index.ts";
export default function useTable(){
    //表格高度
    const tableHeight = ref(0)
    //定义表格数据
    const tableList = reactive({
        list:[]
    })
    //定义列表参数
    const listParm = reactive<CategoryParm>({
        categoryName:'',
        currentPage:1,
        pageSize:10,
        total:0
    })
    //获取列表
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
    //重置按钮
    const resetBtn = ()=>{
        listParm.categoryName = ''
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
        listParm,
        getList,
        searchBtn,
        resetBtn,
        tableList,
        sizeChange,
        currentChange,
        tableHeight
    }
}
