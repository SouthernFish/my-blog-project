<template>
  <div class="loading">
    <!-- <el-image src="/static/gif/point-loading.gif" fit="fill"></el-image> -->
    <!-- <span>上传中...</span> -->
  </div>
</template>
 
<script>
  export default {
    name: 'Loading'
  }
</script>
 
<style scoped>
  .loading {
    position: relative;
    background: url('/static/gif/point-loading.gif') center center no-repeat #fff;
    width: 100%;
    height: 100%;
    z-index: 1000;
  }

  .loading span {
    position: absolute;
    margin-top: 30%; 
    margin-left: 45%; 
    color: cornflowerblue;
  }
</style>

<style>
  .fade-enter, .fade-leave-active {
    opacity: 0;
  }
  .fade-enter-active, .fade-leave-active {
    transition: opacity 0.5s;
  }
</style>