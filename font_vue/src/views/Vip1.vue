<template>
  <div class="wrapper">
    <!-- 头部区域 -->
    <header class="header" id="header">
      <p><i class="el-icon-back"  @click="Fallback()"></i> 首页</p>
      <!-- 搜索框区域 -->
      <div class="search">
        <i class="ri-fire-fill"></i>
        <input type="search" placeholder="哇塞电影网" />
      </div>
      <!-- 竖线图标 -->
      <span class="icon-line"></span>
      <!-- 用户头像 -->
      <div class="userlogo">
        <a href="https://blog.csdn.net/IAMLSD550?spm=1010.2135.3001.5343" target="_blank">
          <img src="https://img0.baidu.com/it/u=2685051271,176347936&fm=253&fmt=auto&app=138&f=JPEG?w=475&h=500"
            alt="小柴爱程序" />
        </a>
      </div>
      <!-- 菜单栏 -->
      <div class="menu" id="menu">
        <img id="img1" src="../assets/edit.png" alt="菜单栏" />
      </div>
    </header>
    <!-- 主内容区域 -->
    <div class="main_cont">
      <!-- 第一部分 -->
      <div class="top_bg">
        <div class="user_bg">
          <div class="user_logo">
            <img src="../assets/dcfl01.png" alt="用户1" />
          </div>
          <div class="dp_cont">
            <h4>用户1</h4>
            <p>开通电影网会员年卡可参与抽奖，有机会得千元奖品</p>
          </div>
        </div>
      </div>
      <!-- 第二部分 -->
      <div class="tabs">
        <div class="tabs_hd">-------百万电影资源随心-------</div>
        <div class="tabs_bd" id="tabsbd">
          <i class="ri-arrow-left-s-line right" id="right"></i>
          <i class="ri-arrow-right-s-line left" id="left"></i>
          <ul id="listmove">
            <li>
              <div class="top_text">VIP月卡</div>
              <div class="middle_pri">¥<span id="price1">98.00</span>/月</div>
              <div class="bottom_text">享 30次购票优惠</div>
            </li>
            <li>
              <div class="top_text">VIP年卡(24个月)</div>
              <div class="middle_pri">¥<span id="price2">12.41</span>/月</div>
              <div class="bottom_text">享 400次购票优惠</div>
            </li>
            <li>
              <div class="top_text">超级会员年卡</div>
              <div class="middle_pri">¥<span id="price3">32.33</span>/月</div>
              <div class="bottom_text">精选电影、专栏免费看</div>
            </li>
            <li>
              <div class="top_text">VIP两年卡</div>
              <div class="middle_pri">¥<span id="price4">20.33</span>/月</div>
              <div class="bottom_text">享 800次购票优惠</div>
            </li>
          </ul>
        </div>
      </div>
      <!-- 第三部分 -->
      <div class="payways">
        <h4>支付方式</h4>
        <div class="pay_bd" id="paybd">
          <div class="pay_one">
            <img src="../assets/dcfl01.png" alt="" />
            <div class="pay_name">余额抵扣</div>
            <div class="pay_way">
              <span id="user_balance">暂无余额</span>
              <i class="ri-arrow-right-s-line"></i>
            </div>
          </div>
          <ul>
            <li>
              <img src="../assets/dcfl01.png" alt="" />
              <div class="pay_name">微信</div>
              <div class="pay_way">
                <img id="img3" src="../assets/dcfl01.png" alt="" />
              </div>
            </li>
            <li>
              <img src="../assets/dcfl01.png" alt="" />
              <div class="pay_name">支付宝</div>
              <div class="pay_way">
                <img id="img4" src="../assets/dcfl01.png" alt="" />
              </div>
            </li>
            <li>
              <img src="../assets/dcfl01.png" alt="" />
              <div class="pay_name">京东</div>
              <div class="pay_way">
                <img id="img5" src="../assets/dcfl01.png" alt="" />
              </div>
            </li>
          </ul>
        </div>
        <!-- <div class="pay_more" id="paymore">
          展开更多支付方式
          <i class="ri-arrow-down-s-line"></i>
        </div> -->
      </div>
      <!-- 第四部分 -->
      <div class="vipcards">
        <div class="vip_hd">
          <h4>VIP年卡(24个月)</h4>
          <p id="cont_open">会员权益与服务说明</p>
          <i class="ri-arrow-right-s-line"></i>
        </div>
        <div class="vip_bd">
          <ul>
            <li>
              <div>
                <img src="../assets/dcfl01.png" alt="" />
              </div>
              <p>享 400次购票优惠</p>
            </li>
            <li>
              <div>
                <img src="../assets/dcfl01.png" alt="" />
              </div>
              <p>享 400次购票优惠</p>
            </li>
            <li>
              <div>
                <img src="../assets/dcfl01.png" alt="" />
              </div>
              <p>享 400次购票优惠</p>
            </li>
            <li>
              <div>
                <img src="../assets/dcfl01.png" alt="" />
              </div>
              <p>享 400次购票优惠</p>
            </li>
            <li>
              <div>
                <img src="../assets/dcfl01.png" alt="" />
              </div>
              <p>享 400次购票优惠</p>
            </li>
            <li>
              <div>
                <img src="../assets/dcfl01.png" alt="" />
              </div>
              <p>享 400次购票优惠</p>
            </li>
            <li>
              <div>
                <img src="../assets/dcfl01.png" alt="" />
              </div>
              <p>享 400次购票优惠</p>
            </li>
            <li>
              <div>
                <img src="../assets/dcfl01.png" alt="" />
              </div>
              <p>享 400次购票优惠</p>
            </li>
            <li>
              <div>
                <img src="../assets/dcfl01.png" alt="" />
              </div>
              <p>享 400次购票优惠</p>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- 底部区域 -->
    <footer class="footer" id="footer">
      <div class="footer_hd" id="footerhd">
        <span>距离优惠结束还有</span>
        <div class="victime">
          <span class="hour">11</span>
          <span>:</span>
          <span class="minute">35</span>
          <span>:</span>
          <span class="second">56</span>
        </div>
      </div>
      <div class="footer_bd" id="footerbd">
        <div class="footer_left">
          <div class="price">
            <span>¥</span><span class="money" id="money">248.00</span>
          </div>
          <div class="count">
            <span id="dis_font">已减<span id="discount">50.00</span>元，</span><span class="details" id="detail_open">查看明细<i
                class="ri-arrow-up-s-line"></i></span>
          </div>
        </div>
        <div class="footer_right">
          <div class="pay_btn">立即开通</div>
        </div>
      </div>
    </footer>

    <!-- <Footer></Footer> -->
  </div>
