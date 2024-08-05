import { getCurrentInstance,ComponentInternalInstance  } from "vue";

//获取全局挂载属性
export default function useInstance(){
    const {appContext,proxy} =  getCurrentInstance() as ComponentInternalInstance
    const global = appContext.config.globalProperties;
    return{
        proxy,
        global
    }
}
