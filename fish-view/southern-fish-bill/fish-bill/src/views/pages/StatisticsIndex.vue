<template>
  <div class="main-container">
    第一个饼图

    第二个折线图
    <vab-chart
      :autoresize="true"
      theme="vab-echarts-theme"
      :options="fwl" :style="{width: '100%',height:'350px'}"
    />
  </div>
</template>


<script>
import VabChart from '@/plugins/echart'

export default {
  name: "StatisticsIndex",
  props: {

  },
  data() {
    return {
      
    };
  },
  setup() {},
  methods: {
  
  },
}
</script>

<style scoped>

</style>