</template>

<script>
import Footer from "../components/Footer.vue";
import "../fonts/remixicon.css";
import "../css/base.css";

export default {
  data() {
    return {};
  },
  components: {
    Footer,
  },
  methods:{
    Fallback(){
      this.$router.push('/')
      localStorage.clear()
    }
  },
  created() { },
};
</script>

<style scoped>
/****************** 总容器 ******************/
.wrapper {
  width: 100%;
  height: 100%;
}

/* 鏁翠綋鍖哄煙 */
html {
  position: relative;
  width: 100%;
  height: 100%;
}

body {
  position: relative;
  width: 100%;
  height: 100%;
  z-index: 0;
}

/* 头部 */
.header {
  position: fixed;
  display: flex;
  justify-content: space-between;
  top: 0px;
  padding: 11px 16px;
  width: 100%;
  height: 52px;
  background-color: #fff;
  box-shadow: 0 2px 4px 0 rgb(0 0 0 / 5%);
  z-index: 6;
}

.slideUp {
  transform: translateY(-52px);
  transition: all 0.7s ease-out;
}

.slideDown {
  transform: translateY(100);
  transition: all 0.5s ease-out;
}

.header div {
  margin-right: 12px;
}

.header div:last-child {
  margin-right: 0;
}

.header .logo {
  position: relative;
  margin-top: 1px;
  width: 60px;
  height: 26px;
  background-color: aliceblue;
  overflow: hidden;
}

.header .logo h1 {
  margin-left: -999px;
}

.header .logo a {
  position: absolute;
  top: 0px;
  display: block;
  width: 100%;
  height: 100%;
  background-color: #fff;
}

.header .search {
  position: relative;
  display: flex;
  /* padding-left: 14px; */
  margin-left: 15px;
  flex: 1;
  height: 32px;
  border-radius: 4px;
  border: 1px solid #e8e8ed;
  background-color: #f5f6f7;
  overflow: hidden;
}

