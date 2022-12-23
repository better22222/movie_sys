<template>
  <div class="wrapper">
    <!-- header部分 -->
    <div class="header">

      <div class="pic_show">
        <img :src="fans.avatar" alt="" />
      </div>
      <div class="name">{{ fans.username }}</div>
      <div class="share"><el-button round type="success" size="mini"><i class="el-icon-more"></i>&nbsp;|&nbsp;<i
            class="el-icon-circle-close" /></el-button></div>
    </div>

    <div class=" card">
      <div class="show">
        <div class="pay_font">我的订单</div>
        <ul>
          <li>
            <i class="iconfont icon-dianyingpiao" />
            <p>电影票</p>
          </li>
          <li>
            <i class="iconfont icon-yanchu1" />
            <p>演出票</p>
          </li>
          <li>
            <i class="iconfont icon-shoucang" />
            <p>收藏夹</p>
          </li>
          <li>
            <i class="iconfont icon-daipingjia" />
            <p>待评价</p>
          </li>
        </ul>
      </div>

    </div>


    <div class="my_order">
      <div class="order_list">
        <ul>
          <li>
            <i class="iconfont icon-youhuiquan" />
            <p>优惠券</p>
          </li>
          <li>
            <i class="iconfont icon-yingchengka" />
            <p>影城卡</p>
          </li>
          <li>
            <i class="iconfont icon-qiapian" />
            <p>票夹</p>
          </li>
          <li>
            <i class="iconfont icon-icon" />
            <p>足迹</p>
          </li>
        </ul>

      </div>
    </div>

    <div class="service">
      <div class="servicelist">
        <div class="my_service">我的服务</div>
        <ul>
          <li>
            <i class="iconfont icon-xiaoxizhongxin" />
            <p @click="editpersonal()">个人信息<i class="el-icon-arrow-right"></i></p>

          </li>
          <li>
            <i class="iconfont icon-xiangkan" />
            <p>我的想看 <i class="el-icon-arrow-right"></i></p>

          </li>
          <li>
            <i class="iconfont icon-zhifushezhi" />
            <p>支付设置 <i class="el-icon-arrow-right"></i></p>

          </li>
          <li>
            <i class="iconfont icon-guanyanren" />
            <p>常用观演人 <i class="el-icon-arrow-right"></i></p>

          </li>
          <li>
            <i class="iconfont icon-kefu" />
            <p @click="exit()">退出登录<i class="el-icon-arrow-right"></i></p>

          </li>
        </ul>
      </div>
    </div>

    <!-- <el-dialog title="个人信息" :visible.sync=" dialogFormVisible" width="90vw" >

            <el-form  :model="fans"  ref="fans" label-width="50vw" class="demo-ruleForm">
              <el-form-item label="头像" label-width="10">       
                <el-upload
                class="avatar-uploader"
                action="/api/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :headers="headers">
                <img v-if="fans.avatar" :src="fans.avatar" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item>
         
                <el-form-item label="用户名" prop="username" label-width="25">
                <el-input v-model="fans.username"  auto-complete="off"></el-input> 
                </el-form-item>

                <el-form-item label="密码"  prop="password" label-width="20">
                    <el-input v-model="fans.password"   auto-complete="off"></el-input> 
                </el-form-item>          
                <el-form-item>

                <el-button type="primary" @click="submitForm('fans')">保存</el-button>
                </el-form-item>
            </el-form>
      </el-dialog> -->

    <!-- 底部菜单部分 -->
    <Footer></Footer>
  </div>
</template>

<script>

import Footer from '../components/Footer.vue'
export default {
  data() {
    return {
      fans: {},
      fans: this.$getSessionStorage('fans'),
      // dialogFormVisible:false,  
      // headers: {
      //       token: localStorage.getItem('token'),
      //   },

    }
  },
  created() {


  },
  methods: {

    exit() {
      localStorage.clear()
      sessionStorage.clear()
      this.$message({
        showClose: true,
        message: '登出成功',
        type: 'success'
      })
      this.$router.push('/login')
    },
    editpersonal() {
      this.$router.push('/personal')
    },
    // handleAvatarSuccess(response, file) {
    //       this.fans.avatar=response.data;
    // },


  },
  components: {
    Footer,
  },
}
</script>

