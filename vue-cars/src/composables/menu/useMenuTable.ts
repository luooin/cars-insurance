import { onMounted, reactive, ref,nextTick } from "vue";
// @ts-ignore
import { getListApi } from "@/api/menu/index.ts";
export default function useMenuTable() {
    //表格高度
    const tableHeight = ref(0)
    //定义表格数据
    const tableList = reactive({
        list: []
    })
    //获取表格数据
    const getList = async () => {
        let res = await getListApi()
        if (res && res.code == 200) {
            tableList.list = res.data
        }
    }
    //刷新列表
    const refresh = ()=>{
        getList()
    }
    onMounted(() => {
        getList()
        nextTick(() => {
            tableHeight.value = window.innerHeight - 200
        })
    })
    return {
        tableList,
        getList,
        tableHeight,
        refresh
    }
}