.header .search i {
  margin-right: 3px;
  line-height: 28px;
  color: red;
}

.header .search input {
  line-height: 32px;
  width: 100%;
  height: 100%;
  font-size: 16px;
  /* margin-left: 5px; */
  background-color: #f5f6f7;
}

.header .openapp {
  font-size: 14px;
  line-height: 32px;
  font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  font-weight: 400;
  color: #222226;
}

.header .icon-line {
  margin: 0 12px;
  width: 1px;
  height: 14px;
  background: #999aaa;
  margin-top: 9px;
}

.header .userlogo {
  display: inline-block;
  width: 25px;
  height: 25px;
  line-height: 25px;
  align-self: center;
  border-radius: 50%;
  overflow: hidden;
}

.header .userlogo img {
  scale: 1.45;
}

.header .menu {
  position: relative;
  align-self: center;
  align-items: center;
  width: 16px;
  height: 25px;
  overflow: hidden;
  cursor: pointer;
}

.header .menu img {
  position: absolute;
  top: 6px;
  width: 16px;
  height: 16px;
}

.header .menu img:nth-child(1) {
  visibility: visible;
}

.header .menu img:nth-child(2) {
  visibility: hidden;
}

/* 主内容区域 */
/* 一 */
.main_cont {
  position: absolute;
  top: 52px;
  width: 100%;
  z-index: -1;
}

.main_cont .top_bg {
  padding: 16px 16px 0;
  width: 100%;
  height: 104px;
  background-image: url(../assets/m09.png);
  background-size: cover;
  overflow: hidden;
}

.main_cont .top_bg .user_bg {
  display: flex;
  justify-content: flex-start;
  padding: 16px;
  width: 100%;
  height: 100%;
  background: hsla(0, 0%, 100%, 0.6);
  border-radius: 8px;
  overflow: hidden;
}

.main_cont .top_bg .user_bg .user_logo {
  margin-right: 13px;
  width: 56px;
  height: 56px;
  border-radius: 50%;
  overflow: hidden;
}

.main_cont .top_bg .user_logo img {
  scale: 1.35;
}

.main_cont .top_bg .user_bg .dp_cont {
  flex: 1;
}

.main_cont .top_bg .user_bg .dp_cont h4 {
  color: #222226;
  line-height: 26px;
  font-size: 16px;
  margin-bottom: 5px;
  font-weight: 400;
}

.main_cont .top_bg .user_bg .dp_cont p {
  color: #555666;
  line-height: 17px;
  font-size: 12px;
}

/* 二 */
.main_cont .tabs {
  display: flex;
  flex-direction: column;
  padding-top: 24px;
  width: 100%;
  height: 212px;
  background-color: rgb(255 255 255);
  z-index: 0;
}

.main_cont .tabs .tabs_hd {
  position: relative;
  margin: 0 auto;
  margin-bottom: 14px;
  font-size: 13.5px;
  text-align: center;
  color: #69421b;
}

.main_cont .tabs .tabs_hd::before,
.main_cont .tabs .tabs_hd::after {
  content: "";
  position: absolute;
  display: block;
  width: 40px;
  height: 10px;
  background-size: contain;
}

/* .main_cont .tabs .tabs_hd::before {
    top: 6px;
    left: -48px;
    background-image: url(../assets/dcfl01.png);
}

.main_cont .tabs .tabs_hd::after {
    top: 6px;
    right: -48px;
    background-image: url(../assets/dcfl01.png);
} */

.main_cont .tabs .tabs_bd {
  position: relative;
  padding: 10px 16px 16px;
  overflow: hidden;
}

.main_cont .tabs .tabs_bd .right,
.main_cont .tabs .tabs_bd .left {
  position: absolute;
  top: 50%;
  transform: translate(0, -50%);
  width: 44px;
  height: 44px;
  border-radius: 50%;
  border: 1px solid #e8e8ed;
  background: hsla(0, 0%, 100%, 0.7);
  text-align: center;
  line-height: 44px;
  font-size: 35px;
  font-weight: 400;
  color: #999aaa;
  cursor: pointer;
}

.main_cont .tabs .tabs_bd .right {
  left: -10px;
}

.main_cont .tabs .tabs_bd .left {
  right: -10px;
}

