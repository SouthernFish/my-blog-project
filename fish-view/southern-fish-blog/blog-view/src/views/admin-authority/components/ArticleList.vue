<template>
  <div>
    <div class="top-search" v-show="tabType == 1">
      <el-form :inline="true" ref="searchVO" :model="searchVO">
        <el-form-item label="分类">
          <el-select v-model="searchVO.categoryInfoId" filterable placeholder="请选择/输入分类名称">
            <el-option v-for="item in categoryList" :key="item.categoryInfoId"
                        :label="item.categoryInfoName"
                        :value="item.categoryInfoId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="searchVO.tagInfoId" filterable placeholder="请选择/输入标签名称">
            <el-option v-for="item in tagList" :key="item.tagInfoId"
                        :label="item.tagInfoName"
                        :value="item.tagInfoId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文章信息">
          <el-input v-model="searchVO.searchContent" clearable style="width: 250px" placeholder="请输入文章标题"></el-input>
        </el-form-item>
        <el-form-item style="text-align: center">
          <el-button type="primary" icon="el-icon-search" @click="searchArticle">查询</el-button>
          <el-button type="primary" icon="el-icon-refresh-left" @click="restSearchForm">重置</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="addArticle">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div v-show="tabType == 2" >
      <el-button type="primary" style="float: right; margin-bottom: 10px;" icon="el-icon-delete" @click="deleteAll">清空回收站</el-button>
    </div>
    <!-- <el-divider></el-divider> -->
    <div class="table-region">
      <el-table v-loading :data="articleList" highlight-current-row @current-change="handleCurrentRow" border :height="tabType == 1 ? '320' : '330' ">
        <el-table-column prop="articleInfoId" label="文章ID" align="center" width="70px"></el-table-column>
        <el-table-column prop="articleTitle" label="文章标题" align="center" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="description" label="文章描述" align="center" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="categoryInfoName" label="文章分类" align="center" width="120px"></el-table-column>
        <el-table-column prop="tagInfoName" label="文章标签" align="center" width="120px"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center" width="140px" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="操作" align="center" width="140px" v-if="tabType == 1">
          <template slot-scope="scope">
            <el-button type="text" @click="jumpToArticle(scope.row, 'detail')">查看</el-button>
            <el-button type="text" @click="jumpToArticle(scope.row, 'edit')">编辑</el-button>
            <el-button type="text" @click="deleteArticle(scope.row)">删除</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="180px" v-if="tabType == 2">
          <template slot-scope="scope">
            <el-button type="text" @click="jumpToArticle(scope.row, 'detail')">查看</el-button>
            <el-button type="text" @click="restoreArticle(scope.row)">还原</el-button>
            <el-button type="text" @click="deleteAbsolute(scope.row)">彻底删除</el-button>
          </template>
        </el-table-column>
      </el-table>      
    </div>
    <!-- 分页区域 -->
    <div class="pageRegion">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="pageData.pageNum" :page-sizes="[10, 20, 50]" :page-size="10"
                     :total="pageData.total"
                     layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </div>
  </div>
</template>
<script>
import * as api from "@/api/article.js"
import {seniorOperatorId} from "@/config"
export default {
  name: 'ArticleList',
  components: {},
  props: {
    tabType: Number
  },
  data() {
    return {
      categoryList: [],
      tagList: [],
      articleList: [],
      pageTotal: null,  
      searchVO: {
        categoryInfoId: null,
        tagInfoId: null,
        searchText: null        
      },      
      // 分页参数
      pageData: {
        total: 0, //总条数
        pageNum: 1, // 页码
        pageSize: 10, //每页显示多少数据
      },
    };  
  },
  watch: {
    // 分类筛选
    'searchVO.categoryInfoId': {
      handler() {
        this.getTagList(this.searchVO.categoryInfoId)
      },
    },
  },
  mounted() {},
  created() {
    this.getCategoryList()
    this.getTagList()
    this.getArticleList()
  },
  methods: {
    // 搜索文章
    searchArticle() {
      this.getArticleList()
    },

    // 获取分类
    getCategoryList() {
      api.getCategoryList({
        operatorId: seniorOperatorId,
      }).then(res => {
        if (res.code == 200) {
          this.categoryList = res.data  
        }
      })
    },

    // 获取标签
    getTagList(categoryInfoId) {
      api.getTagList({
        categoryInfoId: categoryInfoId,
        operatorId: seniorOperatorId,
      }).then(res => {
        if (res.code == 200) {
          this.tagList = res.data  
        }
      })
    },    

    // 搜索参数
    createSearchParams() {
      let params = Object.assign(this.searchVO, this.pageData);
      params.tabType = this.tabType
      params.position = 1
      return params;
    },

    // 当前每页数据条数
    handleSizeChange(val) {
      this.pageData.pageSize = val
      this.getArticleList()
    },

    // 当前页
    handleCurrentChange(val) {
      this.pageData.pageNum = val
      this.getArticleList()
    },

    //重置查询form
    restSearchForm() {
      this.$refs['searchVO'].resetFields() // 不生效
      this.searchVO.categoryInfoId = null
      this.searchVO.tagInfoId = null
      this.searchVO.searchText = null
      this.getArticleList()
    },

    // 文章列表
    getArticleList() {
      let params = this.createSearchParams()
      api.getArticleList(params).then(res => {
        if (res.code == 200) {
          this.pageData.total  = res.data.total
          this.articleList = res.data.list
        }
      })
    },

    // 选中行
    handleCurrentRow(val) {
      this.currentRow = val;
    },

    // 新增文章
    addArticle() {
      this.$router.push("/article/editor");
    },

    // 跳转详情/编辑
    jumpToArticle(row, type) {
      let routerPath = ( type == 'detail') ? "/article/detail" : ( type == 'edit') ? "/article/editor" : null
      this.$router.push({
        path: routerPath,
        query: {
          articleInfoId: row.articleInfoId,
        },
      });
    },

    // 删除文章
    deleteArticle(row) {
      api.saveArticleInfo({
        articleInfoId: row.articleInfoId,
        operationType: 3, // 删除
      }).then(res => {
        if (res.code == 200) {
          this.$message({
            title: "提示",
            message: `文章《${row.articleTitle}》删除成功`,
            type: "success",
          });
          this.getArticleList()
        }
      });
    },



    // 从回收站还原
    restoreArticle(row) {
      api.saveArticleInfo({
        articleInfoId: row.articleInfoId,
        operationType: 2, // 还原 编辑
        delFlag: 1,
      }).then(res => {
        if (res.code == 200) {
          this.$message({
            title: "提示",
            message: `文章《${row.articleTitle}》还原成功`,
            type: "success",
          });
          this.getArticleList()
        }
      });
    },

    // 清空回收站
    deleteAll() {
      console.log("从服务器删除所有")
        this.$message({
        message: '操作敏感，暂未开发',
        type: 'warning'
      });
    },

    // 彻底删除
    deleteAbsolute(row) {
      console.log("从服务器删除", row)
      this.$message({
        message: '操作敏感，暂未开发',
        type: 'warning'
      });
    },
  },
}
</script>

<style scoped>
  .el-divider {
    margin-top: 10px;
  }
  
  .pageRegion {
    text-align: center;
    margin-top: 10px;
  }
</style>
