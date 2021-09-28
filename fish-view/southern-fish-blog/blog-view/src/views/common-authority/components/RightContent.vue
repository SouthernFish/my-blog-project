<template>
  <div class="right-container">
    <div class="call-me small-card">
      <div class="text-center avatar-region">
        <el-avatar :src="avaterUrl"></el-avatar>
      </div>
      <div class="text-center">
        <div style="font-size: 1.4em;">{{mainerInfo.operatorName}}</div>
        <div style="font-size: 14px;">{{mainerInfo.signature}}</div>
      </div>
      <div class="text-center statistics-region">
        <el-row>
          <el-col :span="8">
            <div><el-button size="medium" type="text" @click="goArticleLine">文章</el-button></div>
            <div>{{statistics.articleTotal}}</div>
          </el-col>
          <el-col :span="8">
            <div><el-button size="medium" type="text">分类</el-button></div>
            <div>{{statistics.categoryTotal}}</div>
          </el-col>
          <el-col :span="8">
            <div><el-button size="medium" type="text">标签</el-button></div>
            <div>{{statistics.tagTotal}}</div>
          </el-col>
        </el-row>
      </div>          
      <div class="text-center link-region">
        <el-button size="medium" circle icon="iconfont icon-github"></el-button>
        <el-button size="medium" circle icon="iconfont icon-qq01"></el-button>
        <el-button size="medium" circle icon="iconfont icon-weixin"></el-button>
        <el-button size="medium" circle icon="iconfont icon-weibo"></el-button>
        <el-button size="medium" circle icon="iconfont icon-C"></el-button>
        <el-button size="medium" circle icon="iconfont icon-word"></el-button>
        <el-button size="medium" circle icon="el-icon-more"></el-button>
      </div>
    </div>
    <div class="text-center isLikeMe small-card">
      <div style="font-size: 1.2em;">Do you like me ?</div>
      <div>
        <img @click="likeMe" style="height: 1.7em;" class="fheart" src="static/logo/heart.png"/>&ensp;
        <span style="font-size: 2.0em;">{{mainerInfo.popularity}}</span>
      </div>
    </div>
    <div class="timer small-card">
      <div style="text-align:center; padding: 10px 0px; color: #91e5fa;">
        <div style="font-size: 15px;">{{date|formatDate}}</div>
        <div style="font-size: 60px;">{{date|formatTime}}</div>
      </div>
    </div>  
    <div class="article small-card">
      <div style="text-align:center; font-size: 18px;">
        <i class="el-icon-time"/>近期文章
      </div>  
      <div class="article-list" v-for="(item, index) in articleList" :key="index">
        <span class="list-button" @click="jumpToArticle(item, index)">{{item.articleTitle}}</span>
        <br/>
        <span> ----------------- </span>
        <span>{{item.createTime}}</span>
      </div>
    </div>   
  </div>
</template>
<script>
import * as api from "@/api/article.js"
import * as user from "@/api/user.js"
import {baseImgURL, seniorOperatorId} from "@/config"
export default {
  name: 'rightContent',
  components: {},
  data() {
    return {
      avaterUrl: "/static/logo/header-avatar.webp",
      mainerInfo: {},
      statistics: {
        articleTotal: 0,
        categoryTotal: 0,
        tagTotal: 0,
      },
      date: new Date(),
      articleList: [],
    };
    
  },
  watch: {},
  mounted() {
    var _this = this
    this.timer = setInterval(() => {
      _this.date = new Date() // 修改日期数据
    }, 1000)
  },
  created() {
    this.getUserInfoById()
    this.getArticleList()
    this.getStatistics()
  },
  destroyed() {
    if (this.timer) {
      clearInterval() // 在Vue实例销毁前清楚当前日期计时器
    }
  },
  methods: {
    // 博主信息
    getUserInfoById() {
      user.getUserInfoById({
        operatorId: seniorOperatorId,
      }).then(res => {
         if (res.code == 200) {
          this.mainerInfo = res.data
          this.avaterUrl = (this.mainerInfo.avatar == null) ? this.avaterUrl : baseImgURL + this.mainerInfo.avatar
        }
      })
    },

    // 统计数据
    getStatistics() {
      api.getStatistics({
        seniorOperatorId: seniorOperatorId,
      }).then(res => {
        if (res.code == 200) {
          this.statistics = res.data
        }
      })
    },

    // 增加人气值
    likeMe() {
      user.addPopularity({
        blogPopularityId: seniorOperatorId,
      }).then(res => {
        if (res.code == 200) {
          this.mainerInfo.popularity = this.mainerInfo.popularity + 1
        }
      })
    },

    // 文章生产线
    goArticleLine() {
      this.$router.push({
        path: "/article/line",
        query: {
          articleTotal: this.statistics.articleTotal,
        },
      })
    },

    // 文章列表
    getArticleList() {
      api.getArticleList({
        operatorId: seniorOperatorId,
        pageNum: 1,
        pageSize: 10,
      }).then(res => {
        if (res.code == 200) {
          this.articleList = res.data.list   
        }
      })
    },

    // 跳转详情
    jumpToArticle(article, index) {
      if (this.$route.query.articleInfoId != article.articleInfoId) {
        this.$router.push({
          path: "/article/detail",
          query: {
            articleInfoId: article.articleInfoId,
          },
        });
      }
    },
  },

  filters: {
    // 当前日期
    formatDate: function(date) {
      var str = "";
      var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
      function setzero(m){return m < 10 ? '0' + m : m }
      str += setzero(date.getFullYear()) +"-";//获取年份
      str += setzero(date.getMonth() + 1) +"-";//获取月份
      str += setzero(date.getDate()) +"  ";//获取日
      str += weekDay[date.getDay()];//获取星期
      return str
    },

    // 当前时间
    formatTime: function(date) {
      var str = "";
      function setzero(m){return m < 10 ? '0' + m : m }
      str +=""+ setzero(date.getHours()) +":";//获取时
      str += ""+setzero(date.getMinutes( )) +":";//获取分
      str += setzero(date.getSeconds()); //获取秒
      return str
    },
  },
}
</script>

<style scoped>
  .right-container {
    margin-bottom: 20px;
  }
  
  .right-container>div+div {
    margin-top: 20px;
  }

  .article>div+div{
    margin-top: 10px;
  }

  .call-me {
    padding-bottom: 10px;
  }

  .call-me .avatar-region .el-avatar{
    height: 100px; 
    width: 100px; 
    margin: 10px 0px;
  }

  .call-me .link-region {
    width: 60%;
    margin-left: 20%;
    margin-top: 20px;
  }

  .isLikeMe {
    color: rgb(241, 57, 57);
    padding: 10px 0px 0px 0px;
  }

  .article {
    padding: 10px 0px 10px 0px;
  }

  .article-list {
    margin-left: 10px;
    margin-right: 10px;
    /* height: 20px; */
    line-height: 20px;
    color: rgb(134, 129, 129); 
    overflow: hidden;
    /* border-bottom: 1px solid rgb(226, 226, 226); */
  }

  .article-list .list-button {
    font-size: 15px; 
    font-weight: bold; 
    background-color: white; 
  }

  .article-list .list-button:hover {
    background-color: #91e5fa; 
  }
  /* .article-list:hover {
    background-color: #91e5fa; 
  } */

  .statistics-region .el-row .el-col .el-button {
    font-size: 1.0em; 
    color: black;
  }

</style>
