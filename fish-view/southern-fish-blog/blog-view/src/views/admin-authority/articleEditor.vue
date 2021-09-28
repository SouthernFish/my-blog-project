<template>
  <div class="page-container">
    <!-- 中部区域 -->
    <div class="editor-region">
      <!-- 顶部区域 -->
      <div class="title-box">
        <input type="text" class="title" v-model="article.articleTitle" placeholder="请输入标题..." />
        <el-popover placement="bottom" width="700" trigger="click">
          <!-- 激活按钮 -->
          <el-button slot="reference" @click="openArticleProperty()">文章属性</el-button>
          <!-- 内容 -->
          <div class="property-title">发布文章：{{article.articleTitle}}</div>
          <div class="property-prop">
            <el-form label-position="left" label-width="90px">
              <el-form-item label="所属分类：">
                <el-tag v-for="(category, index) in categoryList" :key="index" 
                  :class="category.activated ? 'el-tag-activated': '' " 
                  @click="selectCategoryOrTag('category', index)">
                  {{category.categoryInfoName}}
                </el-tag>
              </el-form-item>

              <el-form-item label="所属标签：" style="width:fit-content" v-if="tagAlive">
                <el-tag v-for="(tag, index) in tagList" :key="index" 
                  :class="tag.activated ? 'el-tag-activated': '' "
                  @click="selectCategoryOrTag('tag', index)">
                  {{tag.tagInfoName}}
                </el-tag>
              </el-form-item>

              <el-form-item label="文章摘要：">
                <el-input type="textarea" v-model="article.description" rows="4" maxlength="500" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="文章封面：">
                <el-upload
                  class="avatar-uploader"
                  :action="uploadUrl"
                  :show-file-list="false"
                  :on-success="handleCoverSuccess">
                  <el-image v-if="articleCoverImgUrl" :src="articleCoverImgUrl" class="avatar article-cover"></el-image>
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </el-form-item>
              <el-form-item style="text-algin: center;">
                <el-button style="margin-left: 30%;" @click="handleArticleCancel">取 消</el-button>
                <el-button type="primary" @click="handleArticleSubmit">发布</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-popover>
        <el-button style="float: right;" icon="el-icon-menu" @click="backPanel">面板</el-button>
      </div>

      <!-- 文章编辑 @imgDel="$imgDel" -->
      <!-- <mavon-editor ref="md" v-model="article.articleContent" :options="mavonOption" class="editor" />  -->
      <mavon-editor ref="md" v-model="article.articleContent" @imgAdd="$imgAdd" class="editor" /> 

      <!-- 回到顶部 -->
      <back-top />
    </div>
    <!-- 底部区域 -->
    <bottom-region />
  </div>
</template>

<script>

import BottomRegion from '../components/bottomRegion.vue'
import BackTop from '@/views/components/backTop.vue'
import * as api from "@/api/article.js"
import { baseImgURL, baseURL, seniorOperatorId } from "@/config"
import {getAccessToken } from '@/utils/accessToken'
import axios from 'axios'

