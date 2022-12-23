import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import qs from 'qs'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import filters from './filters/index.js'
import '../src/assets/iconFont/iconfont.css'


/*导入 common.js工具类  */
import {
  getCurDate,
  setSessionStorage,
  getSessionStorage,
  removeSessionStorage,
  setLocalStorage,
  getLocalStorage,
  removeLocalStorage,
} from './common.js'

//使用ElementUI
Vue.use(ElementUI)

axios.defaults.baseURL="/api"
//设置axios拦截器，每次请求都带有token
axios.interceptors.request.use(config=>{
  config.headers['token'] = getSessionStorage('token')
  return config
})

//在Vue.filter 方法中需要注册，所有的全局过滤器
Object.keys(filters).forEach((key,index)=>{
  Vue.filter(key,filters[key])
})


Vue.config.productionTip = false

//将axios挂载vue上
Vue.prototype.$axios = axios
Vue.prototype.$qs = qs
Vue.prototype.$getCurDate = getCurDate
Vue.prototype.$setSessionStorage = setSessionStorage
Vue.prototype.$getSessionStorage = getSessionStorage
Vue.prototype.$removeSessionStorage = removeSessionStorage
Vue.prototype.$setLocalStorage = setLocalStorage
Vue.prototype.$getLocalStorage = getLocalStorage
Vue.prototype.$removeLocalStorage = removeLocalStorage

new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app')
