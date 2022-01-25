<template>
  <div class="main-container">
      <div style="text-align: left; ">
        <van-button class="date-picker-button" color="linear-gradient(to right, #ff6034, #ee0a24)"
          round type="default" @click="datePickerShow = true">{{showDate}}</van-button>
      </div>
      <!-- 列表展示 -->
      <div style="width: 90%; padding-left: 5%; padding-right: 5%;">
        <el-collapse v-model="collapseName" accordion>
          <el-collapse-item v-for="item in dateList" :key="item.dateTime" 
              :title="item.dateTime + ' ' + item.amount" :name="item.dateTime">
            <van-list :v-model="false" :finished="true" finished-text="">
              <van-row v-if="item.billList && item.billList.length > 0">
                <van-col span="6">名称</van-col>
                <van-col span="6">类型</van-col>
                <van-col span="6">数量</van-col>
                <van-col span="6">金额</van-col>
              </van-row>
              <van-row v-for="(bill, index) in item.billList" :key="index">
                <van-col span="6">{{bill.name}}</van-col>
                <van-col span="6">{{bill.type}}</van-col>
                <van-col span="6">{{bill.quantity}}</van-col>
                <van-col span="6">{{bill.price}}</van-col>
              </van-row>
            </van-list>
          </el-collapse-item>
        </el-collapse>
      </div>

      <!-- 日期选择 -->
      <van-popup v-model="datePickerShow" position="bottom" round :style="{ height: '40%' }">
        <van-datetime-picker v-model="currentDate" type="year-month"
          title="选择年月" :min-date="minDate" :max-date="maxDate" 
          :formatter="formatter" @cancel="dateClose" @confirm="dateConfirm"/>
      </van-popup>
  </div>
</template>

<script>
export default {
  name: "BillDetail",
  props: {},
  data() {
    return {
      showDate: undefined,
      datePickerShow: false,
      minDate: new Date(2020, 0, 1),
      maxDate: new Date(),
      currentDate: new Date(),

      // 按天展示清单
      collapseName: undefined,
      // 月份下日期列表
      dateList: [ 
        {dateTime: "2022-01-14", amount: 188.04, 
          billList: [
            {name: 'in', type: 'daily', quantity: 1, price: 22.21},
            {name: 'in', type: 'daily', quantity: 1, price: 22.21},
            {name: 'in', type: 'daily', quantity: 1, price: 22.21},
            {name: 'in', type: 'daily', quantity: 1, price: 22.21},
          ]
        },
        {dateTime: "2022-01-15", amount: 19.04, billList: []},
        {dateTime: "2022-01-16", amount: 178.04, billList: []},
        {dateTime: "2022-01-17", amount: 8.04, billList: []},
      ],
    };
  },
  setup() {},
  created() {
    var month = new Date().getMonth()+1
    this.showDate = new Date().getFullYear() + "-" + (month < 10 ? ('0'+month) : month)
    this.getDateList() // 获取月清单列表
    
  },
  methods: {
    // 日期格式化
    formatter(type, val) {
      if (type === 'year') {
        return `${val}年`;
      } else if (type === 'month') {
        return `${val}月`;
      }
      return val;
    },  
    // 完成选择
    dateConfirm(value) {
      let month = new Date(value).getMonth()+1
      month = (month < 10 ) ? ('0' + month) : month
      this.showDate = new Date(value).getFullYear() + "-" + month
      this.datePickerShow = false
    },
    // 取消选择
    dateClose() {
      this.datePickerShow = false
    },
    // 月清单列表
    getDateList() {
      // 后端获取
      if (this.dateList && this.dateList.length > 0) {
        this.collapseName = this.dateList[0].dateTime
      }
    },
  },
}
</script>

<style scoped>
  .date-picker-button {
    width: 25%; 
    margin-left: 5%;
  }

</style>