<template>
  <div class="attach-left">
    <div class="dateClass">{{creatTime}}</div>
    <div class="tagClass">{{tagInfoName}}</div>
  </div>
</template>
<script>

export default {
  name: 'aboutContent',
  components: {},
  props: {
    creatTime: String,
    tagInfoName: String,
  },
  data() {
    return {
      logoUrl: "/static/logo/pikachu-logo.png",
      positionUrl: "/static/logo/pikaqu.webp",
    };
    
  },
  watch: {},
  mounted() {},
  created() {},
  methods: {},
}
</script>

<style scoped>
  .attach-left {
    position: absolute;
    left: -25px;
    top: -25px;
    width: 100px;
    color: white;
  }

  .dateClass {
    text-align: center;
    width: 50%;
    height: 50px;
    background-color: #91e5fa;
    font-size: 20px;
    border-radius: 50%;
  }

  .tagClass {
    width: 80%;
    margin: 30px 0px 0px 15px;
    padding: 5px;
    text-align: right;
    font-size: 16px;
    border-top-right-radius: 5px;
    background-color: #91e5fa; 
  }
</style>
