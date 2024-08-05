//@ts-nocheck
import { defineStore } from 'pinia'
import { getInfoApi } from '@/api/login/index.ts'
//定义一个store
export const loginStore = defineStore('loginStore', {
    state: () => {
        return {
            token: '',
            userId:'',
            codeList: []
        }
    },
    getters: {
        getToken(state) {
            return state.token
        },
        getUserId(state) {
            return state.userId
        }
    },
    actions: {
        setToken(token: string) {
            this.token = token
        },
        setUserId(userId: string) {
            this.userId = userId
        },
        getInfo() {
            return new Promise((resolve, reject) => {
                getInfoApi(this.userId).then((res) => {
                    if (res && res.code == 200) {
                        this.codeList = res.data.permissons
                    }
                    resolve(this.codeList)
                }).catch((error) => {
                    reject(error)
                })
            })
        }
    },
    persist: {
        enabled: true,
        strategies: [
            { storage: localStorage, paths: ['token','userId'] },
        ],
    }
})
