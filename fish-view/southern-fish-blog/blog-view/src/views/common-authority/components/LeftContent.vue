<template>
  <div class="left-container">
    <div v-for="(item, index) in tagList" :key="index">
      <el-button size="medium" style="background-color: #91e5fa; color: white;" circle @click="selectTag(item)">{{item.tagInfoName}}</el-button>
    </div>
  </div>
</template>
<script>
import * as api from "@/api/article.js"
export default {
  name: 'leftContent',
  components: {},
  props: {
    categoryInfoId: Number
  },
  data() {
    return {
      logoUrl: "/static/logo/pikaqu.webp",
      tagList: []
    };
    
  },
  watch: {},
  mounted() {},
  created() {
    this.getTagList()
  },
  methods: {
    // 标签列表
    getTagList(categoryInfoId) {
      api.getTagList({
        categoryInfoId: categoryInfoId
      }).then(res => {
        if (res.code == 200) {
          this.tagList = res.data   
          this.setTagList()
        }
      })
    },

    // 选中标签
    selectTag(tag) {
      this.$emit("tagInfoId", tag.tagInfoId)
    }  
    
  },
}
</script>

<style scoped>
  .left-container>div+div {
    margin-top: 10px;
  }
  
  .left-container {
    padding-bottom: 10px;
  }

</style>
