//全局函数
import Vue from 'vue'
//mixin 多个组件可以共享数据的方法
Vue.mixin({
    methods:{
        //验证权限方法
        hasAuth(perm){ //当前操作所要进行的权限 
            //的到当前用户所有的权限信息
            var authority =  this.$store.state.menus.permList
            return  authority.indexOf(perm) > -1 
        }
    }
})