import mavonConfig from "@/config/mavon-config.js"; 
export default {
  name: 'articleEditor',
  components: {BottomRegion, BackTop},
  data() {
    return {
      uploadUrl: baseURL + "/blog/upload/file/byftp?token=" + getAccessToken(),   //图片上传拼接 url
      articleDialog: false,
      tagAlive: true,
      mavonOption: mavonConfig,
      articleCoverImgUrl: null,
      article: {
        articleInfoId: null,
        articleTitle: null,
        articleContent: null,
        articleUrl: null,
        description: null,
        categoryInfoId: null,
        tagInfoId: null,
        operationType: 1, // 1 新增 2 编辑 3 删除
      },
      categoryList: [],
      tagList: [],
      imagepaths: null,
    };
    
  },
  watch: {},
  mounted() {},
  created() {
    if (this.$route.query.articleInfoId) {
      this.article.articleInfoId = this.$route.query.articleInfoId
      this.article.operationType = 2 // 编辑
      this.getArticleInfo()
    }
  },
  methods: {
    // 绑定@imgAdd event
    $imgAdd(pos, $file) {
      // 第一步 将图片上传到服务器 formdata.get("file")
      var formdata = new FormData()
      formdata.append('file', $file)
      // 上传
      axios({
        url: baseURL + '/blog/upload/file/byftp',
        method: 'post',
        data: formdata,
        headers: {'Content-Type': 'multipart/form-data;'}
      }).then(res => {
        // 第二步 将返回的url替换到文本原位置![...](0) -> ![...](url)
        if (res.data.code === 200) {
          let url = baseImgURL + res.data.data;
          this.$refs.md.$img2Url(pos, url);
        } else {
          this.$message.error("上传失败！")
        } 
      });
    },

    // 删除图片
    $imgDel(pos) {
      if (this.imagepaths == null) {
        this.imagepaths = pos[0] + ","
      } else {
        this.imagepaths = this.imagepaths + pos[0] + ","
      }
    },

    // 获取文章内容
    getArticleInfo() {
      api.getArticleInfo({
        articleInfoId: parseInt(this.article.articleInfoId)
      }).then(res => {
        if (res.code == 200) {
          this.article = res.data
          this.articleCoverImgUrl = baseImgURL + this.article.coverImgUrl
          this.article.operationType = 2 // 修改
        }
      })
    },

    // 获取分类
    getCategoryList() {
      api.getCategoryList({
        operatorId: seniorOperatorId,
      }).then(res => {
        if (res.code == 200) {
          this.categoryList = res.data  
          if (this.categoryList != null && this.categoryList.length > 0) {
            this.setCategoryList() 
          }
        }
      })
    },
    // 初始化分类
    setCategoryList() {
      this.categoryList.forEach(element => {
        element.activated = false
      });
      console.log(this.article.categoryInfoId)
      if (this.article.categoryInfoId == null) {
        this.categoryList[0].activated = true
        this.article.categoryInfoId = this.categoryList[0].categoryInfoId
      } else {
        let inx = this.categoryList.findIndex((val) => val.categoryInfoId === this.article.categoryInfoId)
        this.categoryList[inx].activated = true
      } 
      this.getTagList(this.article.categoryInfoId)
    },

    // 获取标签
    getTagList(categoryInfoId) {
      api.getTagList({
        categoryInfoId: categoryInfoId,
        operatorId: seniorOperatorId,
      }).then(res => {
        if (res.code == 200) {
          this.tagList = res.data
          if (this.tagList && this.tagList.length > 0) {
            this.setTagList()
          }
        }
      })
    },
    // 初始化标签
    setTagList() {
      this.tagList.forEach(element => {
        element.activated = false
      });  
      if (this.article.tagInfoId == null) {
        // 默认选中第一个
        this.tagList[0].activated = true
        this.article.tagInfoId = this.tagList[0].tagInfoId
      } else {
        let inx = this.tagList.findIndex((val) => val.tagInfoId === this.article.tagInfoId)
        inx = (inx === -1) ? 0 : inx
        this.tagList[inx].activated = true
        this.article.tagInfoId = this.tagList[inx].tagInfoId
      }
    },

    // 打开文章属性
    openArticleProperty() {
      // 已经开过 就不再请求了
      if (!this.articleDialog) {
        this.getCategoryList()
        this.articleDialog = true
      }
    },

    // 封面上传成功
    handleCoverSuccess(res, file) {
      this.articleCoverImgUrl = baseImgURL + res.data
      this.article.coverImgUrl = res.data
    },

    // 返回首页
    backPanel() {
      if (this.article.articleContent) {
        this.$confirm('文章还未保存，是否确认返回到管理面板?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push("/panel")
        })
      } else {
        this.$router.push("/panel")
      }
    },

    // 选中标签
    selectCategoryOrTag(type, index) {
      if (type === 'category') {
        this.categoryList.forEach(element => {
          element.activated = false
        })
        this.categoryList[index].activated = true
        this.article.categoryInfoId = this.categoryList[index].categoryInfoId
        this.getTagList(this.article.categoryInfoId)
      }

      if (type === 'tag') {
        this.tagList.forEach(element => {
          element.activated = false
        })
        this.tagList[index].activated = true
        this.article.tagInfoId = this.tagList[index].tagInfoId 
        // 重载组件 不加不渲染
        this.tagAlive = false
        this.$nextTick(() => (this.tagAlive = true))
      }
    },
  
    // 取消
    handleArticleCancel() {
      this.$confirm('是否取消保存，点击确认返回到管理面板?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push("/panel")
      })
    },

    // 判空
    assertNotEmpty(element, msg) {
      if (element == null || element == undefined || element == '' || element == "") {
          this.$message.error(msg)
      } else {
        return true
      }
    },

    // 保存文章
    handleArticleSubmit() {
      if (
        this.assertNotEmpty(this.article.articleTitle, "文章标题不能为空") &&
        this.assertNotEmpty(this.article.categoryInfoId, "文章分类不能为空") &&
        this.assertNotEmpty(this.article.tagInfoId, "文章标签不能为空") &&
        this.assertNotEmpty(this.article.description, "文章摘要不能为空") &&
        this.assertNotEmpty(this.article.articleContent, "文章内容不能为空")
      ) {
        this.saveArticleInfo()
        this.articleDialog = false
      }
    },

    // 保存文章
    saveArticleInfo() {
      api.saveArticleInfo(this.article).then(res => {
        if (res.code == 200) {
          this.$message({
            title: "提示",
            message: `文章《${this.article.articleTitle}》发布成功`,
            type: "success",
          });
          this.article.articleInfoId = this.article.articleInfoId == null ? res.data.articleInfoId : this.article.articleInfoId
          // if (this.imagepaths != null && this.imagepaths != "") {
          //   this.saveDeleteImageInfo() // 保存删除图片路径信息
          // }
          this.$router.push({
            path: "/article/detail",
            query: {
              articleInfoId: this.article.articleInfoId,
            },
          });
        }
      })
    },

    // 保存删除的图片信息
    saveDeleteImageInfo() {
      this.imagepaths = this.imagepaths.substring(0, this.imagepaths.length - 1)
      api.saveDeleteImageInfo({
        imagePath: this.imagepaths,
      }).then(res => {
        if (res.code == 200) {
          this.$message.error("200")
        }
      });
    }
  },
}
</script>