<style  scoped>
/****************** 总容器 ******************/
.wrapper {
  width: 100%;
  height: 100%;
  color: #4e4e4e;
  /* background-color: rgb(221,224,220); */
  background-color: #F5f5f5;
}

/****************** header ******************/
.wrapper .header {
  width: 100%;
  height: 20vw;
  display: flex;
  align-items: center;
  justify-content: flex-start;

}

.wrapper .header .pic_show {
  margin-left: 5vw;
  width: 15vw;
}

.wrapper .header .pic_show img {
  border-radius: 15vw;
  width: 15vw;
  height: 15vw;

}

.wrapper .header .name {
  width: 80vw;
  margin-left: 5vw;

}

/* .wrapper .header .share{
    display: flex;
    align-items: center;
  } */
.wrapper .header .share .el-button--success {
  border: none;
  /* background-color: #DCDCDC; */
  background-color: rgb(196, 194, 191);
  width: 18vw;
  color: white;
  float: right;
}




.wrapper .card {
  width: 97%;
  height: 25vw;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  /*要使用align-content。10个子元素将自动换行为两行，而且两行作为一个整体垂直居中*/
  align-content: center;
  /* background-color: rgb(228,228,225); */
  /* background-color: rgb(229,227,221); */
  background-color: white;
  border: 1px solid white;
  border-radius: 1em;
  margin: 1vw auto;

}

.wrapper .card .show {
  width: 100%;
}

.wrapper .card .show ul {
  display: flex;
  justify-content: space-around;
  height: 20vw;
  align-items: center;
}

.wrapper .card .show .pay_font {
  height: 5vw;
  padding-left: 5vw;

}

.wrapper .card .show ul li {
  align-items: center;
  font-weight: 100;
}

.wrapper .card .show ul li i {
  font-size: 7vw;
  padding-left: 3vw;
}


.wrapper .my_order {
  width: 97%;
  margin: 3vw auto;
  height: 30vw;
  /*此三个样式组合，可以保证背景图片充满整个容器*/
  box-sizing: border-box;
  color: black;
  background-color: white;
  border: 1px solid white;
  border-radius: 1em;


}

.wrapper .my_order .order_list {
  padding-top: 5vw;

}

.wrapper .my_order .order_list ul {
  display: flex;
  justify-content: space-around;
  height: 30vm;
  align-items: center;
  font-weight: 100;
}

.wrapper .my_order .order_list ul li i {
  font-size: 8vw;
  margin-left: 1vw;
}


/****************** 超级会员部分 ******************/
.wrapper .service {
  /*这里也设置容器宽度95%，不能用padding，因为背景色也会充满padding*/
  width: 95%;
  margin: 0 auto;
  height: 60vw;
  margin-top: 2vw;
  background-color: white;
  border: 1px solid white;
  border-radius: 1em;
  display: flex;
  justify-content: space-around;

}

.wrapper .service .servicelist {
  width: 100%;


}

.wrapper .service .servicelist .my_service {
  width: 30vw;
  height: 5vw;
  padding-left: 5vw;
}

.wrapper .service .servicelist ul {
  /* display: flex; */
  /* justify-content: space-around; */
  height: 50vw;
  /* align-items:center;  */

}

.wrapper .service .servicelist ul li {
  width: 90vw;
  height: 5vw;
  font-size: 4vw;
  margin-top: 5vw;
  margin-left: 3vw;
  border-bottom: 1px solid #f5f5f5;
}

.wrapper .service .servicelist ul li i {
  float: left;
}

.wrapper .service .servicelist ul li p i {
  float: right;
}





/****************** 推荐方式部分 ******************/
.wrapper .exit {
  width: 100%;
  height: 20vw;

  /* background-color: #644f1b; */
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin: 0 1vw;
  ;
}

.wrapper .exit .el-button {
  width: 90vw;
  border-bottom: 1px solid #f5f5f5;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 80px;
  height: 80px;
  display: block;
}
</style>