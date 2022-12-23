<template>
  <div>
    <!--工具栏-->
    <el-form :model="searchForm">
      <el-form-item style="width: 250px;">
        <el-input v-model="searchForm.name" placeholder="请提示搜索内容"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getCategoryList()">搜索</el-button>
        <!--TODO:新建需要权限验证   -->
        <el-button type="primary" @click="dialogFormVisble = true">新建</el-button>

        <el-popconfirm title="确定删除该这些分类数据？" @confirm="deleteCategory()">
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

      <el-table-column label="头像" width="120">

        <template slot-scope="scope">
          <el-image style="width: 50px; height: 50px" :src="scope.row.url"></el-image>
        </template>
      </el-table-column>

      <el-table-column prop="created" label="创建时间" width="300">
      </el-table-column>

      <el-table-column prop="updated" label="更新时间" width="300">
      </el-table-column>


      <el-table-column prop="statu" label="状态" width="200">
        <!-- slot-scope="scope" 代表循环时的每一行数据 -->
        <template slot-scope="scope">
          <el-tag size="mini" type="danger" v-if="scope.row.statu == 0">禁用</el-tag>
          <el-tag size="mini" type="success" v-else-if="scope.row.statu == 1">正常 </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">


        <template slot-scope="scope">


          <el-button type="text" @click="editCategory(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="确定删除该分类数据？" @confirm="deleteCategory(scope.row.id)">
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
    <el-dialog title="分类信息" :visible.sync="dialogFormVisble" width="600px">
      <el-form :model="categoryForm" ref="categoryForm" :rules="categoryFormRules" label-width="100px"
        class="demo-ruleForm">

        <el-form-item label="分类名称" label-width="100px" prop="name">
          <el-input v-model="categoryForm.name" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>

        <el-form-item label="上传分类图">
          <!-- 
          action="服务器接口路径"
          :headers="headers"  headers就是变量中存储 请求需要的token
         -->
          <el-upload class="avatar-uploader" action="/api/upload" :show-file-list="false"
            :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" :headers="headers">
            <img v-if="categoryForm.url" :src="categoryForm.url" class="url" style="width:200px;">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          {{ imageUrl }}
        </el-form-item>

        <el-form-item label="状态" prop="statu">
          <el-radio-group v-model="categoryForm.statu" label-width="100px">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('categoryForm')">提交表单</el-button>
          <el-button @click="resetForm('categoryForm')">重置</el-button>
        </el-form-item>
      </el-form>
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
      categoryFormRules: {
        name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
        statu: [{ required: true, message: '请选择状态', trigger: 'blur' }],
      },
      //批量删除按钮是否可用
      deleteBtnStu: true,
      dialogFormVisble: false,
      //需要搜索的数据对象

      //新增，修改表单角色信息
      categoryForm: {},
      searchForm: {},
      tableData: [],

      //新建角色或编辑角色对话框表单
      roleForm: {},
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
    this.getCategoryList()
  },
  methods: {

    handleAvatarSuccess(res, file) {
      console.log(res.data)
      this.categoryForm.url = res.data //存储到Java项目中图片的路径
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
    //提交对话框，新建的表单
    submitForm(formName) {

      this.$refs[formName].validate((valid) => {
        //表单中的rules验证通过
        console.log(this.categoryForm.url)
        if (valid) {
          this.$axios.post('/system/category/' + (this.categoryForm.id ? 'update' : 'save'), this.categoryForm)
            .then((response) => {
              this.$message({
                showClose: true,
                message: '编辑类别成功',
                type: 'success',
                onClose: () => {
                  //添加菜单后，重新请求加载菜单数据
                  this.getCategoryList()
                }
              })
            })
          this.resetForm(formName)
        }
      })
    },
    //页面加载时调用该方法，查询所有角色，  搜索时进行模糊查询
    getCategoryList() {
      this.$axios.get('/system/category/list', {
        params: {
          name: this.searchForm.name,
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
    editCategory(id) {
      //请求服务器端，通过id查询数据
      this.$axios.get('/system/category/info/' + id).then(response => {
        this.categoryForm = response.data.data
        this.dialogFormVisble = true
      })
    },
    //删除与批量删除
    deleteCategory(id) {
      //如果有id，删除单个， 没有id，批量删除
      var roleIds = []
      id ? roleIds.push(id) : this.selection.forEach(val => {
        roleIds.push(val.id)
      })

      this.$axios.post('/system/category/delete', roleIds).then(response => {
        this.$message({
          showClose: true,
          message: '删除成功',
          type: 'success',
          //删除菜单关闭提示框后重新加载数据
          onClose: () => {
            this.getCategoryList()
          },
        })
      })
    },

    //更改页码数据数方法
    handleSizeChange(size) {
      this.size = val
      this.getCategoryList()
    },
    //页码翻页事件方法
    handleCurrentChange(val) {
      this.current = val
      this.getCategoryList()
    },
    //重置表单
    resetForm(fromName) {
      this.$refs[fromName].resetFields()
      this.categoryForm = {}
      this.dialogFormVisble = false
      this.permDialogForm = false
    },
    //选择表格复选框触发的事件
    selectionChange(val) {
      //val选中的行对象
      this.selection = val
      console.log(this.selection)
      this.deleteBtnStu = this.selection.length == 0
    }
  }
}
</script>
      