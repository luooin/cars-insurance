import { nextTick, onMounted, reactive,ref } from "vue";
// @ts-ignore
import { ListParm } from '@/api/user/UserModel.ts'
// @ts-ignore
import { getListApi } from "@/api/user/index.ts";
export default function useTable() {
    //定义表格高度
    const tableHeight = ref(0)
    //列表查询的参数
    const listParm = reactive<ListParm>({
        currentPage: 1,
        pageSize: 10,
        total: 0,
        nickName: '',
        phone: ''
    })
    //定义表格数据
    const tableData = reactive({
        list:[]
    })
    //获取表格数据
    const getList = async()=>{
        let res = await getListApi(listParm)
        if(res && res.code == 200){
            console.log(res)
            tableData.list = res.data.records;
            listParm.total = res.data.total;
        }
    }
    //搜索
    const searchBtn = ()=>{
        getList()
    }
    //重置
    const resetBtn = ()=>{
        listParm.nickName = ''
        listParm.phone = ''
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
        nextTick(()=>{
            tableHeight.value = window.innerHeight - 230
        })
        getList()
    })
    return{
        listParm,
        searchBtn,
        resetBtn,
        tableData,
        getList,
        sizeChange,
        currentChange,
        tableHeight,
    }
}
