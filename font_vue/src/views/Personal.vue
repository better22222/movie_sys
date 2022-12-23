<template>
    <div class="wrapper">
        <!-- header部分 -->
        <div class="header">
            <i class="el-icon-arrow-left" @click="back()"></i>
            <div class="information">个人信息</div>
            <i class="el-icon-more"></i>
        </div>


        <div class="personal_detail">
            <ul>
                <li>
                    头像<p><img :src="fans.avatar" /></p><i class="el-icon-arrow-right"></i>
                </li>
                <li @click="editUsername()">
                    昵称<p>{{ fans.username }}</p><i class="el-icon-arrow-right"></i>
                </li>
                <li>
                    邮箱<p>{{ fans.email }}</p><i class="el-icon-arrow-right"></i>
                </li>

                <li>
                    手机号<p>{{ fans.id }}</p><i class="el-icon-arrow-right"></i>
                </li>

                <li @click="editPassword()">
                    修改密码<p></p><i class="el-icon-arrow-right"></i>
                </li>

            </ul>
        </div>
        <div id="change_username">
            <el-dialog :visible.sync="dialogUserVisible" width="80%">
                <el-form ref="fans" :model="fans">
                    <p>修改昵称</p>
                    <el-form-item>
                        <el-input v-model="fans.username"></el-input>
                    </el-form-item>
                    <el-form-item class="update" width="20vw;">
                        <el-button @click="onSubmitName()">确认修改</el-button>
                        <el-button @click="dialogUserVisible = false">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>


        <div id="change_password">
            <el-dialog :visible.sync="dialogVisible" width="80%">
                <el-form ref="fans" :model="fans">
                    <p>修改密码</p>
                    <el-form-item>
                        <el-input show-password v-model="fans.password"></el-input>
                    </el-form-item>
                    <el-form-item class="update" width="20vw;">
                        <el-button @click="onSubmitPass()">确认修改</el-button>
                        <el-button @click="dialogVisible = false">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>


        <Footer></Footer>
    </div>
</template>
  
<script>

import Footer from '../components/Footer.vue'
export default {
    data() {
        return {
            fans: this.$getSessionStorage('fans'),
            personal: {},
            dialogVisible: false,
            dialogUserVisible: false,

        }
    },
    created() {


    },
    methods: {
        back() {
            this.$router.push('/userCenter')
        },

        editPassword() {
            this.dialogVisible = true
        },
        onSubmitPass() {
            this.dialogVisible = true
            this.$axios.post('/fans/chang', this.fans).then(response => {
                if (response.data.code == 1) {
                    this.$message({
                        message: "修改成功，请重新登录！",
                        type: "success",
                    });
                    localStorage.clear()
                    sessionStorage.clear()
                    this.$router.push('/login')
                }
            })

            this.dialogVisible = false
        },
        editUsername() {
            this.dialogUserVisible = true
        },
        onSubmitName() {
            this.dialogUserVisible = false
        }


    },
    components: {
        Footer,
    },
}
</script>
  
<style  scoped>
/****************** 总容器 ******************/
.wrapper {
    width: 100%;
    height: 100%;
    color: #4e4e4e;
    /* background-color: rgb(221,224,220); */
    background-color: #F5f5f5;
}

/****************** header ******************/
.wrapper .header {
    width: 100%;
    height: 20vw;
    display: flex;
    align-items: center;
    justify-content: space-around;
    background-color: white;

}


.wrapper .header .information {
    width: 60vw;
    text-align: center;
}

.wrapper .personal_detail {
    /*这里也设置容器宽度95%，不能用padding，因为背景色也会充满padding*/
    width: 100%;
    margin: 0 auto;
    height: 80vw;
    margin-top: 2vw;
    background-color: white;
    border: 1px solid white;
    display: flex;
    justify-content: space-around;

}

.wrapper .personal_detail ul {
    width: 100%;
    height: 50vw;
}

.wrapper .personal_detail ul li {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 90vw;
    height: 10vw;
    font-size: 4vw;
    margin-top: 5vw;
    margin-left: 3vw;
    border-bottom: 1px solid #f5f5f5;
}

.wrapper .personal_detail ul li i {
    /* float: right;

        */
    width: 2vw;
    ;
}

.wrapper .personal_detail ul li p {
    margin-left: 45vw;
    float: right;



}

.wrapper .personal_detail ul li p img {
    width: 10vw;
    height: 10vw;
    margin-left: 5vw;
}

#change_password .el-dialog--title {
    font-size: 5vw;
}

#change_password .el-form p {
    font-size: 5vw;
    text-align: center;

}

#change_password .el-form .el-input {
    width: 50vw;
    margin-top: 5vw;
    margin-left: 10vw;

}

#change_password .el-form .el-button {
    /* width:12vw; */
    height: 12vw;
    margin-left: 5vw;
    background-color: #F5f5f5;
    border: none;

}

#change_username .el-dialog--title {
    font-size: 5vw;
}

#change_username .el-form p {
    font-size: 5vw;
    text-align: center;

}

#change_username .el-form .el-input {
    width: 50vw;
    margin-top: 5vw;
    margin-left: 10vw;

}

#change_username .el-form .el-button {
    /* width:12vw; */
    height: 12vw;
    margin-left: 5vw;
    background-color: #F5f5f5;
    border: none;

}
</style>
