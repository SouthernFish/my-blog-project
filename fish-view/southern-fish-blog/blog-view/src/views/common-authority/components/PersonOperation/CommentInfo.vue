<template>
  <div class="collection-content">
    <div class="table-region">
      <el-table v-loading :data="commentList" highlight-current-row @current-change="handleCurrentRow" border max-height="500">
        <el-table-column prop="articleTitle" label="文章标题" align="center" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="commentContent" label="留言内容" align="center" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="popularity" label="人气值" align="center"></el-table-column>
        <el-table-column prop="replyCount" label="回复数" align="center"></el-table-column>
        <el-table-column prop="createTime" label="留言时间" align="center" width="140px" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="操作" align="center" width="80px">
          <template slot-scope="scope">
            <!-- <el-button type="text">查看</el-button> -->
            <el-button disabled type="text" @click="deleteComment(scope.row, 'detail')">删除</el-button>
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
      commentList: [],
    };
  },
  created() {
    this.getCommentList()
  },
  methods: {
    // 选中行
    handleCurrentRow(val) {
      this.currentRow = val;
    },

    // 当前每页数据条数
    handleSizeChange(val) {
      this.pageData.pageSize = val
      this.getCommentList()
    },

    // 当前页
    handleCurrentChange(val) {
      this.pageData.pageNum = val
      this.getCommentList()
    },

    // 个人留言列表
    getCommentList() {
      api.getPersonalComment({
        pageNum: this.pageData.pageNum,
        pageSize: this.pageData.pageSize,
        position: 2,
      }).then(res => {
        if (res.code == 200) {
          this.pageData.total = res.data.total
          this.commentList = res.data.list
        }
      })
    },

    // 删除评论
    deleteComment(row) {
      api.saveCommentInfo({
        commentId: row.commentId,
        delFlag: '0',
      }).then(res => {
        if (res.code == 200) {
          this.$message({
            type: 'success',
            message: '留言删除成功'
          });
          this.getCommentList()
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

  /* el-form  label 两端对齐 */
  /* .el-form>>>.el-form-item label:after {
      content: " ";
      display: inline-block;
      width: 100%;
  }

  .el-form>>>.el-form-item__label {
      text-align: justify
  } */

</style>
