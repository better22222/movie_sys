<template>
  <div class="wrapper">

    <!-- header部分 -->
    <header>
      <p><i class="el-icon-back" @click="Fallback()"></i> {{ filmInfo.name }}</p>
    </header>

    <div class="header-logo">
      <div>
        <img :src="filmInfo.cover">
      </div>
      <div class="header-list">
        <p>{{ filmInfo.name }}</p>
        <span><i class="el-icon-star-on"></i> {{ filmInfo.grade }}</span>
        <ul>
          <span v-for="cName in categoryNames" :key="cName">
            {{ cName }}
          </span><br />
          <span><button><i class="el-icon-view" />想看</button></span>
          <span><button><i class="el-icon-star-off" />看过</button></span>
          <li>{{ filmInfo.region }}/{{ filmInfo.duration }}分钟</li>
          <li>{{ filmInfo.releaseTime }}上映</li>
          <li><button @click="toArrangement(fid)">购票</button></li>
        </ul>
      </div>
    </div>

    <el-collapse v-model="activeNames">
      <el-collapse-item title="简介" name="1">
        {{ filmInfo.intro }}
      </el-collapse-item>
    </el-collapse>


    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>观众评论</span>
        <el-button size="mini" type="primary" @click="putE()" round><span>写短评</span></el-button>
      </div>

      <el-card class="box-card" v-for="evaluate in filmEvaluateInfo" :key="evaluate">
        <div slot="header" class="comment">
          <div class="comment_header">
            <div><el-avatar :size="30" :src="evaluate.fansUrl"></el-avatar></div>
            <div>
              <span>{{ evaluate.fansName }} </span>
              <span>为这件作品打了{{ evaluate.star }}分</span>
            </div>
          </div>
          <div class="film_comment">
            {{ evaluate.comment }}
          </div>
          <!-- <el-divider></el-divider> -->
          <div class="time">
            {{ evaluate.created }}
          </div>
        </div>

      </el-card>

    </el-card>

    <el-dialog title="评论" :visible.sync="dialogFormVisible" width="80%" @closed="resetForm('evaluateForm')">
      <el-form :model="evaluateForm" :rules="evaluateForm" ref="evaluateForm" label-width="100px" class="demo-ruleForm">

        <el-input type="textarea" :rows="2" placeholder="输入评论" v-model="evaluateForm.comment">
        </el-input>

        <el-form-item label="评分" style="margin-left:-20%;margin-top:10%;" label-width="100px" prop="type">
          <div class="block">
            <el-rate v-model="evaluateForm.star"></el-rate>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" style="" @click="submitForm('evaluateForm')">提交评论</el-button>
          <el-button size="small" @click="resetForm()">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <Footer></Footer>
  </div>
</template>
  
<script>
import Footer from '../components/Footer.vue'
export default {
  data() {
    return {
      activeNames: ['1'],
      //电影id
      fid: 0,
      fans:{},
      //电影信息
      filmInfo: {},
      //电影评论信息
      filmEvaluateInfo: [],
      //电影对应的类型
      categoryNames: [],
      dialogFormVisible: false,
      evaluateForm: {}
    }
  },
  created() {
    this.fid = localStorage.getItem('fid')
    this.fans = this.$getSessionStorage('fans')
    this.getFilmList()
    this.getFilmEvaluate()

  },
  mounted() {
  },
  methods: {
    resetForm(){
      this.evaluateForm ={}
    },
    submitForm(){
      var e = {
        fid : this.fid,
        uid : this.fans.id,
        star: this.evaluateForm.star,
        comment: this.evaluateForm.comment
      }
      console.log(e)
      this.$axios.post(
        "/evaluate/putComment?fid="+this.fid+"&uid="+this.fans.id+"&star="+this.evaluateForm.star+"&comment="+this.evaluateForm.comment
      ).then(response => {
        //查询下一页的数据需要和现有的数据合并为一个数组
        if (response.data.code == 1) {
          this.getFilmEvaluate()
          this.dialogFormVisible= false
          this.$message({
            showClose: true,
            message: "添加评论成功",
            type: 'success'
          })
        } else {
          this.$message({
            showClose: true,
            message: response.data.message,
            type: 'error'
          })
        }
      })

    },
    putE() {
      this.dialogFormVisible = true
    },
    getFilmList() {
      this.$axios.get("/film/info/" + this.fid,
      ).then(response => {
        console.log(response.data.data.film)
        //查询下一页的数据需要和现有的数据合并为一个数组
        if (response.data.code == 1) {
          this.filmInfo = response.data.data.film
          this.$axios.get("/category/byId/" + this.filmInfo.categoryId).then(response => {
            this.categoryNames = response.data.data.categoryNames
          })
        } else {
          this.$message({
            showClose: true,
            message: response.data.message,
            type: 'error'
          })
        }
      })

    },
    getFilmEvaluate() {
      this.$axios.get("/evaluate/info/" + this.fid,
      ).then(response => {
        //查询下一页的数据需要和现有的数据合并为一个数组
        if (response.data.code == 1) {
          this.filmEvaluateInfo = response.data.data.filmEvaluate.records
        } else {
          this.$message({
            showClose: true,
            message: response.data.message,
            type: 'error'
          })
        }
      })
    },
    //跳转至电影排片页面
    toArrangement(fid) {
      this.$router.push({
        path: '/arrangement',
        query: {
          fid: fid
        }
      })
    },
    Fallback() {
      this.$router.push('/')
      localStorage.clear()
    }
  },
  components: {
    Footer,
  },
}
</script>
  
