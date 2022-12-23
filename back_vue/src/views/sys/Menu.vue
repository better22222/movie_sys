<template>
  <div>
    <el-form :inline="true">
      <!--:inline="true" 横向排列-->
      <el-form-item>
        <!--TODO 添加权限编码检查，检查是否有权限添加菜单-->
        <el-button type="primary" v-if="hasAuth('sys:menu:save')" @click="dialogVisible = true">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- :cell-style="{padding:2px}" -->
    <el-table :cell-style:="{padding : '20px'}" :data="tableData" border stripe style="width: 100%; margin-bottom:20px" default-expand-all
      row-key="id" :tree-props="{ children: 'children' }">
      <el-table-column prop="name" label="名称" width="170" sortable="true">
      </el-table-column>
      <el-table-column prop="perms" label="权限编码" width="130">
      </el-table-column>
      <el-table-column prop="icon" label="图标" width="150px">
      </el-table-column>
      <!--菜单类型显示 0目录 1菜单 2按钮-->
      <el-table-column prop="type" label="菜单类型" width="70px">
        <!-- slot-scope="scope" 代表循环时的每一行数据 -->
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.type == 0">目录</el-tag>
          <el-tag size="mini" type="success" v-else-if="scope.row.type == 1">菜单</el-tag>
          <el-tag size="mini" type="info" v-else-if="scope.row.type == 2">按钮</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="path" label="菜单URL">
      </el-table-column>
      <el-table-column prop="component" label="菜单组件">
      </el-table-column>
      <el-table-column prop="orderNum" label="排序号">
      </el-table-column>

      <el-table-column prop="statu" label="状态">
        <!-- slot-scope="scope" 代表循环时的每一行数据 -->
        <template slot-scope="scope">
          <el-tag size="mini" type="danger" v-if="scope.row.statu == 0">禁用</el-tag>
          <el-tag size="mini" type="success" v-else-if="scope.row.statu == 1">正常 </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" v-if="hasAuth('sys:menu:update')" @click="editMenu(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确定删除该菜单数据？" @confirm="deleteMenu(scope.row.id)">
              <el-button type="text"  v-if="hasAuth('sys:menu:delete')" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>
    </el-table>


    <!--新增菜单对话框-->
    <el-dialog title="菜单" :visible.sync="dialogVisible" width="600px" :before-close="handleClose">
      <el-form :model="menuForm"  ref="menuForm" :rules="menuFormRules" label-width="100px" class="demo-ruleForm" >
        <el-form-item label="上级菜单" label-width="100px" prop="parentId">

          <el-select v-model="menuForm.parentId" placeholder="请选择">
            <el-option :label="'/'" :value="0"></el-option>
            <template v-for="item in tableData" > 
              <el-option  :key="item.id" :label="item.name" :value="item.id">
                <span>{{item.name}}</span>
              </el-option> 
            <!--二级菜单-->
              <el-option v-for="(child) in item.children" :key="child.id" :label="child.name" :value="child.id">
                <span>{{"--"+child.name}}</span>
              </el-option>
            </template>
          </el-select>

        </el-form-item>
        <el-form-item label="菜单名称" label-width="100px" prop="name">
          <el-input v-model="menuForm.name" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>
        <el-form-item label="权限编码" label-width="100px" prop="perms">
          <el-input v-model="menuForm.perms" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>
        <el-form-item label="图标" label-width="100px">
          <el-input v-model="menuForm.icon" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>
        <el-form-item label="菜单URL" >
          <el-input v-model="menuForm.path" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>
        <el-form-item label="菜单组件">
          <el-input v-model="menuForm.component" auto-complete="off" label-width="90px"></el-input>
        </el-form-item>
        <el-form-item label="类型" label-width="100px" prop="type">
          <el-radio-group v-model="menuForm.type">
            <el-radio :label="0">目录</el-radio>
            <el-radio :label="1">菜单</el-radio>
            <el-radio :label="2">子菜单</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="statu">
          <el-radio-group v-model="menuForm.statu" label-width="100px" >
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排序号" label-width="100px" prop="orderNum">
          <el-input-number v-model="menuForm.orderNum" :min="1" label="排序号"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('menuForm')">提交表单</el-button>
          <el-button @click="resetForm('menuForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
    
<script  type="text/javascript">


export default {
  data() {
    return {
      dialogVisible: false,
      tableData: [],
      menuForm: {},
      //新建或编辑的菜单验证
      menuFormRules: {
        parentId:[
          {required:true,message:'请选择上级菜单',trigger:'blur'}
        ],
        name:[{required:true,message:'请输入菜单名称',trigger:'blur'}],
        perms:[{required:true,message:'请输入权限编码',trigger:'blur'}],
        type:[{required:true,message:'请选择类型',trigger:'blur'}],
        statu:[{required:true,message:'请选择状态',trigger:'blur'}],
        orderNum:[{required:true,message:'请输入排序号',trigger:'blur'}]
      }
    }
  },
  created() {
    this.getMenu()
  },
  methods: {
    //获得菜单的数据
    getMenu() {
      this.$axios.get('/system/menu/list').then(response => {
        this.tableData = response.data.data
      })
    },

    //根据当前数据行的编号，删除当前一行数据方法
    deleteMenu(id) {
      //采用restFul风格 http://localhost/10001/system/menu/delete/10
      this.$axios.post('/system/menu/delete/' + id).then(response => {
        this.$message({
          showClose: true,
          message: response.data.message,
          type: 'success',
          //删除菜单关闭提示框后重新加载数据
          onClose: () => {
            this.getMenu()
          }
        })
      })
    },
    //关闭对话框方法
    handleClose() {
      this.dialogVisible = false
      //新建和编辑对话框的数据对象
    },
    //提交对话框，新建的表单
    submitForm(formName) {
      this.$refs[formName].validate((valid)=>{
        //表单中的rules验证通过
        if(valid){
          this.$axios.post('/system/menu/'+(this.menuForm.id ? 'update':'save'),this.menuForm)
          .then((response)=>{
            this.$message({
              showClose:true,
              message:'编辑菜单成功',
              type:'success',
              onClose:()=>{
                //添加菜单后，重新请求加载菜单数据
                this.getMenu()
              }
            })
          })
          this.dialogVisible = false
          this.menuForm = []
        }
      })
    },
    //编辑菜单信息
    editMenu(id){
      //请求服务器端，通过id查询数据
      this.$axios.get('/system/menu/info/'+id).then(response=>{
        this.menuForm = response.data.data
        this.dialogVisible = true
      })
      this.getMenu()
    },


    //重置新建或编辑对话框表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.menuForm= {}
    }
  }
}
</script>
    