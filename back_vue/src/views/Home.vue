<template>
  <el-container>
    <el-aside width="200px">

      <MenuBar ></MenuBar>
    </el-aside>

    <el-container>
      <el-header style="height: 55px;">
        <strong >后台管理系统</strong>
        <div class="header-avatar">
          
          <el-avatar
            class="el-avatar"
           
            :src="userInfo.avatar">
          </el-avatar>

          <el-dropdown>
            <span class="el-dropdown-link">
              {{ userInfo.username}}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item >
                <router-link to='/userCenter'>个人中心</router-link>
              </el-dropdown-item>
              <el-dropdown-item >
                <router-link to='/changeP' >修改密码</router-link>
              </el-dropdown-item>
              <el-dropdown-item @click.native="logout()">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </div>
      </el-header>

      <el-main>
        <Tab></Tab>
        <div style="margin:0 15px;">
          <!-- 子路由出口 -->
          <router-view/>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script type="text/javascript">
import MenuBar from '@/components/MenuBar.vue'
import Tab from '@/components/Table.vue'
export default {
  data() {
    return {
      //登录用户信息
      userInfo: {
        id:'',
        username: "",
        avatar: '',
      },
      componentKey:false,
    };
  },
  created() {
    //在生命周期钩子函数中获得用户信息
    this.getUserInfo()

  },
  methods: {
    logout(){
      this.$axios.post('/logout').then((response) =>{
          //清空localstorage与sessonstorage的用户信息  
          localStorage.clear()
          sessionStorage.clear()
          this.$store.commit('resetState')
          this.$router.push('/login')
      })
      this.$store.state.menus.editableTabs = []
    },
    
    //请求服务器端，获得用户的纤细信息：路径：/user/userInfo
    getUserInfo(){
      this.$axios.get('/user/userInfo').then(response =>{
        this.userInfo = response.data.data
      })
    },


  },
  components:{
    //注册组件Menu后，才可以在页面中使用Menu组件
    MenuBar:MenuBar,
    Tab:Tab
  }
};
</script>

<style scoped>
.el-container {
  padding: 0;
  margin: 0;
  height: 100%;
}
.header-avatar {
  float: right;
  width: 120px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.el-header{
  background: rgb(217, 222, 222);
  color: #333;
  text-align: left;
  line-height: 60px;
}
.el-dropdown-link{
  cursor: pointer;
  color: #494856;
}

.el-aside{
  background-color: rgb(154, 175, 176);
  color: #333;
  line-height: 200px;
  text-align: left;
}

.el-main{
  color: #333;
  padding: 0;
}

a{
  text-decoration: none;
}

</style>
