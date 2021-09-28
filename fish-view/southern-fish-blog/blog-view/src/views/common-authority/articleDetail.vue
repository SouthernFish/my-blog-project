<template>
  <el-container class="page-container">
    <!-- 顶部区域 -->
    <el-header height="100px"> 
      <top-region />
    </el-header>
    <!-- 中部 -->
    <el-main >
      <div class="middle-region">
        <el-row :gutter="1" type="flex" justify="space-between">
          <el-col :span="17" class="mid">
            <article-content :articleInfoId="articleInfoId" />
            <community-comment :articleInfoId="articleInfoId" />
          </el-col> 
          <el-col :span="6" class="right">
            <right-content></right-content>
          </el-col>
        </el-row> 
          
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
import BackTop from '../components/backTop.vue'
import RightContent from './components/RightContent.vue';
import ArticleContent from './components/ArticleContent.vue';
import CommunityComment from './components/CommunityComment.vue';

export default {
  name: 'articleDetail',
  components: {TopRegion, BottomRegion, BackTop, RightContent, ArticleContent, CommunityComment},
  inject: ['reload'],
  data() {
    return {
      articleInfoId: null,
    };
    
  },
  watch: {
    '$route.query.articleInfoId': {
      handler() {
        this.reload()
      }
    }
  },
  mounted() {},
  created() {
    this.articleInfoId = this.$route.query.articleInfoId
  },
  methods: {},
}
</script>

<style scoped>
  .el-main {
    padding: 0px;
  }
</style>
