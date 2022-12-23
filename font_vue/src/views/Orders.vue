<template>
  <div class="wrapper">

    <!-- header部分 -->
    <header>
      <p><i class="el-icon-back" @click="Fallback()"></i> </p>
    </header>

    <div class="header-logo">
      <div>
        <img :src="film.filmInfo.cover">
      </div>
      <div class="header-list">
        <p>{{ film.name }}</p>
        <span><i class="el-icon-star-on"></i> 8.1</span>
        <ul>
          <li class="tagList">
            <div v-for="cName in film.category" :key="cName">
              &nbsp{{ cName }}
            </div>

          </li>
          <li>{{ film.filmInfo.region }}/{{ film.filmInfo.duration }}</li>
          <li>{{ film.filmInfo.releaseTime }}{{ film.filmInfo.region }}上映</li>
          <li>
            <div class="tag">{{ film.type }}</div>
          </li>
        </ul>
      </div>
    </div>

    <ul class="arrangement_time">
      <li> <i class="el-icon-success txt"></i> 支持退票</li>
      <li> <i class="el-icon-success txt"></i>支持改签</li>
      <li></li>
      <li></li>
      <li>退改签通知 <i class="el-icon-arrow-right"></i> </li>
    </ul>

    <div class="info">
      <div class="list">
        <div class="item">
          <ul>
            <li>座位：</li>
            <li>{{ film.seats }}</li>
          </ul>
        </div>
        <div class="item">
          <ul>
            <li>票价小计：</li>
            <li>{{ film.seats.split(',').length }}张 票价:￥{{ film.seats.split(',').length * film.price }}</li>
          </ul>
        </div>
        <div class="item">
          <ul>
            <li>手机号：</li>
            <li>{{ this.$getSessionStorage('fans').id }}</li>
          </ul>
        </div>
      </div>
      <div class="checkout">
        <ul>
          <li><i class="el-icon-thumb" @click="topay()"></i> 确认支付</li>
          <li>￥{{ film.seats.split(',').length * film.price }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      film: this.$getSessionStorage('film'),
      fans:this.$getSessionStorage('fans'),

    }
  },
  created() { 

  },
  methods: {

    topay(){
      //axios调用服务器 保存订单方法
      // 路径orders/save
      let order = {
        uid:this.fans.id,
        phone: this.fans.id,
        aid: this.film.aid,
        seats: this.film.seats,
        price: this.film.seats.split(',').length * this.film.price,
        status: 1,
        statu:1
      }
      console.log(order)
      
      this.$axios.post('/orders/save', order).then(response => {
        if (response.data.code == 1) {
          this.$router.push('/tickets')
          this.$message({
            showClose: true,
            message: '创建订单成功',
            type: "success",
            onClose: () => {
              return
            }
          }
          )
        }
      })

    },
    to_seat() {
      this.$router.push('/seat')
    },
    Fallback() {
      this.$router.push('/seat')
    },
  },
  components: {},
}
</script>

<style scoped>
/****************** 总容器 ******************/
.wrapper {
  width: 100%;
  height: 100%;
  background-color: #d3dce6;
}

/****************** header部分 ******************/
.wrapper header {
  width: 100%;
  height: 12vw;
  /* background-color: #d3dce6; */
  background-color: #d3dce6;
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

.wrapper .arrangement_time .txt {
  color: #3333ff;
}

.wrapper .arrangement_time li {
  font-size: 3.5vw;
  color: #292929;
  padding: 3vw 0;
  font-size: 3.8vw;
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

.wrapper .header-logo .header-list ul .tagList {
  display: flex;
}

.wrapper .info {
  width: 100%;
  height: 60vw;
  background-color: #fff;
  position: fixed;
  left: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  border-radius: 6vw 6vw 0vw 0vw;
}

.wrapper .info .list {
  width: 100%;
  height: 100vw;

  /* background-color: aqua; */
  margin-top: 5vw;
}

.wrapper .info .list .item {
  margin: 5vw;
}

.wrapper .info .list .item ul {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  font-size: 4vw;
}

/* .wrapper .info .list ul {
                                                                        display: flex;
                                                                        flex-direction: row;
                                                                      }

                                                                      .wrapper .info .list ul li {
                                                                        background-color: #fff;
                                                                        box-shadow: 0 1vw 0.5vw 0.5vw rgba(227, 231, 239, 0.8);

                                                                        margin-left: 5vw;
                                                                        padding: 2vw 2vw;
                                                                        color: #191919;
                                                                      } */

.wrapper .info .checkout {
  width: 100%;
  height: 20vw;
  background-image: linear-gradient(#0067ff, #5202ff);
}

.wrapper .info .checkout ul {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  height: 20vw;
  align-items: center;
}

.wrapper .info .checkout ul li {
  color: #fff;
  font-size: 5vw;
}
</style>
