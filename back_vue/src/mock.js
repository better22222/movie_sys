//引入mockjs
const Mock = require('mockjs')

//模拟验证码响应
const Random = Mock.Random //获得mocl.Random对象

//服务器返回数据的结构
let Result = {
  code: 200,
  message: '操作成功',
  data: null
}

//Mock.mock(uri,post/get,function(){}) 模拟服务器响应

//验证码模拟响应 
//captcha,从服务器获取验证码的路径
Mock.mock('/captcha', 'get', () => {
  //模拟生成数据：验证码
  Result.data = {
    key: Random.toString(32), //获取一个32位的随机码字符串,
    captchaImg: Random.dataImage('120x40', '7fa5w'), //生成验证码是

  }
  return Result
})

//模拟登录响应
Mock.mock('/login', 'post', () => {
  return Result //登录成功
})

//登出用户
Mock.mock('/logout', 'post', () => {
  return Result
})


//返回用户信息的响应
Mock.mock('/user/userInfo', 'get', () => {
  Result.data = {
    id: '1',
    username: 'admin',
    avatar: 'https://img1.baidu.com/it/u=1811445190,4171898561&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500'
  }
  return Result
})

//模拟菜单数据的响应
Mock.mock('/menu/nav', 'get', () => {
  //菜单JSON，从Menu表中查询出来的数据
  let menu_json = [
    {
      id: 1,
      name: 'sys:manage',
      title: '系统管理',
      icon: 'el-icon-s-operation',
      path: '', //点击菜单后跳转的地址
      component: '', //路由组件名
      children: [
        {
          id: 2,
          name: "sys:user:list",
          title: "用户管理",
          icon: 'el-icon-s-custom',
          path: "/sys/users",
          component: 'sys/User',
          children: []
        },
        {
          id: 3,
          name: "sys:role:list",
          title: "角色管理",
          icon: 'el-icon-rank',
          path: "/sys/roles",
          component: 'sys/Role',
          children: []
        },
        {
          id: 4,
          name: "sys:menu:list",
          title: "菜单管理",
          icon: 'el-icon-s-management',
          path: "/sys/menus",
          component: 'sys/Menu',
          children: []
        },
      ]
    },
    {
      id: 5,
      name: 'customer:manage',
      title: '账户管理',
      icon: 'el-icon-s-finance',
      path: '', //点击菜单后跳转的地址
      component: '', //路由组件名
      children: [
        {
          id: 6,
          name: "customer:info:list",
          title: "用户信息管理",
          icon: 'el-icon-s-custom',
          path: "/sys/customer",
          component: 'customer/CustomerInfo',
          children: []
        },
        {
          id: 7,
          name: "order:info:list",
          title: "订单信息管理",
          icon: 'el-icon-s-custom',
          path: "/sys/order",
          component: 'customer/OrderInfo',
          children: []
        },
        {
          id: 8,
          name: "cart:info:list",
          title: "购物车管理",
          icon: 'el-icon-s-custom',
          path: "/sys/cart",
          component: 'customer/CartInfo',
          children: []
        },
      ]
    },
    {
      id: 9,
      name: 'film:manage',
      title: '电影管理',
      icon: 'el-icon-s-finance',
      path: '', //点击菜单后跳转的地址
      component: '', //路由组件名
      children: [
        {
          id: 10,
          name: "film:info:list",
          title: "电影信息管理",
          icon: 'el-icon-s-custom',
          path: "/sys/film",
          component: 'film/FilmInfo',
          children: []
        },
        {
          id: 11,
          name: "film:arrangement:list",
          title: "电影排片管理",
          icon: 'el-icon-s-custom',
          path: "/sys/arrangement",
          component: 'film/FilmArrangment',
          children: []
        },
        {
          id: 12,
          name: "film:evaluate:list",
          title: "电影评论管理",
          icon: 'el-icon-s-custom',
          path: "/sys/evaluate",
          component: 'film/FilmEvaluate',
          children: []
        },
      ]
    },
    {
      id: 13,
      name: 'sys:tools',
      title: '系统工具',
      icon: 'el-icon-s-tools',
      path: '', //点击菜单后跳转的地址
      component: '', //路由组件名
      children: [
        {
          id: 14,
          name: "sys:data",
          title: "数据管理",
          icon: 'el-icon-s-order',
          path: "/sys/data",
          component: 'sysTool/Data',
          children: []
        },
        {
          id: 15,
          name: "sys:upload",
          title: "文件管理",
          icon: 'el-icon-s-order',
          path: "/sys/upload",
          component: 'sysTool/Upload',
          children: []
        },
        {
          id: 16,
          name: "sys:poster",
          title: "广告图管理",
          icon: 'el-icon-s-order',
          path: "/sys/poster",
          component: 'sysTool/Poster',
          children: []
        },
      ]
    }
  ]

  //权限的限定数据
  let authoritys = ['sys:manage', 'sys:user:list']
  Result.data = {}
  Result.data.nav = menu_json
  Result.data.authoritys = authoritys
  return Result
})

