import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse, AxiosRequestHeaders } from "axios";
// @ts-ignore
import { ElMessage } from 'element-plus';

//axios请求配置
//配置axios

const config = {
    // @ts-ignore
    baseURL: process.env.BASE_API,
    timeout: 10000,
    withCredentials: true //解决sesson不一致的问题
}
//定义返回值类型
export interface Result<T = any> {
    code: number;
    msg: string;
    data: T;
}

class Http {
    //axios实例
    private instance: AxiosInstance;
    //构造函数里面初始化
    constructor(config: AxiosRequestConfig) {
        this.instance = axios.create(config)
        //定义拦截器
        this.interceptors()
    }
    //拦截器
    private interceptors() {
        //axios发送请求之前的处理
        // @ts-ignore
        this.instance.interceptors.request.use((config: AxiosRequestConfig) => {
            //在请求头部携带token
            // let token = sessionStorage.getItem('token');
            let token = '';
            if (token) {
                config.headers!['token'] = token
                //把token放到headers里面
                // (config.headers as AxiosRequestHeaders).token = token
            }
            console.log(config)
            return config;
        }, (error: any) => {
            error.data = {}
            error.data.msg = '服务器异常，请联系管理员!'
            return error;
        })
        //axios请求返回之后的处理
        //请求返回之后的处理
        this.instance.interceptors.response.use((res: AxiosResponse) => {
            // console.log(res.data)
            if (res.data.code != 200) {
                ElMessage.error(res.data.msg || '服务器出错!')
                return Promise.reject(res.data.msg || '服务器出错')
            } else {
                return res.data
            }
        }, (error) => {
            console.log('进入错误')
            error.data = {};
            if (error && error.response) {
                switch (error.response.status) {
                    case 400:
                        error.data.msg = '错误请求';
                        ElMessage.error(error.data.msg)
                        break
                    case 401:
                        error.data.msg = '未授权，请重新登录';
                        ElMessage.error(error.data.msg)
                        break
                    case 403:
                        error.data.msg = '拒绝访问';
                        ElMessage.error(error.data.msg)
                        break
                    case 404:
                        error.data.msg = '请求错误,未找到该资源';
                        ElMessage.error(error.data.msg)
                        break
                    case 405:
                        error.data.msg = '请求方法未允许';
                        ElMessage.error(error.data.msg)
                        break
                    case 408:
                        error.data.msg = '请求超时';
                        ElMessage.error(error.data.msg)
                        break
                    case 500:
                        error.data.msg = '服务器端出错';
                        ElMessage.error(error.data.msg)
                        break
                    case 501:
                        error.data.msg = '网络未实现';
                        ElMessage.error(error.data.msg)
                        break
                    case 502:
                        error.data.msg = '网络错误';
                        ElMessage.error(error.data.msg)
                        break
                    case 503:
                        error.data.msg = '服务不可用';
                        ElMessage.error(error.data.msg)
                        break
                    case 504:
                        error.data.msg = '网络超时';
                        ElMessage.error(error.data.msg)
                        break
                    case 505:
                        error.data.msg = 'http版本不支持该请求';
                        ElMessage.error(error.data.msg)
                        break
                    default:
                        error.data.msg = `连接错误${error.response.status}`;
                        ElMessage.error(error.data.msg)
                }
            } else {
                error.data.msg = "连接到服务器失败";
                ElMessage.error(error.data.msg)
            }
            return Promise.reject(error)
        })
    }

    /* GET 方法 */
    get<T>(url: string, params?: object): Promise<T> {
        return this.instance.get(url, { params })
    }
    /* POST 方法 */
    post<T>(url: string, data?: object): Promise<T> {
        return this.instance.post(url, data)
    }
    /* PUT 方法 */
    put<T>(url: string, data?: object): Promise<T> {
        return this.instance.put(url, data)
    }
    /* DELETE 方法 */
    delete<T>(url: string): Promise<T> {
        return this.instance.delete(url)
    }

    //图片上传
    upload<T = Result>(url: string, params?: object): Promise<T> {
        return this.instance.post(url, params, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
    }
}
export default new Http(config)
