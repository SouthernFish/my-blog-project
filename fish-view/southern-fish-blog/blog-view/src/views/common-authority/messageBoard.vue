<template>
  <div class="page-container">
    <!-- 顶部区域 -->
    <top-region />
    <!-- 中部区域 -->
    <div class="middle-region">
      <div class="comment-info">
        <community-comment />
      </div>
      <!-- 回到顶部 -->
      <back-top />
    </div>
    <!-- 底部区域 -->
    <bottom-region />
  </div>
</template>

<script>
import TopRegion from '../components/topRegion.vue'
import BottomRegion from '../components/bottomRegion.vue'
import BackTop from '@/views/components/backTop.vue'
import CommunityComment from './components/CommunityComment.vue'

import * as api from "@/api/user.js"
export default {
  name: 'MessageBoard',
  components: {TopRegion, BottomRegion, BackTop, CommunityComment},
  inject: ['reload'],
  data() {
    return {
      userInfo: null,
    };
  },
  watch: {},
  mounted() {},
  created() {},
  methods: {},
}
</script>

<style scoped>
  .comment-info {
    width: 80%;
    margin-left: 10%;
    margin-top: 25px;
    margin-bottom: 100px;
  }
</style>
