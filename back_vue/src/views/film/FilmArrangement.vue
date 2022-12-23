<template>
  <div>
    <!-- 工具栏 -->
    <el-form :inline="true" :model="searchForm">
      <el-form-item>
        <el-input v-model="searchForm.username" placeholder="请输入搜索的内容"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getUserList">搜索</el-button>
        <!-- TODO新建需要进行权限验证 -->
        <el-button type="primary" @click="dialogFormVisible = true">新增</el-button>

        <el-popconfirm title="确定要删除这些角色的数据吗？" @confirm="deleteUser()">
          <!-- TODO新建需要进行权限验证 -->
          <el-button type="danger" :disabled="deleteBtnStu" slot="reference">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>
    <!-- 角色表格数据 -->
    <el-table :cell-style="{ padding: '2px' }" :data="tableData" border default-expand-all stripe
      style="width: 100%; margin-bottom: 20px" @selection-change="selectionChange">
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="name" label="电影名称" width="190">
      </el-table-column>
      <el-table-column prop="seatNumber" label="开放座位"> </el-table-column>
      <el-table-column prop="boxOffice" label="总票房"> </el-table-column>
      <el-table-column prop="price" label="票价" width="100"> </el-table-column>
      <el-table-column prop="type" label="电影类型" width="100">
      </el-table-column>
      <el-table-column prop="date" label="上映日期" width="130">
      </el-table-column>
      <el-table-column prop="startTime" label="电影开始时间" width="200">
      </el-table-column>
      <el-table-column prop="endTime" label="电影结束时间" width="200">
      </el-table-column>
      <el-table-column prop="statu" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.statu == 1" type="success">待开始</el-tag>
          <el-tag size="mini" v-else-if="scope.row.statu == 0" type="danger">已结束</el-tag>
          <el-tag size="mini" v-else-if="scope.row.statu == 2" type="warning">放映中</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="created" label="创建时间" width="200">
      </el-table-column>
      <el-table-column prop="updated" label="跟新时间" width="200">
      </el-table-column>

      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="text" @click="editUser(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="确定要删除该用户的数据吗？" @confirm="deleteUser(scope.row.id)">
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
    <!-- 新建或编辑角色信息 -->
    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="500px" :before-close="handleClose"
      class="demo-ruleForm">
      <el-form :model="roleForm" :rules="roleFormRules" ref="roleForm" label-width="100px">
        <!-- <el-form-item label="电影名称" label-width="100px" prop="name">
                <el-input v-model="roleForm.name" auto-complete="off"></el-input>
            </el-form-item> -->
        <el-form-item label="电影名称" prop="name">
          <el-select v-model="roleForm.name" placeholder="请选择上级菜单">
            <template>
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.label">
              </el-option>
            </template>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="类型" label-width="100px" prop="type">
                <template>
                    <el-select v-model="FilmForm.categoryId" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </template>
            </el-form-item> -->
        <!-- <el-form-item label="电影id" label-width="100px" prop="fid">
                <el-input v-model="roleForm.fid" auto-complete="off"></el-input>
            </el-form-item> -->
        <el-form-item label="开放座位" label-width="100px" prop="seat_number">
          <el-input v-model="roleForm.seatNumber" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="总票房" label-width="100px" prop="box_office">
          <el-input v-model="roleForm.boxOffice" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="电影类型" label-width="100px" prop="type">
          <el-input v-model="roleForm.type" auto-complete="off"></el-input>
          <!-- <template>
                    <el-select v-model="roleForm.categoryId" placeholder="请选择">
                        <el-option v-for="item in options1" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </template> -->
        </el-form-item>
        <el-form-item label="票价" label-width="100px" prop="price">
          <el-input v-model="roleForm.price" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="上映时间" label-width="100px" prop="date">
          <el-date-picker v-model="roleForm.date" type="date" placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开始时间" label-width="100px" prop="start_time">
          <el-time-picker v-model="roleForm.startTime" placeholder="选择时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px" prop="end_time">
          <el-time-picker v-model="roleForm.endTime" placeholder="选择时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="状态" label-width="100px" prop="statu">
          <el-radio-group v-model="roleForm.statu">
            <el-radio :label="0">待开始</el-radio>
            <el-radio :label="1">已结束</el-radio>
            <el-radio :label="2">放映中</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('roleForm')">保存菜单</el-button>
          <el-button @click="resetForm('roleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // options1: [],
      options: [],
      //树形控件 读取数据设置(指定Tree控件如何从permTreeData数组中取数据)
      defaultProps: {
        label: "name", //从返回的菜单列表数据中，取对应属性值，取name菜单名称
        children: "children",
      },
      //批量删除按钮是否可用  true 不可用 false可用
      deleteBtnStu: true,
      // 新建或编辑角色的对话框是否显示：fa1se不显示，true显示
      dialogFormVisible: false,
      //需要搜索的数据对象
      searchForm: {},
      //权限分配的表单 数据对象
      permForm: {},
      //新建角色或编辑角色对话框表单对象
      roleForm: {},
      //树形控件的 权限基础数据
      roleTreeData: [],
      //表格显示的角色数组
      submitRoleForm(formName) { },
      tableData: [],
      current: 1, //分页：当前页码，默认显示第一页
      size: 10, //分页：一页显示多少条数据，默认显示10条
      total: 0, //分页：所有的角色记录数
      //批量删除变量,存储需要删除的行
      selection: [],
      roleFormRules: {
        avatar: [
          { required: true, message: "请输入角色名称", trigger: "blur" },
        ],
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        roles: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
        email: [{ required: true, message: "请选择邮箱", trigger: "blur" }],
        tel: [{ required: true, message: "请选择电话", trigger: "blur" }],
        statu: [{ required: true, message: "请选择角色状态", trigger: "blur" }],
        created: [
          { required: true, message: "请选择创建日期", trigger: "blur" },
        ],
        categoryId: [
          { required: true, message: "请输入类型", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getUserList();
  },
  methods: {
    //提交对话框新建和编辑菜单的表单    参数fromName 需要操作表单的名字
    submitForm(fromName) {
      this.$refs[fromName].validate((valid) => {
        //valid参数为true,表示表单中的验证规则通过
        if (valid) {
          // 添加菜单和编辑菜单使用的是同一·个对话框，所以提交服务端地址，需要判断是添加还是修改
          // 新增一条菜单：服务器端：/system/arrangement/save
          //编辑完毕跟新菜单数据,服务器端:/system/arrangement/update
          this.$axios
            .post(
              "/system/arrangement/" + (this.roleForm.id ? "update" : "save"),
              this.roleForm
            )
            .then((response) => {
              this.$message({
                showClose: true,
                message: "新增排片成功",
                type: "success",
                onClose: () => {
                  // 添加菜单之后，新增了一个菜单，所以重新请求加载菜单数据
                  this.getUserList();
                },
              });
              //添加操作之后,对话框需要隐藏
              this.dialogFormVisible = false;
              this.roleForm = {};
            });
        } else {
          return false;
        }
      });
    },
    //选择表格复选框触发的方法
    selectionChange(val) {
      //val参数就是行参数
      this.selection = val;
      //如果批量别除的数组中有值，批量别除按钮设置为可用
      this.deleteBtnStu = this.selection.length == 0; //数组长度为，表示没有选中复选数组，所以为tue,按钮不可用，否则相反。
    },
    //删除与批量删除
    deleteUser(id) {
      //如果有id,就表示一个·个别除，如果id没有值，代表就是批量别除
      var userIds = []; //存储要批量刷除的角色的编号
      id
        ? userIds.push(id)
        : this.selection.forEach((val) => {
          userIds.push(val.id);
        });

      this.$axios
        .post("/system/arrangement/delete", userIds)
        .then((response) => {
          this.$message({
            showClose: true,
            message: "排片信息删除成功!",
            type: "success",
            onClose: () => {
              //删除菜单之后,关闭成功消息提示框,重新加载菜单的数据
              this.getUserList();
            },
          });
        });
    },
    //重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields(); //重置
      this.permForm = {}; //清空权限设置对话框的表单对象
      this.roleForm = {}; //新建或编辑角色对话框的表单对象
      // this.dialogFormVisble = false
      // this.permDialogFormVisble = false
    },
    //关闭对话框的方法
    handleClose() {
      // this.resetForm('roleForm')
      this.dialogFormVisible = false;
      this.roleDialogFormVisible = false;
      this.roleForm = {};
    },
    //获得所有用户信息
    getUserList() {
      //拿类型数据
      // this.$axios.get('/system/arrangement/type').then(response => {
      //     this.options1 = response.data.data
      // })
      //拿电影名数据
      this.$axios.get("/system/arrangement/name").then((response) => {
        this.options = response.data.data;
        console.log(this.options);
      });
      this.$axios
        .get("/system/arrangement/list", {
          params: {
            name: this.searchForm.username, //搜索栏里的搜索名称
            //当前的页号
            current: this.current,
            //一共显示多少条
            size: this.size,
          },
        })
        .then((response) => {
          this.tableData = response.data.data.records;
          this.total = response.data.data.total;
          this.size = response.data.data.size;
          this.current = response.data.data.current;

          console.log(this.tableData);
        });
    },
    editUser(id) {
      this.$axios.get("/system/arrangement/info/" + id).then((response) => {
        this.roleForm = response.data.data;
        this.dialogFormVisible = true;
      });
    },
    //修改一页显示的记录数
    handleSizeChange(val) {
      this.size = val;
      this.getUserList();
    },
    //点击页码的方法
    handleCurrentChange(val) {
      this.current = val;
      this.getUserList();
    },
  },
};
</script>

<style>

</style>