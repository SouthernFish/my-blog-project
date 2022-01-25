<template>
  <div class="main-container">
    <van-tabs v-model="activeName">
      <van-tab title="流水" name="detail">
        <van-search v-model="searchText" placeholder="请输入搜索关键词" />
        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
          <el-collapse v-model="collapseName" accordion style="width: 96%; margin-left: 2%;">
            <el-collapse-item v-for="(item, index) in relationList" :key="index" 
                :title="item.name" :name="item.name">
              <van-row v-for="(bill, inx) in item.billList" :key="inx">
                <van-col span="6">{{bill.dateTime}}</van-col>
                <van-col span="6">{{bill.incident}}</van-col>
                <van-col span="6">{{bill.amount}}</van-col>
                <van-col span="6">{{bill.remark}}</van-col>
              </van-row>
              
            </el-collapse-item>
          </el-collapse>
        </van-pull-refresh> 
      </van-tab>
      <van-tab title="导入" name="import" style="text-align: left; padding-left: 2%;">
        <div style="margin-top: 5%; margin-bottom: 5%; padding-left: 10%;">
          <el-link target="_blank" :href="importMouldUrl"> 点击下载导入模板 </el-link>
        </div>
        <div style="padding-left: 5%;">
          <van-form @submit="onSubmit('import')">
            <van-field v-model="incident" name="事件" label="事件" placeholder="事件描述"
              :rules="[{ required: true, message: '请填写事件描述' }]" />
            <input id="upload" type="file" @change="importData(this)" style="margin: 16px;"
                    accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/>
            <div style="margin: 16px;">
              <van-button round block type="info" native-type="submit">提交</van-button>
            </div>
          </van-form>
        </div>
      </van-tab>
      <van-tab title="手动填写" name="add">
        <div style="padding-left: 5%;">
          <van-form @submit="onSubmit('add')">
            <van-field v-model="name" name="姓名" label="姓名" placeholder="姓名"
              :rules="[{ required: true, message: '请填写姓名' }]"
            />
            <van-field v-model="incident" name="事件" label="事件" placeholder="事件"
              :rules="[{ required: true, message: '请填写事件名称' }]"
            />
            <div style="margin: 16px;">
              <van-button round block type="info" native-type="submit">提交</van-button>
            </div>
          </van-form>
        </div>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
export default {
  name: "RelationshipIndex",
  props: {},
  data() {
    return {
      activeName: "detail",
      searchText: undefined,
      refreshing: false,
      collapseName: undefined,
      relationList: [
        {name: "张三(重庆)", billList: [
          {dateTime: "2022-01-01", incident: "事件1", amount: "-280", remark: "备注1"},
          {dateTime: "2021-04-01", incident: "事件2", amount: "+280", remark: "备注2"}
        ]},
        {name: "李四", billList: [
          {dateTime: "2022-01-01", incident: "事件1", amount: "-260", remark: "备注1"},
          {dateTime: "2021-04-01", incident: "事件2", amount: "+270", remark: "备注2"}
        ]}
      ],
      importMouldUrl: undefined,
      incident: undefined,
      importList: [],
      addData: {
        name: undefined,
        incident: undefined,
        dateTime: undefined,
        remark: undefined,
        amount: undefined,
      },


    };
  },
  setup() {},
  methods: {
    onRefresh() {
      // 重新加载数据
      // 将 loading 设置为 true，表示处于加载状态
      this.loading = true;
    },

    // 下载订单模块

    // 导入订单数据处理
    importData(obj) {
      // 通过DOM取文件数据
      let _this = this;
      this.file = event.currentTarget.files[0];
      var rABS = false; //是否将文件读取为二进制字符串
      var f = this.file;
      this.orderImportData.importFile = f.name
      var reader = new FileReader();
      FileReader.prototype.readAsBinaryString = function (f) {
        var binary = "";
        var rABS = false; //是否将文件读取为二进制字符串
        var wb; //读取完成的数据
        var reader = new FileReader();
        reader.onload = function (e) {
          var bytes = new Uint8Array(reader.result);
          var length = bytes.byteLength;
          for (var i = 0; i < length; i++) {
            binary += String.fromCharCode(bytes[i]);
          }
          var XLSX = require('xlsx');
          if (rABS) {
            wb = XLSX.read(btoa(fixdata(binary)), { // 手动转化
              type: 'base64'
            });
          } else {
            wb = XLSX.read(binary, {
              type: 'binary'
            });
          }
          var outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]); // 表格数据
          this.da = [...outdata]
          // 导入数据处理
          if (this.da && this.da.length > 0) {
            _this.importDataDeal(this.da)
          }
        }
        reader.readAsArrayBuffer(f);
      }
      if (rABS) {
        reader.readAsArrayBuffer(f);
      } else {
        reader.readAsBinaryString(f);
      }
    },

    // 导入数据处理
    importDataDeal(outdata) {
      outdata.forEach((item, index) => {
        const billData = {} // 固定数据在保存时导入
        // 客户姓名	金额 备注
        billData.name = item.客户姓名 // 客户姓名
        billData.amount = item.金额 // 金额
        billData.remark = item.备注 // 备注
        this.importList.push(billData)
      });
      console.log("importList =====", this.importList)
    },

    onSubmit(values) {
      console.log('submit', values);
    },
  }
}
</script>

<style scoped>

</style>