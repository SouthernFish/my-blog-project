<template>
  <div style="margin-bottom: 20px;">
    <div class="article small-card" v-for="(item, index) in articleList" :key="index">
      <attach-article :creatTime="item.tagTime" :tagInfoName="item.tagInfoName"></attach-article>
      <div class="hearder-region">
        <div class="hearder-title">{{item.articleTitle}}</div>
        <div style="font-size: 13px;">
          <i class="el-icon-user"></i>
          发表于 <span >{{item.createTime}}</span> •
          分类于 <span style="color: #91e5fa;">{{item.categoryInfoName}}</span>
        </div>
      </div>   
      <div style="text-indent: 2em; padding: 0px 20px;">
        {{item.description}}
      </div>           
      <div style="text-align:center; height: 200px; overflow: hidden;">
        <el-image style="width: 100%; height: 100%;" :src="item.coverImgUrl" fit="scale-down"></el-image>
      </div>
      <div style="text-align:center; margin-bottom: 10px; font-size: 25px;">
        <el-button size="medium" @click="jumpToArticle(item, index)">
          阅读全文
          <i class="el-icon-d-arrow-right"></i>
        </el-button>
      </div>
    </div>
    <div class="more-button" v-if="pageTotal > (params.PageNum * params.pageSize)">
      <el-button size="medium" @click="loadMoreArticle">点击加载更多</el-button>
    </div>
    <div class="no-more" v-else>
      暂无更多数据
    </div>
  </div>
</template>
<script>
import AttachArticle from './AttachArticle.vue';
import {baseImgURL, seniorOperatorId} from "@/config"
import * as api from "@/api/article.js"
import {imeFormat} from "@/utils/index";
export default {
  name: 'articleList',
  components: {AttachArticle},
  props: {
    categoryInfoId: String,
    tagInfoId: Number,
    searchText: String
  },
  data() {
    return {
      coverImgUrl: "/static/logo/article.jfif",
      articleList: [],
      pageTotal: null,        
      params: {
        pageNum: 1,
        pageSize: 10,
      }
    };
  },
  watch: {},
  mounted() {},
  created() {
    this.params.searchText = this.searchText
    this.params.categoryInfoId= this.categoryInfoId
    this.params.tagInfoId = this.tagInfoId
    this.params.operatorId = seniorOperatorId
    this.getArticleList()
  },
  methods: {
    // 文章列表
    getArticleList() {
      api.getArticleList(this.params).then(res => {
        if (res.code == 200) {
          this.pageTotal = res.data.total
          this.articleList.push.apply(this.articleList, res.data.list);
          this.articleList.forEach(element => {
            element.tagTime = imeFormat(element.createTime, 3)
            element.coverImgUrl = baseImgURL + element.coverImgUrl
          })
        }
      })
    },

    // 跳转详情
    jumpToArticle(article, index) {
      this.$router.push({
        path: "/article/detail",
        query: {
          articleInfoId: article.articleInfoId,
        },
      });
    },

    // 加载更多
    loadMoreArticle() {
      this.params.pageNum = this.params.pageNum + 1
      this.getArticleList()
    },
  },
}
</script>

<style scoped>
  .article {
    position: relative;
    padding: 0px 0px 10px 0px;
    margin-bottom: 40px;
  }

  .article>div+div {
    margin-bottom: 10px;
  }

  .hearder-region {
    text-align:center; 
    height: 100px; 
    padding-top: 20px 
  }

  .hearder-region .hearder-title {
    font-size: 1.5em; 
    font-weight: bold; 
    margin-bottom: 10px; 
  }
  
  .el-button {
    background-color: #91e5fa; 
    color: white; 
    border-color: #91e5fa;
  }

  .more-button .el-button {
    width: 100%;
  }

  .el-button:hover {
    background-color: #23a4c4;
    border-color: #23a4c4; 
  }

  .no-more {
    text-align: center;
    height: 30px;
    line-height: 30px;
    font-size: 1.1em;
    background-color: #91e5fa; 
    color: white; 
    border-color: #91e5fa;
  }
</style>
