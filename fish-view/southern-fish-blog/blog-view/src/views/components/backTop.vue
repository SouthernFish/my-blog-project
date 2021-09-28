<template>
  <!-- 回到顶部 -->
  <el-backtop  :bottom="100">
    <div class="backTopIcon">
      <i class="el-icon-caret-top"></i>
    </div>
  </el-backtop>
</template>

<script>
export default {
  name: 'backTop',
  data() {
    return {};
  },
  watch: {},
  mounted() {},
  created() {},
  methods: {},
}
</script>

<style scoped>
  /* 回到顶部 */
  .backTopIcon {
    height: 100%;
    width: 100%;
    background-color: #f2f5f6;
    box-shadow: 0 0 6px rgba(0, 0, 0, .12);
    text-align: center;
    line-height: 40px;
    border-radius: 50%;
    font-size: 25px;
    color: #84bef8;
  }
</style>
