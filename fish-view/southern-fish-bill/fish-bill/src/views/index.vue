<template>
  <el-container>
    <el-header>
      <!-- left-arrow="false" 左箭头 left-text="" @click-left="onClickLeft"  -->
      <van-nav-bar :title="pageTitle" right-text="家庭账户" @click-right="onClickRight"/>
    </el-header>

    <el-main v-if="isTabAlive">
      <add-index v-if="tabbarActive == 'add'" />
      <statistics-index v-if="tabbarActive == 'statistics'" />
      <bill-detail v-if="tabbarActive == 'detail'" />
    </el-main>

    <el-footer>
      <van-tabbar v-model="tabbarActive" @change="tabbarOnChange">
      <van-tabbar-item name="add">新增</van-tabbar-item>
      <van-tabbar-item name="statistics">统计</van-tabbar-item>
      <van-tabbar-item name="detail">明细</van-tabbar-item>
      </van-tabbar>
    </el-footer>
  </el-container>
</template>

<script>
import AddIndex from './pages/AddIndex.vue';
import StatisticsIndex from './pages/StatisticsIndex.vue';
import BillDetail from './pages/BillDetail.vue';
export default {
  name: "Index",
  components: {AddIndex, StatisticsIndex, BillDetail},
  data() {
    return {
      tabbarActive: 'add',
      pageTitle: "新增",
      isTabAlive: true,
    };
  },
  watch: {
    'tabbarActive': {
      handler() {
        this.mainReload()
      }
    }
  },
  created() {},
  setup() {},
  methods: {
    // nav - bar
    onClickLeft() {},
    onClickRight() {
      this.$router.push({
        path: '/family',
        query: {},
      });
    },

    // tabbar Change
    tabbarOnChange(index) {
      console.log(index, "===", index)
      this.tabbarActive = index
      this.getPageTitle(index)
    },

    mainReload() {
      this.isTabAlive = false
      this.$nextTick(function() {
        this.isTabAlive = true
      })
    },

    // 获取页面标题和路径
    getPageTitle(index) {
      switch(index) {
        case 'add':
          this.pageTitle = "新增"
          break;
        case 'statistics':
          this.pageTitle = "统计"
          break;
        case 'detail':
          this.pageTitle = "明细"
          break;
        case 'relationship':
          this.pageTitle = "人际"
          break;
        default:
          this.pageTitle = "新增"
      } 
    },
  },
}
</script>