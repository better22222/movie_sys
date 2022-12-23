<template>
  <div id="app">
    <!--路由出口-->
    <router-view/>

  </div>

</template>

<script>
export default {
  data(){
    return{

    }
  },
  //解决刷新页面 浏览器没有保存激活的tab选项卡
  watch:{
    $route(to,from){
      if(to.path != '/login'){
        let obj = {
          name: to.name,
          title: to.meta.title,
        }
        this.$store.commit('addActiveTable',obj)
      }

    }
  }
}
</script>

<style>
html,
body,
#app{
  font-family: 'Microsoft Yahei', sans-serif;
  height:100%;
  padding:0;
  margin:0;
  color:#494949;
  font-size:15px;
}
</style>
