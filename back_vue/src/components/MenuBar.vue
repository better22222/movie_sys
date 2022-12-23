<template>
  <el-menu
    :default-active="this.$store.state.menus.editableTabsValue"
    class="el-menu-vertical-demo" 

    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"

  >
    <router-link to="/index">
      <el-menu-item index="Index" @click="selectMenu()">
        <template slot="title">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </template>
      </el-menu-item>
    </router-link>

    <el-submenu :index="menu.name" v-for="(menu,index) in menuList" :key="index">
      <template slot="title">
        <i class="menu.icon"></i>
        <span slot="title">{{menu.title}}</span>
      </template>
      <router-link :to="item.path" v-for="(item,index) in menu.children" :key="index">
        <el-menu-item :index="item.name" @click="selectMenu(item)">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{item.title}}</span>

          </template>

        </el-menu-item>

      </router-link>

    </el-submenu>
  </el-menu>
</template>

<script type="text/javascript">


export default {
  data() {
    return {
      menuList: this.$getSessionStorage('menuList'),
    };
  },
  created() {

  },
  methods: {

    selectMenu(item){
      //调用vuex中的addActiveTable方法添加选项卡
      let obj = {
        title:item.title,
        name:item.name
      }
      this.$store.commit('addActiveTable',obj)
    },
    getMenuList(){
      menuList = this.$getSessionStorage('menuList')
    }
  },
};
</script>

<style scoped>
.el-menu-vertical-demo {
  height: 100%;
}
a {
  text-decoration: none;
}
</style>
