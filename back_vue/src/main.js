import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// import axios from 'axios'
import axios from '@/api/request.js'
import qs from 'qs'
import *as echarts from 'echarts'
import gobal from './globalFunction.js'
//使用Element
Vue.use(Element)
Vue.prototype.$echarts = echarts
//挂载
Vue.prototype.$axios = axios
Vue.prototype.$qs = qs
Vue.prototype.$getCurDate = getCurDate
Vue.prototype.$setSessionStorage = setSessionStorage
Vue.prototype.$getSessionStorage = getSessionStorage
Vue.prototype.$removeSessionStorage = removeSessionStorage
Vue.prototype.$setLocalStorage = setLocalStorage
Vue.prototype.$getLocalStorage = getLocalStorage
Vue.prototype.$removeLocalStorage = removeLocalStorage

//mock数据
// require('./mock')

//导入common.js工具类
import {
  getCurDate,
  setSessionStorage,
  getSessionStorage,
  removeSessionStorage,
  setLocalStorage,
  getLocalStorage,
  removeLocalStorage,
  } from './common.js'

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