//模拟： 响应获得菜单的数据  仔细阅读
Mock.mock('/system/menu/list', 'get', () => {
  let menus = [
    {
      id: 1,
      created: '2021-01-15T18:58:18',
      updated: '2021-01-15T18:58:20',
      statu: 1,
      parentId: 0,
      name: '系统管理',
      path: '',
      perms: 'sys:manage',
      component: '',
      type: 0,
      icon: 'el-icon-s-operation',
      ordernum: 1,
      children: [
        {
          id: 2,
          created: '2021-01-15T19:03:45',
          updated: '2021-01-15T19:03:48',
          statu: 1,
          parentId: 1,
          name: '用户管理',
          path: '/system/users',
          perms: 'sys:user:list',
          component: 'system/User',
          type: 1,
          icon: 'el-icon-s-custom',
          ordernum: 1,
          children: [
            {
              id: 9,
              created: '2021-01-17T21:48:32',
              updated: null,
              statu: 1,
              parentId: 2,
              name: '添加用户',
              path: null,
              perms: 'sys:user:save',
              component: null,
              type: 2,
              icon: null,
              ordernum: 1,
              children: [],
            },
            {
              id: 10,
              created: '2021-01-17T21:49:03',
              updated: '2021-01-17T21:53:04',
              statu: 1,
              parentId: 2,
              name: '修改用户',
              path: null,
              perms: 'sys:user:update',
              component: null,
              type: 2,
              icon: null,
              ordernum: 2,
              children: [],
            },
            {
              id: 11,
              created: '2021-01-17T21:49:21',
              updated: null,
              statu: 1,
              parentId: 2,
              name: '删除用户',
              path: null,
              perms: 'sys:user:delete',
              component: null,
              type: 2,
              icon: null,
              ordernum: 3,
              children: [],
            },
            {
              id: 12,
              created: '2021-01-17T21:49:58',
              updated: null,
              statu: 1,
              parentId: 2,
              name: '分配角色',
              path: null,
              perms: 'sys:user:role',
              component: null,
              type: 2,
              icon: null,
              ordernum: 4,
              children: [],
            },
            {
              id: 13,
              created: '2021-01-17T21:50:36',
              updated: null,
              statu: 0,
              parentId: 2,
              name: '重置密码',
              path: null,
              perms: 'sys:user:repass',
              component: null,
              type: 2,
              icon: null,
              ordernum: 5,
              children: [],
            },
          ],
        },
        {
          id: 3,
          created: '2021-01-15T19:03:45',
          updated: '2021-01-15T19:03:48',
          statu: 1,
          parentId: 1,
          name: '角色管理',
          path: '/system/roles',
          perms: 'sys:role:list',
          component: 'system/Role',
          type: 1,
          icon: 'el-icon-rank',
          ordernum: 2,
          children: [],
        },
      ],
    },
    {
      id: 5,
      created: '2021-01-15T19:06:11',
      updated: null,
      statu: 1,
      parentId: 0,
      name: '系统工具',
      path: '',
      perms: 'sys:tools',
      component: null,
      type: 0,
      icon: 'el-icon-s-tools',
      ordernum: 2,
      children: [
        {
          id: 6,
          created: '2021-01-15T19:07:18',
          updated: '2021-01-18T16:32:13',
          statu: 1,
          parentId: 5,
          name: '数字字典',
          path: '/system/dicts',
          perms: 'sys:dict:list',
          component: 'system/Dict',
          type: 1,
          icon: 'el-icon-s-order',
          ordernum: 1,
          children: [],
        },
      ],
    },
  ]

  Result.data = menus

  return Result
})

