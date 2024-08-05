// @ts-nocheck
import { SelectTypeList } from "@/api/user/UserModel";
import { reactive } from "vue";
import { getSelectApi } from "@/api/category/index.ts";
export default function useSelectCategory(){
    //角色数据
    const categoryData = reactive<SelectTypeList>({
        list:[]
    })
    //获取数据
    const getCateList = async()=>{
        let res = await getSelectApi()
        if(res && res.code == 200){
            categoryData.list = res.data;
        }
    }
    return{
        categoryData,
        getCateList
    }
}
