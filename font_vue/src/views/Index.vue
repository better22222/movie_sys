<template>
  <div class="wrapper">
    <!-- header部分 -->
    <div class="header">
      <div class="icon-location-box">
        <div class="icon-location"></div>
      </div>
      <div class="location-text">
        <i class="el-icon-location-outline"></i>
        上海浦东蓝村路<i class="fa fa-caret-down"></i>
      </div>
      <span style="position: absolute; right:10%">{{ fans.username }}</span>
    </div>

    <!-- search部分 -->
    <!-- 搜索框部分） 
				-->
    <template>
      <el-autocomplete style="width:90%;margin-left:5%" v-model="keywords" :fetch-suggestions="querySearchAsync" placeholder="请输入内容"
        :trigger-on-focus="false" @select="handleSelect">
        <template v-slot="{ item }">
          <span>{{ item.name }}</span>
        </template>
      </el-autocomplete>

    </template>


    <!--轮播图-->
    <el-carousel :interval="4000" height="44vw" style="margin:2vw">
      <el-carousel-item v-for="p in poster.slice(0, 3)" :key="p.id">
        <el-image :src="p.url" :fit="fit"></el-image>
      </el-carousel-item>
    </el-carousel>


    <!-- TODO:电影分类部分 -->
    <ul class=" foodtype" id="categoryElement">
      <!-- 循环迭代 categoryList数组，将服务器获得的分类信息，循环显示出来 -->
      <li v-for="c in categoryList" :key="c.id">
        <img :src="c.url" @click="toFenlei(c.id)">
        <p> {{ c.name }} </p>
      </li>
    </ul>
    <!-- 电影分类部分End -->

    <!-- 横幅广告部分（注意：此处有背景图片） -->
    <div class="banner">
      <h3>欢乐电影节</h3>
      <p>每周一日，欢乐电影节</p>
      <a @click="toDiscount()">立即抢购 &gt;</a>
    </div>

    <!-- 超级会员部分 -->
    <div class="supermember">
      <div class="left">
        <img src="../assets/super_member.png">
        <h3>超级会员</h3>
        <p>&#8226; 每月享超值权益</p>
      </div>
      <div class="right" @click="toVip()">
        立即开通 &gt;
      </div>
    </div>

    <!-- 推荐商家部分 -->
    <!-- <div class="recommend">
      <div class="recommend-line"></div>
      <p>推荐商家</p>
      <div class="recommend-line"></div>
    </div> -->

    <!-- 推荐方式部分 -->
    <ul class="recommendtype">
      <li @click="setType(0)" :class="typeCss[0]">全部电影<i class="fa fa-caret-down"></i></li>
      <li @click="setType(1)" :class="typeCss[1]">正在热映</li>
      <li @click="setType(2)" :class="typeCss[2]">即将上映</li>
    </ul>

    <ul class="movie">
      <li v-for="film in filmArray" :key="film.id">
        <img @click="toFilmDetails(film.id)" :src="film.cover">
        <div class="movie-info">
          <h3>{{ film.name }}</h3>
          <div class="movie-info-node">
            <p><i class="el-icon-star-on"></i>{{ film.grade }}</p>
            <button @click="toArrangement(film.id)">购票</button>
          </div>
        </div>
      </li>
    </ul>

    <!-- 底部菜单部分 -->
    <Footer></Footer>
  </div>
</template>

<script>
//导入共通组件
import Footer from '../components/Footer.vue'