//模拟：删除菜单操作
//*表示执行操作是delete、insert、update
Mock.mock(RegExp('/system/menu/delete/*'), 'post', () => {
  return Result
})

Mock.mock(RegExp('/system/menu/*'), 'post', () => {
  //代表模拟删除、插入、更新操作都是成功的。
  return Result
})


//修改密码验证
Mock.mock('/system/changePsw/', 'post', () => {
  return Result
})

Mock.mock(RegExp('/system/menu/info/*'), 'get', () => {
  Result.data = {
    id: 3,
    statu: 1,
    parentId: 1,
    name: 'name',
    path: 'path',
    perms: 'perms',
    component: 'component',
    type: 1,
    icon: "1",
    orderNum: '2',
    children: '[]'
  }
  return Result
})


//获得所有角色信息
Mock.mock(RegExp('/system/role/list*'), 'get', () => {
  Result.data = {
    //records封装分页的数据
    records: [
      {
        id: 3,
        created: '2021-01-04T10:09:14',
        updated: '2021-01-30T08:19:52',
        statu: 1,
        name: '普通用户',
        code: 'normal',
        remark: '只有基本查看功能',
        menuIds: [],
      },
      {
        id: 6,
        created: '2021-01-16T13:29:03',
        updated: '2021-01-17T15:50:45',
        statu: 0,
        name: '超级管理员',
        code: 'admin',
        remark: '系统默认最高权限，不可以编辑和任意修改 ',
        menuIds: [],
      },
    ],
    total: 2, //当前总记录数
    page: 10, //一页显示多少数据
    current: 1, //当前页码
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1, //一共有多少页
  }

  return Result
})

//////通过角色id获得角色信息///////   RestFul
//* 参数就是传递角色编号 /system/role/info/6
Mock.mock(RegExp('/system/role/info/*'), 'get', () => {
  Result.data = {
    id: 6,
    created: '2021-01-16T13:29:03',
    updated: '2021-01-17T15:50:45',
    statu: 1,
    name: '超级管理员',
    code: 'admin',
    remark: '系统默认最高权限，不可以编辑和任意修改',
    menuIds: [9, 10, 11, 12, 13], //菜单id数组，就是该角色权限信息
  }

  return Result
})

///////////保存或更新 、删除 角色方法////////////
//* 代表传递参数，update是更新角色，save要保存新角色
Mock.mock(RegExp('/system/role/*'), 'post', () => {
  return Result
})



///////////保存或更新 、删除顾客 方法////////////
//* 代表传递参数，update是更新顾客，save要保存新顾客
Mock.mock(RegExp('/system/customer/*'), 'post', () => {
  return Result
})


///////////保存或更新 、删除电影 方法////////////
//* 代表传递参数，update是更新电影，save要保存新电影
Mock.mock(RegExp('/system/film/*'), 'post', () => {
  return Result
})


///////////保存或更新 、删除排片 方法////////////
//* 代表传递参数，update是更新排，save要保存新排片
Mock.mock(RegExp('/system/arrangement/*'), 'post', () => {
  return Result
})

///////////保存或更新 、删除评论方法////////////
//* 代表传递参数，update是更新评论，save要保存新评论
Mock.mock(RegExp('/system/evaluate/*'), 'post', () => {
  return Result
})

