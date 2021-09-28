<template>
  <div class="category-manager">
    <div class="tags-region">
      <div class="line-div">所有分类：</div>
      <div class="line-div">
        <el-tag v-for="(category, index) in categoryList" :key="index" :closable="(category.categoryInfoId == 1) ? false : true"
          :class="category.activated ? 'el-tag-activated': '' "
          @close="categoryOrTagDelete('category', category, index)" 
          @click="selectCategoryOrTag('category', index)">
          {{category.categoryInfoName}}
        </el-tag>
        <el-input class="input-new-tag" size="small" v-if="categoryDialog" 
          v-model="categoryInfoName" ref="saveTagInput"
          @keyup.enter.native="handleCategoryConfirm"
          @blur="categoryDialog = false">
        </el-input>
        <el-button v-else class="button-new-tag" size="small" @click="openDialog('category')" icon="el-icon-plus">新分类</el-button>
      </div>
    </div>

    <div class="tags-region">
      <div class="line-div">【{{tagInfo.categoryInfoName}}】有如下标签：</div>
      <div class="line-div">
        <el-tag v-for="(tag, index) in tagList" :key="index"  :closable=isAddTag
          :class="tag.activated ? 'el-tag-activated': '' "
          @click="selectCategoryOrTag('tag', index)" 
          @close="categoryOrTagDelete('tag', tag, index)" >
          {{tag.tagInfoName}}
        </el-tag>
        <el-button v-show="isAddTag" class="button-new-tag" size="small" @click="openDialog('tag')" icon="el-icon-plus">新标签</el-button>
      </div>
    </div>
    <el-dialog width="40%" :title="'【' + tagInfo.categoryInfoName + '】添加标签'" :visible.sync="tagDialog" append-to-body>
      <el-form label-position="left" label-width="90px">
        <el-form-item label="标签名称：">
          <el-input type="text" v-model="tagInfo.tagInfoName"></el-input>
        </el-form-item>
        <el-form-item label="标签封面：">
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleCoverSuccess">
            <el-image v-if="tagInfoCoverImgUrl" :src="tagInfoCoverImgUrl" class="avatar tag-cover"></el-image>
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="handleTagCancel()">取 消</el-button>
        <el-button type="primary" @click="handleTagSubmit">提 交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import * as api from "@/api/article.js"
