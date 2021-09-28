<template>
  <div class="personal-region">
    <!-- 基本信息 -->
    <div id="pageTop" class="base-info">
      <div class="title"> <span style="color: red;">|</span> 基本信息</div>
      <div class="line-div-parent">
        <div class="line-div avatar text-center">
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <el-image v-if="avaterUrl" :src="avaterUrl" class="avatar"></el-image>
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <!-- <div><el-link :underline="false">点击图片更换头像</el-link></div> -->
          <div style="color: #606266; font-size: 0.9em;">点击图片更换头像</div>
        </div>
        <div class="line-div person-info">
          <el-form ref="form" :model="userInfo" label-width="80px">
            <el-form-item label="账户名：">
              <div>{{userInfo.operatorAccount}}</div>
            </el-form-item>
            <el-form-item label="昵称：">
              <el-input style="width: 40%;" v-model="userInfo.operatorName" placeholder="请输入昵称"></el-input>
            </el-form-item>
            <el-form-item label="签名：">
              <el-input style="width: 60%;" type="textarea" v-model="userInfo.signature" placeholder="写点什么吧..."
               rows="2" maxlength="30" show-word-limit></el-input>
            </el-form-item>
            <el-form-item label="电话：">
              <div>{{userInfo.operatorTel|checkData}} &emsp; <el-link :underline="false" type="primary" @click="goToSetting('tel')">去设置</el-link></div>
            </el-form-item>
            <el-form-item label="邮箱：">
              <div>{{userInfo.operatorEmail|checkData}} &emsp; <el-link :underline="false" type="primary" @click="goToSetting('email')">去设置</el-link></div>
            </el-form-item>
            <el-form-item label="性别：">
              <el-radio-group v-model="userInfo.sex">
                <el-radio label="0">保密</el-radio>
                <el-radio label="1">男</el-radio>
                <el-radio label="2">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="生日：">
              <el-date-picker style="width: 40%;" v-model="userInfo.birthday" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button @click="saveUserInfo">保存修改</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <el-divider></el-divider>

    <!-- 账户安全 -->
    <div class="account-security">
      <div class="title"> <span style="color: red;">|</span> 账户安全</div>
      <div class="security-content">
        <!-- 修改密码 -->
        <div id="passwordModify" class="security-element">
          <modify-password />
        </div>

        <!-- 绑定邮箱 -->
        <div id="emailBand" class="security-element">
          <div class="line-div-parent">
            <div class="line-div avatar text-center">
              <el-avatar shape="square" :size="50" icon="el-icon-message"></el-avatar>
            </div>
            <div class="line-div type text-center">
              <div>绑定邮箱</div>
              <div>{{userInfo.operatorEmail|checkDataOperation}}</div>  
            </div>
            <div class="line-div description">
              进行邮箱验证后，可用于接收敏感操作的身份验证信息，可用于找回密码。<br/>
              绑定时，系统将发送验证邮件到您绑定的信箱，您需在24小时内登录邮箱并完成验证。
            </div>
            <div class="line-div button-div text-center">
              <el-button @click="securityLaunch('email')" disabled>绑定邮箱</el-button>
            </div>
          </div>
          <div class="drop-operation-div" v-show="emailDialog">
            <el-form width="60%" label-width="100px" class="demo-form" :rules="rules">
              <el-form-item label="绑定邮箱：" prop="bandEmail">
                <el-input type="text" v-model="bandEmail" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button @click="emailDialog = false" style="margin-left: 10%;">取 消</el-button>
                <el-button type="primary" @click="bandEmailSubmit">确 定</el-button>
              </el-form-item>
            </el-form>  
          </div>
        </div>

        <!-- 绑定电话 -->
        <div id="telBand" class="security-element">
          <div class="line-div-parent">
            <div class="line-div avatar text-center">
              <el-avatar shape="square" :size="50" icon="el-icon-mobile-phone"></el-avatar>
            </div>
            <div class="line-div type text-center">
              <div>绑定手机</div>
              <div>{{userInfo.operatorTel|checkDataOperation}}</div>  
            </div>
            <div class="line-div description">
              绑定手机后可直接通过短信接受安全验证等重要操作。<br/>
              绑定手机号时，收到安全验证码后，请在30分钟内完成验证。
            </div>
            <div class="line-div button-div text-center">
              <el-button @click="securityLaunch('tel')" disabled>绑定手机</el-button>
            </div>
          </div>
          <div class="drop-operation-div" v-show="telDialog">
            <el-form :model="bandTel" width="100%" label-width="100px" class="demo-form" :rules="rules">
              <el-form-item label="绑定电话：" prop="telephone">
                <el-input type="text" v-model="bandTel.telephone" autocomplete="off"></el-input>
                <el-link :underline="false" style="margin-left: 15px;">发送验证码</el-link>
              </el-form-item>
              <el-form-item label="验证码：" prop="validateCode">
                <el-input type="text" v-model="bandTel.validateCode" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button @click="telDialog = false" style="margin-left: 10%;">取 消</el-button>
                <el-button type="primary" @click="bandTelSubmit">确 定</el-button>
              </el-form-item>
            </el-form>  
          </div>
        </div>

      </div>
    </div>
    <el-divider></el-divider>

    <!-- 收藏信息 -->
    <div class="collection-Info">
      <div class="title"> <span style="color: red;">|</span> 我的收藏</div>
      <collection-info />
    </div> 
    <el-divider></el-divider>

    <!-- 我的留言 -->
    <div class="comment-Info">
      <div class="title"> <span style="color: red;">|</span> 我的留言</div>
      <comment-info />
    </div>        
  </div>