export default {
  name: 'Index',
  data: function () {
    return {
      url: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.2008php.com%2F2012_Website_appreciate%2F2012-09-03%2F20120903102615.jpg&refer=http%3A%2F%2Fwww.2008php.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1672750160&t=bb40f9a456244b15efa0b2e45eb9b460',
      fit: 'cover',
      //定义一个分类信息的数组，存储所有的分类信息
      categoryList: [],
      type: 0,//存储电影显示的类别
      current: 1,//当前页码
      size: 6, //默认显示5个
      pages: 10,//可以显示多少页
      filmName: '', //搜索的电影名
      filmArray: [],//存储首页电影数据的数组
      typeCss: ['active', '', ''],
      poster: [],
      fans: {},
      keywords: "",
      list: [

      ]
    }
  },
  mounted() {
    document.onscroll = () => {
      //获取滚动条位置
      let s1 = document.documentElement.scrollTop
      let s2 = document.body.scrollTop
      let scroll = s1 == 0 ? s2 : s1
      //获取视口宽度
      let width = document.documentElement.clientWidth

      //获取顶部固定块
      let search = this.$refs.fixedBox

      //判断滚动条超过视口宽度的12%时，搜索块变固定定位
      if (scroll > width * 0.12) {
        search.style.position = 'fixed'
        search.style.left = '0'
        search.style.top = '0'
      } else {
        search.style.position = 'static'
      }
    }

    //将滚动条分页的方法调用
    window.addEventListener('scroll', this.lazyLoading)


  },
  destroyed() {
    //当切换到其他组件时，就不需要document滚动条事件，所以将此事件去掉
    document.onscroll = null
  },
  components: {
    Footer,
  },
  created() {
    this.fans = this.$getSessionStorage('fans')
    console.log(this.fans)
    this.getFileList() //加载首页的电影信息
    this.getPosterList()
    this.getCtegoryList()
    console.log(this.categoryList)

  },
  methods: {
    toFenlei(id){
      localStorage.setItem('cateId',id)
      this.$router.push('/fenlei')
    },
    toVip(){
      this.$router.push('/vip')
    },
    querySearchAsync(queryString, cb) {

      this.$axios.get("/film/completion/" + queryString).then(response => {
        this.list = response.data.data.completion
        console.log(this.list)
        let result = this.list.filter(item => {
          console.log(item.name)
            return true;
        });
        cb(result);
      })

    },

    handleSelect(item) {
      console.log(item.id)
      this.$store.commit('setFid', item.id)
      this.$router.push('/filmDetails')
    },

    //当前页面高度+滚动条滚动距离 == 滚动条总高度
    lazyLoading() {
      //滚动条滚动距离
      const scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      const windowHeight = document.documentElement.clientHeight
      const scrollHeight = document.documentElement.scrollHeight

      if (windowHeight + scrollTop >= scrollHeight - 1) {
        this.current++
        if (this.current > this.pages) return
        this.getFileList()
      }
    },

    getCtegoryList() {
      this.$axios.get("/category/list").then(response => {
        this.categoryList = response.data.data.category
      })
    },
    getPosterList() {
      this.$axios.get("/poster/list").then(response => {
        this.poster = response.data.data.poster
      })
    },
    getFileList(m) {
      if (m == 1) {
        this.current = 1
      }
      this.$axios.get("/film/list/" + this.type,
        {
          params: {
            filmName: this.filmName,
            current: this.current,
            size: this.size,
          }
        }).then(response => {
          console.log(response.data.data.films)
          //查询下一页的数据需要和现有的数据合并为一个数组
          if (response.data.code == 1) {
            if (m == 1) {
              this.filmArray = response.data.data.films.records
            } else {
              this.filmArray = this.filmArray.concat(response.data.data.films.records)
            }

            this.current = response.data.data.films.current
            this.size = response.data.data.films.size
            this.pages = response.data.data.films.pages
          } else {
            this.$message({
              showClose: true,
              message: response.data.message,
              type: 'error'
            })
          }

        })
    },
    //点击分类信息图片，跳转至 商家列表组件页面
    toBusinessList(categoryId) { },
    toArrangement(fid) {
      this.$store.commit('setFid', fid)
      this.$router.push('/arrangement')
    },
    toFilmDetails(fid) {
      console.log(fid)
      this.$store.commit('setFid', fid)
      this.$router.push('/filmDetails')
    },
    setType(n) {
      this.type = n;
      this.typeCss = ['', '', '']
      this.typeCss[n] = 'active'
      this.getFileList(1)
    },
    toDiscount(){
      this.$router.push('/discount')
    }

  },
}
</script>

<style scoped>
.wrapper .el-carousel__item:nth-child(2n) {
  background-color: #d3dce6;
}

.wrapper .el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

/****************** 总容器 ******************/
.wrapper {
  width: 100%;
  height: 100%;
  color: #4e4e4e;
}

/****************** header ******************/
.wrapper .header {
  width: 100%;
  height: 10vw;
  background-color: #fff;

  display: flex;
  align-items: center;
}

.wrapper .header .icon-location-box {
  width: 3.5vw;
  height: 3.5vw;
  margin: 0 1vw 0 3vw;
}

.wrapper .header .location-text {
  font-size: 4.5vw;
  font-weight: 700;
  color: #4e4e4e;
}

.wrapper .header .location-text .fa-caret-down {
  margin-left: 1vw;
}

/****************** search ******************/
.wrapper .search {
  width: 100%;
  height: 15vw;
  margin-top: -2vw;
}

.wrapper .search .search-fixed-top {
  width: 100%;
  height: 15vw;
  background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}

.wrapper .search .search-fixed-top .search-box {
  width: 90%;
  height: 9vw;
  background-color: #e9e9e9;
  border-radius: 6px;

  display: flex;
  justify-content: center;
  align-items: center;

  font-size: 3.5vw;
  color: #aeaeae;
  font-family: '宋体';
  /*此样式是让文本选中状态无效*/
  /*user-select: none;*/
}

.wrapper .search .search-fixed-top .search-box .fa-search {
  margin-right: 1vw;
}

/****************** 点餐分类部分 ******************/
.wrapper .foodtype {
  width: 100%;
  height: 24vw;

  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  /*要使用align-content。10个子元素将自动换行为两行，而且两行作为一个整体垂直居中*/
  align-content: center;
}

