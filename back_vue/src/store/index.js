import Vue from 'vue'
import Vuex from 'vuex'
import menus from './modules/menus.js'
Vue.use(Vuex)

export default new Vuex.Store({
  //store/modules/menus.js 会将state的数据合并为一个
  state: {
    token:''
  },
  getters: {
  },
  mutations: {
    SET_TOKEN:(state,tokenValue)=>{
      //存储token到localStorage中
      state.token = tokenValue
      localStorage.setItem('token',tokenValue)
      sessionStorage.setItem('token',tokenValue)
    }
  },
  actions: {
  },
  modules: {
    menus
  }
})
