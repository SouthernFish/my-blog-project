<template>
  <el-container class="page-container">
    <!-- 顶部区域 -->
    <el-header height="100px"> 
      <el-row type="flex"  justify="space-between" class="top-region">
        <el-col :span="12" class="top-left">
          <el-image :src="topLeftUrl" fit="fill"></el-image>
          <div class="top-title-region">
            <span>SouthernFish's Blog</span> 
          </div>
        </el-col>
        <el-col :span="12" class="top-right">
          <el-image :src="topRightUrl" fit="fill"></el-image>
          <div class="top-button-region">
            <el-button type="primary" icon="el-icon-s-home" @click="goHome"></el-button>
          </div>
        </el-col>
      </el-row>
    </el-header>
    <!-- 中部 -->
    <el-main >
      <div class="middle">
        <!-- 注册 -->
        <div class="form-region">
          <div class="title-div" style="display: flex; width: 80%; margin-left: 10%; margin-bottom: 20px;">
            <div style="display: line-block; font-size: 1.5em; font-weight: bold; line-height: 50px; width: 50%; color: rgb(104, 101, 101);">
              注册
            </div>
            <div style="display: line-block; font-size: 0.9em; line-height: 50px; width: 50%;">
              已注册<el-button type="text" @click="login">登录</el-button>
            </div>
          </div>
          <el-form :model="formData" status-icon :rules="rules" ref="formData" label-width="100px" class="demo-form">
            <el-form-item label="邮箱：" prop="operatorAccount">
              <el-input v-model="formData.operatorAccount"></el-input>
            </el-form-item>
            <el-form-item label="密码：" prop="operatorPwd">
              <el-input type="password" v-model="formData.operatorPwd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码：" prop="operatorConfirmPwd">
              <el-input type="password" v-model="formData.operatorConfirmPwd" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleReister">注册</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-main>
    <el-footer height="60px">
      <!-- 底部区域 -->
      <bottom-region />
    </el-footer>
  </el-container>
</template>

<script>
import { isPassword, isEmail } from "@/utils/validate";
import bottomRegion from '../components/bottomRegion.vue';
import {register} from '@/api/user.js'
import {seniorOperatorId} from "@/config"
export default {
  components: { bottomRegion },
  name: "Register",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请填写密码"));
      } else if (!isPassword(value)) {
        callback(new Error("密码不能少于6位"));
      } else {
        callback();
      }
    };
    const validateConfirmPassword = (rule, value, callback) => {
      if (value != this.formData.operatorPwd) {
        callback(new Error("密码不一致"));
      } else {
        callback();
      }
    };

    return {
      topLeftUrl: "/static/logo/all-top-left.png",
      topRightUrl: "/static/logo/all-top-right.png",
      getPhoneInterval: null,
      formData: {
        seniorOperatorId: seniorOperatorId,
        operatorAccount: "",
        operatorPwd: "",
        operatorConfirmPwd: ""
      },
      rules: {
        operatorAccount: [
          { required: true, message: '请填写邮箱', trigger: 'blur' },
          { type: 'email', message: '输入应为邮箱格式', trigger: 'blur' },
        ],
        operatorPwd: [
          {required: true, trigger: "blur", validator: validatePassword,},
        ],
        operatorConfirmPwd: [
          {required: true, trigger: "blur", validator: validateConfirmPassword,}
        ],
      },
    };
  },
  watch: {},
  created() {
    document.body.style.overflow = 'hidden'
  },
  beforeDestroy() {
    document.body.style.overflow = 'auto'
    // this.getPhoneInterval = null
    // clearInterval(this.getPhoneInterval)
  },
  mounted() {},
  methods: {
    // 注册
    handleReister() {
      this.$refs.formData.validate(async (valid) => {
        if (valid) {
          const { code, msg , data} = await register(this.formData)
          this.$baseMessage(msg, 'success')
          if (code === 200) {
            this.$confirm('已注册成功，去登录?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'success'
            }).then(() => {
              this.$router.push("/login");
            })
          }
        }
      })
    },

    // 回到首页
    goHome() {
      this.$router.push("/");
    },

    // 去登录
    login() {
      this.$router.push("/login");
    }
  },
};
</script>

<style scoped>
  .top-title-region {
    position: absolute;
    align-content: center;
    z-index: 1;
    bottom: 30px;
    left: 40%;
    font-size: 2.0em;
    color: rgb(104, 101, 101);
  }
  
  .top-region {
    position: fixed;
    top: 0px;
    z-index: 1;
    opacity: 0.8;
    height: 100px;
    overflow: hidden;
  }

  .top-region .top-right {
    position: relative;
  }

  .top-button-region {
    position: absolute;
    align-content: center;
  }

  .top-region .top-right .top-button-region {
    bottom: 10px;
    right: 20%;
  }

  .top-region .top-button-region .el-button {
    border: #91e5fa;
    background-color: #91e5fa;
   /*  color: #979899; */
  }

  .top-region .el-button {
    font-size: 20px;
    margin-left: 10px;
  }

  .top-region .el-image {
    height: 100%;
    height: inherit;
  }

  .middle {
    margin-left: 25%;
    width: 50%;
    text-align: center;
  }

  .middle .form-region {
    margin-top: 50px;
    padding-top: 10px;
    background-color: white;
    opacity: 0.8;
    text-align: center;
    width: 100%;
    padding-bottom: 20px;
    border-radius: 5px;
  }

  .el-form {
    width: 50%;
    margin-left: 22%;
  }

  .el-form .el-button {
    width: 200px;
    background-color: #91e5fa;
    border-color: #91e5fa;
  }
</style>