<style scoped>
  .editor-region {
    min-height: 100vh;
    padding-top: 50px;
    margin-left: 5%;
    width: 90%;
  }

  .title {
    width: 43%;
    background: #efeff0;
    opacity: 0.5;
    color: #303133;
    text-indent: 0px;
    outline: none;
    font-size: 16px;
    font-weight: bold;

    border: none;
    border-bottom: 1px solid #e0e0e0;
    border-radius: 0;
    line-height: 1;
    overflow: visible;
    margin-bottom: 15px;
    padding: 4px;
  }

  .title:focus {
    border-color: #409eff;
  }

  .el-button {
    margin-left: 20px;
    background-color: #91e5fa; 
    color: white; 
    border-color: #91e5fa;
  }

  .editor {
    height: 650px;
  }

  .el-tag-activated {
    background-color: #91e5fa;
  }

  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }

  .el-upload {
    border: 1px solid #d9d9d9;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader-icon {
    border: 1px solid #d9d9d9;
    font-size: 25px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
  }

  .article-cover {
    width: 150px;
    height: 150px;
  }

  /* 文章属性设置 */
  .property-title, .property-prop{
    width: 90%; 
    margin-top: 10px;
    margin-left: 5%;
  }

  .property-title {
    margin-bottom: 10px;
    font-weight: 600;
    font-size: 1.2em;
  }
  ::v-deep .el-textarea .el-input__count {
    background: none;
    bottom: -5px;
  }
</style>