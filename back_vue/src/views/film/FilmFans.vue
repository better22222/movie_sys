<template>
  <div>
    <!-- 工具栏 -->
    <el-form :model="searchForm">
      <el-form-item style="width: 250px;">
        <el-input v-model="searchForm.username" placeholder="请输入搜索的内容"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getFansList()">搜索</el-button>
        <!-- TODO:新建需要进行权限验证 -->
        <el-popconfirm title="确定要删除这些粉丝数据吗？" @confirm="deleteUser()">
          <!-- TODO:新建需要进行权限验证 -->
          <el-button type="danger" :disabled="deleteBtnStu" slot="reference">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <!-- 用户表格 -->
    <el-table :cell-style="{ padding: '2px' }" :data="tableData" border default-expand-all stripe
      style="width: 100%; margin-bottom:20px; " @selection-change="selectionChange">
      <el-table-column type="selection" width="50">
      </el-table-column>
      <el-table-column label="头像" width="55">
        <template slot-scope="scope">
          <el-avatar size="small" :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="用户id" width="150">
      </el-table-column>

      <el-table-column prop="username" label="用户名" width="90">
      </el-table-column>


      <el-table-column prop="sex" label="性别" width="50">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.sex == 1" type="success">男</el-tag>
          <el-tag size="mini" v-else-if="scope.row.sex == 0" type="danger">女</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="email" label="邮箱">
      </el-table-column>
      <el-table-column prop="statu" label="状态" width="60">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.statu == 1" type="success">正常</el-tag>
          <el-tag size="mini" v-else-if="scope.row.statu == 0" type="danger">禁用</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="280">
        <template slot-scope="scope">
          <!-- TODO:新建需要进行权限验证 -->
          <el-button type="text" @click="resetPass(scope.row.id, scope.row.username)">重置密码</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" @click="editFans(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确定要删除该用户的数据吗？" @confirm="deleteFans(scope.row.id)">
              <el-button type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>

        </template>
      </el-table-column>
    </el-table>
    <!-- 页码 -->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="current"
      :page-sizes="[5, 10, 15, 20]" :page-size="size" :total="total" layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 新增或编辑用户对话框 -->
    <el-dialog title="提示" :visible.sync="dialogFormVisible" width="600px" @closed="resetForm('editForm')">
      <el-form :model="editForm" ref="editForm" :rules="editFormRules">

        <el-form-item label="上传图片">
          <!-- 
            action="服务器接口路径"
            :headers="headers"  headers就是变量中存储 请求需要的token
           -->
          <el-upload class="avatar-uploader" action="/api/upload" :show-file-list="false"
            :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" :headers="headers">
            <img v-if="editForm.avatar" :src="editForm.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          {{ imageUrl }}
        </el-form-item>

        <el-form-item label="用户名" prop="username" label-width="100px">
          <el-input v-model="editForm.username" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="性别" prop="sex" label-width="100px">
          <el-radio-group v-model="editForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="邮箱" prop="email" label-width="100px">
          <el-input v-model="editForm.email" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="状态" prop="statu" label-width="100px">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('editForm')">取 消</el-button>
        <el-button type="primary" @click="submitEditForm('editForm')">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>
  
<script type="text/javascript">
export default {
  data() {
    return {
      imageUrl: '',
      form: {},
      url: '',
      headers: {
        token: localStorage.getItem('token'),
      },

      //搜索栏对象
      searchForm: {},
      //新建与编辑用户对话框是否显示
      dialogFormVisible: false,
      //用户分配角色对话框
      roleDialogFormVisble: false,
      //表示使用复选框 选中的多行用户对象
      multiplieSelection: [],
      //给用户分配的角色表单对象
      fansForm: {},
      //编辑用户的表单对象
      editForm: {},
      //批量删除的按钮是否可用
      deleteBtnStu: true,

      //用户表格的数据:
      tableData: [],
      current: 1, //分页：当前页码，默认显示第一页
      size: 10, //分页：一页显示多少条数据，默认显示10条
      total: 0, //分页：所有的角色记录数
      editFormRules: {
        username: [
          { required: true, message: '请输入用户名称', trigger: 'blur' },
        ],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
        statu: [{ required: true, message: '请选择状态', trigger: 'blur' }],
        sex: [{ required: true, message: '请选择性别', trigger: 'blur' }],
      },
    }
  },
  created() {
    this.getFansList()

    //加载分配角色Tree数据 (角色基础信息)
    this.$axios.get('/system/fans/list').then((response) => {
      this.roleTreeData = response.data.data.records
    })
  },
  methods: {
    handleAvatarSuccess(res, file) {
      console.log(res.data)
      this.editForm.avatar = res.data //存储到Java项目中图片的路径
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },

    //获得所有用户的信息
    getFansList() {
      //获得用户的信息
      this.$axios
        .get('/system/fans/list', {
          params: {
            name: this.searchForm.username, //搜索栏里的  搜索名称
            //当前的页码
            current: this.current,
            //一页显示多少条
            size: this.size,
          },
        })
        .then((response) => {
          this.tableData = response.data.data.records
          this.size = response.data.data.size
          this.current = response.data.data.current
          this.total = response.data.data.total
        })
    },
    //删除一个或批量删除用户的方法  参数：用户编号
    deleteFans(id) {
      var ids = [] //存储需要删除的所有用户id
      id
        ? ids.push(id)
        : this.multiplieSelection.forEach((row) => {
          ids.push(row.id)
        })
      console.log('删除的id是', ids)
      this.$axios.post('/system/fans/delete', ids).then((response) => {
        this.$message({
          showClose: true,
          message: '删除粉丝成功',
          type: 'success',
          onClose: () => {
            //数据提交成功，重新加载页面角色信息
            this.getFansList()
          },
        })
      })
    },
    //复选框选中的事件
    selectionChange(rows) {
      this.multiplieSelection = rows //得到复选框 选中 多个用户对象
      this.deleteBtnStu = this.multiplieSelection.length == 0 //如果选择的数量不是0，批量删除按钮可用
    },

    //编辑用户的方法
    editFans(id) {
      //编辑用户，首先请求服务器API ,获得当前编辑 用户信息的数据，显示在对话框表单中
      this.$axios.get('/system/fans/info/' + id).then((response) => {
        this.editForm = response.data.data
        this.dialogFormVisible = true //显示编辑用户对话框
      })
    },
    //重置 用户编辑对话框 表单
    resetForm(n) {
      console.log(this.$refs[n])
      this.$refs[n].resetFields()
      this.editForm = {}
      this.dialogFormVisible = false //编辑 角色对话框 为false不显示
      //给用户分配角色对话框
      this.roleDialogFormVisible = false
      this.imageUrl = ''
    },
    //重置密码的方法
    resetPass(id, username) {
      this.$confirm(
        '重置用户【' + username + '】的密码，是否继续执行该操作',
        '重置密码提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        //请求服务器端，进行重置密码的请求操作
        this.$axios.post('/system/fans/repass/' + id).then((response) => {
          if (response.data.code == 200 && response.data.code != undefined) {
            this.$message({
              showClose: true,
              type: 'success',
              message: '重置密码成功!',
            })
          }
        })
      })
    },
    //提交新建或编辑用户
    submitEditForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .post(
              '/system/fans/update',
              this.editForm
            )
            .then((response) => {
              //提交成功，重置表单
              this.$message({
                showClose: true,
                message: '操作成功',
                type: 'success',
                onClose: () => {
                  //数据提交成功，重新加载页面用户信息
                  this.getFansList()
                  this.dialogFormVisible = false //隐藏对话框
                  this.resetForm(formName)
                },
              })
            })
        } else {
          return false
        }
      })
    },
    //修改一页显示的记录数
    handleSizeChange(val) {
      this.size = val
      this.getFansList()
    },
    //点击页码的方法
    handleCurrentChange(val) {
      this.current = val
      this.getFansList()
    },
  },
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
  border-color: #409eff;
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
  
  