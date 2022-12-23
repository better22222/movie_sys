import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '@/views/Index.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import Arrangement from '@/views/Arrangement.vue'
import FilmDetails from '@/views/FilmDetails.vue'
import Seat from '@/views/Seat.vue'
import Orders from '@/views/Orders.vue'
import UserCenter from '@/views/UserCenter.vue'
import ShoppingCart from '@/views/ShoppingCart.vue'
import Tickets from '@/views/Tickets.vue'
import OrderList from '@/views/OrderList.vue'
import Vip from '@/views/Vip.vue'
import Personal from '@/views/Personal.vue'
import Discount from '@/views/Discount.vue'
import Fenlei from '@/views/Fenlei.vue'
Vue.use(VueRouter)

const routes = [
  {
    name: 'Index',
    path: '/',
    component: Index,
  },
  {
    name: 'Login',
    path: '/login',
    component: Login,
  },
  {
    name: 'Arrangement',
    path: '/arrangement',
    component: Arrangement,
  },
  {
    name: 'Seat',
    path: '/seat',
    component: Seat,
  },
  {
    name: 'Register',
    path: '/register',
    component: Register,
  },
  {
    name: 'FilmDetails',
    path: '/filmDetails',
    component: FilmDetails
  },
  {
    name: 'Orders',
    path: '/orders',
    component: Orders
  },
  {
    name: 'UserCenter',
    path:'/userCenter',
    component: UserCenter
  },
  {
    name:'ShoppingCart',
    path:'/shoppingCart',
    component: ShoppingCart
  },
  {
    name:'Tickets',
    path:'/tickets',
    component: Tickets
  },
  {
    name:'OrderList',
    path:'/orderList',
    component:OrderList
  },
  {
    name:'Vip',
    path:'/vip',
    component: Vip
  },
  {
    name:'Personal',
    path:'/personal',
    component:Personal
  },
  {
    name:'Discount',
    path:'/discount',
    component:Discount
  },
  {
    name:'Fenlei',
    path:'/fenlei',
    component:Fenlei
  }

]

const router = new VueRouter({
  mode: 'history',
  routes,
})

export default router
