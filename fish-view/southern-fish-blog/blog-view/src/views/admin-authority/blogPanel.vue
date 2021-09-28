<template>
  <el-container class="page-container">
    <!-- 顶部区域 -->
    <el-header height="100px"> 
      <top-region />
    </el-header>
    <!-- 中部 -->
    <el-main>
      <div class="middle-region">
        <el-tabs type="border-card" v-model="activeName">
          <el-tab-pane label="文章管理" name="article">
            <article-list :tabType="1" v-if="activeName == 'article' " />
          </el-tab-pane>
          <el-tab-pane label="分类管理" name="category">
            <category-editor v-if="activeName == 'category' " />
          </el-tab-pane>
          <el-tab-pane label="留言管理" name="comment">
            <comment-editor v-if="activeName == 'comment' " />
          </el-tab-pane>
          <el-tab-pane label="回收站" name="litter">
            <article-list :tabType="2"  v-if="activeName == 'litter' " />
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-main>
    <el-footer height="60px">
      <!-- 底部区域 -->
      <bottom-region />
    </el-footer>
  </el-container>
</template>
<script>
import BottomRegion from '@/views/components/bottomRegion.vue';
import TopRegion from '@/views/components/topRegion.vue';
import CategoryEditor from './components/CategoryEditor.vue'
import ArticleList from './components/ArticleList.vue';
import CommentEditor from './components/CommentEditor.vue';
export default {
  name: 'blogPanel',
  components: {TopRegion, BottomRegion, CategoryEditor, ArticleList, CommentEditor},
  data() {
    return {
      logoUrl: "/static/logo/pikachu-logo.png",
      positionUrl: "/static/logo/pikaqu.webp",
      activeName: "article"
    };
    
  },
  watch: {},
  mounted() {},
  created() {},
  methods: {

  },
}
</script>

<style scoped>
  .el-button {
    margin-left: 20px;
    background-color: #91e5fa; 
    color: white; 
    border-color: #91e5fa;
  }

  .el-main {
    padding: 10px;
    padding-top: 20px;
  }

</style>
