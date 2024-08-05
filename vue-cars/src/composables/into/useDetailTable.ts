// @ts-nocheck
import { MaterialIntoParm } from "@/api/into/IntoModel";
import { nextTick, onMounted, reactive, ref } from "vue";
import { getIntoListApi,intoApplyApi } from "@/api/into/index.ts";
import useInstance from "@/hooks/useInstance";
import {ElMessage, ElNotification} from "element-plus";
export default function useDetailTable() {
    const { global } = useInstance()
    //表格高度
    const tableHeigth = ref(0)
    //列表参数
    const listParm = reactive<MaterialIntoParm>({
        currentPage: 1,
        pageSize: 10,
        province: '',
        name: '',
        total: 0,
        status: '',
        type: ''
    })
    //表格数据
    const tableList = reactive({
        list: []
    })
    //获取表格数据
    const getList = async () => {
        let res = await getIntoListApi(listParm)
        if (res && res.code == 200) {
            tableList.list = res.data.records;
            listParm.total = res.data.total;
        }
    }

    //搜索
    const searchBtn = () => {
        getList()
    }
    //重置
    const resetBtn = () => {
        listParm.name = ''
        listParm.province = ''
        listParm.status = ''
        listParm.type = ''
        getList()
    }
    //页容量改变时触发
    const sizeChange = (size: number) => {
        listParm.pageSize = size
        getList()
    }
    //页数改变时触发
    const currentChange = (page: number) => {
        listParm.currentPage = page;
        getList()
    }
    //拒绝
    const rejectBtn = async(row: any) => {
        if(row.status == '1' || row.status == '2'){
            ElMessage.warning('该信息已经审核通过，不用重复审核!')
            return;
        }
        const confirm = await global.$myconfirm('确定拒绝审核吗？')
        if(confirm){
            let res = await intoApplyApi({
                intoId:row.intoId,
                status:'2'
            })
            if(res && res.code == 200){
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                getList()
            }
        }
    }
    //同意
    const applyBtn = async(row: any) => {
        if(row.status == '1' || row.status == '2'){
            ElMessage.warning('该信息已经审核通过，不用重复审核!')
            return;
        }
        const confirm = await global.$myconfirm('确定同意审核吗？')
        if(confirm){
            let res = await intoApplyApi({
                intoId:row.intoId,
                status:'1'
            })
            if(res && res.code == 200){
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                getList()
            }
        }
    }
    onMounted(() => {
        getList()
        nextTick(() => {
            tableHeigth.value = window.innerHeight - 230
        })
    })
    return {
        listParm,
        getList,
        tableList,
        searchBtn,
        resetBtn,
        sizeChange,
        currentChange,
        tableHeigth,
        rejectBtn,
        applyBtn,
        global
    }
}
