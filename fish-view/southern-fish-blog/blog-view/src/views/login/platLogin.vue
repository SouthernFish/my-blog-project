<template>
  <el-container class="page-container">
    <!-- 顶部区域 -->
    <el-header height="100px"> 
      <el-row type="flex"  justify="space-between" class="top-region">
        <el-col :span="12" class="top-left">
          <el-image :src="topLeftUrl" fit="fill"></el-image>>
          <div class="top-title-region">
            <span>我的博客管理</span> 
          </div>
        </el-col>
        <el-col :span="12" class="top-right">
          <el-image :src="topRightUrl" fit="fill"></el-image>
        </el-col>
      </el-row>
    </el-header>
    <!-- 中部 -->
    <el-main >
      <div class="middle">
        <!-- 管理员登录 -->
        <div class="form-region">
          <el-form :model="form" status-icon :rules="rules" ref="form" label-width="100px" class="demo-form">
            <el-form-item label="账户：" prop="operatorAccount">
              <el-input v-model="form.operatorAccount" v-focus @input="watchInput"></el-input>
            </el-form-item>
            <el-form-item label="密码：" prop="operatorPwd">
              <el-input type="password" v-model="form.operatorPwd" 
                @keyup.enter.native="handleLogin"
                @input="watchInput"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :loading="loading" @click="handleLogin" :disabled="btnDisabled">登录</el-button>
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
import bottomRegion from '../components/bottomRegion.vue';
import {seniorOperatorId} from "@/config"
export default {
  components: { bottomRegion },
  name: "PlatLogin",
  directives: {
    focus: {
      inserted(el) {
        el.querySelector("input").focus();
      },
    },
  },
  data() {
    return {
      topLeftUrl: "/static/logo/all-top-left.png",
      topRightUrl: "/static/logo/all-top-right.png",

      form: {
        seniorOperatorId: seniorOperatorId,
        operatorAccount: "",
        operatorPwd: "",
        operatorType: 1,
      },
      rules: {
        operatorAccount: [
          {required: true, trigger: "blur", message: '请输入账户', },
        ],
        operatorPwd: [
          {required: true, trigger: "blur", message: '请输入密码',},
        ],
      },

      loading: false,
      passwordType: "password",
      redirect: undefined,
      btnDisabled: true,
    };
  },
  watch: {
    $route: {
      handler(route) {
        this.redirect = (route.query && route.query.redirect) || "/";
      },
      immediate: true,
    },
  },
  created() {
    this.loading= false;
    document.body.style.overflow = "hidden";
  },

  beforeDestroy() {
    document.body.style.overflow = "auto";
  },
  mounted() {},
  methods: {
    watchInput() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.btnDisabled = false;
        }
      });
    },

    // 登录
    async handleLogin() {
      await this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$store.dispatch("user/login", this.form).then(() => {
              const routerPath = this.redirect === "/404" || this.redirect === "/401" ? "/panel" : this.redirect;
              this.$router.push(routerPath).catch(() => {});
              this.loading = false;
              localStorage.setItem('userName', this.form.operatorAccount)
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
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

  .top-title-region {
    position: absolute;
    align-content: center;
    z-index: 1;
    bottom: 30px;
    left: 43%;
    font-size: 2.0em;
    color: rgb(104, 101, 101);
  }

  .top-region .el-image {
    height: 100%;
    height: inherit;
  }

  .middle {
    margin-top: 50px;
    margin-left: 25%;
    width: 50%;
  }

  .middle .form-region {
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
    margin-top: 50px;
  }

  .el-form .el-button {
    width: 200px;
    background-color: #91e5fa;
    border-color: #91e5fa;
  }
</style>
