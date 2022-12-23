<template>
  <div class="wrapper">
    <!-- header部分 -->
    <header>
      <p><i class="el-icon-back"></i>我的电影票</p>
      <span><i class="el-icon-film">{{film.name}}</i></span>
    </header>

    <div class="tickets">
      <div class="cover" >
        <el-image :src="film.filmInfo.cover" style="height:50%"></el-image>
        <div class="info">
          <div class="time">
            <span>2022-12-12</span>
            <p>{{film.startTime.split(" ")[1]}}——{{film.endTime.split(" ")[1]}}</p>
          </div>

          <el-image style="width:60vw; height: 60vw; margin:10% auto" :src="qrcode"></el-image>
        </div>
      </div>

    </div>
    <Footer></Footer>
  </div>
</template>

<script type="text/javascript">
  import Footer from '../components/Footer.vue'
  export default {
    data() {
      return {
        qrcode: '',
        img: '',
        film: this.$getSessionStorage('film'),
      }
    },
    created() {
      this.img = "background-image:url('" + this.film.cover + "')"
      this.getQRCode()
    },
    methods: {
      getQRCode() {
        let obj = {
          //TODO:动态获取当前购买的电影票信息
          content:  this.film.seats + this.film.startTime +  this.film.endTime ,
        }
        console.log(obj)
        this.$axios.post('/add', this.$qs.stringify(obj)).then((response) => {
          if (response.data.code == 1) {
            this.qrcode = response.data.data.QRCode
            console.log(this.qrcode)
          } else {
            this.$message({
              showClose: true,
              message: '二维码生成失败',
              type: 'warning',
              onClose: () => {
                return
              },
            })
          }
        })
      },
    },
    components: {
      Footer,
    },
  }
</script>

<style scoped>
  .wrapper {
    width: 100%;
    background-color: #181818;
  }
  .wrapper header {
    width: 100%;
    height: 22vw;
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
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
  }

  .wrapper header span {
    font-size: 4vw;
    margin-top: 3vw;
    margin-left: 1vw;
  }

  .wrapper .tickets {
    width: 100%;
    height: 180vw;
    background-color: #181818;

    display: flex;
    flex-direction: column;
  }

  .wrapper .tickets .cover {
    height: 160vw;
    background-color: aqua;
    margin: 5vw 5vw 0 5vw;
    background-size: 100% 100%;

    display: flex;
    flex-direction: column;
  }

  .wrapper .tickets .cover .space {
    height: 60vw;
  }
  .wrapper .tickets .cover .info {
    height: 100vw;
    background-color: #fff;
    display: flex;

    flex-direction: column;
  }

  .wrapper .tickets .cover .info .time {
    height: 14vw;
    /* background-color: aqua; */
    margin: 5vw 5vw 0 5vw;
    justify-content: center;
  }

  .wrapper .tickets .cover .info .time p {
    font-size: 8vw;
    font-family: Arial, Helvetica, sans-serif;
  }
  .wrapper .tickets .cover .info .time span {
    font-size: 5vw;
    font-family: Arial, Helvetica, sans-serif;
  }
</style>
