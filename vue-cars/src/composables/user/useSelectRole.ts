// @ts-ignore
import { SelectTypeList } from "@/api/user/UserModel.ts";
import {reactive, ref} from "vue";

// @ts-ignore
import { getSelectApi ,getRoleByUserIdApi} from "@/api/user/index.ts";
export default function useSelectRole(){
    // 角色id
    const roleId = ref('')
    //角色数据
    const roleData = reactive<SelectTypeList>({
        list:[]
    })
    //获取数据
    const listRole = async()=>{
        let res = await getSelectApi()
        if(res && res.code == 200){
            roleData.list = res.data;
        }
    }
    // 获取角色id
    const getRoleByUserId =async (userId: string)=> {
       let res =   await  getRoleByUserIdApi(userId)
        if(res && res.code == 200){
            roleId.value = res.data.roleId
        }
    }
    return{
        roleData,
        listRole,
        roleId,
        getRoleByUserId
    }
}
