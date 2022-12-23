<template>
<el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab" 
@tab-click="clickTab"
>

  <el-tab-pane
    :key="index"
    v-for="(item, index) in editableTabs"
    :label="item.title"
    :name="item.name"
   >
  </el-tab-pane>

</el-tabs>
</template>
    
<script type="text/javascript">
export default{
    data(){
        return{}
    },
    computed:{
        //表示当前激活的选项卡
        editableTabsValue:{
            get(){
                return this.$store.state.menus.editableTabsValue
            },
            set(val){
                this.$store.state.menus.editableTabsValue = val
            }
        },
        //所有打开的选项卡
        editableTabs:{
            get(){
                return this.$store.state.menus.editableTabs
            },
            set(val){
                this.$store.state.menus.editableTabs = val
            }
        }
    },
    methods:{
        //移除选项卡方法
        removeTab(targetName){
            //获得所有选项卡
            let tabs = this.editableTabs
            //获得当前激活的选项卡
            let activeName = this.editableTabsValue
            //首页默认页面，不能够删除
            if(targetName == 'Index'){
                return
            }

            //删除的选项卡正是当前激活选项卡
            if(targetName == activeName){
                tabs.forEach((tab,index)=>{
                    if(tab.name == targetName){
                        //若后选项卡后面有值，后选型卡为新选项卡
                        //否者使用前选项卡
                        let nextTab = tabs[index + 1] || tabs[index - 1]
                        if(nextTab){
                            activeName = nextTab.name
                        }
                        console.log(activeName)
                    }
                })
            }

            this.editableTabsValue = activeName
    

            // //删除的不是当前激活的选项卡
            this.editableTabs = []
            tabs.forEach(item =>{
                if(item.name != targetName){
                    this.editableTabs.push(item)
                }
            })

    
            this.$router.push({name : activeName})
        },
        //选项卡单击事件
        clickTab(target){
            this.$router.push({name: target.name})
        }
    }
}

</script>
    