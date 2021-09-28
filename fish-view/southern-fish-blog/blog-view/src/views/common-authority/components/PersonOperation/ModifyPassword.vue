<template>
  <div>
    <div class="line-div-parent">
      <div class="line-div avatar text-center">
        <el-avatar shape="square" :size="50" icon="el-icon-lock"></el-avatar>
      </div>
      <div class="line-div type text-center">
        <div>登录密码</div>
        <div>已设置</div>  
      </div>
      <div class="line-div description">
        安全性高的密码，可以使账户更安全，建议您定期更换密码。<br/>
        请牢记登录密码，如果丢失密码，可以通过绑定的手机或邮箱找到。
      </div>
      <div class="line-div button-div text-center"><el-button @click="modifyPwdLaunch">修改密码</el-button></div>
    </div>
    <div class="drop-operation-div" v-show="modifyDialog">
      <el-form :model="modifyPwd" ref="modifyPwd" width="60%" label-width="100px" :rules="rules">
        <el-form-item label="原密码：" prop="oldOperatorPwd">
          <el-input type="password" v-model="modifyPwd.oldOperatorPwd" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码：" prop="newOperatorPwd">
          <el-input type="password" v-model="modifyPwd.newOperatorPwd" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码：" prop="operatorConfirmPwd">
          <el-input type="password" v-model="modifyPwd.operatorConfirmPwd" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="modifyDialog = false" style="margin-left: 10%;">取 消</el-button>
          <el-button type="primary" @click="modifyPassword">确 定</el-button>
        </el-form-item>
      </el-form> 
       
    </div>
    <el-dialog class="countdown-dialog" :visible.sync="countDownDialog" width="30%" top="10%" destroy-on-close
      :show-close="false" :modal="false" :close-on-click-modal="false" :close-on-press-escape="false">
      <div class="text-center" style="font-size:1.2em;">
        密码修改成功，系统将在&ensp;
        <span style="color: rgb(241, 57, 57); font-weight: 600;">{{countdown}}</span>
        &ensp;秒后自动退出!
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="logoutDirectly">直接退出</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {isPassword} from "@/utils/validate";
import * as api from "@/api/user.js"
import { recordRoute } from "@/config";
export default {
  name: 'ModifyPassword',
  components: {},
  data() {
    const validatePassword = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请填写新密码"));
      } else if (!isPassword(value)) {
        callback(new Error("密码不能少于6位"));
      } else {
        callback();
      }
    };
    const validateConfirmPassword = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请确认密码"));
      } else if (value != this.modifyPwd.newOperatorPwd) {
        callback(new Error("密码不一致"));
      } else {
        callback();
      }
    };
    return {
      modifyDialog: false,
      modifyPwd: {
        oldOperatorPwd: null,
        newOperatorPwd: null, 
        operatorConfirmPwd: null,
      },
      countdown: null, // 倒计时
      countDownDialog: false,
      rules: {
        oldOperatorPwd: [
          {required: true, trigger: "blur", message: "请填写原密码", },
        ],
        newOperatorPwd: [
          {required: true, trigger: "blur", validator: validatePassword,},
        ],
        operatorConfirmPwd: [
          {required: true, trigger: "blur", validator: validateConfirmPassword,}
        ],
      },
    };
  },
  methods: {
    //账户安全区域选择按钮： 密码修改提出
    modifyPwdLaunch() {
      this.modifyDialog = !this.modifyDialog
    },

    // 修改密码
    modifyPassword() {
      this.$refs.modifyPwd.validate(async (valid) => {
        if (valid) {
          api.modifyPassword({
            oldOperatorPwd: this.modifyPwd.oldOperatorPwd,
            newOperatorPwd: this.modifyPwd.newOperatorPwd, 
          }).then(res => {
            if (res.code == 200) {
              this.$message({
                type: 'success',
                message: '密码修改成功!'
              });
              this.modifyDialog = false
              // 密码修改成功 退出系统
              this.countDownDialog = true
              this.countdownFun()     
            }
          })           
        }
      })
    },

    // 直接退出
    logoutDirectly() {
      clearInterval(this.timer)
      this.timer = null
      this.logout()
      this.countDownDialog = false
    },

    // 倒计时 跳转
    countdownFun() {
      const TIME_COUNT = 5
      if (!this.timer) {
        this.countdown = TIME_COUNT
        this.timer = setInterval(() => {
          if (this.countdown > 0 && this.countdown <= TIME_COUNT) {
            this.countdown = this.countdown - 1
          } else {
            this.logoutDirectly()
          }
        }, 1000)
      }
    },

    // 退出登录
    async logout() {
      const operatorType = localStorage.getItem("_TONG_OPERATOR_TYPE_")
      await this.$store.dispatch("user/logout");
      if (recordRoute) {
        const fullPath = this.$route.fullPath;
        this.$router.push(`/login?redirect=${fullPath}`);
      } else {
        if (operatorType == 1) {
          this.$router.push("/panel");
        }
        if (operatorType == 2) {
          this.$router.push("/index");
        }
      }
    },
  },
}
</script>
<style scoped>
  .text-center {
    text-align: center;
  }

  .line-div-parent {
    display: flex;
  }

  .line-div {
    display: inline-block;
  }

  .el-button {
    background-color: #91e5fa; 
    border-color: #91e5fa;
    color: white; 
  }

  .el-button:hover {
    background-color: #23a4c4;
    border-color: #23a4c4; 
  }

  .avatar {
    width: 6%;
  }
  .avatar .el-avatar {
    background-color: coral;
    color: white;
  }
  .type {
    width: 14%;
  }
  .type div:first-child {
    font-size: 1.1em;
  }
  .type div:nth-child(2) {
    color: #C0C4CC;
  }

  .description {
    width: 70%;
    padding: 6px 20px;
    font-size: 0.8em;
    /* border: 1px solid red; */
  }

  .button-div {
    width: 20%;
  } 

  .button-div .el-button {
    margin-top: 10px;
  }

  .drop-operation-div {
    margin-top: 10px;
  }

  .drop-operation-div .el-form {
    width: 60%;
    margin-left: 20%;
  }

  .drop-operation-div .el-form .el-input {
    width: 60%;
  }

</style>