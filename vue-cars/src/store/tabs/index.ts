//@ts-nocheck
import { defineStore } from 'pinia'
//定义Tab类型
export type Tab = {
    title: string;
    path: string;
}
//定义state类型
export type TabState = {
    tabList: Tab[]
}
//定义store
export const tabStore = defineStore('tabStore', {
    state: (): TabState => {
        return {
            tabList: []
        }
    },
    getters: {
        getTabs(state) {
            return state.tabList
        }
    },
    actions: {
        addTab(tab: Tab) {
            //判断是否已经存在，如果不存在，才放入
            if (this.tabList.some(item => item.path === tab.path)) return;
            this.tabList.push(tab);
        }
    } ,persist: {
        enabled: true,
        strategies: [
            { storage: localStorage, paths: ['tabList'] },
        ],
    }

})
