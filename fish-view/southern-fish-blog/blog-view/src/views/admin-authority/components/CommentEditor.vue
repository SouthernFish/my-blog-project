<template>
  <div class="comment-Info">
    <div class="table-region">
      <el-table v-loading :data="commentList" highlight-current-row @current-change="handleCurrentRow" border  height="380">
        <el-table-column prop="operatorName" label="留言人" align="center" width="140px"></el-table-column>
        <el-table-column prop="articleTitle" label="文章标题" align="center"></el-table-column>
        <el-table-column prop="commentContent" label="留言内容" align="center" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="popularity" label="人气值" align="center"></el-table-column>
        <el-table-column prop="replyCount" label="回复数" align="center"></el-table-column>
        <el-table-column prop="createTime" label="留言时间" align="center" width="140px"></el-table-column>
        <el-table-column label="操作" align="center" width="140px">
          <template slot-scope="scope">
            <!-- <el-button type="text">查看</el-button> -->
            <el-button type="text" @click="auditComment(scope.row, 1)">通过</el-button>
            <el-button type="text" @click="auditComment(scope.row, 2)">不通过</el-button>
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
  name: 'CommentEditor',
  components: {},
  data() {
    return {
      commentList: [],
      // 分页参数
      pageData: {
        total: 0, //总条数
        pageNum: 1, // 页码
        pageSize: 10, //每页显示多少数据
      },
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

    // 留言待审核列表
    getCommentList() {
      api.getPersonalComment({
        pageNum: this.pageData.pageNum,
        pageSize: this.pageData.pageSize,
        position: 1,
      }).then(res => {
        if (res.code == 200) {
          this.pageData.total = res.data.total
          this.commentList = res.data.list
        }
      })
    },

    // 审核评论
    auditComment(row, type) {
      const msg = (type == 1) ? '审核通过' : '审核不通过'
      api.saveCommentInfo({
        commentId: row.commentId,
        auditStatus: type
      }).then(res => {
        if (res.code == 200) {
          this.$message({
            type: 'success',
            message: msg
          });
          this.getCommentList()
        }
      })
    }
  },
}
</script>

<style scoped>

</style>