///////////保存或更新 、删除订单方法////////////
//* 代表传递参数，update是更新订单，save要保存新订单
Mock.mock(RegExp('/system/order/*'), 'post', () => {
  return Result
})

///////////保存或更新 、删除购物车方法////////////
//* 代表传递参数，update是更新购物车，save要保存新购物车
Mock.mock(RegExp('/system/cart/*'), 'post', () => {
  return Result
})



///////////保存或更新 、删除轮播图方法////////////
//* 代表传递参数，update是更新轮播图，save要保存新轮播图
Mock.mock(RegExp('/system/poster/*'), 'post', () => {
  return Result
})

///获得所有用户的信息////
//////////////// 用户管理 ////////////////
Mock.mock(RegExp('/system/user/list*'), 'get', () => {
  Result.data = {
    records: [
      {
        id: 1,
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
        username: 'admin',
        password:
          '$2a$10$oPaOFhl1IYrUgHj3kcpW7OJYR9N9wGudceRdNVIs8aSY4Q7rLCGuK', //123123
        avatar:
          'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202107%2F09%2F20210709142454_dc8dc.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666333236&t=3733d94221afc7ea3189dc0ffa30fd47',
        email: '999@qq.com',
        tel: '15343517807',
        lastLogin: '2020-12-30T08:38:37',
        roles: [
          {
            id: 6,
            created: '2021-01-16T13:29:03',
            updated: '2021-01-17T15:50:45',
            statu: 1,
            name: '超级管理员',
            code: 'admin',
            remark: '系统默认最高权限，不可以编辑和任意修改',
            menuIds: [],
          },
          {
            id: 3,
            created: '2021-01-04T10:09:14',
            updated: '2021-01-30T08:19:52',
            statu: 1,
            name: '普通用户',
            code: 'normal',
            remark: '只有基本查看功能',
            menuIds: [],
          },
        ],
      },
      {
        id: 2,
        created: '2021-01-30T08:20:22',
        updated: '2021-01-30T08:55:57',
        statu: 1,
        username: 'test',
        password:
          '$2a$10$oPaOFhl1IYrUgHj3kcpW7OJYR9N9wGudceRdNVIs8aSY4Q7rLCGuK',
        avatar:
          'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201908%2F19%2F20190819150344_ALnaX.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666333236&t=a23b8c8c9f154ea09f54728b6e04e7f9',
        email: 'test@qq.com',
        tel: 133667788899,
        lastLogin: null,
        roles: [
          {
            id: 3,
            created: '2021-01-04T10:09:14',
            updated: '2021-01-30T08:19:52',
            statu: 1,
            name: '普通用户',
            code: 'normal',
            remark: '只有基本查看功能',
            menuIds: [],
          },
        ],
      },
    ],
    total: 2,
    size: 10,
    current: 1,
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1,
  }
  return Result
})



///获得所有顾客的信息////
Mock.mock(RegExp('/system/customer/list*'), 'get', () => {
  Result.data = {
    records: [
      {
        id: 1,
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
        username: '18757426466',
        password:
          '$2a$10$oPaOFhl1IYrUgHj3kcpW7OJYR9N9wGudceRdNVIs8aSY4Q7rLCGuK', //123123
        avatar:
          'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202107%2F09%2F20210709142454_dc8dc.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666333236&t=3733d94221afc7ea3189dc0ffa30fd47',
        email: '999@qq.com',
        birthday: '2020-12-30',
        lastLogin: '2020-12-30T08:38:37',
        gender: 1,
        nickname: " zz"

      },
      {
        id: 2,
        created: '2021-01-30T08:20:22',
        updated: '2021-01-30T08:55:57',
        statu: 1,
        username: 'test1',
        password:
          '$2a$10$oPaOFhl1IYrUgHj3kcpW7OJYR9N9wGudceRdNVIs8aSY4Q7rLCGuK',
        avatar:
          'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201908%2F19%2F20190819150344_ALnaX.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666333236&t=a23b8c8c9f154ea09f54728b6e04e7f9',
        email: 'test@qq.com',
        birthday: '2020-12-30',
        lastLogin: '2020-12-30T08:38:37',
        gender: 0,
        nickname: "苦子",
        info: "兴趣使然的懒惰"
      },
    ],
    total: 2,
    size: 10,
    current: 1,
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1,
  }
  return Result
})


