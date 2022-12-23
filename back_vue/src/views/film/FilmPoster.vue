<template>
    <div>
        <!-- 工具栏 -->
        <el-form :inline="true" :model="searchForm">
            <el-form-item>
                <el-input v-model="searchForm.title" placeholder="请输入搜索的内容"></el-input>
            </el-form-item>

            <el-form-item >
                <el-button @click="getPosterList()">搜索</el-button>
                <!-- todo 新建需要进行权限验证 -->
                <el-button type="primary" @click="dialogFormVisible=true">新建</el-button>
               
                <el-popconfirm title="确定要删除这些轮播图的数据吗？" @confirm="deletePoster()">
                        <el-button type="danger" :disabled="deleteBtnStu" slot="reference">批量删除</el-button>
                </el-popconfirm>
            </el-form-item>
        </el-form>
        <!-- 轮播图表格数据 -->
        <el-table :cell-style="{padding:'2px'}" :data="tableData" default-expand-all border stripe style="width: 100%;margin-bottom: 20px;" @selection-change="selectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
          
            <el-table-column prop="title" label="标题" width="160">
            </el-table-column>
            <el-table-column label="轮播图" prop="url" >
                <template slot-scope="scope">
                    <el-image style="width: 200px; height: 100px" :src="scope.row.url">轮播图</el-image>
                </template>
            </el-table-column>
           
            <el-table-column prop="statu" label="状态" width="100">
                <template  slot-scope="scope">
                    <el-tag size="mini" v-if="scope.row.statu==1" type="success">上架</el-tag>
                    <el-tag size="mini" v-else-if="scope.row.statu==0" type="danger">下架</el-tag>
                </template>
            </el-table-column>
          
            <el-table-column prop="created" label="创建时间">
            </el-table-column>
            <el-table-column prop="updated" label="修改时间">
            </el-table-column>
            

            <el-table-column label="操作" width="180"> 
            <template slot-scope="scope">     
                <el-button type="text" @click="editPoster(scope.row.id)">编辑</el-button>
                <!-- 分割线 -->
                <el-divider direction="vertical"></el-divider>
                <template>
                    <el-popconfirm title="确定要删除该轮播图的数据吗？" @confirm="deletePoster(scope.row.id)">
                        <el-button type="text" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
                
            </template>
        </el-table-column>
        </el-table>
        <!-- 页码
            @size-change事件 修改了一页显示的记录数 触发(重新加载数据)
            @current-change事件：当前页码被点击触发事件(点击分页 重新加载数据)
            :current-page 当前显示的是第几页
            :page-size  默认一页显示的记录数
            :total总共记录数

         -->
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="current"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="size"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
        <!-- 新建或编辑轮播图对话框 表单posterForm -->
        <el-dialog title="轮播图信息" :visible.sync=" dialogFormVisible" width="600px" @closed="resetForm('posterForm')">
            <el-form  :model="posterForm" :rules="posterFormRules" ref="posterForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="标题" label-width="100px" prop="title">
                <el-input v-model="posterForm.title" auto-complete="off"></el-input> 
                <!-- auto-complete="off"清空文本框输入历史痕迹 -->
                </el-form-item>

                <el-form-item label="轮播图" label-width="100px" prop="url" >
                    <el-upload
                    class="avatar-uploader"
                    action="/api/upload"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :headers="headers">
                    <img v-if="posterForm.url" :src="posterForm.url" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>

                <el-form-item label="状态" label-width="100px" prop="statu">
                    <el-radio-group v-model="posterForm.statu">
                        <el-radio :label="1">上架</el-radio>
                        <el-radio :label="0">下架</el-radio>
                    </el-radio-group>
                </el-form-item>
               
              
                <el-form-item>
                <el-button type="primary" @click="submitForm('posterForm')">保存</el-button>
                <el-button @click="resetForm('posterForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        
    </div>
</template>

