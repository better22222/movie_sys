<template>
    <div class="wrapper">

        <!-- header部分 -->
        <header>
            <i class="el-icon-arrow-left" @click="back()"></i>
            <p> 我的订单</p>
        </header>

        <div class="orderlist">
            <div v-for="order in orderList" :key="order">
                <div class="box-card">
                    <div class="order_pic">
                        <img :src="order.filmCover" />
                    </div>

                    <div class="order_detail">
                        <ul>
                            <li class="order_name">{{ order.filmName }} </li>
                            <li>订单编号: {{ order.id }} </li>
                            <li>座位: {{ order.seats }} </li>
                            <li>总价: {{ order.price }}元 </li>
                            <li>{{ order.payAt }} </li>
                        </ul>

                    </div>
                </div>
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
            //粉丝id
            fans: {},
            //电影信息
            orderList: [],

        }
    },
    created() {
        this.fans = this.$getSessionStorage('fans')
    },
    mounted() {
        this.getOrderList()
    },
    methods: {
        back() {
            this.$router.push('/Index')
        },
        getOrderList() {
            console.log(this.fans)
            this.$axios.get("/orders/list/" + this.fans.id,
            ).then(response => {
                console.log(response.data.data.orderList)
                //查询下一页的数据需要和现有的数据合并为一个数组
                if (response.data.code == 1) {
                    this.orderList = response.data.data.orderList
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
/****************** 总容器 ******************/
.wrapper {
    width: 100%;
    height: 100%;
    background-color: #F5f5f5;
}

/****************** header部分 ******************/
.wrapper header {
    width: 100%;
    height: 12vw;
    /* background-color: #d3dce6; */
    /* background-color: #aab7c8; */
    background-color: rgb(82, 91, 102);
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


.wrapper .orderlist {

    width: 95%;
    margin-top: 15vw;
    margin-left: 2vw;
    height: 100%;
    border-radius: 1em;

    /* display: flex; */
    /* justify-content:space-around; */

}

.wrapper .orderlist .box-card {
    background-color: white;
    width: 100%;
    display: flex;
    height: 100%;
    margin-bottom: 3vw;
}

.wrapper .orderlist .box-card .order_pic {
    width: 40vw;
    margin-left: 7vw;
}

.wrapper .orderlist .box-card .order_pic img {
    border-radius: 0.5em;
    width: 30vw;
    height: 40vw;
}

.wrapper .orderlist .order_detail {
    width: 70vw;
    margin-left: 5vw;
}

.wrapper .orderlist .order_detail button {
    /* width:15vw;
    height:5vw; */
    border-radius: 1em;
    background-color: #f5f5f5;
    margin-top: 2vw;
    margin-left: 30vw;
}

.wrapper .orderlist .order_detail .order_name {
    font-size: 4vw;
    font-family: FangSong_GB2312;
    font-weight: 600;
    margin-bottom: 3vw;
}

.wrapper .orderlist .order_detail ul {
    font-size: 3vw;

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
