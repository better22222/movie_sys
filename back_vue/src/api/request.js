//axios全局的设置
import axios from 'axios'
import Element from 'element-ui'
import router from '@/router/index.js'
import {Promise} from 'core-js'

//设置axios基础路径：
// //java服务器端： http://localhost:10001/system/login
axios.defaults.baseURL = '/api'



const myAxios = axios.create({
    //请求超时时间
    timeout: 5000,
    // // 请求头信息
    // headers:{
    //     'Content-Type':'application/json;charset=utf-8',
    //     'token':"123"
    // },   
})

//设置axios的请求参数（vue前端到服务器的拦截器设置
myAxios.interceptors.request.use(config => {
    //需要携带token，将登录获得的token设置到request的请求头中
    //携带java服务器中
    config.headers['token'] = localStorage.getItem('token')
    return config
})

//设置axios响应的拦截器（服务器端到前端
myAxios.interceptors.response.use(
    response => {
        //所有从服务器到前端的响应都会被拦截
        const result = response.data
        //http状态码：200成功，3xx重定向，400找不到，500找不到服务器
        if(result.code == 200){
            //http请求成功
            return response
        }else{
            //请求失败
            Element.Message({
                showClose: true,
                message: result.message?result.message:"系统异常",
                type: 'error'
            });
            return Promise.resolve(console.error.message)
        }
    },error =>{
        console.log(error)
        Element.Message({
            showClose: true,
            message: error.message,
            type: 'error'
        })
        return Promise.reject(error)
    }
)

export default myAxios