<template>
  <div class="wrapper">
    <!-- header部分 -->
    <header>
      <p>用户登陆</p>
    </header>

    <!-- 表单部分 -->
    <ul class="form-box">
      <li>
        <div class="title">
          手机号码：
        </div>
        <div class="content">
          <input type="text" v-model="fans.id" placeholder="手机号码">
        </div>
      </li>
      <li>
        <div class="title">
          密码：
        </div>
        <div class="content">
          <input type="password" v-model="fans.password" placeholder="密码">
        </div>
      </li>
    </ul>

    <div class="button-login">
      <button @click="login">登陆</button>
    </div>
    <div class="button-register">
      <button @click="register">去注册</button><br><br>

    </div>

    <!-- 底部菜单部分 -->
    <Footer></Footer>
  </div>
</template>

<script>
import Footer from '../components/Footer.vue'

export default {
  name: 'Login',
  data() {
    return {
      fans: {
        id: '', //声明变量双向绑定 文本框输入用户Id
        password: '', //声明变量双向绑定  密码框输入password
      },
    }
  },
  methods: {
    login() {
      this.$axios.post('/fans/login', this.fans).then(response => {
        let fans = response.data.data.fans
        if (response.data.code == 20001) {
          this.$message({
            showClose: true,
            message: '用户不存在',
            type: "warning",
            onClose: () => {
              return
            }
          })
        } else if (response.data.code == 20003) {
          this.$message({
            showClose: true,
            message: '密码不存在',
            type: "warning",
            onClose: () => {
              return
            }
          })
        } else if (response.data.code == 20004) {
          this.$message({
            showClose: true,
            message: '用户账户被禁用',
            type: "warning",
            onClose: () => {
              return
            }
          })
        } else if (response.data.code == 1) {
          this.$setSessionStorage('token', fans.token)
          this.$setSessionStorage('fans', fans)
          this.$setLocalStorage('fans',fans)
          this.$router.push('/')
          this.$message({
            showClose: true,
            message: '登录成功',
            type: "success",
            onClose: () => {
              return
            }
          })
        }


      })
    },
    register() {
      this.$router.push('/register')
    },
  },
  components: {
    Footer,
  },
}
</script>

<style>
/****************** 总容器 ******************/
.wrapper {
  width: 100%;
  height: 100%;
}

/****************** header部分 ******************/
.wrapper header {
  width: 100%;
  height: 12vw;
  background-color: #eff3f4;
  color: #2d2d2d;
  font-size: 4.8vw;

  position: fixed;
  left: 0;
  top: 0;
  z-index: 1000;

  display: flex;
  justify-content: center;
  align-items: center;
}

/****************** 表单部分 ******************/
.wrapper .form-box {
  width: 100%;
  margin-top: 20vm;

  padding: 20vw 0 0 0;
}

.wrapper .form-box li {
  box-sizing: border-box;
  padding: 4vw 3vw 0 3vw;
  display: flex;
  align-items: center;
}

.wrapper .form-box li .title {
  flex: 0 0 18vw;
  font-size: 3.5vw;
  font-weight: 700;
  color: #666;
}

.wrapper .form-box li .content {
  flex: 1;
}

.wrapper .form-box li .content input {
  border: none;
  outline: none;
  width: 100%;
  height: 4vw;
  font-size: 3.5vw;
}

.wrapper .button-login {
  width: 100%;
  box-sizing: border-box;
  padding: 4vw 3vw 0 3vw;
  margin-top: 5vw;
}

.wrapper .button-login button {
  width: 100%;
  height: 10vw;
  font-size: 3.8vw;
  font-weight: 700;
  color: #fff;
  background-color: #138bde;
  border-radius: 4px;
  border: none;
  outline: none;
}

.wrapper .button-register {
  width: 100%;
  box-sizing: border-box;
  padding: 4vw 3vw 0 3vw;
}

.wrapper .button-register button {
  width: 100%;
  height: 10vw;
  font-size: 3.8vw;
  font-weight: 700;
  /*与上面登陆按钮不同的只有颜色、背景色、边框不同*/
  color: #757575;
  background-color: #f3f3f3;

  border-radius: 4px;

  border: none;
  outline: none;
}

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
  justify-content: space-around;
  align-items: center;
}

.wrapper .footer li {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  color: #999;
  user-select: none;
  cursor: pointer;
}

.wrapper .footer li p {
  font-size: 3vw;
}

.wrapper .footer li i {
  font-size: 5vw;
}
</style>
