<template>
  <div>
    <!--工具栏-->
    <el-form :iniline="true" :model="searchForm">
      <el-form-item style="width: 600px;">
                <span>
                    <el-input v-model="searchForm.name" placeholder="请提示搜索内容" style="width: 30%;"></el-input>
                    <span>    添加时间    </span>
                    <el-date-picker v-model="searchForm.time" type="daterange" align="right" unlink-panels range-separator="至"
                        start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions"
                        style="width: 40%;">
                    </el-date-picker>
                </span>

            </el-form-item>
      <el-form-item>
        <el-button @click="getRoleList()">搜索</el-button>
        <!--TODO:新建需要权限验证   -->
        <el-button type="primary" @click="dialogFormVisble = true">新建</el-button>

        <el-popconfirm title="确定删除该这些顾客数据？" @confirm="deleteUser()">
          <el-button type="danger" :disabled="deleteBtnStu" slot="reference" style="margin-left:10px">批量删除</el-button>
        </el-popconfirm>

      </el-form-item>
    </el-form>

    <!--角色表格数据-->
    <el-table :cell-style:="{ padding: '20px' }" :data="tableData" border stripe style="width: 100%; margin-bottom:20px"
      default-expand-all @selection-change="selectionChange">
      <el-table-column type="selection" wudth="55"></el-table-column>

      <el-table-column label="头像" width="70">
        <template slot-scope="scope">
          <el-avatar class="el-avatar" :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>

      <el-table-column prop="username" label="账号名" width="120">
      </el-table-column>

      <el-table-column prop="nickname" label="昵称" width="70">
      </el-table-column>

      <el-table-column prop="email" label="邮箱" width="140">
      </el-table-column>

      <el-table-column label="性别" width="50">
        <!-- slot-scope="scope" 代表循环时的每一行数据 -->
        <template slot-scope="scope">
          <el-tag size="mini" type="danger" v-if="scope.row.gender == 0">男</el-tag>
          <el-tag size="mini" type="success" v-else-if="scope.row.gender == 1">女 </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="状态" width="55">
        <!-- slot-scope="scope" 代表循环时的每一行数据 -->
        <template slot-scope="scope">
          <el-tag size="mini" type="danger" v-if="scope.row.statu == 0">禁用</el-tag>
          <el-tag size="mini" type="success" v-else-if="scope.row.statu == 1">正常 </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="birthday" label="生日" width="110">
      </el-table-column>

      <el-table-column prop="created" label="创建时间" width="180">
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">


          <el-button type="text" @click="editUser(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>

          <el-button type="text" @click="resetPass(scope.row.id, scope.row.username)">重置密码</el-button>
          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="确定删除该角色数据？" @confirm="deleteUser(scope.row.id)">
              <el-button type="text" slot="reference">删除</el-button>
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



    <!--新增菜单对话框-->
    <el-dialog title="顾客信息" :visible.sync="dialogFormVisble" width="600px">
      <el-form :model="userForm" ref="userForm" :rules="userFormRules" label-width="100px" class="demo-ruleForm">


        <el-form-item label="头像" label-width="100px">
          <el-input v-model="userForm.avatar" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>

        <el-form-item label="账号名" label-width="100px" prop="username">
          <el-input v-model="userForm.username" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>


        <el-form-item label="昵称" label-width="100px" prop="username">
          <el-input v-model="userForm.nickname" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>

        <el-form-item label="邮箱" label-width="100px">
          <el-input v-model="userForm.email" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>

        <el-form-item label="状态" prop="statu">
          <el-radio-group v-model="userForm.statu" label-width="100px">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="userForm.gender" label-width="100px">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>


        <el-form-item label="生日" label-width="100px">
          <div class="block">
            <el-date-picker v-model="userForm.birthday" type="date" placeholder="选择日期">
            </el-date-picker>
          </div>
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
      upload_btn: false,
      limitCount: 1,
      userFormRules: {
        username: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
        code: [{ required: true, message: '请输入唯一编码', trigger: 'blur' }],
        statu: [{ required: true, message: '请选择状态', trigger: 'blur' }],
        gender: [{ required: true, message: '请选择性别', trigger: 'blur' }],
      },
      //批量删除按钮是否可用
      deleteBtnStu: true,
      //分配角色对话框
      roleDialogForm: false,
      dialogFormVisble: false,
      searchForm: {},
      tableData: [],
      //角色分配的表单对象
      roleForm: {},

      userForm: {},

      //分页
      //当前页码
      current: 1,
      //一页显示多少条数据
      page: 10,
      //所有角色记录数
      total: 0,
      //批量删除变量,需要删除的行
      selection: []
    }
  },
  created() {
    this.getUserList()
  },
  methods: {
    handleSuccess(response, file, fileList) {
      this.upload_btn = fileList.length >= this.limitCount

    },
    //重置表单
    resetForm(fromName) {
      this.$refs[fromName].resetFields()
      this.roleForm = {}
      this.userForm = {}
      this.dialogFormVisble = false
      this.roleDialogForm = false
    },
    //提交对话框，新建的表单
    submitForm(formName) {

      this.$refs[formName].validate((valid) => {
        //表单中的rules验证通过
        if (valid) {
          this.$axios.post('/system/customer/' + (this.userForm.id ? 'update' : 'save'), this.$qs.stringify(this.userForm))
            .then((response) => {
              this.$message({
                showClose: true,
                message: '编辑角色成功',
                type: 'success',
                onClose: () => {
                  //添加菜单后，重新请求加载菜单数据
                  this.getUserList()
                }
              })
            })
          this.resetForm(formName)
        }
      })
    },
    //页面加载时调用该方法，查询所有角色，  搜索时进行模糊查询
    getUserList() {
      this.$axios.get('/system/customer/list', {
        params: {
          name: this.searchForm.username,
          //TODO:当前页码
          current: this.current,
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
    },
    //编辑角色
    editUser(id) {
      //请求服务器端，通过id查询数据
      this.$axios.get('/system/customer/info/' + id).then(response => {
        this.userForm = response.data.data
        this.dialogFormVisble = true
      })

    },
    //删除与批量删除
    deleteUser(id) {
      //如果有id，删除单个， 没有id，批量删除
      var roleIds = []
      id ? roleIds.push(id) : this.selection.forEach(val => {
        roleIds.push(val.id)
      })

      this.$axios.post('/system/customer/delete', roleIds).then(response => {
        this.$message({
          showClose: true,
          message: '删除成功',
          type: 'success',
          //删除菜单关闭提示框后重新加载数据
          onClose: () => {
            this.getUserList()
          },
        })
      })
    },

    //更改页码数据数方法
    handleSizeChange(size) {
      this.size = val
      this.getUserList()
    },
    //页码翻页事件方法
    handleCurrentChange(val) {
      this.current = val
      this.getUserList()
    },

    //选择表格复选框触发的事件
    selectionChange(val) {
      //val选中的行对象
      this.selection = val
      console.log(this.selection)
      this.deleteBtnStu = this.selection.length == 0
    },
    //重置密码
    resetPass(id, username) {
      this.$confirm('重置顾客【' + username + '】的密码，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post('/system/customer/repass', id).then(response => {
          if (response.data.code == 200 && response.data.code != undefined) {
            console.log(1)
            this.$message({
              showClose: true,
              type: 'success',
              message: '重置成功!'
            });
          }
        })
      })
    }
  }
}
</script>

<style >

</style>