<template>
  <el-container class="page-container">
    <!-- 顶部区域 -->
    <el-header height="100px"> 
      <top-region />
    </el-header>
    <!-- 中部 -->
    <el-main>
      <div class="middle-region">
        <div class="about-content ">
          <div class="title text-center">关于我</div>
          <el-row class="content">
            <el-col :span="2"></el-col>
            <el-col :span="10">
              <p>Hello!</p>
              <p>I'm <span style="font-weight: 600; font-style: italic">Southern Fish</span></p>
              <p>一枚前端萌新，也懂点后端。</p>
              <p>嗯......目前来说希望做一个技术宅！</p>
              <p>兴趣比较多，不过最喜欢静下来放空自己。</p>
            </el-col>
            <el-col :span="10" class="text-center">
              <el-image style="width: 220px; height: 220px;" :src="logoUrl" fit="fill"></el-image>
            </el-col>
            <el-col :span="2"></el-col>
          </el-row>
          <div class="divider-div"><el-divider></el-divider></div>
          
          <div style="height: 20px;"></div>
        </div>
        <!-- 回到顶部 -->
        <back-top />
      </div>
    </el-main>
    <el-footer height="60px">
      <!-- 底部区域 -->
      <bottom-region />
    </el-footer>
  </el-container>
</template>
<script>
import BackTop from '../components/backTop.vue';
import BottomRegion from '../components/bottomRegion.vue';
import TopRegion from '../components/topRegion.vue';

export default {
  name: 'AboutContent',
  components: {TopRegion, BackTop, BottomRegion},
  data() {
    return {
      logoUrl: "/static/logo/about.jfif",
    };
    
  },
  watch: {},
  mounted() {},
  created() {},
  methods: {},
}
</script>

<style scoped>
  .about-content {
    width: 80%;
    margin-left: 10%;
    margin-top: 40px;
    padding-top: 20px;
    background-color: white;
    opacity: 0.8;
    border-radius: 20px;
  }

  .text-center {
    text-align: centers;
  }
  .title, .content, .divider-div {
    margin: 3px 20px;
  }

  .title {
    font-size: 1.5em; 
  }

  .content .p {
    line-height: 1.5em;
  }

  .el-header {
    padding: 0px;
  }

  .el-footer {
    background-color: #232323;
  }

</style>
