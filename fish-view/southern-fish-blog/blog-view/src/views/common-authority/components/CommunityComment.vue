<template>
  <div class="comment-region">
    <div class="submit-comment">
      <div class="comment-region-title">发表留言</div>
      <el-input type="textarea" :rows="4" placeholder="说点什么呢......" v-model="commentInfo.commentContent" maxlength="500" show-word-limit></el-input>
      <el-button @click="submitComment">发送</el-button>
    </div>
    <div class="comment-show">
      <div class="comment-region-title">| 近期留言</div>
      <div class="comment-list" v-for="(comment, index) in commentList" :key="index">
        <div class="line-div avatar">
          <el-avatar :src="(comment.avatar == '' || comment.avatar == null) ? avaterUrl : baseImgURL + comment.avatar "></el-avatar>
        </div>
        <div class="line-div">
          <div class="line-div-title">{{comment.fromPersonName}}</div>
          <div class="line-div-content" style="margin-bottom: 10px;">{{comment.commentContent}}</div>
          <div class="line-div-time">
            {{comment.createTime}}
            <div class="oepration-region">
              <el-button v-show="comment.isFabulous == 0" round size="mini" icon="el-icon-thumb"
                @click="fabulousComment(index, null, 'fabulous')">
                  &nbsp;|&nbsp;{{comment.popularity}}
              </el-button>
              <el-button v-show="comment.isFabulous == 1" class="is-fabulous-activated" round size="mini" 
                icon="el-icon-thumb" @click="fabulousComment(index, null, 'no-fabulous')">
                  &nbsp;|&nbsp;{{comment.popularity}}
              </el-button>
              <el-button round size="mini" icon="el-icon-share"></el-button>
              <el-button round size="mini" icon="el-icon-chat-dot-round" @click="replyLaunch(index, 'parent', null)"></el-button>  
            </div>
          </div>
          <div v-if="comment.child" style="margin-top: 10px;">
            <div class="comment-list" v-for="(item, inx) in comment.child" :key="inx">
              <div class="line-div avatar">
                <el-avatar :src="(item.avatar == '' || item.avatar == null) ? avaterUrl : baseImgURL + item.avatar"></el-avatar>
              </div>
              <div class="line-div">
                <div class="line-div-title">{{item.fromPersonName}}<span> 回复 </span>{{item.toPersonName}}</div>
                <div class="line-div-content">{{item.commentContent}}</div>
                <div class="line-div-time">
                  {{item.createTime}}
                  <div class="oepration-region">
                    <el-button v-show="item.isFabulous == 0" round size="mini" icon="el-icon-thumb"
                      @click="fabulousComment(index, inx, 'fabulous')">
                        &nbsp;|&nbsp;{{item.popularity}}
                    </el-button>
                    <el-button v-show="item.isFabulous == 1" class="is-fabulous-activated" round size="mini" 
                      icon="el-icon-thumb" @click="fabulousComment(index, inx, 'no-fabulous')">
                        &nbsp;|&nbsp;{{item.popularity}}
                    </el-button>
                    <el-button round size="mini" icon="el-icon-share"></el-button>
                    <el-button round size="mini" icon="el-icon-chat-dot-round" @click="replyLaunch(index, 'child', inx)"></el-button>  
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-show="comment.isReplyLaunch" class="comment-reply" style="margin-top: 10px;">
            <el-input type="textarea" :rows="4" :placeholder="'回复【' + commentInfo.toPersonName + '】......' " v-model="commentInfo.commentContent" maxlength="500" show-word-limit></el-input>
            <el-button @click="submitReply(index)">发送</el-button>
          </div>
          <div class="child-more-button">
            <el-button v-if="searchVo.childTotal > (searchVo.childPageNum * searchVo.childPageSize)"  @click="moreComment('child', comment.commentId)">查看全部回复</el-button>
          </div>
         </div>
      </div>
      <div style="border-top: 1px solid #E4E7ED; margin-bottom: 20px;"></div>
      <el-button v-if="searchVo.parentPageTotal > (searchVo.parentPageNum * searchVo.parentPageSize)" @click="moreComment('parent', 0)">加载更多...</el-button>
      <el-button v-else>暂无更多留言</el-button>
    </div>
  </div>
</template>

