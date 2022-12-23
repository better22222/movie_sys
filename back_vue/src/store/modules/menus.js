import Vue from 'vue'
import Vuex from 'vuex'
export default{
    state:{
        //系统菜单数据
        menuList:[],
        //系统权限数据
        permList:[],
        //表示菜单是否加载过
        hasRoute: false,
        //选项卡，记录当前激活的选项卡
        editableTabsValue:'Index', //默认激活首页
        //选项卡，记录所有打开的选项卡
        editableTabs:[
            //默认打开首页
            {
                title:'首页',
                name:'Index'
            }
        ]

    },
    mutations:{
        //设置存储菜单  
        setMenuList(state, menus){
            state.menuList = menus
            //将数据存入session
            sessionStorage.setItem('menuList',JSON.stringify(menus))
        },
        //设置存储权限信息
        setPermList(state,authoritys){
            state.permList = authoritys

        },
        //修改hasRoute（是否已加载
        changeRouteStatus(state, hasRoute){
            state.hasRoute = hasRoute
            sessionStorage.setItem('hasRoute',hasRoute)
        },
        //设置激活的选型卡
        setActiveTable(state,tabName){
            state.editableTabsValue = tabName
        },
        //添加打开的选项卡
        addActiveTable(state,tabName){
            //如果点击菜单需要打开的选项卡已在数组中，不需要添加
            //只需将其设置为激活状态
            let index = state.editableTabs.findIndex(item =>
                item.name == tabName.name
            )
            //不存在选型卡
            if(index == -1){
                //将新的选项卡对象添加进选项卡数组中
                state.editableTabs.push(tabName)
            }
            //激活该选项卡
            state.editableTabsValue = tabName.name
        },
        resetState: state=>{
            state.menuList =[]
            state.permList =[]
            state.hasRoute = false
            state.editableTabsValue="Index"
            state.editableTabs=[
                {
                    title:'首页',
                    name:'Index'
                }
            ]
        }
    },
    actions:{

    }
    
}