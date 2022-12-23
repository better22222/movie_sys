<template>
    <el-row>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-avatar class="el-avatar" :src="user.avatar">
                </el-avatar>
                <h1>修改密码<el-divider direction="vertical"></el-divider>{{ user.username }}</h1>

            </div>
            <div>
                <el-form :model="userInfo" ref="userInfo" :rules="rules" label-width="200px" class="demo-ruleForm">
                    <el-form-item label="旧密码" label-width="90px" prop="oldPassword">
                        <el-input show-password v-model="userInfo.oldPassword" auto-complete="off" label-width="200px"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" label-width="90px" prop="newPassword">
                        <el-input show-password v-model="userInfo.newPassword" auto-complete="off" label-width="200px"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" label-width="90px" prop="password">
                        <el-input show-password v-model="userInfo.password" auto-complete="off" label-width="200px"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="changePass('userInfo')">修改密码</el-button>
                        <el-button @click="resetForm('userInfo')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
    </el-row>

</template>
      
<script type="text/javascript">
export default {
    data() {
        const newPassFn = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入新密码'))
            } else {
                if (this.userInfo.newPassword !== '') {
                    this.$refs.userInfo.validateField('checkPass')
                }
                callback()
            }
        }
        const confirmPassFn = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'))
            } else if (value !== this.userInfo.newPassword) {
                callback(new Error('两次输入密码不一致!'))
            } else {
                callback()
            }
        }
        return {
            userInfo: {
                id: 0,
                oldPassword: '',
                newPassword: '',
                password: '',
            },
            user: {

            },
            rules: {
                password: [
                    { required: true, validator: confirmPassFn, trigger: 'blur' }
                ],
                oldPassword: [
                    { required: true, message: '请输入旧密码', trigger: 'blur' }
                ],
                newPassword: [
                    { required: true, validator: newPassFn, trigger: 'blur' },
                ]
            }
        }
    },
    created() {
        this.seteditableTabsValue(),
            this.getUser()
    },
    methods: {
        seteditableTabsValue() {
            let obj = {
                title: '修改密码',
                name: 'ChangeP'
            }
            //添加打开的选项卡
            this.$store.commit('addActiveTable', obj)
            //将修改密码选项卡设置激活
            this.$store.commit('setActiveTable', obj.name)
        },
        getUser() {
            //获取用户信息
            this.$axios.get('/user/userInfo').then(response => {
                this.user = response.data.data
            })
        },
        changePass(userInfo) {
            //修改密码验证
            this.$refs[userInfo].validate((valid) => {
                if (valid) {
                    this.$axios.post('/system/user/changePsw/' +
                        this.userInfo.newPassword + "" + this.user.id).then(response => {
                            if (response.data.code == 200) {
                                this.$message({
                                    message: "修改成功，请重新登录！",
                                    type: "success",
                                });
                                localStorage.clear()
                                sessionStorage.clear()
                                this.$store.commit('resetState')
                                this.$router.push('/login')
                                this.$refs[userInfo].resetFields()
                            }
                        })
                } else {
                    return false
                }
            })
        }
    },
}

</script>

<style scoped>
.el-row {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
}
</style>
    