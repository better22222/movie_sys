<template>
  <div>
    <!-- 工具栏 -->
    <el-form :inline="true" :model="searchForm">
      <el-form-item>
        <el-input
          v-model="searchForm.name"
          placeholder="请输入搜索的内容"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getOrderList()">搜索</el-button>
       
        <el-popconfirm
          title="确定要删除这些订单的数据吗？"
          @confirm="deleteOrder()"
        >
          <el-button type="danger" :disabled="deleteBtnStu" slot="reference"
            >批量删除</el-button
          >
        </el-popconfirm>
      </el-form-item>
    </el-form>
    <!-- 订单表格数据 -->
    <el-table
      :cell-style="{ padding: '2px' }"
      :data="tableData"
      default-expand-all
      border
      stripe
      style="width: 100%; margin-bottom: 20px"
      @selection-change="selectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="订单编号" width="50" />
      <el-table-column prop="filmName" label="电影名称" width="100"/>
      <el-table-column prop="userName" label="用户名称" width="100">
      </el-table-column>
      <el-table-column prop="uid" label="用户编号" width="100">
      </el-table-column>
      <el-table-column prop="phone" label="电话号" width="100">
      </el-table-column>
      <el-table-column prop="aid" label="排片编号" width="80">
      </el-table-column>
      <el-table-column prop="seats" label="座位" width="60"> </el-table-column>
      <el-table-column prop="price" label="金额" width="60"> </el-table-column>

      <el-table-column prop="statu" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag size="small" type="info" v-if="scope.row.statu == 0">等待支付</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.statu == 1">支付成功</el-tag>
          <el-tag size="small" type="danger" v-else-if="scope.row.statu == 2">支付失败</el-tag>
          <el-tag size="small" type="info" v-else-if="scope.row.statu == 3">已被撤销</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="created" label="创建时间"> </el-table-column>
      <el-table-column prop="updated" label="修改时间"> </el-table-column>

      <el-table-column prop="payAt" label="支付时间"> </el-table-column>

      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <template>
            <el-popconfirm
              title="确定要删除该订单的数据吗？"
              @confirm="deleteOrder(scope.row.id)"
            >
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
      :total="total"
    >
    </el-pagination>
 
  
  </div>
</template>

<script>
export default {
  data() {
    return {
      //树形控件 读取数据设置(指定Tree控件如何从permTreeData中取数据 )
      defaultProps: {
        label: "name", //从返回的菜单列表数据中 取对应属性值 取name菜单名称
        children: "children", //从返回的菜单列表数据中 取对应属性值 取children就是子菜单项
      },
      //批量删除按钮是否可用 true不可用 false可用
      deleteBtnStu: true,
      //新建或编辑订单的对话框是否显示：false不显示，true显示
      dialogFormVisible: false,
      //分配权限的对话框是否显示 false不显示 true显示
      permDialogFormVisible: false,
      //需要搜索的数据对象
      searchForm: {},
      //权限分配的表单 数据对象
      permForm: {},
      //新建订单或编辑订单对话框表单对象
      orderForm: {},
      //新建或编辑订单的验证
      orderFormRules: {
        id: [{ required: true, message: "请输入订单编号", trigger: "blur" }],
        phone: [{ required: true, message: "请填写电话号", trigger: "blur" }],
        aid: [{ required: true, message: "请选择排片编号", trigger: "blur" }],
        seats: [{ required: true, message: "请填写座位", trigger: "blur" }],
        price: [{ required: true, message: "请输入订单金额", trigger: "blur" }],
        statu: [{ required: true, message: "请选择状态", trigger: "blur" }],
      },
      //树形控件的权限基础数据(菜单列表)
      permTreeData: [],

      //表格显示的订单数组
      tableData: [],
      current: 1, //分页：当前页码 默认显示第一页
      size: 10, //一页显示多少条数据，默认显示10条
      total: 0, //分页:所有的订单记录数
      //批量删除变量，存储需要删除的行
      selection: [],
    };
  },
  created() {
    this.getOrderList();
  },
  methods: {
    selectionChange(val) {
      //val参数就是选中的行对象
      this.selection = val;
      console.log("批量删除行", this.selection);
      //如果批量删除的数组中有值，批量删除按钮设置为可用
      this.deleteBtnStu = this.selection.length == 0; //数组长度为0，表示没有选中复选按钮，所以为true,按钮不可用，否则相反
    },
    //加载页面订单列表数据
    //页面加载时，调用该方法显示所有订单信息，搜索进行模糊查询，设置模糊查询的条件，重新加载页面的订单信息
    getOrderList() {
      //请求服务器端：/system/orders/list 获得所有的订单信息
      //params:地址/system/orders/list?name=xxxx
      this.$axios
        .get("/system/orders/list", {
          params: {
            name: this.searchForm.name, //搜索栏里的搜索名称
            //当前的页码
            current: this.current,
            //一页显示多少条
            size: this.size,
          },
        })
        .then((response) => {
          this.tableData = response.data.data.records; //获得订单数据赋值给this.tableData
          console.log(this.tableData);
          //todo赋值分页信息(页码需要这些数据)
          this.total = response.data.data.total; //赋值总记录数
          this.size = response.data.data.size; //赋值一页显示多少行
          this.current = response.data.data.current; //赋值当前页码
        });
      
    },
   
    //删除与批量删除 参数id就是订单的编号
    deleteOrder(id) {
      //如果有id 就表示一个一个删除，如果id没有值，代表就是批量删除
      var orderIds = []; //存储要批量删除的订单的编号
      id
        ? orderIds.push(id)
        : this.selection.forEach((val) => {
            orderIds.push(val.id);
          });
      this.$axios.post("/system/orders/delete/", orderIds).then((response) => {
        this.$message({
          showClose: true,
          message: "订单信息删除成功",
          type: "success",
          onClose: () => {
            //重新加载 订单的列表信息 getOrderList()
            this.getOrderList();
          },
        });
      });
    },

    //分页组件 更改页面显示多少条记录触发方法
    handleSizeChange(val) {
      //val参数就是修改size之后的值
      this.size = val;
      this.getOrderList(); //修改size之后，重新加载页面的数据
    },
    //分页组件：点击页码进行翻页的事件方法
    handleCurrentChange(val) {
      //val参数 就是点击页面页码
      this.current = val;
      this.getOrderList(); //修改了页码之后 重新加载页面的数据
    },
    //重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields(); //重置
      this.permForm = {}; //清空 权限设置 对话框的表单对象
      this.orderForm = {}; //新建或编辑订单对话框的表单对象
      //this.dialogFormVisible=false
      //this.$refs.permTree.setCheckedKeys([])
      this.permDialogFormVisible = false;
    },

  
  },
};
</script>


<style scoped>
</style>