.main_cont .tabs .tabs_bd ul {
  width: 449px;
  height: 100%;
  transition: all 0.7s;
}

.main_cont .tabs .tabs_bd ul li {
  float: left;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 24px 0 18px;
  margin-right: 8px;
  width: 104px;
  height: 130px;
  border: 1px solid #e8e8ed;
  border-radius: 6px;
  text-align: center;
}

.main_cont .tabs .tabs_bd ul li:nth-child(2) {
  position: relative;
  background: #fff9f1;
  border: 1px solid #b87100;
}

.main_cont .tabs .tabs_bd ul li:nth-child(2)::before {
  content: "送一年抽好礼";
  position: absolute;
  top: -9px;
  width: 81px;
  height: 18px;
  font-size: 12px;
  color: #fff;
  border-radius: 9px 9px 9px 0;
  background: #fc5531;
}

.tabs .tabs_bd ul li .top_text {
  color: #222226;
  font-size: 12px;
  line-height: 16px;
}

.tabs .tabs_bd ul li .middle_pri {
  margin: 16px 0 14px;
  color: #b87100;
  font-size: 14px;
  line-height: 16px;
}

.tabs .tabs_bd ul li .middle_pri span {
  color: #b87100;
  font-size: 24px;
}

.tabs .tabs_bd ul li .bottom_text {
  padding: 0 8px;
  color: #99a;
  font-size: 12px;
  line-height: 16px;
}

/* 支付方式 */
.payways {
  display: flex;
  flex-direction: column;
  width: 100%;
  background-color: #fff;
  overflow: hidden;
}

.payways h4 {
  padding: 8px 16px 16px;
  color: #212529;
  font-size: 16px;
  line-height: 16px;
  font-weight: 400;
}

.payways .pay_bd {
  padding: 0 16px;
  width: 100%;
  height: 80px;
  overflow: hidden;
}

.payways .pay_bd .pay_one {
  width: 100%;
  height: 35px;
  border-bottom: 1px solid #f0f0f2;
}

.payways .pay_bd .pay_one img {
  float: left;
  margin-right: 8px;
  width: 24px;
  height: 24px;
}

.payways .pay_bd .pay_way i {
  float: right;
  margin-top: 1px;
  font-size: 20px;
  vertical-align: bottom;
}

.payways .pay_bd ul li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 0 8px;
  width: 100%;
  height: 40px;
  border-bottom: 1px solid #f0f0f2;
}

.payways .pay_bd img {
  float: left;
  width: 24px;
  height: 24px;
  margin-right: 8px;
}

.payways .pay_bd .pay_name {
  float: left;
  flex: 1;
  color: #222226;
  font-size: 14px;
  line-height: 23px;
}

.payways .pay_bd .pay_way {
  float: right;
  color: #999aaa;
  font-size: 14px;
  line-height: 25px;
  cursor: pointer;
}

.payways .pay_bd .pay_way img {
  float: right;
  margin-right: 0px;
  width: 16px;
  height: 16px;
  visibility: hidden;
}

.payways .pay_bd ul li:first-child .pay_way img {
  visibility: visible;
}

.payways .pay_more {
  padding: 0 16px;
  width: 150px;
  font-size: 12px;
  color: #3987d1;
  line-height: 18px;
}

.payways .pay_more i {
  margin-left: -5px;
  font-size: 16px;
  vertical-align: middle;
}

/* 绗洓閮ㄥ垎 */
.vipcards {
  display: flex;
  flex-direction: column;
  margin: 17px 0 140px;
  padding: 0 16px;
  width: 100%;
  background-color: #b87100;
  background-color: #fff;
}

.vipcards .vip_hd {
  position: relative;
  margin-bottom: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
}

.vipcards .vip_hd h4 {
  font-size: 16px;
  font-weight: 400;
  color: #212529;
  flex: 1;
}

.vipcards .vip_hd p,
.vipcards .vip_hd i {
  font-size: 12px;
  font-weight: 400;
  color: #3082ce;
  cursor: pointer;
}

.vipcards .vip_hd i {
  margin: 0 -3px;
  font-size: 16px;
}

.vipcards .vip_bd {
  padding: 8px 3px 0;
  width: 100%;
  background-color: #fff9f1;
  border-radius: 10px;
  font-size: 12px;
  color: #b87100;
}

.vipcards .vip_bd ul {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
}

