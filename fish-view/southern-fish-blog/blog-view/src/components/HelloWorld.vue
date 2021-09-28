<template>
  <div class="hello">
    <h1>{{msg}}</h1>
    <h1>{{ components }}</h1>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  components: {},
  props: {
    msg: String
  },
  data() {
    return {
      str: "components test",
    };
    
  },
  watch: {},
  mounted() {},
  created() {},
  methods: {},
}
</script>

<style scoped>

</style>
