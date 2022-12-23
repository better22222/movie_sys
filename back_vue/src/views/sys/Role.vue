<template>
  <div>
    <!--工具栏-->
    <el-form  :model="searchForm">
      <el-form-item style="width: 250px;">
        <el-input v-model="searchForm.name" placeholder="请提示搜索内容"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getRoleList()">搜索</el-button>
        <!--TODO:新建需要权限验证   -->
        <el-button type="primary" v-if="hasAuth('sys:role:save')" @click="dialogFormVisble = true">新建</el-button>

        <el-popconfirm title="确定删除该这些角色数据？" @confirm="deleteRole()">
          <el-button type="danger" :disabled="deleteBtnStu" slot="reference" style="margin-left:10px">批量删除</el-button>
        </el-popconfirm>

      </el-form-item>
    </el-form>
    <!--角色表格数据-->
    <el-table :cell-style:="{ padding: '20px' }" :data="tableData" border stripe style="width: 100%; margin-bottom:20px"
      default-expand-all @selection-change="selectionChange">
      <el-table-column type="selection" wudth="55"></el-table-column>
      <el-table-column prop="name" label="名称" width="120">
      </el-table-column>
      <el-table-column prop="code" label="编码" width="80">
      </el-table-column>
      <el-table-column prop="remark" label="描述">
      </el-table-column>
      <el-table-column prop="statu" label="状态" width="80">
        <!-- slot-scope="scope" 代表循环时的每一行数据 -->
        <template slot-scope="scope">
          <el-tag size="mini" type="danger" v-if="scope.row.statu == 0">禁用</el-tag>
          <el-tag size="mini" type="success" v-else-if="scope.row.statu == 1">正常 </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220">


        <template slot-scope="scope">

          <el-button type="text" v-if="hasAuth('sys:role:perm')" @click="permRole(scope.row.id)">分配权限</el-button>
          <el-divider direction="vertical"></el-divider>

          <el-button type="text" v-if="hasAuth('sys:role:update')" @click="editRole(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>

          <template v-if="hasAuth('sys:role:delete')">
            <el-popconfirm title="确定删除该角色数据？" @confirm="deleteRole(scope.row.id)">
              <el-button type="text"  slot="reference">删除</el-button>
            </el-popconfirm>
          </template>

        </template>
      </el-table-column>

    </el-table>

    <!--页码
      size-change: 修改了一页的显示数，重新加载数据
      current-change 当前页面被点击出发事件，重新加载数据
      current-page 默认一页显示记录数
      total 总记录数
    -->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="current"
      :page-sizes="[5, 10, 15, 20, 25]" :page-size="page" layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>

    <!--分配权限对话框-->
    <el-dialog title="分配权限" :visible.sync="permDialogForm" width="600px" @closed="resetForm('permForm')">
      <el-form ref="permForm" :model="permForm" label-width="100px">
        <el-tree ref="permTree" :data="permTreeData" node-key="id" :props="defaultProps" show-checkbox 
        :default-expand-all="true" :check-strictly="true" >
        </el-tree>
      </el-form>
      <div slot="footer" class="dialog-footer">
          <el-button @click="resetForm('permForm')">取 消</el-button>
         <el-button type="primary" @click="submitPermForm('permForm')">确 定</el-button>
        </div>
    </el-dialog>

    <!--新增菜单对话框-->
    <el-dialog title="角色信息" :visible.sync="dialogFormVisble" width="600px" >
      <el-form :model="userForm"  ref="userForm" :rules="userFormRules" label-width="100px" class="demo-ruleForm" >

        <el-form-item label="角色名称" label-width="100px" prop="name">
          <el-input v-model="userForm.name" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>
        <el-form-item label="唯一编码" label-width="100px" prop="code">
          <el-input v-model="userForm.code" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>
        <el-form-item label="描述" label-width="100px">
          <el-input v-model="userForm.remark" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>

        <el-form-item label="状态"  prop="statu">
          <el-radio-group v-model="userForm.statu" label-width="100px">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('userForm')">提交表单</el-button>
          <el-button @click="resetForm('userForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>
    
