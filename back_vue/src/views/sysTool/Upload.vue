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
                <el-button @click="getUploadList()">搜索</el-button>
                <!--TODO:新建需要权限验证   -->
                <el-popconfirm title="确定删除该这些文件数据？" @confirm="deleteUpload()">
                    <el-button type="danger" :disabled="deleteBtnStu" slot="reference"
                        style="margin-left:10px">批量删除</el-button>
                </el-popconfirm>
            </el-form-item>
            <el-form-item>
                <el-upload class="upload-demo" ref="upload" action="/system/upload/" :on-preview="handlePreview"
                    :on-remove="handleRemove" :file-list="fileList" :auto-upload="false">
                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                    <el-button style="margin-left: 10px;" size="small" type="success"
                        @click="submitUpload">上传到服务器</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                </el-upload>
            </el-form-item>
        </el-form>

        <!--电影表格数据-->
        <el-table :cell-style:="{ padding: '20px' }" :data="tableData" border stripe h
            style="width: 100%; margin-bottom:20px" default-expand-all @selection-change="selectionChange">
            <el-table-column type="selection" wudth="55"></el-table-column>

            <el-table-column prop="bytes" label="文件名" width="150">
            </el-table-column>

            <el-table-column prop="md5" label="文件标识" width="280">
            </el-table-column>

            <el-table-column prop="created" label="创建时间" width="280">
            </el-table-column>

            <el-table-column label="操作">
                <template slot-scope="scope">
                    <template>
                        <el-popconfirm title="确定删除该电影数据？" @confirm="deleteUpload(scope.row.id)">
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
    </div>

</template>
      
<script type="text/javascript">
export default {
    data() {
        return {

            //批量删除按钮是否可用
            deleteBtnStu: true,
            searchForm: {},
            tableData: [],
            //分页
            //当前页码
            current: 1,
            //一页显示多少条数据
            page: 10,
            //所有角色记录数
            total: 0,
            //批量删除变量,需要删除的行
            selection: [],
            fileList: []
        }
    },
    created() {
        this.getUploadList()
    },
    methods: {
        submitUpload() {
            this.$refs.upload.submit();
            this.$message({
                    showClose: true,
                    message: '上传成功',
                    type: 'success',
                    //删除菜单关闭提示框后重新加载数据
                    onClose: () => {
                        this.getUploadList()
                    },
                })
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },

        //重置表单
        resetForm(fromName) {
            this.$refs[fromName].resetFields()
            this.uploadForm = {}
        },
        //页面加载时调用该方法，查询所有角色，  搜索时进行模糊查询
        getUploadList() {
            this.$axios.get('/system/upload/list', {
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
        //删除与批量删除
        deleteUpload(id) {
            //如果有id，删除单个， 没有id，批量删除
            var roleIds = []
            id ? roleIds.push(id) : this.selection.forEach(val => {
                roleIds.push(val.id)
            })

            this.$axios.post('/system/upload/delete', roleIds).then(response => {
                this.$message({
                    showClose: true,
                    message: '删除成功',
                    type: 'success',
                    //删除菜单关闭提示框后重新加载数据
                    onClose: () => {
                        this.getUploadList()
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