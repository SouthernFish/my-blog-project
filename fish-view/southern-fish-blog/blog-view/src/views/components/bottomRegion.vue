<template>
  <div class="bottom-region">
    <div class="bottomContent">
      
    </div>
    <div class="fcontainer">
      <div>
          博客已噌噌噌运行了
          <span v-html='longTime'>{{longTime}}</span>
          <span class="timeJump">(●'◡'●)ﾉ♥</span>
      </div>
      <div>
          托管于<a href="https://github.com/southernfish/blog-view" target="_blank">GitHub</a>. 
          <a href="https://www.aliyun.com/?spm=a2c49.11131515.0.0.5Z9AkR" target="_blank">阿里云</a> 提供静态文件云存储服务. 
          <a href="https://docs.docker.com/" target="_blank">Docker Documentation</a> 提供环境部署参考. 
          <a href="https://www.cloudxns.net/Order/index.html" target="_blank">CloudXNS</a> 提供 DNS 解析服务.
      </div>
      <div>
        <span>Made with </span>
        <!-- <img class="fheart" src="static/logo/heart.png"/> -->
        <span>by SouthernFish.</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'bottomRegion',
  components: {},
  data() {
    return {
      logoUrl: "/static/logo/pikachu-logo.png",
      longTime: null
    };
  },
  watch: {},
  mounted() {},
  created() {
    this.runTime()
  },
  methods: {
    runTime:function(){//运行倒计时
      var that = this;
      var oldTime =new Date('2021/09/01 00:00:00');
      var timer = setInterval(function(){
          var nowTime = new Date();
          var longTime = nowTime - oldTime;
          var days = parseInt(longTime / 1000 / 60 / 60 / 24 , 10); //计算剩余的天数
            var hours = parseInt(longTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时
            var minutes = parseInt(longTime / 1000 / 60 % 60, 10);//计算剩余的分钟
            var seconds = parseInt(longTime / 1000 % 60, 10);//计算剩余的秒数
            that.longTime = days+"天" + hours+"小时" + minutes+"分"+seconds+"秒";
      }, 1000)
    }
  },
}
</script>

<style scoped>
  .bottom-region {
    background-color: #232323;
    color:#888;
    font-size: 13px;
    line-height: 1.5em;
    text-align: center;
  }

  .fcontainer {
    background: #232323;
    box-sizing: border-box;
    width: 100%!important;
  }

  .fcontainer>div {
    height: 20px;
    line-height: 20px;
  }
</style>
