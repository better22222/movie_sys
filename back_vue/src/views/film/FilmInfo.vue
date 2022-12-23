<template>
    <div>
        <!--工具栏-->
        <el-form :iniline="true" :model="searchForm">
            <el-form-item style="width: 600px;">
                <span>
                    <el-input v-model="searchForm.name" placeholder="请提示搜索内容" style="width: 30%;"></el-input>

                </span>

            </el-form-item>
            <el-form-item>
                <el-button @click="getFilmList()">搜索</el-button>
                <!--TODO:新建需要权限验证   -->
                <el-button type="primary" @click="dialogFormVisble = true">新建</el-button>

                <el-popconfirm title="确定删除该这些电影数据？" @confirm="deleteFilm()">
                    <el-button type="danger" :disabled="deleteBtnStu" slot="reference"
                        style="margin-left:10px">批量删除</el-button>
                </el-popconfirm>
            </el-form-item>
        </el-form>

        <!--电影表格数据-->
        <el-table :cell-style:="{ padding: '20px' }" :data="tableData" border stripe h
            style="width: 100%; margin-bottom:20px" default-expand-all @selection-change="selectionChange">
            <el-table-column type="selection" wudth="55"></el-table-column>

            <el-table-column label="电影封面" width="100">
                <template slot-scope="scope">
                    <el-image style="width: 80px; height: 100px" :src="scope.row.cover">
                    </el-image>
                </template>
            </el-table-column>

            <el-table-column prop="name" label="电影名" width="120">
            </el-table-column>


            <el-table-column label="状态" width="70">
                <!-- slot-scope="scope" 代表循环时的每一行数据 -->
                <template slot-scope="scope">
                    <el-tag size="mini" type="danger" v-if="scope.row.statu == 0">下架</el-tag>
                    <el-tag size="mini" type="success" v-else-if="scope.row.statu == 1">上架 </el-tag>
                </template>
            </el-table-column>

            <el-table-column prop="region" label="地区" width="100">
            </el-table-column>

            <el-table-column prop="grade" label="热度" width="130">
                <template slot-scope="scope">
                    <span style="display: flex;">
                        <el-progress :text-inside="true" :stroke-width="15" :percentage="(scope.row.grade * 10)"
                            status="exception"></el-progress>
                        <i class="el-icon-s-flag" style="color: red"></i>
                    </span>
                </template>
            </el-table-column>

            <el-table-column prop="intro" label="介绍" width="180" :show-overflow-tooltip="true">
            </el-table-column>

            <el-table-column prop="duration" label="时长(分钟)" width="60">
            </el-table-column>

            <el-table-column prop="releaseTime" label="上映日期" width="180">
            </el-table-column>



            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="text" @click="editFilm(scope.row.id)">编辑</el-button>
                    <el-divider direction="vertical"></el-divider>
                    <template>
                        <el-popconfirm title="确定删除该电影数据？" @confirm="deleteFilm(scope.row.id)">
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
        <!-- 页码 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="current"
            :page-sizes="[5, 10, 15, 20]" :page-size="size" :total="total"
            layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>



        <!--新增菜单对话框-->
        <el-dialog title="电影信息" :visible.sync="dialogFormVisble" width="600px">
            <el-form :model="filmForm" ref="filmForm" :rules="userFormRules" label-width="100px" class="demo-ruleForm">


                <el-form-item label="电影封面" label-width="100px" prop="url">
                    <el-upload width="100px" class="avatar-uploader" action="/api/upload" :show-file-list="false"
                        :on-success="handleAvatarSuccess" >
                        <img width="100px" v-if="filmForm.cover" :src="filmForm.cover" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>

                <el-form-item label="电影名" label-width="100px" prop="name">
                    <el-input v-model="filmForm.name" auto-complete="off" label-width="90px"></el-input>
                </el-form-item>



                <el-form-item label="状态" prop="statu">
                    <el-radio-group v-model="filmForm.statu" label-width="100px">
                        <el-radio :label="0">下架</el-radio>
                        <el-radio :label="1">上架</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="介绍">
                    <el-input type="textarea" v-model="filmForm.intro"></el-input>
                </el-form-item>

                <el-form-item label="地区">
                    <el-input v-model="filmForm.region"></el-input>
                </el-form-item>

                <el-form-item label="时长" label-width="100px" prop="duration">
                    <el-input v-model="filmForm.duration" auto-complete="off" label-width="90px"></el-input>
                </el-form-item>

                <el-form-item label="上映时间" label-width="100px" prop="release_time">
                    <div class="block">

                        <el-date-picker v-model="filmForm.releaseTime" type="datetime" placeholder="选择日期">
                        </el-date-picker>
                    </div>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('filmForm')">提交表单</el-button>
                    <el-button @click="resetForm('filmForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>
      
