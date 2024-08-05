import {nextTick, onMounted, reactive, ref} from "vue";
// @ts-ignore
import { ListParm } from "@/api/role/RoleModel.ts";
import {getListApi} from "../../api/role";
export default function useTable(){
    //定义表格高度
    const tableHeight = ref(0)
    //定义表格数据
    const tableList = reactive({
        list: []
    })

    //列表参数
    const listParm = reactive<ListParm>({
        roleName:'',
        currentPage:1,
        pageSize:10,
        total:0
    })
    //分页
    //列表查询
    const getList =async ()=> {
        let res = await getListApi(listParm)
        if (res && res.code == 200) {
            console.log(res)
            //设置表格数据
            tableList.list = res.data.records
            //设置分页的总条数
            listParm.total = res.data.total
        }
    }
    //搜索
    const searchBtn = ()=>{
        getList()
    }
    //重置
    const resetBtn = ()=>{
        listParm.roleName = ''
        getList()
    }


    // 页容量改变时触发
    const sizeChange = (val: Number) => {
        listParm.pageSize = val
        getList()
    }

    //刷新列表
    const refresh = ()=>{
        getList()
    }
    //页数改变是触发
    const currentChange = (val: number) => {
        listParm.currentPage = val
        getList()
    }
    onMounted(() => {
        getList()
        nextTick(() => {
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
        refresh,
        tableHeight,
    }
}