/////////////////////根据用户编号id 获得用户信息/////////////////////////
Mock.mock(RegExp('/system/user/info/*'), 'get', () => {
  Result.data = {
    id: 2,
    created: '2022-01-30T08:20:22',
    updated: '2022-01-30T08:55:57',
    statu: 1,
    username: 'test',
    password: '$2a$10$oPaOFhl1IYrUgHj3kcpW7OJYR9N9wGudceRdNVIs8aSY4Q7rLCGuK',
    avatar:
      'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201908%2F19%2F20190819150344_ALnaX.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666333236&t=a23b8c8c9f154ea09f54728b6e04e7f9',
    email: '999@qq.com',
    tel: '15343517807',
    lastLogin: null,
    roles: [
      //该用户目前所拥有的角色对象
      {
        id: 6,
        created: '2021-01-16T13:29:03',
        updated: '2021-01-17T15:50:45',
        statu: 1,
        name: '超级管理员',
        code: 'admin',
        remark: '系统默认最高权限，不可以编辑和任意修改',
        menuIds: [],
      },
      {
        id: 3,
        created: '2021-01-04T10:09:14',
        updated: '2021-01-30T08:19:52',
        statu: 1,
        name: '普通用户',
        code: 'normal',
        remark: '只有基本查看功能',
        menuIds: [],
      },
    ],
  }
  return Result
})


/////////////////////根据顾客编号id 获得顾客信息/////////////////////////
Mock.mock(RegExp('/system/customer/info/*'), 'get', () => {
  Result.data = {
    id: 1,
    created: '2021-01-12T22:13:53',
    updated: '2021-01-16T16:57:32',
    statu: 1,
    username: '18757426466',
    password:
      '$2a$10$oPaOFhl1IYrUgHj3kcpW7OJYR9N9wGudceRdNVIs8aSY4Q7rLCGuK', //123123
    avatar:
      'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202107%2F09%2F20210709142454_dc8dc.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666333236&t=3733d94221afc7ea3189dc0ffa30fd47',
    email: '999@qq.com',
    birthday: '2020-12-30',
    lastLogin: '2020-12-30T08:38:37',
    gender: 1,
    nickname: " zz"
  }
  return Result
})

/////////////////////根据电影编号id 获得电影信息/////////////////////////
Mock.mock(RegExp('/system/film/info/*'), 'get', () => {
  Result.data = {
    id: 1,
    name:"夺命六头鲨",
    created: '2021-01-12T22:13:53',
    updated: '2021-01-16T16:57:32',
    release_time: '2021-01-16T16:57:32',
    statu: 1,
    type:'科幻',
    region:'美国',
    hot:"782",
    introduction:'《夺命六头鲨》（又名《六头鲨来袭》）是由马克.阿特金斯执导，马克·阿特金斯编剧，Meghan Oberholzer、Thandi Sebe、Caitlin Harty、Naima Sebe、Nikita Faber、Marie Cavanna、雅伊玛·托雷斯、布兰登·欧瑞特、Cord Newman、Chris Fisher、Wilco Wilkens、Tapiwa Musvosvi等人主演的动作，冒险，喜剧，惊悚，恐怖，灾难，爱情，科幻片 [1-5]     。为《夺命双头鲨》《夺命三头鲨》《夺命五头鲨》的续作。',
    cover:
      'https://img1.baidu.com/it/u=1848816660,1227023098&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750',
    duration:'120'
  }
  return Result
})


