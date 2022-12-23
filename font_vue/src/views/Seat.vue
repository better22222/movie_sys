<template>
  <div class="wrapper">
    <!-- header部分 -->
    <header>
      <p><i class="el-icon-back" @click="Fallback()"></i> {{ film.name }}</p>
      <span><i class="el-icon-alarm-clock"></i>{{ film.startTime }} {{ film.type }} </span>
    </header>

    <div class="scree"></div>
    <div class="seat">
      <div class="row" v-for="row in 9" :key="row">
        <div v-for="col in 9" :key="col">
          <div v-if="isTeken(col, row)" class="disabled" :ref="col + '-' + row" @click="handleChooseSeat(col, row)">
          </div>
          <div v-else :ref="col + '-' + row" :class="seatStyle" @click="handleChooseSeat(col, row)"></div>
        </div>
      </div>
    </div>

    <div class="info">
      <div class="list">
        <ul>
          <li v-for="item, index in checkout" :key="index">
            {{ item.p + '排' + item.z + '座' }}
          </li>
        </ul>
      </div>
      <div class="checkout">
        <ul @click="toOrders()">
          <li><i class="el-icon-thumb"></i> 支付</li>
          <li><i class="el-icon-bank-card"> × {{ checkout.length }}</i></li>
          <li>￥{{ checkout.length * film.price }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script type="text/javascript">
export default {
  data() {
    return {
      seatStyle: 'inner-seat',
      checkout: [],
      ordersList: [], //已经购买的订单数据
      seatsArr: [], //已购买座位的坐标
      //获得session中film的电影信息
      film: this.$getSessionStorage('film'),
    }
  },
  created() {
    this.getOrders() //得到所有该场次（通过aid查询）的 订单
  },
  components: {},
  methods: {
    //根据传递的座位坐标，判断是否卖出
    isTeken(p, z) {
      let b = false
      this.seatsArr.forEach((m) => {
        if (m.p == p && m.z == z) b = true
      })
      return b
    },
    getOrders() {
      console.log(this.film.aid)
      this.$axios.get('/orders/info/' + this.film.aid).then((response) => {

        if (response.data.code == 1) {
          this.ordersList = response.data.data.orderList

          //对于订单的加工方法：获得具体的 座位的坐标的对象数据
          this.getSeatsPos()
        } else {
          this.$message({
            showClose: true,
            message: response.data.message,
            type: 'warning',
          })
        }
      })
    },
    //从订单的集合中，加工出所有的座位 坐标
    // 数据库中：3排4座,3排5座,6排2座    加工getSeatsPos()  将座位信息变为：[{p:3,z:4},{p:3,z:5},{p:6,z:2}]
    getSeatsPos() {
      // this.ordersList.forEach((m) => {
      //   console.log(m.seats.split(',')[0].charAt(0))
      // })
      this.ordersList.forEach((item) => {
        let arr = item.seats.split(',') //分割前：3排4座,3排5座  分割后：["3排4座","3排5座"]
        let obj_temp = arr.map((e) => {
          return { p: e.charAt(0), z: e.charAt(2) }
        })

        obj_temp.forEach((m) => {
          this.seatsArr.push(m) //this.seatsArr数组中存储：[{p:3,z:4},{p:3,z:5},{p:6,z:2}]
        })
      })

      console.log(this.seatsArr)
    },

    handleChooseSeat(c, r) {
      //判断如果该座位卖出，那么提示 卖出，不能点击
      if (this.$refs[c + '-' + r][0].className == 'disabled') {
        this.$message({
          showClose: true,
          message: '该座位号已经卖出',
          onClose: () => {
            return
          },
        })
        return
      }
      if (this.$refs[c + '-' + r][0].className == 'inner-seat') {
        if (this.checkout.length >= 4) {
          this.$message({
            showClose: true,
            message: '用户最多可以购买4张',
            type: 'warning',
            onClose: () => {
              return
            },
          })
          return
        } else {
          this.$refs[c + '-' + r][0].className = 'checked'
          this.checkout.push({ p: c, z: r })
        }
      } else {
        this.$refs[c + '-' + r][0].className = 'inner-seat'
        const arr = this.checkout
        this.checkout = arr.filter((item) => {
          console.log(item.p != c && item.z != r)
          if (!(item.p == c && item.z == r)) {
            return item
          }
        })
      }
    },
  
    toOrders() {
      let newArray = this.checkout.map((item) => {
        return item.p + '排' + item.z + '座'
      })

      this.film.seats = newArray.join(',')
      this.$setSessionStorage('film', this.film)
      this.$router.push('/orders')
    },
    Fallback() {
      this.$router.push('/arrangement')
    },
  },
}
</script>

<style scoped>
.wrapper {
  width: 100%;
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

.wrapper .scree {
  width: 100%;
  height: 30vw;
  background-image: url(../assets/screen.png);
  background-repeat: no-repeat;
  background-size: 100% 30vw;

  box-sizing: border-box;
  margin-top: 23vw;
}

.wrapper .seat {
  /* background-color: aqua; */
  margin: 5vw;
  width: 90vw;
  height: 100vw;
  display: flex;
  flex-direction: row;
}

.wrapper .info {
  width: 100%;
  height: 35vw;

  position: fixed;
  left: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
}

.wrapper .info .list {
  width: 100%;
  height: 15vw;
  /* background-color: aqua; */
}

.wrapper .info .list ul {
  display: flex;
  flex-direction: row;
}

.wrapper .info .list ul li {
  background-color: #fff;
  box-shadow: 0 1vw 0.5vw 0.5vw rgba(227, 231, 239, 0.8);
  border-radius: 2vw;
  margin-left: 5vw;
  padding: 2vw 2vw;
  color: #191919;
}

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

.wrapper .seat .inner-seat {
  width: 7vw;
  height: 7vw;
  background-color: #bdbdbd;
  margin: 1.5vw;
  border: 0.1 solid #191919;
  border-radius: 1.5vw;
}

.wrapper .seat .checked {
  width: 7vw;
  height: 7vw;
  background-color: aliceblue;
  margin: 1.5vw;
  border: 0.1 solid #efefef;
  border-radius: 1.5vw;
  background-color: #0067ff;
}

.disabled {
  width: 7vw;
  height: 7vw;
  background-color: aliceblue;
  margin: 1.5vw;
  border: 0.1 solid #efefef;
  border-radius: 1.5vw;
  background-color: #efefef;
}
</style>

