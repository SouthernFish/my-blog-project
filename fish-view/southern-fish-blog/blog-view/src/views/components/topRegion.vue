<template>
  <div>
    <el-row type="flex"  justify="space-between" class="top-region">
      <el-col :span="12" class="top-left">
        <el-image :src="topLeftUrl" fit="fill"></el-image>
        <div class="top-button-region">
          <el-radio-group v-model="labelPosition" @change="navBarChange">
            <el-radio-button label="Home" style="right: -1px;"><i class="el-icon-s-home"/> 首页</el-radio-button>
            <el-dropdown @command="categoryCheck" style="top: 4px;">
              <el-radio-button label="Category" >
                <i class="el-icon-box"/> 分类 <i class="el-icon-arrow-down"/>
              </el-radio-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="(item, index) in categoryList" :key="index" 
                  v-text="item.categoryInfoName" 
                  :command="item.categoryInfoId">
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <!-- <el-radio-button label="Message"><i class="el-icon-chat-dot-square"/> 留言板</el-radio-button> -->
            <el-radio-button label="Line"><i class="el-icon-ship"/> 文章生产线</el-radio-button>
            <el-radio-button label="About"><i class="el-icon-postcard"/> 关于我</el-radio-button>
            <el-radio-button label="Panel" v-if="operatorType == '1' "><i class="el-icon-menu"/> 面板</el-radio-button>
          </el-radio-group>
        </div>
      </el-col>
      <el-col :span="12" class="top-right">
        <el-image :src="topRightUrl" fit="fill"></el-image>
        <div class="top-button-region">
          <el-radio-group v-model="labelPosition" @change="navBarChange">
            <el-radio-button label="Register" v-if="!isLogin"> 注册</el-radio-button>
            <el-radio-button label="Login" v-if="!isLogin"> 登录</el-radio-button>
            <el-dropdown @command="operatorCheck" style="top: 4px;" v-if="isLogin">
              <el-radio-button label="Operator">
                <i class="el-icon-user"/>&emsp;<i class="el-icon-arrow-down"/>
              </el-radio-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="Personal">个人中心</el-dropdown-item>
                <el-dropdown-item command="LoginOut">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-popover placement="bottom" width="400" trigger="hover">
              <el-input placeholder="请输入内容" v-model="searchText">
                <i slot="suffix" class="el-input__icon el-icon-search" @click="searchAll"></i>
              </el-input>
              <el-radio-button slot="reference" label="search" style="left: -1px;"><i class="el-icon-search"/></el-radio-button>
            </el-popover>
          </el-radio-group>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import {getAccessToken} from "@/utils/accessToken";
import * as article from "@/api/article.js"
import { recordRoute, seniorOperatorId } from "@/config";
export default {
  name: 'topRegion',
  components: {},
  data() {
    return {
      topLeftUrl: "/static/logo/all-top-left.png",
      topRightUrl: "/static/logo/all-top-right.png",
      username: localStorage.getItem('userName'),
      operatorType: localStorage.getItem('_TONG_OPERATOR_TYPE_'),
      pageName: "Home",
      categoryInfoId: null,
      searchText: null,
      labelPosition: null,
      categoryList: [],
      isLogin: false
    };
    
  },
  watch: {},
  mounted() {},
  computed: {},

  created() {
    if (getAccessToken()) {
      this.isLogin = true
    }
    this.getCategoryList()
  },
  methods: {
    // 获取目录列表
    getCategoryList() {
      article.getCategoryList({
        operatorId: seniorOperatorId,
      }).then(res => {
        if (res.code == 200) {
          this.categoryList = res.data   
        }
      })
    },

    // 搜索框内容
    searchAll() {
       this.$router.push({
        path: "/index",
        query: {
          searchText: this.searchText,
        },
      });
    },

    // 分类下拉框选择
    categoryCheck(type) {
      if (type != this.categoryInfoId) {
        this.categoryInfoId = type
        this.$router.push({
          path: "/index",
          query: {
            categoryInfoId: this.categoryInfoId,
          },
        });
      }
    },

    // 操作员下拉框选择
    operatorCheck(type) {
      if (type == 'LoginOut') {
        this.logout()
      }
      if (type == 'Personal') {
        this.$router.push('/personal/center')
      }
    },

    // 导航栏切换
    navBarChange(value) {
      this.jumpToPage(value)
    },

    // 退出登录
    logout() {
      const operatorType = localStorage.getItem("_TONG_OPERATOR_TYPE_")
      this.$baseConfirm(
        "您确定要退出" + this.$baseTitle + "吗?",
        null,
        async () => {
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
        }
      );
    },


    // 页面跳转
    jumpToPage(type) {
      this.pageName = type
      if (type === 'Home') {
        this.$router.push("/index");
      }
      if (type === 'About') {
        this.$router.push("/about");
      }
      if (type == 'Panel') {
        this.$router.push("/panel")
      }
      if (type === 'Login') {
        this.$router.push("/login");
      }
      if (type === 'Register') {
        this.$router.push("/register");
      }
      if (type === 'Message') {
        this.$router.push("/message/board");
      }
      if (type === 'Line') {
        this.$router.push("/article/line")
      }
    },

  },
}
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

  .el-row .top-right, .el-row .top-left  {
    position: relative;
  }

  .top-button-region {
    position: absolute;
    align-content: center;
  }

  .el-row .top-left .top-button-region {
    bottom: 15px;
    left: 23%;
  }
  .el-row .top-right .top-button-region {
    bottom: 10px;
    right: 15%;
  }

  .el-row .top-button-region .el-button {
    border: #DCDFE6;
    background-color: #DCDFE6;
    color: #979899;
  }

  .top-region .el-button {
    font-size: 20px;
    margin-left: 10px;
  }

  .el-row .el-image {
    height: 100%;
    height: inherit;
  }

  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }

  .el-dialog .el-form {  
    width: 60%;
    margin-left: 20%;
  }
</style>