.vipcards .vip_bd ul li {
  width: 25%;
  text-align: center;
}

.vipcards .vip_bd ul li div {
  width: 100%;
  height: 56px;
  margin-bottom: 4px;
}

.vipcards .vip_bd ul li div img {
  width: 56px;
  height: 56px;
}

.vipcards .vip_bd ul li p {
  margin: 0 2px 6px;
}

/* 寮圭獥鍖哄煙 */
.pop_bg {
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0);
  transition: all 0.5s;
  z-index: -3;
}

.pop_bg .pop_cont1,
.pop_bg .pop_cont2 {
  position: absolute;
  padding: 0 16px;
  width: 100%;
  height: 60%;
  border-radius: 10px 10px 0 0;
  background-color: #fff;
  overflow: hidden;
  transition: all 0.5s;
}

.pop_bg .pop_cont1 {
  bottom: -60%;
}

.pop_bg .pop_cont2 {
  bottom: -60%;
}

.pop_bg .pop_cont1 .cont_hd,
.pop_bg .pop_cont2 .cont_hd {
  top: 0px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 56px;
  line-height: 20px;
  color: #212529;
}

.pop_bg .pop_cont1 .cont_hd i,
.pop_bg .pop_cont2 .cont_hd i {
  font-size: 30px;
  color: #c8c9cc;
}

.pop_bg .pop_cont1 .cont_bd,
.pop_bg .pop_cont2 .cont_bd {
  width: 100%;
  height: 90%;
  color: #555666;
  font-size: 14px;
  font-family: Arial, Helvetica, sans-serif;
  overflow: auto;
}

.pop_bg .pop_cont2 .cont_bd {
  display: flex;
  flex-direction: column;
  color: #222226;
}

.pop_bg .pop_cont2 .cont_bd .detail_item {
  margin-bottom: 16px;
}

.pop_bg .pop_cont2 .cont_bd .detail_item span {
  float: right;
  font-weight: 700;
}

.pop_bg .pop_cont2 .cont_bd .detail_item .num {
  font-size: 20px;
  float: right;
  font-weight: 700;
}

/* 闅愯棌婊氬姩鏉� */
.pop_bg .pop_cont1 .cont_bd::-webkit-scrollbar {
  display: none;
}

.pop_bg .pop_cont1 .cont_bd a {
  color: #277ccc;
}

/* 底部 */
.footer {
  position: fixed;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 128px;
  bottom: 0;
  background-color: #fff;
}

.footer .footer_hd {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 40px;
  background-color: #fff0ed;
  font-size: 14px;
  line-height: 40px;
  text-align: center;
  color: #fc5531;
}

.footer .footer_hd .victime {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 8px;
}

.footer .footer_hd .victime span {
  width: 24px;
  height: 24px;
  border-radius: 5px;
  border: 1.3px solid #fc5531;
  font-size: 13px;
  font-family: sans-serif;
  line-height: 22px;
}

.footer .footer_hd .victime span:nth-child(2n) {
  line-height: 24px;
  border: none;
}

.footer .footer_bd {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 16px;
  width: 100%;
  height: 88px;
  background-color: #fff;
}

.footer .footer_bd .footer_left {
  width: 170px;
  height: 100%;
  font-size: 14px;
  color: #555666;
  font-family: Microsoft YaHei, sans-serif;
}

.footer .footer_bd .footer_left .price {
  margin-bottom: 8px;
  color: #fc5531;
  font-size: 14px;
}

.footer .footer_bd .footer_left .price .money {
  font-size: 32px;
  line-height: 30px;
  font-family: Microsoft YaHei, sans-serif;
}

.footer .footer_bd .footer_left .count {
  line-height: 6px;
}

.footer .footer_bd .footer_left .count .details {
  color: #277ccc;
}

.footer .footer_bd .footer_left .count .details i {
  margin-left: -3px;
  font-size: 20px;
  font-weight: 400;
  vertical-align: middle;
  color: #277ccc;
}

.footer .footer_bd .footer_right {
  width: 108px;
  height: 100%;
}

.footer .footer_bd .footer_right .pay_btn {
  margin-top: -5px;
  width: 100%;
  height: 40px;
  background-color: #fc5531;
  border-radius: 20px;
  text-align: center;
  line-height: 38px;
  color: #fff;
}
</style>