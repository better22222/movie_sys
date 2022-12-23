<template>
  <div class="wrapper">
    <header class="header" id="header">
      <p class="p1"><i class="el-icon-back" @click="Fallback()"></i> 首页</p>
      <p class="p2">优惠券详情</p>
    </header>
    <el-row type="flex" class="row1" justify="space-around">
      <el-col :span="2" class="col1">
        <div class="grid-content bg-purple-dark">
          <el-avatar
            shape="square"
            :size="80"
            src="https://img0.baidu.com/it/u=2685051271,176347936&fm=253&fmt=auto&app=138&f=JPEG?w=475&h=500"
          ></el-avatar>
        </div>
      </el-col>
      <el-col :span="12" class="col2">
        <div class="grid-content bg-purple-dark">
          <!-- <h5>[电影支付券]电影支付满100减50元</h5> -->
          <span class="title">[电影支付券]电影支付满100减50元</span>
          <br />
          <el-button type="danger" plain disabled size="mini"
            >08:00开领</el-button
          >
        </div>
      </el-col>
    </el-row>
    <el-card class="box-card1">
      <div slot="header" class="clearfix">
        <span>适用门店(123)</span>
        <el-button style="float: right; padding: 4px 0" type="text"
          >更多</el-button
        >
        <i class="ri-menu-unfold-fill" style="float: right; padding: 1px 0"></i>
      </div>
      <div class="text item">
        <h5>
          上影国际影城(鄞州体育馆店)&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp据您1.56km
        </h5>
        <i class="el-icon-location-outline"></i>
        <span style="font-size: x-small"
          >鄞州区首南街道文化路135号联安文体广场3F</span
        >
      </div>
    </el-card>

    <el-card class="box-card2">
      <div slot="header" class="clearfix">
        <span>领取须知</span>
      </div>
      <div class="text item">
        <i class="ri-calendar-check-fill"></i>
        使用有效期<br />
        <span style="font-size: x-small">领券后7天之内有效</span>
      </div>
      <div class="text item">
        <i class="ri-compass-line"></i>&nbsp适用对象
        <br />
        <span style="font-size: x-small"
          >开通建行数字人民币钱包的用户领取规则</span
        >
      </div>
      <div class="text item">
        <i class="ri-message-2-line"></i>&nbsp领取规则
        <br />
        <span style="font-size: x-small"
          >1．活动时间：2022年12月1日00:00一2022年12月31日23:59。2．领券时间：每天08:00开领，每天限量8万张，领完即止。
          3．同一用户每月限领1张优惠券。用户可在【建行生活 APP
          ﹣我的﹣优惠券】查看查看领取成功的优惠券，优惠券领取后7日有效，超时未使用自动失。
          4．使用本券在合作商家通过指定支付方式买单可享受单笔订单额满100元抵扣50元的优惠。具体适用门店详见优惠券详情页适用门店列表，具体适用商品以商家安排为准（本优惠券不适用于购买或者充值会员卡、餐券等预付卡），请在用券前咨询商家。</span
        >
      </div>
    </el-card>
    <el-card class="box-card2">
      <div slot="header" class="clearfix">
        <span>优惠券详情</span>
      </div>
      <div class="text item">
        <span style="font-size: x-small"
          >1．支付方式： (1）用户在【建行生活 APP
          】内使用“数字人民币”进行支付可使用优惠券（仅支持一类、二类数
          字人民币钱包使用）。
          (2)优惠券通过“数字人民币”支付使用时，开通建行数字人民币钱包的预留手机号需与建行生活注册用户手机号一致。 </span
        ><br />
        <span style="font-size: x-small"
          >2．用户领取成功后，可在优惠券详情页点击“去使用”按钮，选择当前消费门店，点击“买单”按钮，根据提示输入买单金额或者展示付款码买单（使用付款码时默认会使用优惠力度最大的优惠券。优惠力度相同时，优先使用先到期的优惠券）。</span
        ><br />
        <span style="font-size: x-small"
          >3．本券仅适用于用户本人到店消费使用，不支持远程下单核券。请在下单前先领券使用，若下单后领券，则需重新下单后オ能使用优惠券。同一用户单次消费、单笔订单限使用1张优惠券，优惠券不可叠加使用，不与建行生活
          APP其他优惠叠加使用。同一银行卡卡号、同一身份证号、同一登录账号、同一手机号、同一终端设备号或其它合理显示为同一用
          户的情形，均视为同一用户。</span
        >
        <br />
        <span style="font-size: x-small"
          >4．优惠券不支持提现，截屏、修改无效，打印、复印无效；由于本优惠券为不记名券码，请用户妥善保管。若整笔订单退款且发生退款时使用的优惠券仍在有效期内，则还用户实际支付金额（不包含优惠券抵扣金额）以及已使用的优惠券（有效期不予延长）；整笔订单退款时所使用的优惠券已过有效期的或仅就订单中部分商品进行退款的，仅退还用户实际支付的金额（不包含优惠券抵扣部分），优惠券不予退还。</span
        >
      </div>
    </el-card>
    <footer class="footer" id="footer">
      <div class="footer_bd" id="footerbd">
        <div class="footer_right">
          <div class="pay_btn">0元抢</div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
import "../fonts/remixicon.css";
import "../css/base.css";

export default {
  data() {
    return {};
  },
  methods: {
    Fallback() {
      this.$router.push("/");
      localStorage.clear();
    },
  },
};
</script>

<style scoped>
.wrapper {
  width: 100%;
  height: 100%;
}
.header .p1 {
  margin-right: auto;
}
.header .p2 {
  margin-right: 120px;
}
.row1 {
  line-height: 30px;
  margin-top: 70px;
}
/* .rol2 {
  margin-top: 15px;
} */

.box-card1 {
  margin-top: 8px;
}
.box-card2 {
  margin-top: 5px;
}
/* 底部 */
.footer {
  position: fixed;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 58px;
  bottom: 0;
  padding: 0 60px;
  background-color: #fff;
}

/* .footer .footer_hd {
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
} */

/* .footer .footer_bd {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 16px;
  width: 100%;
  height: 88px;
  background-color: #fff;
} */

.footer .footer_bd .footer_right {
  width: 108px;
  height: 100%;
}

.footer .footer_bd .footer_right .pay_btn {
  margin-top: 6px;
  margin-left: -95px;
  width: 300px;
  height: 40px;
  background-color: #fc5531;
  border-radius: 20px;
  text-align: center;
  line-height: 38px;
  color: #fff;
}
</style>