<script>
import * as api from "@/api/comment.js"
import {baseImgURL} from "@/config"
export default {
  name: 'commentPage',
  components: {},
  props: {
    articleInfoId: String
  },
  data() {
    return {
      operatorType: localStorage.getItem('_TONG_OPERATOR_TYPE_'), // 操作员类型
      token: localStorage.getItem('TONG-BLOG-LOGIN-TOKEN'), // 操作员类型
      avaterUrl: "/static/logo/header-avatar.webp",
      baseImgURL: baseImgURL,
      searchVo: {
        selectType: 1, // 1 评论 2 回复
        articleInfoId: this.articleInfoId,
        parentId: 0,
        parentPageNum: 1,
        parentPageSize: 10,
        parentPageTotal: 0,
        childPageNum: 1,
        childPageSize: 10,
        childTotal: 0,
      },
      childPageInfo: [],
      commentInfo: {
        articleInfoId: this.articleInfoId,
        commentContent: null,

        isReply: null,
        toPerson: null,
        parentId: null,
        replyCommentId: null,
        auditStatus: localStorage.getItem('_TONG_OPERATOR_TYPE_') == 1 ? 1 : null
      },
      oldIndex: {
        index: null, // 上次选中坐标 parent
        inx: null, // 上次选中坐标 child
      }, 
      commentList: [],
      fabulousInfo: {
        personalInterestId: null,
        objectType: 2,
        articleInfoId: this.articleInfoId,
        commentId: null,
        isFabulous: null,
      },
    };
    
  },
  watch: {},
  mounted() {},
  created() {
    this.getCommentList()
  },
  methods: {
    // 登录验证：
    checkToken() {
      if (this.token == null || this.token == undefined || this.token == '') {
        this.$message.error('您还没有登录，请先登录');
        return false
      } else {
        return true
      } 
    },

    // 发起留言
    submitComment() {
      if (this.checkToken()) {
          if (!this.commentInfo.commentContent) {
          this.$message.error('您还没有输入留言内容，请先输入');
        }
        this.commentInfo.articleInfoId = this.articleInfoId
        this.saveCommentInfo(this.commentInfo)
      }
    },

    // 加载更多
    moreComment(type, parentId) {
      if (type === 'parent') {
        this.searchVo.parentPageNum = this.searchVo.parentPageNum + 1
        this.searchVo.selectType = 1
      }
      if (type === 'child') {
        this.searchVo.childPageSize = 9999
        this.searchVo.selectType = 2
      }
      this.searchVo.parentId = parentId
      this.getCommentList()
    },

    // 发起回复
    replyLaunch(index, type, inx) {
      if (this.checkToken()) {
        // 清空历史数据
        this.commentInfo = this.$options.data().commentInfo
        // 回复数据初始化
        this.commentInfo.articleInfoId = this.articleInfoId
        this.commentInfo.isReply = 1
        this.commentInfo.parentId = this.commentList[index].commentId
        if (type == 'parent') {
          this.commentInfo.toPerson = this.commentList[index].fromPerson
          this.commentInfo.replyCommentId = this.commentList[index].commentId
        }
        if (type == 'child') {
          const child = this.commentList[index].child
          this.commentInfo.toPerson = child[inx].fromPerson
          this.commentInfo.replyCommentId = child[inx].commentId
        }

        // 关闭/开启留言框
        if (this.oldIndex.index != null && this.oldIndex.index != index) {
          this.commentList[this.oldIndex.index].isReplyLaunch = false
          this.commentList[index].isReplyLaunch = true
        } else if (this.oldIndex.inx == inx && this.oldIndex.index == index) {
          this.commentList[index].isReplyLaunch = !this.commentList[index].isReplyLaunch 
        } else {
          this.commentList[index].isReplyLaunch = true
        }   
    
        // 记录上次坐标    
        this.oldIndex.index = index 
        this.oldIndex.inx = inx         
      }
    },

    // 保存回复
    submitReply(index) {
       if (!this.commentInfo.commentContent) {
        this.$message.error('您还没有输入留言内容，请先输入');
      }
      this.commentList[index].isReplyLaunch = false
      this.saveCommentInfo(this.commentInfo)
      this.searchVo = this.$options.data().searchVo
      this.searchVo.articleInfoId = this.articleInfoId
    },

    // 留言列表
    getCommentList() {
      api.getCommentList(this.searchVo).then( res => {
        if (res.code == 200) {
          if (this.searchVo.selectType === 1) {
            this.searchVo.parentPageTotal = res.data.total
            this.commentList.push.apply(this.commentList, res.data.list)
          }
           if (this.searchVo.selectType === 2) {
            let inx = this.commentList.findIndex(val => val.commentId == this.searchVo.parentId)
            this.commentList[inx].child.push.apply(this.commentList[inx].child, res.data.list)
          }
        } 
      }) 

    },

    // 保存留言
    saveCommentInfo(params) {
      const operatorType = localStorage.getItem('_TONG_OPERATOR_TYPE_')
      const msg = (operatorType == 1) ? '留言成功！' : '留言成功，等待管理员审核！'
      api.saveCommentInfo(params).then( res => {
        if (res.code == 200) {
          this.$message({
            message: msg,
            type: 'success'
          });
          // 清空数据
          // this.commentList.splice(0, this.commentList.length)
          // 清空历史数据
          this.commentInfo = this.$options.data().commentInfo
          this.commentList = this.$options.data().commentList
          if (operatorType == 1) {
             this.getCommentList()
          }
        } 
      }) 
    },

    // 点赞/取消点赞
    fabulousComment(index, inx, type) {
      if (this.checkToken()) {
        const msg = (type == 'fabulous') ? '点赞成功' : '已取消点赞'
        const isFabulous = (type == 'fabulous') ? '1' : '0'
        const step = (type == 'fabulous') ? 1 : -1
        // 参数
        this.fabulousInfo.isFabulous = isFabulous
        if (inx == null) {
          this.fabulousInfo.commentId = this.commentList[index].commentId
          this.fabulousInfo.personalInterestId = this.commentList[index].personalInterestId
        } else {
          this.fabulousInfo.commentId = this.commentList[index].child[inx].commentId
          this.fabulousInfo.personalInterestId = this.commentList[index].child[inx].personalInterestId
        }
        // 保存
        this.savePersonalInterest(index, inx, msg)
        // 样式
        if (inx == null) {
          this.commentList[index].isFabulous = isFabulous
          this.commentList[index].popularity = this.commentList[index].popularity + step
        } else {
          this.commentList[index].child[inx].isFabulous = isFabulous
          this.commentList[index].child[inx].popularity = this.commentList[index].child[inx].popularity + step
        }        
      }
    },

    // 保存点赞信息
    savePersonalInterest(index, inx, msg) {
      api.savePersonalInterest(this.fabulousInfo).then(res => {
        if (res.code == 200) {
          this.$message({
            message: msg,
            type: 'success'
          });
          // 点赞ID
          if (inx == null) {
            this.commentList[index].personalInterestId = res.data.personalInterestId
          } else {
            this.commentList[index].child[inx].personalInterestId = res.data.personalInterestId
          } 
        }
      })
    },

  },
}
</script>