<style scoped>
.wrapper .box-card {
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
  /* padding-bottom: 20vw; */
}

.wrapper .box-card span {
  font-size: 3vw;
}

.wrapper .box-card .clearfix {
  width: 90vw;
}

.wrapper .box-card .clearfix .el-button {
  border: none;
  height: 7vw;
  background-color: #ff8800;
  float: right;

}

.wrapper .box-card .clearfix .el-button span {
  font-size: 3vw;

}

.wrapper .box-card .comment .comment_header {
  display: flex;
  justify-content: space-between;
  width: 80vw;
}

.wrapper .box-card .comment .time {
  font-size: 2vw;
  width: 80vw;
  text-align: right;

}

.wrapper .box-card .comment .film_comment {
  margin-top: 5vw;
  margin-left: 1vw;
  font-size: 3vw;
}

.wrapper .el-collapse {
  margin: 5vw;
}


/****************** 总容器 ******************/
.wrapper {
  width: 100%;
  height: 100%;
}

/****************** header部分 ******************/
.wrapper header {
  width: 100%;
  height: 12vw;
  /* background-color: #d3dce6; */
  background-color: #181818;
  color: #fff;
  font-size: 4.8vw;
  padding-left: 2vw;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 1000;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.wrapper .header-logo .header-list button {
  border: none;
  border-radius: 1.5vw;
  background-color: #f5f5f5;
  margin-right: 1vw;

}

.wrapper .sessions ul {
  width: 100%;
  /* background-color: #ffc300; */
  display: flex;
  justify-content: space-around;
  flex-shrink: 1;
  border-bottom: 0.1vw solid #cecece;
}

/* .wrapper .sessions ul button {
                                                                                                                                                                  font-size: 3.5vw;
                                                                                                                                                                  background-color: #dedede;
                                                                                                                                                                  border: 0vw none #fff;
                                                                                                                                                                  color: #2d2d2d;
                                                                                                                                                                  padding: 1vw 3vw;
  
                                                                                                                                                                  border-radius: 10vw;
                                                                                                                                                                } */
/* .wrapper .sessions .active {
                                                                                                                                                  background-color: #0068ff;
                                                                                                                                                  color: #fff;
                                                                                                                                                } */

.wrapper .sessions ul li {
  display: flex;
  width: 20vw;
  /* background-color: aqua; */
  align-items: center;
  margin: 5vw 3vw;
}

.wrapper .sessions .time {
  font-size: 5.5vw;
  font-family: Arial, Helvetica, sans-serif;
}

.wrapper .sessions .type {
  font-size: 3vw;
}

.wrapper .sessions .price {
  font-size: 5vw;
  color: #ffc300;
  justify-content: flex-end;
  font-family: Arial, Helvetica, sans-serif;
}

.wrapper .sessions .btn {
  border: none;
  border-radius: 1.5vw;
  padding: 1vw 1vw;
  background-color: #0068ff;
  font-size: 3vw;
  color: #fff;
  justify-content: center;
}

.wrapper .arrangement_time {
  width: 100%;
  height: 5vw;
  margin-top: 4vw;
  margin-bottom: 2vw;

  display: flex;
  justify-content: space-around;
  align-items: center;
}

.wrapper .arrangement_time .active {
  border-bottom: 3px solid #5202ff;
}

.wrapper .arrangement_time li {
  font-size: 3.5vw;
  color: #292929;
  padding: 3vw 0;
}

.wrapper .header-logo {
  width: 100%;
  height: 58vw;
  /*使用上外边距避开header部分*/
  margin-top: 12vw;

  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  background-color: #d3dce6;
  color: #2d2d2d;
}

.wrapper .header-logo .header-list {
  margin: 2vw 3vw 0vw 0vw;
  width: 100%;
  /* background-color: violet; */
  padding: 0vw 2vw 2vw 4vw;
  height: 48vw;
}

.wrapper .header-logo .header-list p {
  font-size: 5vw;

}

.wrapper .header-logo .header-list span {
  font-size: 4vw;
  color: #ffc300;
  /* font-weight: 600; */
}


.wrapper .header-logo .header-list ul span {
  margin-top: 11vw;
}

.wrapper .header-logo .header-list ul li {
  margin-top: 1.5vw;
  font-size: 3.5vw;
}

.wrapper .header-logo .header-list ul li .tag {
  width: 7vw;
  height: 4.3vw;
  background-color: #5202ff;
  font-size: 3vw;
  text-align: center;
  border-radius: 2vw;
  color: #fff;
}

.wrapper .header-logo img {
  width: 35vw;
  height: 50vw;
  margin: 2vw 0vw 0vw 3vw;
}
</style>
  