<script type="text/javascript">
export default {
    data() {
        return {

            userFormRules: {
                name: [{ required: true, message: '请输入电影名称', trigger: 'blur' }],
                type: [{ required: true, message: '请输入电影类型', trigger: 'blur' }],
                duration: [{ required: true, message: '请输入电影时长', trigger: 'blur' }],
                statu: [{ required: true, message: '请选择状态', trigger: 'blur' }],
            },
            //批量删除按钮是否可用
            deleteBtnStu: true,
            //分配角色对话框
            roleDialogForm: false,
            dialogFormVisble: false,
            searchForm: {},
            tableData: [],
            //角色分配的表单对象
            filmForm: {},
            //分页
            //当前页码
            current: 1, //分页：当前页码，默认显示第一页
            size: 10, //分页：一页显示多少条数据，默认显示10条
            total: 0, //分页：所有的角色记录数
            //批量删除变量,需要删除的行
            selection: []
        }
    },
    created() {
        this.getFilmList()
    },
    methods: {
        handleAvatarSuccess(response, file) {
            this.filmForm.cover = response.data;
        },
        //重置表单
        resetForm(fromName) {
            this.$refs[fromName].resetFields()
            this.filmForm = {}
            this.dialogFormVisble = false
            this.roleDialogForm = false
        },
        //提交对话框，新建的表单
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                //表单中的rules验证通过
                if (valid) {
                    this.$axios.post('/system/film/' + (this.filmForm.id ? 'update' : 'save'), this.filmForm)
                        .then((response) => {
                            this.$message({
                                showClose: true,
                                message: '编辑电影成功',
                                type: 'success',
                                onClose: () => {
                                    //添加菜单后，重新请求加载菜单数据
                                    this.getFilmList()
                                }
                            })
                        })
                    this.resetForm(formName)
                }
            })
        },
        //页面加载时调用该方法，查询所有角色，  搜索时进行模糊查询
        getFilmList() {
            console.log(this.searchForm.username)
            this.$axios.get('/system/film/list', {
                params: {
                    name: this.searchForm.name, //搜索栏里的  搜索名称
                    //当前的页码
                    current: this.current,
                    //一页显示多少条
                    size: this.size,
                },
            }).then(response => {
                this.tableData = response.data.data.records
                this.total = response.data.data.total;
                this.size = response.data.data.size;
                this.current = response.data.data.current;

            })
        },
        //编辑角色
        editFilm(id) {
            //请求服务器端，通过id查询数据
            this.$axios.get('/system/film/info/' + id).then(response => {
                this.filmForm = response.data.data
                this.dialogFormVisble = true
            })

        },
        //删除与批量删除
        deleteFilm(id) {
            //如果有id，删除单个， 没有id，批量删除
            var roleIds = []
            id ? roleIds.push(id) : this.selection.forEach(val => {
                roleIds.push(val.id)
            })

            this.$axios.post('/system/film/delete', roleIds).then(response => {
                this.$message({
                    showClose: true,
                    message: '删除成功',
                    type: 'success',
                    //删除菜单关闭提示框后重新加载数据
                    onClose: () => {
                        this.getFilmList()
                    },
                })
            })
        },

        //更改页码数据数方法
        handleSizeChange(size) {
            this.size = val
            this.getFilmList()
        },
        //页码翻页事件方法
        handleCurrentChange(val) {
            this.current = val
            this.getFilmList()
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