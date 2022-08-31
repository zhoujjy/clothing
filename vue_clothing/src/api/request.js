import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import router from '@/router'
import tokenUtils from '@/utils/tokenUtils'

// create an axios instance
const service = axios.create({
  baseURL:'http://localhost:8081/',
  // baseURL:'/api',
  timeout: 5000 // request timeout
})

// http request 拦截器
service.interceptors.request.use(
  config => {
    const token = tokenUtils.getToken()
    if (token !==undefined ) { // 判断是否存在token，如果存在的话，则每个http header都加上token
      config.headers.token = token  //请求头加上token
    }
    return config
  },
  err => {
    return Promise.reject(err)
  })


// http response 拦截器
service.interceptors.response.use(response => {
  
    //当后端返回登录异常时，跳转到登录页面，并返回Promise信息
   if(response.data.code === 501){
    router.push({name:'login'})
    tokenUtils.removeToken()
    Message.error(response.data.msg)
    return Promise.reject(response.data.msg)
   }
   if(response.data.code === 502){
     Message.error(response.data.msg)
     return
   }
   return response
    
  },
  //接口错误状态处理，也就是说无响应时的处理
  error => {
    Message.error("请求超时，请检查网络是否正常")
    return Promise.reject(error.response.status) // 返回接口返回的错误信息
  })


export default service