/////////////////////根据电影编号id 获得电影信息/////////////////////////
Mock.mock(RegExp('/system/evaluate/info/*'), 'get', () => {
  Result.data = {
    id: 1,
    fid:1,
    uid:1,
    star:4,
    comment:"鲨鱼实在太可爱啦！",
    created: '2021-01-12T22:13:53',
    updated: '2021-01-16T16:57:32',
    statu: 1,
    film:{
      fname:"绝命四头鲨"
    },
    customer:{
      cname:"苦子"
    }
  }
  return Result
})


///获得所有评论的信息////
Mock.mock(RegExp('/system/evaluate/list*'), 'get', () => {
  Result.data = {
    records: [
      {
        id: 1,
        fid:1,
        uid:1,
        star:3.5,
        comment:"鲨鱼实在太可爱啦！",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
        film:{
          fname:"绝命四头鲨"
        },
        customer:{
          cname:"苦子"
        }
      },
      {
        id: 2,
        fid:2,
        uid:1,
        star:4,
        comment:"鱼头越多越厉害吧",
        created: '2021-01-11T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
        film:{
          fname:"绝命六头鲨"
        },
        customer:{
          cname:"苦子"
        }
      },
    ],
    total: 2,
    size: 10,
    current: 1,
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1,
  }
  return Result
})

///获得所有电影的信息////
Mock.mock(RegExp('/system/film/list*'), 'get', () => {
  Result.data = {
    records: [
      {
        id: 1,
        name:"夺命六头鲨",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        release_time: '2021-01-16T16:57:32',
        statu: 1,
        type:'科幻',
        region:'美国',
        hot:"782",
        introduction:'《夺命六头鲨》（又名《六头鲨来袭》）是由马克.阿特金斯执导，马克·阿特金斯编剧，Meghan Oberholzer、Thandi Sebe、Caitlin Harty、Naima Sebe、Nikita Faber、Marie Cavanna、雅伊玛·托雷斯、布兰登·欧瑞特、Cord Newman、Chris Fisher、Wilco Wilkens、Tapiwa Musvosvi等人主演的动作，冒险，喜剧，惊悚，恐怖，灾难，爱情，科幻片 [1-5]     。为《夺命双头鲨》《夺命三头鲨》《夺命五头鲨》的续作。',
        cover:
          'https://img1.baidu.com/it/u=1848816660,1227023098&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750',
        duration:'120'
      },
      {
        id: 2,
        name:"夺命五头鲨",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        release_time: '2021-11-16T16:57:32',
        statu: 0,
        type:'科幻',
        region:'非洲',
        hot:"666",
        introduction:'《夺命五头鲨》是Nico De Leon执导，Lorna Hernandez、Michelle Cortes、Lindsay Sawyer、Amaanda Méndez、Nikki Alexis Howard、Stephanie Cruz、Yinoelle Colón等人主演的动作，冒险，喜剧，惊悚，恐怖，科幻，灾难电影',
        cover:
          'https://img2.baidu.com/it/u=1519050340,3225006097&fm=253&fmt=auto&app=138&f=JPEG?w=354&h=500',
        duration:'120'
      },
    ],
    total: 2,
    size: 10,
    current: 1,
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1,
  }
  return Result
})

///获得所有排片的信息////
Mock.mock(RegExp('/system/arrangement/list*'), 'get', () => {
  Result.data = {
    records: [
      {
        id: 1,
        name:"夺命六头鲨",
        seat_number:88,
        box_office:256115,
        price:66,
        type:'2D',
        date:"2021-01-12",
        start_time:"22:13:53",
        end_time:"22:15:53",
        founder:"苦子",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
      },
      {
        id: 2,
        name:"夺命四头鲨",
        seat_number:88,
        box_office:25005,
        price:61,
        type:'3D',
        date:"2021-01-12",
        start_time:"22:13:53",
        end_time:"22:15:53",
        founder:"苦子",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
      },
      {
        id: 3,
        name:"夺命三头鲨",
        seat_number:88,
        box_office:256115,
        price:66,
        type:'2D',
        date:"2021-01-12",
        start_time:"22:13:53",
        end_time:"22:15:53",
        founder:"苦子",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 0,
      },
    ],
    total: 2,
    size: 10,
    current: 1,
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1,
  }
  return Result
})