import {baseImgURL, baseURL} from "@/config"
import { getAccessToken } from '@/utils/accessToken'
import {seniorOperatorId} from "@/config"
export default {
  name: 'CategoryEditor',
  components: {},
  data() {
    return {
      uploadUrl: baseURL + "/blog/upload/file/byftp?token=" + getAccessToken(),   //图片上传拼接 url
      categoryDialog: false,
      categoryList: [],
      categoryInfoName: null,
      isAddTag: false,
      tagDialog: false,
      tagList: [],
      tagInfoCoverImgUrl: baseImgURL + "/static-img-tong/2021-09-14/1566419848072600_6338.webp",
      tagInfo: {
        tagInfoName: null,
        coverImgUrl: null,
        categoryInfoId: null,
      },

    };
  },
  watch: {},
  mounted() {},
  created() {
    this.getCategoryList()
    this.getTagList(1)
  },
  methods: {
    // 获取分类
    getCategoryList() {
      api.getCategoryList({
        operatorId: seniorOperatorId,
      }).then(res => {
        if (res.code == 200) {
          this.categoryList = res.data  
          // 默认选中第一个
          this.categoryList.forEach(element => {
            element.activated = false
          })
          this.categoryList[0].activated = true
          this.tagInfo.categoryInfoName = this.categoryList[0].categoryInfoName
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

    // 添加分类保存
    handleCategoryConfirm() {
      if (this.assertNotEmpty(this.categoryInfoName, "分类名称不能为空")) {
        api.saveCategoryInfo({
            categoryInfoName: this.categoryInfoName
        }).then(res => {
          if (res.code == 200) {
            this.$message({
              type: 'success',
              message: `分类【${this.categoryInfoName}】添加成功`
            });
            // 重新获取分类
            this.getCategoryList()
            this.categoryDialog = false;
            this.categoryInfoName = null;
          }
        })
      }
    },

    // 选中标签
    selectCategoryOrTag(type, index) {
      if (type === 'category') {
        this.categoryList.forEach(element => {
          element.activated = false
        })
        this.categoryList[index].activated = true
        let categoryInfoId = this.categoryList[index].categoryInfoId
        this.tagInfo.categoryInfoName = this.categoryList[index].categoryInfoName
        this.isAddTag = false
        if (this.categoryList[index].categoryInfoName != '默认') {
          this.tagInfo.categoryInfoId = categoryInfoId
          this.isAddTag = true
        }
        this.getTagList(categoryInfoId)
      }
      if (type === 'tag') {
        this.tagList.forEach(element => {
          element.activated = false
        })
        this.tagList[index].activated = true 
      }
    },

    // 打开弹窗
    openDialog(type) {
      if (type === 'category') {
        this.categoryDialog = true
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      }
      if (type === 'tag') {
        this.tagDialog = true
      }
    },

    // 删除分类或者标签
    categoryOrTagDelete(type, element, index) {
      if (type === 'category') {
        api.saveCategoryInfo({
          categoryInfoId: element.categoryInfoId,
          delFlag: '0',
        }).then(res => {
          if (res.code == 200) {
            this.$message({
              type: 'success',
              message: `分类【${element.categoryInfoName}】删除成功`
            });
            this.categoryList.splice(index, 1)
          }
        })
      }
      if (type === 'tag') {
        api.saveTagInfo({
          tagInfoId: element.tagInfoId,
          delFlag: '0',
        }).then(res => {
          if (res.code == 200) {
            this.$message({
              type: 'success',
              message: `标签【${element.tagInfoName}】删除成功`
            });
            this.tagList.splice(index, 1)
          }
        })
      }
    },

    // 取消保存
    handleTagCancel() {
      this.tagDialog = false
    },

     // 头像上传成功
    handleCoverSuccess(res, file) {
      this.tagInfoCoverImgUrl = baseImgURL + res.data
      this.tagInfo.coverImgUrl = res.data
    },

    // 保存标签
    handleTagSubmit() {
      if (
        this.assertNotEmpty(this.tagInfo.tagInfoName, "标签名称不能为空") &&
        this.assertNotEmpty(this.tagInfo.coverImgUrl, "标签图片不能为空") &&
        this.assertNotEmpty(this.tagInfo.categoryInfoId, "标签分类不能为空")
      ) {
        this.saveTagInfo() 
        this.tagDialog = false
      }
    },

    // 保存标签
    saveTagInfo() {
      api.saveTagInfo(this.tagInfo).then(res => {
        if (res.code == 200) {
          this.$message({
            type: 'success',
            message: `标签【${this.tagInfo.tagInfoName}】添加成功`
          });
          this.getTagList(this.tagInfo.categoryInfoId)
        }
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
  },
}
</script>

<style scoped>
  .el-dialog .el-upload {
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

  .el-dialog .tag-cover {
    height: 150px;
    width: 150px;
  }
  .category-manager {
    margin-top: 10px;
  }

  .tags-region {
    margin-bottom: 20px;
    display: flex;
  }
  .line-div {
    min-width: 80px;
    display: inline-block;
    min-height: 100px;
  }

  .el-tag-activated {
    background-color: #91e5fa;
  }

  .el-tag + .el-tag {
    margin-left: 10px;
  }

  .button-new-tag {
    margin-left: 10px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }

  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  } 

  .el-button {
    margin-left: 20px;
    background-color: #91e5fa; 
    color: white; 
    border-color: #91e5fa;
  }
</style>
