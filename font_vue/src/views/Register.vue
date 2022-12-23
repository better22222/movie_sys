<template>
  <div class="wrapper">
    <!-- header部分 -->
    <header>
      <p>用户注册</p>
    </header>

    <!-- 表单部分 -->
    <ul class="form-box">
      <li>
        <div class="title">
          手机号码：
        </div>
        <div class="content">
          <input type="text" @blur="checkUserId" v-model="user.id" placeholder="输入手机号码">
        </div>
      </li>
      <li>
        <div class="title">
          密码：
        </div>
        <div class="content">
          <input type="password" v-model="user.password" placeholder="输入密码">
        </div>
      </li>
      <li>
        <div class="title">
          确认密码：
        </div>
        <div class="content">
          <input type="password" v-model="confirmPassword" placeholder="输入确认密码">
        </div>
      </li>
      <li>
        <div class="title">
          用户名称：
        </div>
        <div class="content">
          <input type="text" v-model="user.username" placeholder="输入用户昵称">
        </div>
      </li>
      <li>
        <div class="title">
          性别：
        </div>
        <div class="content" style="font-size: 3.5vw;">
          <input type="radio" name="sex" v-model="user.userSex" value="1" style="width:6vw;height: 3.2vw;">男
          <input type="radio" name="sex" v-model="user.userSex" value="0" style="width:6vw;height: 3.2vw;">女
        </div>
      </li>
    </ul>

    <div class="button-login">
      <button @click="register">注册</button>
    </div>

    <!-- 底部菜单部分 -->
    <Footer></Footer>
  </div>
</template>

<script>
import Footer from '../components/Footer.vue'

export default {
  name: 'Register',
  data() {
    return {
      //定义JSON用户对象user，注册请求直接发送至 服务器端
      user: {
        id: '',
        password: '',
        username: '',
        userSex: 1,
      },
      confirmPassword: '', //确认密码无需录入数据库，不需要传递给服务器端，独立定义一个变量
    }
  },
  components: {
    Footer,
  },
  methods: {
    //验证手机号是否重复
    checkUserId() {
      //路径 /user/check/手机号
      this.$axios.get("/fans/check/" + this.user.id).then(response => {
        if (response.data.code == 20005) {
          this.$message({
            showClose: true,
            message: '手机已被注册',
            type: "warning",
            onClose: () => {
              return
            }
          }
          )
        }
      })
    },
    register() {
      this.checkUserId(),
        console.log(this.user)
      this.$axios.post('/fans/register', this.user).then(response => {
        let fans = response.data.data.fans
        if (response.data.code == 1) {
          this.$setSessionStorage('token', fans.token)
          this.$setSessionStorage('fans', fans)
          this.$setLocalStorage('fans', fans)
          this.$router.push('/')
          this.$message({
            showClose: true,
            message: '注册成功',
            type: "success",
            onClose: () => {
              return
            }
          }
          )
        }
      })
    }
  }

}

</script>

<style scoped>
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
  margin-top: 0;
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
  color: #666;
  background-color: #eee;
  border-radius: 4px;

  border: none;
  outline: none;
  border: solid 1px #ddd;
}
</style>