/////////////////////根据排片编号id 获得排片信息/////////////////////////
Mock.mock(RegExp('/system/arrangement/info/*'), 'get', () => {
  Result.data = {
    id: 1,
    name:"夺命六头鲨",
    seat_number:88,
    box_office:256115,
    price:66,
    type:'2D',
    date:"2021-01-12",
    start_time:"2021-01-12T20:13:53",
    end_time:"2021-01-12T22:15:53",
    founder:"苦子",
    created: '2021-01-12T22:13:53',
    updated: '2021-01-16T16:57:32',
    statu: 1,
  }
  return Result
})


///获得所有电影的信息////
Mock.mock(RegExp('/system/film/list*'), 'get', () => {
  Result.data = {
    records: [
      {
        id: 1,
        name:"夺命六头鲨",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        release_time: '2021-01-16T16:57:32',
        statu: 1,
        type:'科幻',
        region:'美国',
        hot:"782",
        introduction:'《夺命六头鲨》（又名《六头鲨来袭》）是由马克.阿特金斯执导，马克·阿特金斯编剧，Meghan Oberholzer、Thandi Sebe、Caitlin Harty、Naima Sebe、Nikita Faber、Marie Cavanna、雅伊玛·托雷斯、布兰登·欧瑞特、Cord Newman、Chris Fisher、Wilco Wilkens、Tapiwa Musvosvi等人主演的动作，冒险，喜剧，惊悚，恐怖，灾难，爱情，科幻片 [1-5]     。为《夺命双头鲨》《夺命三头鲨》《夺命五头鲨》的续作。',
        cover:
          'https://img1.baidu.com/it/u=1848816660,1227023098&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750',
        duration:'120'
      },
      {
        id: 2,
        name:"夺命五头鲨",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        release_time: '2021-11-16T16:57:32',
        statu: 0,
        type:'科幻',
        region:'非洲',
        hot:"666",
        introduction:'《夺命五头鲨》是Nico De Leon执导，Lorna Hernandez、Michelle Cortes、Lindsay Sawyer、Amaanda Méndez、Nikki Alexis Howard、Stephanie Cruz、Yinoelle Colón等人主演的动作，冒险，喜剧，惊悚，恐怖，科幻，灾难电影',
        cover:
          'https://img2.baidu.com/it/u=1519050340,3225006097&fm=253&fmt=auto&app=138&f=JPEG?w=354&h=500',
        duration:'120'
      },
    ],
    total: 2,
    size: 10,
    current: 1,
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1,
  }
  return Result
})


///获得所有订单的信息////
Mock.mock(RegExp('/system/order/list*'), 'get', () => {
  Result.data = {
    records: [
      {
        id: 1324234,
        uid:1,
        phone:"18757426466",
        aid:2,
        seats:'24-1',
        price:66,
        status:1,
        pay_at:'2021-01-12T22:13:53',
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
      },
      {
        id: 11341412,
        uid:2,
        phone:"18757426466",
        aid:2,
        seats:'21-3',
        price:88,
        status:2,
        pay_at:'2021-01-12T22:13:53',
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
      },
      {
        id: 132423432,
        uid:2,
        phone:"18757426466",
        aid:2,
        seats:'21-3',
        price:88,
        status:3,
        pay_at:'2021-01-12T22:13:53',
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
      },
      {
        id: 234233423,
        uid:2,
        phone:"18757426466",
        aid:2,
        seats:'21-3',
        price:88,
        status:3,
        pay_at:'2021-01-12T22:13:53',
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
      },
    ],
    total: 2,
    size: 10,
    current: 1,
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1,
  }
  return Result
})


