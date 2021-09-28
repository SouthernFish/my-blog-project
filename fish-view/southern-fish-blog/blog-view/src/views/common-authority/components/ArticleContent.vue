<template>
  <div class="article-detail">
    <attach-article :creatTime="tagTime" :tagInfoName="article.tagInfoName"></attach-article>
    <div class="hearder-region">
      <div class="hearder-title">{{article.articleTitle}}</div>
      <div>
        <i class="el-icon-user"></i>
        发表于 <span >{{article.createTime}}</span> • 
        分类于 <span style="color: #91e5fa;">{{article.categoryInfoName}}</span>
      </div>
    </div>
    <!-- <div class="article-content" style="min-height: 200px;" v-html="mdContent"></div> -->
    <div class="article-content" style="min-height: 200px;">
      <mavon-editor :toolbars="markdownOption" 
        :box-shadow="false" v-model="mdContent" 
        :subfield="false" 
        style="border: 0" 
        preview-background="#FFF" 
        default-open="preview"/>
    </div>
    <el-divider></el-divider>
    <div class="article-footer">
      <span>分享到:</span>
      <el-button size="medium" circle icon="iconfont icon-QQ"></el-button>
      <el-button size="medium" circle icon="iconfont icon-shejiaotubiao-42"></el-button>
      <el-button size="medium" circle icon="iconfont icon-weixin1"></el-button>
      <div class="footer-right">
        <el-button v-show="article.isFabulous == 0" size="medium" round icon="el-icon-thumb"
          @click="fabulousComment('fabulous', 'fabulous')">
            点赞&nbsp;|&nbsp;{{article.popularity}}
        </el-button>
        <el-button v-show="article.isFabulous == 1" size="medium" round icon="el-icon-thumb"
          class="is-fabulous-activated" @click="fabulousComment('fabulous', 'no-fabulous')">
            点赞&nbsp;|&nbsp;{{article.popularity}}
        </el-button>

        <el-button v-show="article.isCollection == 0" size="medium" round icon="el-icon-thumb"
          @click="fabulousComment('collection', 'fabulous')">
            收藏&nbsp;|&nbsp;{{article.collection}}
        </el-button>
        <el-button v-show="article.isCollection == 1" size="medium" round icon="el-icon-thumb"
          class="is-fabulous-activated" @click="fabulousComment('collection', 'no-fabulous')">
            收藏&nbsp;|&nbsp;{{article.collection}}
        </el-button>
      </div>
      <div class="textCenter" style="padding: 20px 0px 20px 0px;">
        <el-button size="medium" round style="background-color: #91e5fa; color: white; border-color: #91e5fa;" @click="donateMe">
          <i class="el-icon-thumb"></i>赞赏
        </el-button>
      </div>
      <div class="textCenter donate" v-show="isDonate">
        <div class="image-div">
          <el-image :src="logoUrl" fit="scale-down"></el-image>
          <span>微信扫一扫</span>
        </div>
        <div class="image-div">
          <el-image :src="logoUrl" fit="scale-down"></el-image>
          <span>支付宝扫一扫</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import AttachArticle from './AttachArticle.vue';
import * as api from "@/api/article.js"
import {imeFormat} from "@/utils/index";
import * as comment from "@/api/comment.js"
import marked from 'marked'
export default {
  name: 'articleContent',
  components: {AttachArticle},
  props: {
    articleInfoId: String
  },
  data() {
    return {
      logoUrl: "/static/logo/pikaqu.webp",
      isDonate: false,
      tagTime: "",
      article: {},
      mdContent: null,
      // mavon-editor 形式显示 md 文本
      markdownOption: {
        toolbarsFlag: false,
        subfield: false
      },

      fabulousInfo: {
        personalInterestId: null,
        objectType: 1,
        articleInfoId: this.articleInfoId,
        isFabulous: null,
        isCollection: null,
      },
    };
    
  },
  watch: {},
  mounted() {},
  created() {
    this.getArticleInfo()
  },
  methods: {
    // 文章信息
    getArticleInfo() {
      api.getArticleInfo({
        articleInfoId: parseInt(this.articleInfoId)
      }).then(res => {
        if (res.code == 200) {
          this.article = res.data
          this.tagTime = imeFormat(this.article.createTime, 3)
          // 使用 mavon-editor 显示
          this.mdContent = this.article.articleContent  
          //这里把取出来的md格式的文本内容 marked格式化  
          // this.mdContent = marked(this.article.articleUrl || '', {
          //   sanitize:true
          // })
        }
      })
    },

    // 登录验证：
    checkToken() {
      if (this.token == null || this.token == undefined || this.token == '') {
        this.$message.error('您还没有登录，请先登录');
        return false
      } else {
        return true
      } 
    },

    // 点赞/取消点赞  收藏/取消收藏 buttonType 类型
    fabulousComment(buttonType, type) {
      if (this.checkToken) {
        const value = (type == 'fabulous') ? '1' : '0'
        const step = (type == 'fabulous') ? 1 : -1
        this.fabulousInfo.personalInterestId = this.article.personalInterestId
        if (buttonType == 'fabulous') {
          const msg = (type == 'fabulous') ? '点赞成功' : '已取消点赞'
          this.fabulousInfo.isFabulous = value
          // 保存
          this.savePersonalInterest(msg)
          // 样式
          this.article.isFabulous = value
          this.article.popularity = this.article.popularity + step
        }
        if (buttonType === 'collection') {
          const msg = (type == 'fabulous') ? '收藏成功' : '已取消收藏'
          this.fabulousInfo.isCollection = value
          // 保存
          this.savePersonalInterest(msg)
          // 样式
          this.article.isCollection = value
          this.article.collection = this.article.collection + step
        }
      }
    },

    // 保存点赞信息
    savePersonalInterest(msg) {
      comment.savePersonalInterest(this.fabulousInfo).then(res => {
        if (res.code == 200) {
          this.$message({
            message: msg,
            type: 'success'
          });
          this.article.personalInterestId = res.data.personalInterestId
        }
      })
    },

    // 赞赏
    donateMe() {
      this.isDonate = !this.isDonate
    }
  },
}
</script>

<style scoped>
  .textCenter {
    text-align: center;
  }

  .article-detail {
    position: relative;
    background-color: white;
    border-radius: 20px;
  }

  .hearder-region {
    text-align:center; 
    /* height: 100px;  */
    padding-top: 20px 
  }

  .hearder-region .hearder-title {
    font-size: 1.5em; 
    font-weight: bold; 
    margin-bottom: 10px; 
  }

  .article-content, .article-footer {
    padding: 0px 20px 0px 20px;
  }

  .el-divider {
    width: 96%;
    margin-top: 50px;
    margin-left: 2%;
  }

  .article-footer {
    position: relative;
  }

  .article-footer>.el-button {
    margin-left: 10px;
  }

  .footer-right {
    position: absolute;
    right: 20px;
    top: 0;
  }
  
  .donate .image-div {
    display: inline-block;
    width: 20%;
    margin: 20px 0px 20px 0px;
  }

  .donate>.image-div {
    margin-right: 20px;
  }

  .is-fabulous-activated {
    background-color: #e66108; 
    border-color: #e66108;
    color: white;
  }
</style>
