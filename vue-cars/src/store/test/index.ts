import { defineStore } from 'pinia'
//定义store
export const testStore = defineStore('testStore', {
    state: () => {
        return {
            count: 0
        }
    },
    getters: {
        getCount(state) {
            return state.count
        }
    },
    actions: {
        setCount(count: number) {
            console.log(count)
            this.count = count;
        }
    }
})