/////////////////////根据排片编号id 获得订单信息/////////////////////////
Mock.mock(RegExp('/system/order/info/*'), 'get', () => {
  Result.data = {
    id: 12312312,
    uid:1,
    phone:"18757426466",
    aid:2,
    seats:'24-1',
    price:66,
    status:1,
    pay_at:'2021-01-12T22:13:53',
    created: '2021-01-12T22:13:53',
    updated: '2021-01-16T16:57:32',
    statu: 1,
  }
  return Result
})



/////////////////////根据购物车编号id 获得购物车信息/////////////////////////
Mock.mock(RegExp('/system/cart/info/*'), 'get', () => {
  Result.data = {
    id: 112321321321213,
    uid:2,
    aid:3,
    phone:"18757426466",
    seats:"24-1",
    price:66,
    created: '2021-01-12T22:13:53',
    updated: '2021-01-16T16:57:32',
    release_time: '2021-01-16T16:57:32',
    statu: 1,
  }
  return Result
})


///获得所有购物车的信息////
Mock.mock(RegExp('/system/cart/list*'), 'get', () => {
  Result.data = {
    records: [
      {
        id: 123123213211,
        uid:2,
        aid:3,
        phone:"18757426466",
        seats:"24-1",
        price:66,
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        release_time: '2021-01-16T16:57:32',
        statu: 1,
      },
      {
        id: 12312321321321,
        uid:1,
        aid:3,
        phone:"18757426466",
        seats:"24-2",
        price:60,
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        release_time: '2021-01-16T16:57:32',
        statu: 1,
      },
    ],
    total: 2,
    size: 10,
    current: 1,
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1,
  }
  return Result
})


///获得所有轮播图的信息////
Mock.mock(RegExp('/system/poster/list*'), 'get', () => {
  Result.data = {
    records: [
      {
        id: 12312321,
        title:"最新受欢迎的科幻电影",
        url:"https://img1.baidu.com/it/u=1848816660,1227023098&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
      },
      {
        id: 12312323,
        title:"最新受欢迎的科幻电影",
        url:"https://img2.baidu.com/it/u=1519050340,3225006097&fm=253&fmt=auto&app=138&f=JPEG?w=354&h=500",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
      },
    ],
    total: 2,
    size: 10,
    current: 1,
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1,
  }
  return Result
})

/////////////////////根据轮播图编号id 获得购物车信息/////////////////////////
Mock.mock(RegExp('/system/poster/info/*'), 'get', () => {
  Result.data = {
    id: 12312323,
    title:"最新受欢迎的科幻电影",
    url:"https://img2.baidu.com/it/u=1519050340,3225006097&fm=253&fmt=auto&app=138&f=JPEG?w=354&h=500",
    created: '2021-01-12T22:13:53',
    updated: '2021-01-16T16:57:32',
    statu: 1,
  }
  return Result
})


///////////保存或更新 、删除文件方法////////////
//* 代表传递参数，update是更新文件，save要保存新文件
Mock.mock(RegExp('/system/upload/*'), 'post', () => {
  return Result
})

///获得所有文件的信息////
Mock.mock(RegExp('/system/upload/list*'), 'get', () => {
  Result.data = {
    records: [
      {
        id: 12312321,
        bytes:"文件1",
        md5:"12DSASD32E124E@!#e12",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
      },
      {
        id: 12312321,
        bytes:"文件1",
        md5:"12DSASD32E124E@!#e12",
        created: '2021-01-12T22:13:53',
        updated: '2021-01-16T16:57:32',
        statu: 1,
      },
    ],
    total: 2,
    size: 10,
    current: 1,
    orders: [],
    optimizeCountSql: true,
    hitCount: false,
    countId: null,
    maxLimit: null,
    searchCount: true,
    pages: 1,
  }
  return Result
})


//更新密码 /system/user/repass
Mock.mock(RegExp('/system/user/*'), 'post', () => {
  return Result
})

//更改角色名 /system/user/repass
Mock.mock(RegExp('/system/user/role/*'), 'post', () => {
  return Result
})