<script type="text/javascript">
export default {
  data() {
    return {
      //树形控件，读取数据设置
      defaultProps:{
        label:'name',
        children:'children'
      },
      userFormRules: {
        name:[{required:true,message:'请输入角色名称',trigger:'blur'}],
        code:[{required:true,message:'请输入唯一编码',trigger:'blur'}],
        statu:[{required:true,message:'请选择状态',trigger:'blur'}],
      },
      //批量删除按钮是否可用
      deleteBtnStu: true,
      dialogFormVisble: false,
      //需要搜索的数据对象
      //分配权限对话框
      permDialogForm: false,

      //新增，修改表单角色信息
      userForm:{},
      searchForm: {},
      tableData: [],
      //权限分配的表单对象
      permForm: {},
      //数型控件，权限基础数据
      permTreeData:[],
      //新建角色或编辑角色对话框表单
      roleForm:{},
      //分页
      //当前页码
      current: 1,
      //一页显示多少条数据
      page: 10,
      //所有角色记录数
      total: 0,
      //批量删除变量,需要删除的行
      selection:[]
    }
  },
  created() {
    this.getRoleList()
  },
  methods: {
    //提交对话框，新建的表单
    submitForm(formName) {

      this.$refs[formName].validate((valid)=>{
        //表单中的rules验证通过
        if(valid){
          this.$axios.post('/system/role/'+(this.userForm.id ? 'update':'save'),this.userForm)
          .then((response)=>{
            this.$message({
              showClose:true,
              message:'编辑角色成功',
              type:'success',
              onClose:()=>{
                //添加菜单后，重新请求加载菜单数据
                this.getRoleList()
              }
            })
          })
          this.resetForm(formName)
        }
      })
    },
    //页面加载时调用该方法，查询所有角色，  搜索时进行模糊查询
    getRoleList() {
      this.$axios.get('/system/role/list', {
        params: {
          name: this.searchForm.name,
          //TODO:当前页码
          curretnPage: this.current,
          //TODO:一页显示多少条
          page: this.page
        },
      }).then(response => {
        this.tableData = response.data.data.records
        //TODO:赋值分页信息
        this.total = response.data.data.total
        this.page = response.data.data.page
        this.current = response.data.data.current
      })
      //TODO:获取权限数据
      this.$axios.get('/system/menu/list','get').then(response=>{
        this.permTreeData = response.data.data
        console.log(this.permTreeData)
      })
    },
    //编辑角色
    editRole(id) {
      //请求服务器端，通过id查询数据
      this.$axios.get('/system/role/info/'+id).then(response=>{
        this.userForm = response.data.data
        this.dialogFormVisble= true
      })
    },
    //删除与批量删除
    deleteRole(id) {
      //如果有id，删除单个， 没有id，批量删除
      var roleIds = []
      id?roleIds.push(id):this.selection.forEach(val=>{
        roleIds.push(val.id)
      })
      
      this.$axios.post('/system/role/delete',roleIds).then(response=>{
        this.$message({
          showClose: true,
          message: '删除成功',
          type: 'success',
          //删除菜单关闭提示框后重新加载数据
          onClose: () => {
            this.getRoleList()
          },
        })
      })
    },
    //分配权限
    permRole(id) {
      //步骤1 请求当前用户的权限信息
      this.$axios.get('/system/role/info/'+id).then(response=>{
        console.log('该角色权限菜单编号'+response.data.data.menuIds)
        //给勾选框赋值
        this.$refs.permTree.setCheckedKeys(response.data.data.menuIds)
        this.permForm = response.data.data
      })
      //步骤2 显示控件
      this.permDialogForm = true
    },  
    //更改页码数据数方法
    handleSizeChange(size) {
      this.size = val
      this.getRoleList()
    },
    //页码翻页事件方法
    handleCurrentChange(val) {
      this.current = val
      this.getRoleList()
    },
    //重置表单
    resetForm(fromName) {
      this.$refs[fromName].resetFields()
      this.permForm = {}
      this.roleForm= {}
      this.userForm = {}
      this.dialogFormVisble = false
      this.permDialogForm = false
    },
    //修改权限表单提交
    submitPermForm(formName){
      //获得修改后的Tree控件的node选项
      var menuIds =  this.$refs.permTree.getCheckedKeys()
      this.$axios.post('/system/role/perm/'+this.permForm.id,menuIds).then(response=>{
        this.$message({
          showClose: true,
          message: '修改权限信息成功',
          type: 'success',
          //删除菜单关闭提示框后重新加载数据
          onClose: () => {
            this.getRoleList()
          },
        })
      this.resetForm(formName)
    })
    },
    //选择表格复选框触发的事件
    selectionChange(val){
      //val选中的行对象
      this.selection = val
      console.log(this.selection)
      this.deleteBtnStu = this.selection.length == 0
    }
  }
}
</script>
    