<script>
export default {
    data(){
        return{
            //树形控件 读取数据设置(指定Tree控件如何从permTreeData中取数据 )
            defaultProps:{
                label:'name',//从返回的菜单列表数据中 取对应属性值 取name菜单名称
                children:'children',//从返回的菜单列表数据中 取对应属性值 取children就是子菜单项
            },
            //批量删除按钮是否可用 true不可用 false可用
            deleteBtnStu:true,
            //新建或编辑轮播图的对话框是否显示：false不显示，true显示
            dialogFormVisible:false,
            //分配权限的对话框是否显示 false不显示 true显示
            permDialogFormVisible:false,
            //需要搜索的数据对象
            searchForm:{
                
            },
            //权限分配的表单 数据对象
            permForm:{},
            //新建轮播图或编辑轮播图对话框表单对象
            posterForm:{},
            //新建或编辑轮播图的验证
            posterFormRules:{
                title:[
                {required: true, message:'请填写标题', trigger: 'blur'}
                ],
                url:[
                    {required: true, message:'请填写轮播图', trigger: 'blur'}
                ],
                statu:[
                    {required: true, message:'请选择状态', trigger: 'blur'}
                ],
                
            },
            //树形控件的权限基础数据(菜单列表)
            permTreeData:[],

            //表格显示的轮播图数组
            tableData:[],
            current:1,//分页：当前页码 默认显示第一页
            size:10,//一页显示多少条数据，默认显示10条
            total:0,//分页:所有的轮播图记录数
            //批量删除变量，存储需要删除的行
            selection:[],
            headers: {
                token: localStorage.getItem('token'),
            },
          
           
        }
    },
    created(){
        this.getPosterList()
    },
    methods:{
        selectionChange(val){
            //val参数就是选中的行对象
            this.selection=val
            console.log('批量删除行',this.selection)
            //如果批量删除的数组中有值，批量删除按钮设置为可用
            this.deleteBtnStu=this.selection.length==0//数组长度为0，表示没有选中复选按钮，所以为true,按钮不可用，否则相反

        },
        //加载页面轮播图列表数据
        //页面加载时，调用该方法显示所有轮播图信息，搜索进行模糊查询，设置模糊查询的条件，重新加载页面的轮播图信息
        getPosterList(){
            //请求服务器端：/system/poster/list 获得所有的轮播图信息
            //params:地址/system/poster/list?name=xxxx
            this.$axios.get('/system/poster/list',{
                params:{
                    title:this.searchForm.title,//搜索栏里的搜索名称
                    //当前的页码
                    current:this.current,
                    //一页显示多少条
                    size:this.size,
                }
            }).then((response)=>{
                this.tableData=response.data.data.records//获得轮播图数据赋值给this.tableData
                //todo赋值分页信息(页码需要这些数据)
                this.total=response.data.data.total//赋值总记录数
                this.size=response.data.data.size//赋值一页显示多少行
                this.current=response.data.data.current//赋值当前页码
            })
            //获得权限Tree组件的基础数据(就是树控件显示所有的权限) 权限数据(该用户具备哪几个权限)
            //请求服务器端接口:/system/menu/list 菜单的列表数据就是权限数据
            this.$axios.get('/system/menu/list').then((response)=>{
                this.permTreeData=response.data.data
            })
        },
        //编辑轮播图 参数id就是轮播图的编号
        editPoster(id){
             //请求服务端，通过id查询该轮播图信息的方法：/system/poster/info//9
            this.$axios.get('/system/poster/info/'+id).then((response)=>{
            this.posterForm=response.data.data//获得轮播图信息
            //步骤2打开对话框显示
            this.dialogFormVisible=true//设置为true,对话框显示
            
        })
        },
        //删除与批量删除 参数id就是轮播图的编号
        deletePoster(id){
            //如果有id 就表示一个一个删除，如果id没有值，代表就是批量删除
            var posterIds=[]//存储要批量删除的轮播图的编号
            id?posterIds.push(id):this.selection.forEach((val)=>{
                posterIds .push(val.id)
            })
            this.$axios.post('/system/poster/delete',posterIds).then((response)=>{
                this.$message({
                showClose: true,
                message: '轮播图信息删除成功',
                type: 'success',
                onClose:()=>{
                    //重新加载 轮播图的列表信息 getPosterList()
                    this.getPosterList()
                    
                }
            });
            })

        },
       
        //分页组件 更改页面显示多少条记录触发方法
        handleSizeChange(val){
            //val参数就是修改size之后的值
            this.size=val
            this.getPosterList()//修改size之后，重新加载页面的数据
        },
        //分页组件：点击页码进行翻页的事件方法
        handleCurrentChange(val){
            //val参数 就是点击页面页码
            this.current=val
            this.getPosterList()//修改了页码之后 重新加载页面的数据
        },   
        //重置表单
        resetForm(formName){
            this.$refs[formName].resetFields()//重置
            
            this.permForm={}//清空 权限设置 对话框的表单对象
            this.posterForm={}//新建或编辑轮播图对话框的表单对象
            //this.dialogFormVisible=false
            //this.$refs.permTree.setCheckedKeys([])
            this.permDialogFormVisible=false
           
        },     
       
        //提交对话框 新建和编辑轮播图的表单 参数formName 需要操作表单的名字 ref='名字'
        submitForm(formName){
            this.$refs[formName].validate((valid)=>{
          //valid参数为true,表示表单中的验证规则通过
          if(valid){
            //添加轮播图和编辑轮播图 使用的是同一个对话框，所以提交服务端地址，需要判断是添加还是修改
            //新增一条轮播图信息 服务器端：/system/poster/save
            //编辑完毕更新菜单数据:/system/poster/update
            this.$axios
            .post('/system/poster/'+(this.posterForm.id?'update':'save'),
            (this.posterForm))
            .then((response)=>{
                this.$message({
                showClose: true,
                message: '保存轮播图成功',
                type: 'success',
                onClose:()=>{
                    //添加轮播图之后，新增了一个轮播图，所以重新请求加载轮播图数据
                    this.getPosterList()
                 
                },
            })
            //添加操作成功之后，对话框需要隐藏
            this. dialogFormVisible=false
            this.resetForm(formName)
            })
          }else{
            return false
          }
        })

        },
        handleAvatarSuccess(response, file) {
            this.posterForm.url=response.data;
      },
        
    }
}
</script>


<style scoped>
 .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>