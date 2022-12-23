<template>
    <div>
        <!--工具栏-->
        <el-form :iniline="true" :model="searchForm">
            <el-form-item style="width: 600px;">
                <span>
                    <el-input v-model="searchForm.name" placeholder="请提示搜索内容" style="width: 30%;"></el-input>
                    <span> 添加时间 </span>
                    <el-date-picker v-model="searchForm.time" type="daterange" align="right" unlink-panels
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"
                        :picker-options="pickerOptions" style="width: 40%;">
                    </el-date-picker>
                </span>

            </el-form-item>
            <el-form-item>
                <el-button @click="getCartList()">搜索</el-button>
                <!--TODO:新建需要权限验证   -->
                <el-button type="primary" @click="dialogFormVisble = true">新建</el-button>

                <el-popconfirm title="确定删除该这些购物车数据？" @confirm="deleteCart()">
                    <el-button type="danger" :disabled="deleteBtnStu" slot="reference"
                        style="margin-left:10px">批量删除</el-button>
                </el-popconfirm>
            </el-form-item>
        </el-form>

        <!--电影表格数据-->
        <el-table :cell-style:="{ padding: '20px' }" :data="tableData" border stripe h
            style="width: 100%; margin-bottom:20px" default-expand-all @selection-change="selectionChange">
            <el-table-column type="selection" wudth="55"></el-table-column>

            <el-table-column prop="id" label="购物车id" width="150">
            </el-table-column>

            <el-table-column prop="uid" label="用户id" width="80">
            </el-table-column>

            <el-table-column prop="phone" label="电话" width="150">
            </el-table-column>

            <el-table-column prop="aid" label="排片id" width="80">
            </el-table-column>

            <el-table-column prop="price" label="价格" width="60">
            </el-table-column>

            <el-table-column prop="seats" label="座位" width="100">
            </el-table-column>
            <el-table-column prop="created" label="创建时间" width="180">
            </el-table-column>

            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="text" @click="editCart(scope.row.id)">编辑</el-button>
                    <el-divider direction="vertical"></el-divider>
                    <template>
                        <el-popconfirm title="确定删除该电影数据？" @confirm="deleteCart(scope.row.id)">
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
            <el-form :model="cartForm" ref="cartForm" :rules="cartFormRules" label-width="100px"
                class="demo-ruleForm">

                <el-form-item label="用户id" label-width="100px" prop="uid">
                    <el-input v-model="cartForm.uid" auto-complete="off" label-width="90px"></el-input>
                </el-form-item>

                <el-form-item label="电话号" label-width="100px" prop="phone">
                    <el-input v-model="cartForm.phone" auto-complete="off" label-width="90px"></el-input>
                </el-form-item>

                <el-form-item label="排片id" label-width="100px" prop="aid">
                    <el-input v-model="cartForm.aid" auto-complete="off" label-width="90px"></el-input>
                </el-form-item>

                <el-form-item label="座位" label-width="100px" prop="seats">
                    <el-input v-model="cartForm.seats" auto-complete="off" label-width="90px"></el-input>
                </el-form-item>

                <el-form-item label="价格" prop="price" >
                    <el-input v-model="cartForm.price" auto-complete="off" label-width="90px"></el-input>
                </el-form-item>

    
                <el-form-item label="创建时间" label-width="100px" prop="created">
                    <div class="block">
                        <el-date-picker v-model="cartForm.created" type="datetime" placeholder="选择日期时间">
                        </el-date-picker>
                    </div>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('cartForm')">提交表单</el-button>
                    <el-button @click="resetForm('cartForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>
      
<script type="text/javascript">
export default {
    data() {
        return {

            cartFormRules: {
                uid: [{ required: true, message: '请输入用户id', trigger: 'blur' }],
                aid: [{ required: true, message: '请输入排片id', trigger: 'blur' }],
                price: [{ required: true, message: '请输入金额', trigger: 'blur' }],
                seats: [{ required: true, message: '请输入座位号', trigger: 'blur' }],
                statu: [{ required: true, message: '请选择状态', trigger: 'blur' }],
                created: [{ required: true, message: '请输入创建时间', trigger: 'blur' }],
            },
            //批量删除按钮是否可用
            deleteBtnStu: true,
            //分配角色对话框
            roleDialogForm: false,
            dialogFormVisble: false,
            searchForm: {},
            tableData: [],
            //角色分配的表单对象
            cartForm: {},
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
        this.getCartList()
    },
    methods: {
        //重置表单
        resetForm(fromName) {
            this.$refs[fromName].resetFields()
            this.cartForm = {}
            this.dialogFormVisble = false
            this.roleDialogForm = false
        },
        //提交对话框，新建的表单
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                //表单中的rules验证通过
                if (valid) {
                    this.$axios.post('/system/cart/' + (this.cartForm.id ? 'update' : 'save'), this.$qs.stringify(this.cartForm))
                        .then((response) => {
                            this.$message({
                                showClose: true,
                                message: '编辑排片成功',
                                type: 'success',
                                onClose: () => {
                                    //添加菜单后，重新请求加载菜单数据
                                    this.getCartList()
                                }
                            })
                        })
                    this.resetForm(formName)
                }
            })
        },
        //页面加载时调用该方法，查询所有角色，  搜索时进行模糊查询
        getCartList() {
            this.$axios.get('/system/cart/list', {
                params: {
                    name: this.searchForm.name,
                    time: this.searchForm.time,
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
        editCart(id) {
            //请求服务器端，通过id查询数据
            this.$axios.get('/system/cart/info/' + id).then(response => {
                this.cartForm = response.data.data
                this.dialogFormVisble = true
            })

        },
        //删除与批量删除
        deleteCart(id) {
            //如果有id，删除单个， 没有id，批量删除
            var roleIds = []
            id ? roleIds.push(id) : this.selection.forEach(val => {
                roleIds.push(val.id)
            })

            this.$axios.post('/system/cart/delete', roleIds).then(response => {
                this.$message({
                    showClose: true,
                    message: '删除成功',
                    type: 'success',
                    //删除菜单关闭提示框后重新加载数据
                    onClose: () => {
                        this.getCartList()
                    },
                })
            })
        },

        //更改页码数据数方法
        handleSizeChange(size) {
            this.size = val
            this.getCartList()
        },
        //页码翻页事件方法
        handleCurrentChange(val) {
            this.current = val
            this.getCartList()
        },

        //选择表格复选框触发的事件
        selectionChange(val) {
            //val选中的行对象
            this.selection = val
            console.log(this.selection)
            this.deleteBtnStu = this.selection.length == 0
        },

    }
}
</script>
  
<style scoped>
.el-progress {
    width: 80%;
}
</style>