<template>
  <el-container class="page-container">
    <!-- 顶部区域 -->
    <el-header height="100px"> 
      <top-region />
    </el-header>
    <!-- 中部 -->
    <el-main>
      <div class="middle-region">
        <div class="personal-info">
          <personal-info />
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
import BackTop from '../components/backTop.vue'
import PersonalInfo from './components/PersonalInfo.vue'

import * as api from "@/api/user.js"
export default {
  name: 'PersonalCenter',
  components: {TopRegion, BottomRegion, BackTop, PersonalInfo},
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
  .personal-info {
    width: 80%;
    margin-left: 10%;
    margin-bottom: 20px;
  }

  .el-main {
    padding: 0px;
  }
</style>
