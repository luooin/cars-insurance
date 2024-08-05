import { createApp } from 'vue'

// @ts-ignore
import App from '@/App.vue'
import router from "./router/index"

//引入element plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//国际化
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import objCoppy from './utils/objCoppy'
import myconfirm from './utils/myconfirm'
// 引入pinia
import piniaPersist from 'pinia-plugin-persist'
import { createPinia } from 'pinia'
import './permission'

import hasPerm from './permission/hasPerm'
import print from 'vue3-print-nb'
//echarts
import * as echarts from 'echarts'

const pinia = createPinia()
//使用持久化插件
pinia.use(piniaPersist)
const app = createApp(App);
app.use(ElementPlus, {
    locale: zhCn,
}).use(router).use(pinia).use(print).mount('#app')


//挂载全局属性
app.config.globalProperties.$objCoppy = objCoppy;
app.config.globalProperties.$myconfirm = myconfirm;
//全局挂载
app.config.globalProperties.$hasPerm = hasPerm
app.config.globalProperties.$echarts = echarts;
//全局注册图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
