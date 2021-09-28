<template>
  <div class="collection-content">
    <div class="table-region">
      <el-table v-loading :data="collectionList" highlight-current-row @current-change="handleCurrentRow" border max-height="500">
        <el-table-column prop="articleTitle" label="文章标题" align="center" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="categoryInfoName" label="文章分类" align="center"></el-table-column>
        <el-table-column prop="tagInfoName" label="文章标签" align="center"></el-table-column>
        <el-table-column prop="popularity" label="人气值" align="center"></el-table-column>
        <el-table-column prop="collection" label="收藏值" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="140px">
          <template slot-scope="scope">
            <el-button type="text" @click="jumpToArticle(scope.row)">查看</el-button>
            <el-button type="text" @click="abandonCollection(scope.row, 'detail')">取消收藏</el-button>
          </template>
        </el-table-column>
      </el-table>      
    </div>
    <!-- 分页区域 -->
    <div class="pageRegion text-center" style="margin-top: 10px;">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="pageData.pageNum" :page-sizes="[10, 20, 50]" :page-size="10"
                    :total="pageData.total"
                    layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import * as api from "@/api/comment.js"
export default {
  name: 'CollectionInfo',
  components: {},
  data() {
    return {
      // 分页参数
      pageData: {
        total: 0, //总条数
        pageNum: 1, // 页码
        pageSize: 10, //每页显示多少数据
      },
      collectionList: [],
    };
  },
  created() {
    this.getCollectionList()
  },
  methods: {
    // 选中行
    handleCurrentRow(val) {
      this.currentRow = val;
    },

    // 当前每页数据条数
    handleSizeChange(val) {
      this.pageData.pageSize = val
      this.getCollectionList()
    },

    // 当前页
    handleCurrentChange(val) {
      this.pageData.pageNum = val
      this.getCollectionList()
    },

    // 收藏列表
    getCollectionList() {
      api.getPersonalCollection({
        pageNum: this.pageData.pageNum,
        pageSize: this.pageData.pageSize
      }).then(res => {
        if (res.code == 200) {
          this.pageData.pageTotal = res.data.total
          this.collectionList = res.data.list
        }
      })
    },

    // 跳转详情
    jumpToArticle() {
      this.$router.push({
        path: '/article/detail',
        query: {
          articleInfoId: row.articleInfoId,
        },
      });
    },

    // 取消收藏
    abandonCollection(row) {
      api.savePersonalInterest({
        personalInterestId: row.personalInterestId,
        isCollection: '0',
      }).then(res => {
        if (res.code == 200) {
          this.$message({
            type: 'success',
            message: `《${row.articleTitle}》取消收藏成功`
          });
          this.getCollectionList()
        }
      })
    },


  },
}
</script>

<style scoped>
  .table-region {
    margin: 0px 20px;
  }
</style>
