import store from '../store'
import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from "../views/Login.vue"
import axios from '@/api/request.js'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/index',
        name: 'Index',
        component: () => import('@/views/Index.vue'),
      },
      {
        path:'/changeP',
        name:'ChangeP',
        meta:{
          icos:'el-icon-s-order',
          title:'修改密码'
        },
        component:() => import('@/views/sys/ChangeP.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
]



const router = new VueRouter({
  mode: 'history',
  routes
})


//配置前置的路由守卫,类似路由的前置拦截器
//动态的读取菜单信息，根据菜单信息动态的配置路由
router.beforeEach((to, from, next) => {
  //从vuex中的module中读取hasRoute
  let hasRoute = store.state.menus.hasRoute
  //从local读取token，确认当前用户是否登录
  let token = localStorage.getItem('token')

  //登录页面直接放行,
  if (to.path == '/login') {
    next()
  }

  if (!token) {
    //未登录，跳转到登录页面
    next({ path: '/login' })
  }

  if (!hasRoute && token) {
    //hasRoute未加载过，加载菜单和动态路由配置
    //从服务器读取菜单数据与权限数据
    axios.get("/menu/nav").then(response => {
      store.commit('setMenuList', response.data.data.nav)
      store.commit('setPermList', response.data.data.authoritys)
      //动态路由配置
      response.data.data.nav.forEach((item) => {
        //当前item有子菜单，即其是一级菜单，不用路由
        if (item.children) {
          item.children.forEach(child => {
            //转换路由对象
            let route = menuToRoute(child)
            //子菜单路由对不能未空
            if (route) {
              routes[0].children.push(route)
            }
          })
        }
      })
      //将配置完后的新路由，加载如现有的ruoute对象内
      router.addRoutes(routes)
      //菜单已被加载过，修改状态
      store.commit('changeRouteStatus', true)
      next({ path: to.path })
    });
  }
  //放行，跳转目标路径
  next()
})

//添加方法，将菜单信息转换成路由信息
const menuToRoute = menu => {
  if (!menu.component) {
    return null
  }
  //定义一个路由对象
  let route = {
    path: menu.path,
    name: menu.name,
    meta: {
      icon: menu.icon,
      title: menu.title
    }
  }

  route.component = () => import('@/views/' + menu.component + '.vue')

  return route
}


export default router
