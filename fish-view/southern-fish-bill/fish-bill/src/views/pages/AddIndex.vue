<template>
  <div class="main-container">
    <van-tabs v-model="activeName">
      <van-tab title="支出" name="out">
        <!-- 金额 -->
        <div class="div-cell">
          <van-cell-group>
            <van-field v-model="params.amount" type="number" input-align="right"
              center error placeholder="请输入金额" size="large" />
          </van-cell-group>
        </div>
        <!-- 选择标签  square-->
        <div class="div-cell">
          <van-grid :column-num="4">
            <van-grid-item v-for="item in tagArr" clickable @click="tagSelect(item)"
              :key="item.tagId" :icon="item.icon" :text="item.tagName" 
              :style=" item.tagId == params.tagId ? {'background-color': 'blue'} : '' "
              />
          </van-grid>          
        </div>
        <!-- 日期 -->        
        <div class="div-cell">
          <van-button style="width: 80%;" round type="info" @click="calendarShow = true">请选择日期</van-button>
          <van-calendar v-model="calendarShow" color="#1989fa" 
            :show-confirm="false" @confirm="onConfirm" 
            :show-title="false" :row-height="32" :style="{height: '270px'}"/>
        </div>
        <!-- 备注 -->
        <div class="div-cell">
          <van-cell-group>
            <van-field v-model="params.remark" label="备注" label-width="3em" clearable colon  placeholder="" size="large" />
          </van-cell-group>
        </div>
        <!-- 购物清单 自己添加的操作 -->
        <div class="div-cell" style="text-align: left;">
          <van-checkbox v-model="listChecked" shape="round" style="margin-left: 4%;" >附件清单</van-checkbox>
          <div class="bill-list" v-if="listChecked" style="width: 92%; margin-left: 4%; padding-top: 10px;">
              <div class="bill-cell" v-for="(item, index) in params.list" :key="item.name">
                <van-row>
                  <van-col span="22">
                    <van-cell-group>
                      <van-field v-model="item.name" clearable label="名称" label-width="3em" />
                    </van-cell-group>
                  </van-col>
                  <van-col span="2">
                    <van-badge color="#1989fa" :content="(index + 1)" />
                  </van-col>
                </van-row>
                <van-row>
                  <van-col span="11">
                    <van-cell-group>
                      <van-field v-model="item.num" type="digit" clearable label="数量" label-width="3em" />
                    </van-cell-group>
                  </van-col>
                  <van-col span="11">
                    <van-cell-group>
                      <van-field v-model="item.price" type="number" clearable label="价格" label-width="3em" />
                    </van-cell-group>
                  </van-col>
                  <van-col span="2">
                    <van-button type="danger" @click="deleteBillList(index)">-</van-button>
                  </van-col>
                </van-row>
              </div>
              <div style="text-align: center; margin-top: 10px;">
                <van-icon name="fire-o" color="#ee0a24" size="25" @click="addBillList" />
              </div>
          </div>
        </div>
      </van-tab>

      <!-- 收入 -->
      <van-tab title="收入" name="into">
        <!-- 金额 -->
        <div class="div-cell">
          <van-cell-group>
            <van-field v-model="params.amount" type="number" input-align="right"
              center error placeholder="请输入金额" size="large" />
          </van-cell-group>
        </div>

        <!-- 类型 -->        
        <div class="div-cell">
           <van-grid :column-num="4">
             <van-grid-item v-for="item in typeArr" clickable @click="incomeType(item)"
              :key="item.typeId" :icon="item.icon" :text="item.typeName" 
              :style=" item.typeId == params.typeId ? {'background-color': 'blue'} : '' "
              />
          </van-grid>  
        </div>

        <!-- 日期 -->        
        <div class="div-cell">
          <van-button style="width: 80%;" round type="info" @click="calendarShow = true">请选择日期</van-button>
          <van-calendar v-model="calendarShow" color="#1989fa" 
            :show-confirm="false" @confirm="onConfirm" 
            :show-title="false" :row-height="32" :style="{height: '270px'}"/>
        </div>

        <!-- 备注 -->
        <div class="div-cell">
          <van-cell-group>
            <van-field v-model="params.remark" label="备注" label-width="3em" clearable colon placeholder="" size="large" />
          </van-cell-group>
        </div>
      </van-tab>
    </van-tabs>
    <!-- 保存 按钮-->
    <div class="button-region">
      <van-button style="width: 25%; margin-right: 10%;" round type="info" @click="dataConfirm">确认</van-button>
      <van-button style="width: 25%;" round type="info" @click="dataReset">重置</van-button>
    </div>  
    </div>
</template>

<script>
export default {
  name: "AddIndex",
  props: {},
  data() {
    return {
      activeName: "out",
      calendarShow: false,
      tagArr: [
        {tagId: 1, tagName: '餐食', tagIcon: 'photo-o'},
        {tagId: 2, tagName: '零食', tagIcon: 'photo-o'},
        {tagId: 3, tagName: '衣服', tagIcon: 'photo-o'},
        {tagId: 4, tagName: '交通', tagIcon: 'photo-o'},
        {tagId: 5, tagName: '生活', tagIcon: 'photo-o'},
        {tagId: 6, tagName: '人际', tagIcon: 'photo-o'},
      ],
      typeArr: [
        {typeId: 1, typeName: '工资', typeIcon: 'photo-o'},
        {typeId: 2, typeName: '转账', typeIcon: 'photo-o'},
        {typeId: 3, typeName: '红包', typeIcon: 'photo-o'},
        {typeId: 4, typeName: '外快', typeIcon: 'photo-o'},
        {typeId: 5, typeName: 'type5', typeIcon: 'photo-o'},
        {typeId: 6, typeName: 'type6', typeIcon: 'photo-o'},
      ],
      listChecked: false,
      activeIcon: 'https://img01.yzcdn.cn/vant/user-active.png',
      inactiveIcon: 'https://img01.yzcdn.cn/vant/user-inactive.png',
      // 参数
      params: {
        billType: '',
        amount: undefined,
        tagId: undefined,
        list: [
          {name: undefined, price: undefined, num: undefined}
        ],
        date: undefined,
        remark: undefined,
        typeId: undefined,
      },
    };
  },
  setup() {},
  created() {
    this.getOutTagArr() 
    this.getInTypeArr()
  },
  methods: {

    getOutTagArr() {

    },

    getInTypeArr() {

    },

    // 标签
    tagSelect(tag) {
      this.params.tagId = tag.tagId
      console.log(this.tagId)
    },
    // 日期
    formatDate(date) {
      return `${date.getMonth() + 1}/${date.getDate()}`;
    },
    onConfirm(date) {
      this.calendarShow = false;
      this.params.date = this.formatDate(date);
      console.log(this.data)
    },

    // 收入类型
    incomeType(type) {
      this.params.typeId = type.typeId
      console.log(this.typeId)
    },

    // 提交
    dataConfirm() {
      this.params.billType = this.activeName

    },

    // 重置
    dataReset() {
      this.params = this.$options.data()
    },

    // 动态添加清单
    addBillList() {
      var temp = {}
      temp.name = undefined
      temp.price = undefined
      temp.num = undefined
      this.params.list.push(temp)  
    },

    // 删除某个
    deleteBillList(index) {
      this.params.list.splice(index, 1)
      console.log(index)
    }
  }
}
</script>

<style scoped>
  .div-cell {
    margin-bottom: 20px;
  }

  .button-region {
    margin-top: 10px;
  }

  /* .bill-list .bill-cell {
    margin-top: 10px;
  } */

</style>