</template>

<script>
import * as api from "@/api/user.js"
import {baseImgURL, baseURL} from "@/config"
import { getAccessToken } from '@/utils/accessToken'
import {isPhone, isEmail} from "@/utils/validate";
import CollectionInfo from './PersonOperation/CollectionInfo.vue';
import CommentInfo from './PersonOperation/CommentInfo.vue';
import ModifyPassword from './PersonOperation/ModifyPassword.vue';
export default {
  name: 'commentPage',
  components: {CollectionInfo, CommentInfo, ModifyPassword},
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入电话号码"));
      }
      if (!isPhone(value)) {
        callback(new Error("请输入正确的电话号码"));
      } else {
        callback();
      }
    };
    return {
      avaterUrl: "/static/logo/header-avatar.webp",
      uploadUrl: baseURL + "/blog/upload/file/byftp?token=" + getAccessToken(),   //图片上传拼接 url
      userInfo: {},
      rules: {
        bandEmail: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '输入应为邮箱格式', trigger: 'blur' },
        ],
        telephone: [
          {required: true, message: '请输入正确电话号码!', validator: checkPhone},
        ],
      },
      emailDialog: false,
      bandEmail: null,
      telDialog: false,
      bandTel: {
        telephone: null,
        validateCode: null,
      },
    };
    
  },
  watch: {},
  mounted() {},
  created() {
    this.getUserInfo()
  },
  methods: {
    // 用户基本信息
    getUserInfo() {
      api.getUserInfoByToken({
        token: getAccessToken(),
      }).then( res => {
        if (res.code == 200) {
          this.userInfo = res.data    
          this.avaterUrl = (this.userInfo.avatar == null) ? this.avaterUrl : baseImgURL + this.userInfo.avatar
          this.userInfo.sex = this.userInfo.sex + ''
          // 本来是为了设置 默认值的
          // if (res.data.birthday) {
          //   this.$set(this.userInfo, 'birthday', res.data.birthday)
          // }
        } 
      }) 
    },

    // 头像上传成功
    handleAvatarSuccess(res, file) {
      this.avaterUrl = baseImgURL + res.data
      this.userInfo.avatar = res.data
      this.saveUserInfo()
    },

    // 保存用户信息
    saveUserInfo() {
      console.log(this.userInfo)
      api.modifyUserInfo(this.userInfo).then(res => {
        if (res.code == 200) {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
        } 
      })
    },

    // 基础信息锚点 页内跳转
    goToSetting(type) {
      if (type == 'tel') {
        this.telDialog = true
        document.getElementById('telBand').scrollIntoView(true)
      }
      if (type == 'email') {
        this.emailDialog = true
         document.getElementById('emailBand').scrollIntoView(true)
      }
    },
    
    // 账户安全区域选择按钮
    securityLaunch(type) {
      if (type == 'email') {
        this.emailDialog = !this.emailDialog
      }
      if (type == 'tel') {
        this.telDialog = !this.telDialog
      }
    },

    // 绑定邮箱
    bandEmailSubmit() {
      this.emailDialog = false
    },

    // 绑定电话
    bandTelSubmit() {
       this.telDialog = false
    },

  },
  filters: {
    // 邮箱和电话绑定
    checkData(value) {
      if (!value) {
        return '未设置'
      } else {
        return value
      }
    },

    // 邮箱和电话绑定
    checkDataOperation(value) {
      if (!value) {
        return '未设置'
      } else {
        return '已设置'
      }
    },

  }
}
</script>

<style scoped>
  .personal-region {  
    min-height: 400px;
    border-radius: 20px;
    background-color: white;
    opacity: 0.8; 
    padding: 10px 0px 50px 0px;
  }

  .title {
    margin: 20px 20px 10px 20px;
    border-bottom: 1px solid #E4E7ED;
    font-size: 1.2em;
    padding-bottom: 5px;
  }

  .line-div-parent {
    display: flex;
  }

  .line-div {
    display: inline-block;
  }

  .text-center {
    text-align: center;
  }

  .base-info .avatar {
    width: 20%;
  }

  .base-info .el-upload {
    border: 1px solid #d9d9d9;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader-icon {
    font-size: 25px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
  }

  .base-info .avatar .el-image {
    height: 150px;
    width: 150px;
  }

  .person-info {
    width: 75%;
    min-height: 200px;
  }

  .el-link:hover {
    color: red;
  }

  ::v-deep .base-info .el-textarea .el-input__count {
    background: none;
    bottom: -7px;
    right: 5px;
  }

  .base-info .el-form-item .el-button {
    width: 30%;
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

  /* .security-content { border: 1px solid red;} */

  .security-element {
    margin: 0px 20px 20px 20px;
    border-bottom: 1px dashed #DCDFE6;
  }

  .security-element .avatar {
    width: 6%;
  }
  .security-element .avatar .el-avatar {
    background-color: coral;
    color: white;
  }
  .security-element .type {
    width: 14%;
  }
  .security-element .type div:first-child {
    font-size: 1.1em;
  }
  .security-element .type div:nth-child(2) {
    color: #C0C4CC;
  }

  .security-element .description {
    width: 70%;
    padding: 6px 20px;
    font-size: 0.8em;
    /* border: 1px solid red; */
  }
  .security-element .button-div {
    width: 20%;
  } 

  .security-element .button-div .el-button {
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
