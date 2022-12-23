<template>
  <el-row>
    <el-col :xl="6" :lg="7">
      <h2>欢迎使用云卡后台管理系统</h2>
      <el-image
        style="width: 160px; height: 160px"
        :src="require('@/assets/logo.png')" 
      ></el-image>
      <p>SpringBoot2.0+Vue+Mybatis</p>
    </el-col>

    <el-col :span="1">
       <el-divider  direction="vertical">
       </el-divider>
    </el-col>
    
    <el-col :xl="6" :lg="7">
      <el-form ref="loginForm" :model="loginForm" label-width="80px" lable-position="right" :rules="rules"> 
        <el-form-item label="用户名" style="width:380px" prop="username">
           <el-input v-model="loginForm.username"></el-input>
        </el-form-item>

        <el-form-item label="密码" style="width:380px" prop="password">
           <el-input  type="password"  v-model="loginForm.password"></el-input>
        </el-form-item>
        <el-form-item label="验证码" style="width:380px" prop="captchaCode">
 
           <el-input v-model="loginForm.captchaCode" maxlength="5" style="width:170px; float:left" ></el-input>
           <el-image :src="captchaImg" class="captchaImg" @click="getChapthca()" style="margin-left:28px"></el-image>
        </el-form-item>

        <el-form-item style="margin-top: 20px;">
          <el-button type="primary" @click="submitForm('loginForm')" style="margin-left:-200px; margin-right: 80px;">登录</el-button>
          <el-button>取消</el-button>
        </el-form-item>
  
      </el-form>
    </el-col>

  </el-row>
</template>


<script type="text/javascript">


export default {
  data(){
    return{
      captchaImg:'',
      loginForm:{
        username:'',
        password:'', 
        captchaCode:'', //验证码
        key:'' //验证码的key
      },
      rules:{
        username:[
          {required:true, message:'请输入用户名', trigger:'blur'}
        ],
        password:[
          {required:true, message:'请输入密码', trigger:'blur'}
        ],
        captchaCode:[
          {required:true, message:'请输入验证码', trigger:'blur'},
          {min:5, max:5, message:'验证码长度为5个字符', trigger:'blur'}
        ]
      }
    }
  },
  created(){
    this.getChapthca();
  }
  ,
  methods:{
    //获取验证码
    getChapthca(){
      this.$axios.get('/captcha').then((response)=>{
          this.captchaImg = response.data.data.captchaImg
          this.loginForm.key = response.data.data.key
      })
    },
    submitForm(fromName){
      this.$refs[fromName].validate((valid)=>{
        //如果参数为true，表示表单中的验证通过规则
        if(valid){
          //请求服务器端进行登录操作
          /*
            this.$qs.stringify(this.loginForm) 将this.loginForm对象形式转换为单表参数形式
  
          */
          this.$axios.post('/login', this.$qs.stringify(this.loginForm))
          .then((response)=>{
            //获得登录成功返回后，从响应头获得token
            const token = response.headers['token']

            //通过vuex,在vuex定义commit调用mutation中的方法将token存储至local
            this.$store.commit('SET_TOKEN',token)
            //登录成功，跳转至系统首页
            this.$router.push('/index')
            console.log(token)
          })
          .catch((error)=>{
            console.log('异常',error)
            this.loginForm ={}
            this.getChapthca()
          })
        }else{
          this.getChapthca()
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
.el-row{
  background: #fafafa;
  height: 100%;
  display:flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.el-divider--vertical{
  display:inline-block;
  width:1px;
  height:300px;		
  margin:0 8px;
  vertical-align:middle;
  position:relative;

}
.captchaImg{
  float: left;
  width:100px;
  height:40px;
  margin-left: 8px;
  border-radius: 4px;
}
</style>
