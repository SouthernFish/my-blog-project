<template>
  <el-container class="page-container">
    <!-- 顶部区域 -->
    <el-header height="100px"> 
      <top-region />
    </el-header>
    <!-- 中部 -->
    <el-main>
      <div class="middle-region">
        <div class="article-line">
          <div style="font-size: 1.2em; margin: 20px 0px 20px 90px; width: 80%">
            <i class="el-icon-s-opportunity" style="color: #E4E7ED;" /> 嗯..! 目前共计 {{articleTotal}} 篇日志。 继续努力。
          </div>
          <div style="margin-left: 50px; width: 85%">
            <el-timeline>
              <el-timeline-item v-for="(item, index) in lineList" :key="index" placement="top"
                :timestamp="item.createTime">
                <el-card>
                  <h4><el-link @click="goArticleDetail(item)">{{item.articleTitle}}</el-link></h4>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </div>
        </div>   
        <!-- 回到顶部 -->
        <back-top />
      </div>
      <div height="60px">
        <!-- 底部区域 -->
        <bottom-region />
      </div>
    </el-main>
  </el-container>
</template>

<script>
import TopRegion from '../components/topRegion.vue'
import BottomRegion from '../components/bottomRegion.vue'
import BackTop from '@/views/components/backTop.vue'

import * as api from "@/api/article.js"
import {seniorOperatorId} from "@/config"
export default {
  name: 'articleLine',
  components: {TopRegion, BottomRegion, BackTop},
  data() {
    return {
      articleTotal: null,
      lineList: []
    };
  },
  watch: {},
  mounted() {},
  created() {
    this.getLineList()
  },
  methods: {
    // 标签列表
    getLineList() {
      api.getLineList({
        seniorOperatorId: seniorOperatorId
      }).then(res => {
        if (res.code == 200) {
          this.lineList = res.data
          this.articleTotal = (this.$route.query.articleTotal == null) ? this.lineList.length : this.lineList.length 
        }
      })
    },

    // 跳转详情
    goArticleDetail(article) {
      this.$router.push({
        path: "/article/detail",
        query: {
          articleInfoId: article.articleInfoId,
        },
      })
    },

    // 重新加载
    reloadAticle() {
      this.isAlive = false
      this.$nextTick(() => (this.isAlive = true))
    }
  },
}
</script>

<style scoped>
  .article-line {
    width: 80%;
    margin-left: 10%;
    margin-bottom: 20px;
    padding-top: 20px;
    padding-bottom: 20px;
    background-color: white;
    opacity: 0.8;
    border-radius: 20px;
  }
  
  .el-main {
    padding: 0px;
  }

</style>