<style scoped>
  .comment-region {  
    min-height: 400px;
    border-radius: 20px;
    background-color: white;
    /* opacity: 0.8;  */
    padding-bottom: 20px;
    margin: 40px 0px;
  }

  .comment-region-title {
    font-size: 1.2em; 
    font-weight: bold; 
    margin-bottom: 10px; 
  }

  ::v-deep .el-textarea__inner {
    font-size: 1.2em;
  }

  .submit-comment, .comment-show {
    padding: 20px;
  }

  .submit-comment .el-input {
    background-color: #F1F1F1;
    border: 1px solid #F1F1F1;
    cursor: no-drop;
    box-shadow: 40px 40px 40px 40px #F1F1F1 inset;
  }

  .comment-list {
    display: flex;
    padding: 10px 0px;
    border-top: 1px solid #E4E7ED;
  }

  .comment-list .avatar .el-avatar {
    height: 60px; 
    width: 60px; 
  }
  .comment-list .avatar {
    width: 10%;
  }

  .line-div {
    display: inline-block;
    width: 90%;
  }

  .line-div-title {
    font-size: 1.0em; 
    color: #91e5fa;
    margin-bottom: 5px; 
  }

  ::v-deep .line-div-title span {
    color: black;
  }

  .line-div-time {
    font-size: 0.8em; 
    position: relative; 
  }

  .line-div-content {
    font-size: 0.9em;
    margin-bottom: 10px; 
  }

  .oepration-region {
    position: absolute;
    right: 0px;
    bottom: -5px;
  }

  /* 点击不变色 */
  .oepration-region .el-button:focus {
    background: #FFF;
    border: 1px solid #DCDFE6;
    color: #606266;
  }

  .submit-comment>.el-button,
  .comment-show>.el-button,
  .child-more-button>.el-button {
    width: 100%;
    margin-top: 10px;
    background-color: #91e5fa; 
    border-color: #91e5fa;
    color: white; 
  }

  .submit-comment>.el-button:hover, 
  .comment-show>.el-button:hover,
  .child-more-button>.el-button:hover 
  {
    background-color: #23a4c4;
    border-color: #23a4c4; 
  }

  .comment-reply>.el-button {
    margin-top: 10px;
    background-color: #23a4c4;
    border-color: #23a4c4; 
    color: white; 
  }

  .comment-reply>.el-button:hover {
    background-color: #91e5fa; 
    border-color: #91e5fa;
  }

  .is-fabulous-activated {
    background-color: #e66108; 
    border-color: #e66108;
    color: white;
  }
  
</style>