.wrapper .foodtype li {
  /*一共10个子元素，通过计算，子元素宽度在16.7 ~ 20 之间，才能保证换两行*/
  width: 18vw;
  height: 20vw;

  display: flex;
  /*弹性盒子主轴方向设为column，然后仍然是垂直水平方向居中*/
  flex-direction: column;
  justify-content: center;
  align-items: center;

  user-select: none;
  cursor: pointer;
}

.wrapper .foodtype li img {
  width: 12vw;
  /*高度设置为12vw，实际上设置为10.3vw更佳*/
  height: 10.3vw;
}

.wrapper .foodtype li p {
  font-size: 3.2vw;
  color: #666;
}

/****************** 横幅广告部分 ******************/
.wrapper .banner {
  /**
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     * 设置容器宽度95%，然后水平居中，这样两边留白; 
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    * 这里不能用padding，因为背景图片也会覆盖padding
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    */
  width: 95%;
  margin: 0 auto;
  height: 29vw;

  /*此三个样式组合，可以保证背景图片充满整个容器*/
  background-image: url(../assets/index_banner.png);
  background-repeat: no-repeat;
  background-size: cover;

  box-sizing: border-box;
  padding: 2vw 6vw;
  color: #fff;
}

.wrapper .banner h3 {
  font-size: 4.2vw;
  margin-bottom: 1.2vw;
}

.wrapper .banner p {
  font-size: 3.4vw;
  color: #fff;
  margin-bottom: 2.4vw;
}

.wrapper .banner a {
  font-size: 3vw;
  color: #c79060;
  font-weight: 700;
}

/****************** 超级会员部分 ******************/
.wrapper .supermember {
  /*这里也设置容器宽度95%，不能用padding，因为背景色也会充满padding*/
  width: 95%;
  margin: 0 auto;
  height: 11.5vw;
  background-color: #feedc1;
  margin-top: 1.3vw;
  border-radius: 2px;
  color: #644f1b;

  display: flex;
  justify-content: space-between;
  align-items: center;
}

.wrapper .supermember .left {
  display: flex;
  align-items: center;
  margin-left: 4vw;
  user-select: none;
}

.wrapper .supermember .left img {
  width: 6vw;
  height: 6vw;
  margin-right: 2vw;
}

.wrapper .supermember .left h3 {
  font-size: 4vw;
  margin-right: 2vw;
}

.wrapper .supermember .left p {
  font-size: 3vw;
}

.wrapper .supermember .right {
  font-size: 3vw;
  margin-right: 4vw;
  cursor: pointer;
}

/****************** 推荐商家部分 ******************/
.wrapper .recommend {
  width: 100%;
  height: 14vw;
  display: flex;
  justify-content: center;
  align-items: center;
}

.wrapper .recommend .recommend-line {
  width: 6vw;
  height: 0.2vw;
  background-color: #888;
}

.wrapper .recommend p {
  font-size: 4vw;
  margin: 0 4vw;
}

/****************** 推荐方式部分 ******************/
.wrapper .recommendtype {
  width: 100%;
  height: 5vw;
  margin-top: 5vw;
  margin-bottom: 4vw;

  display: flex;
  justify-content: space-around;
  align-items: center;
}

.wrapper .recommendtype li {
  font-size: 4vw;
  color: #292929;
  font-weight: 900;
  padding-bottom: 2vw;
}

.wrapper .recommendtype .active {
  font-size: 4vw;
  color: #5202ff;
  font-weight: 900;
  border-bottom: 1vw solid #5202ff;
}

/****************** 推荐商家列表部分 ******************/
.wrapper .movie {
  width: 100%;

  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
  padding-bottom: 20vw;
}

.wrapper .movie li {
  width: 33vw;
  box-sizing: border-box;
  padding: 2.5vw;
  user-select: none;
  display: flex;
  flex-direction: column;
}

.wrapper .movie li img {
  width: 28vw;
  height: 40vw;
}

.wrapper .movie li .movie-info {
  width: 28vw;
  box-sizing: border-box;
  padding-top: 3vw;
}

.wrapper .movie li .movie-info h3 {
  font-size: 4vw;
  color: #333;
  font-weight: 300;
  margin-bottom: 1vw;
}

.wrapper .movie li .movie-info .movie-info-node {
  width: 28vw;

  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.wrapper .movie li .movie-info .movie-info-node p {
  font-size: 4vw;
  color: #ffc300;
}

.wrapper .movie li .movie-info .movie-info-node button {
  border: none;
  border-radius: 1.5vw;
  padding: 1vw 1vw;
  background-color: #138bde;
  font-size: 2vw;
  color: #fff;
}

.wrapper .button-search button {
  width: 100%;
  height: 10vw;
  font-size: 3.8vw;
  font-weight: 700;
  color: #fff;
  background-color: #ffd700;
  border-radius: 50px;

  border: none;
  outline: none;
}
</style>
