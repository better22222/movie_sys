<template>
  <!-- 底部菜单部分 -->
  <ul class="footer">
    <li @click="toHome()">
      <i class="el-icon-house" @click="toHome()"></i>
      <p>首页</p>
    </li>

    <li>
      <i class="el-icon-shopping-cart-1" @click="toCompass()"></i>
      <p>会员</p>
    </li>
    <li @click="toOrderList">
      <i class="el-icon-mobile" @click="toOrderList()"></i>
      <p>订单</p>
    </li>
    <li @click="toUserCenter">
      <i class="el-icon-user" @click="toUserCenter()"></i>
      <p>我的</p>
    </li>
  </ul>
</template>

<script>
  export default {
    name: 'Footer',
    data() {
      return {}
    },
    methods: {
      toCompass() {
        this.$router.push('/vip')
      },
      toHome() {
        if (this.$route.path == '/index' || this.$route.path == '/') {
          return
        }
        this.$router.push('/')
      },
      toOrderList() {
        /*
                                        $router：路由操作对象，只写。 需要对路由进行操作时候设置，例如路由跳转。
                                        $route: 路由信息对象，只读。 获取路由相关信息。 例如获取路由传递参数、或获得当前路由地址
                                                   */
        if (this.$route.path == '/orderList') {
          return
        }
        this.$router.push('/orderList')
      },
      toUserCenter() {
        this.$router.push('/userCenter')
      },
    },
  }
</script>

<style scoped>
  /****************** 底部菜单部分 ******************/
  .wrapper .footer {
    width: 100%;
    height: 14vw;
    border-top: solid 1px #ddd;
    background-color: #fff;
    position: fixed;
    left: 0;
    bottom: 0;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
  }
  .wrapper .footer li {
    /*li本身的尺寸完全由内容撑起*/
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: #2d2d2d;
    user-select: none;
    cursor: pointer;
  }
  .wrapper .footer li p {
    font-size: 2.8vw;
  }
  .wrapper .footer li i {
    font-size: 5vw;
  }
</style>