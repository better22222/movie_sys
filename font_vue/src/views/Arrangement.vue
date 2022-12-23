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
          <li class="tagList">
            <div  v-for="cName in categoryNames" :key="cName">
              &nbsp{{ cName }}
            </div>
    
          </li>

          <li>{{ filmInfo.region }}/{{ filmInfo.duration }}分钟</li>
          <li>{{ filmInfo.releaseTime }}上映</li>
          <li class="tagList">
            <div class="tag" v-for="(type,index) in film.typeList" :key="index">
              {{type}}
            </div>
          </li>


        </ul>
      </div>
    </div>

    <ul class="arrangement_time">

      <template v-for="(date, index) in film.dateList">
        <div :key="index">
          <li :class="dateNum == index ? 'active' : 'none'" @click="dateNum = index">
            {{date | getWeek}}{{ date }}
          </li>
        </div>
      </template>
    </ul>

    <div class="sessions">
      <div v-if="arrangementList.length <= 0" style="text-align:center">
        <table>该影片没有具体场次</table>
      </div>
      <div v-else>
        <template v-for="item in arrangementList">
          <ul v-if="item.date == film.dateList[dateNum]" :key="item.id">
            <li class="time">{{ item.startTime.split(" ")[1]  }}</li>
            <li class="time">{{ item.endTime.split(" ")[1] }}</li>
            <li class="type" >原版{{item.type}}</li>
            <li class="price">￥{{ item.price }}</li>
            <li class="btn" @click="toSeat(filmInfo.name,item.id,item.price,item.startTime,item.endTime,item.type,categoryNames)">
              购票
            </li>
          </ul>
        </template>

      </div>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Footer from '../components/Footer.vue'
export default {
  data() {
    return {
      film: {
        dateList: [],
        typeList:[]
      },
      fid: 0,
      filmInfo: {},
      categoryNames: [],
      arrangementList: [],
      dateNum: 0
    }
  },
  created() {
    this.fid = localStorage.getItem('fid')
    this.getFilmList()
    this.getArrangement()

  },
  methods: {
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

    //获得所有的排片列表信息
    getArrangement() {
      this.$axios.get("/arrangement/list/" + this.fid,
      ).then(response => {
        //查询下一页的数据需要和现有的数据合并为一个数组
        if (response.data.code == 1) {
          this.arrangementList = response.data.data.arrangements
          if (this.arrangementList.length > 0) {
            this.getArrangementData()
          }

        } else {
          this.$message({
            showClose: true,
            message: response.data.message,
            type: 'error'
          })
        }
      })
    },
    //获得场次的相关信息
    getArrangementData() {
      //获得所有的日期数据
      let dateArr = this.arrangementList.map(item => {
        return item.date
      })
      //dateArr数组中存放的日期有 重复数，去重
      this.film.dateList = Array.from(new Set(dateArr))

      //取出2D,3D,IMAX
      let typeArr =  this.arrangementList.map(item=>{
        return item.type
      })

      this.film.typeList = Array.from(new Set(typeArr))

    },


    Fallback() {
      this.$router.push('/')
      localStorage.clear()
    },
    toSeat(name,aid,price,start,end,type,categorys){
      this.film.name = name
      this.film.aid = aid
      this.film.price = price
      this.film.startTime = start
      this.film.endTime = end
      this.film.type = type
      this.film.category = categorys
      this.film.filmInfo = this.filmInfo
      this.$setSessionStorage('film',this.film)
      this.$router.push('/seat')
    }
  },
  components: {
    Footer,
  },
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
  font-size: 6vw;
  margin-bottom: 5vw;
}

.wrapper .header-logo .header-list span {
  font-size: 4vw;
  color: #ffc300;
  font-weight: 600;
}

.wrapper .header-logo .header-list ul {
  margin-top: 4vw;
}

.wrapper .header-logo .header-list ul li {
  margin-top: 1.5vw;
  font-size: 3.5vw;
}

.wrapper .header-logo .header-list ul .tagList {
  display: flex;
}

.wrapper .header-logo .header-list ul .cname {
  display: flex;
  font: 10px;
}

.wrapper .header-logo .header-list ul li .tag {
  width: 9vw;
  height: 4.3vw;
  background-color: #5202ff;
  font-size: 3vw;
  text-align: center;
  border-radius: 2vw;
  color: #fff;
  margin-left: 1vw;
}

.wrapper .header-logo img {
  width: 35vw;
  height: 50vw;
  margin: 2vw 0vw 0vw 3vw;
}
</style>
