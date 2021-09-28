<template>
  <el-container class="page-container">
    <!-- 顶部区域 -->
    <el-header height="100px"> 
      <top-region />
    </el-header>
    <!-- 中部 -->
    <el-main>
      <div class="middle-region">
        <el-row :gutter="1" type="flex" justify="space-between">
          <el-col v-if="categoryInfoId" :span="2" class="left">
            <div v-for="(item, index) in tagList" :key="index">
              <el-button size="medium" style="background-color: #91e5fa; color: white;" circle @click="selectTag(item)">{{item.tagInfoName}}</el-button>
            </div>
          </el-col>
          <el-col v-if="categoryInfoId" :span="15" class="mid" >
            <div v-if="isAlive">
              <article-list :categoryInfoId="categoryInfoId" :tagInfoId="tagInfoId"></article-list>
            </div>
          </el-col>

          <el-col v-if="!categoryInfoId" :span="17" class="mid">
            <div v-if="isAlive">
              <article-list :searchText="searchText"></article-list>
            </div>
          </el-col>  

          <el-col :span="6" class="right">
            <right-content></right-content>
          </el-col>
        </el-row>    
        <!-- 回到顶部 -->
        <back-top />
      </div>
      <!-- 底部区域 -->
      <div height="60px">
        <bottom-region />
      </div>      
    </el-main>
  </el-container>
</template>

<script>
import TopRegion from '../components/topRegion.vue'
import BottomRegion from '../components/bottomRegion.vue'
import BackTop from '@/views/components/backTop.vue'
import ArticleList from './components/ArticleList.vue';
import LeftContent from './components/LeftContent.vue';
import RightContent from './components/RightContent.vue';

import * as api from "@/api/article.js"
export default {
  name: 'index',
  components: {TopRegion, BottomRegion, BackTop, ArticleList, LeftContent, RightContent},
  inject: ['reload'],
  data() {
    return {
      categoryInfoId: null,
      tagInfoId: null,
      searchText: null,
      isAlive: true,
      tagList: []
    };
  },
  watch: {
    '$route.query.categoryInfoId': {
      handler() {
        this.reload()
      }
    }
  },
  mounted() {},
  created() {
    this.categoryInfoId = this.$route.query.categoryInfoId
    this.searchText = this.$route.query.searchText
    if (this.categoryInfoId) {
      this.getTagList()
    }
  },
  methods: {
    // 标签列表
    getTagList() {
      api.getTagList({
        categoryInfoId: this.categoryInfoId
      }).then(res => {
        if (res.code == 200) {
          this.tagList = res.data   
        }
      })
    },

    // 选中标签
    selectTag(tag) {
      this.tagInfoId = tag.tagInfoId
      this.reloadAticle()
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
  .left>div+div {
    margin-top: 10px;
  }
  
  .left {
    padding-bottom: 10px;
  }

  .el-main {
    padding: 0px;
  }
</style>
