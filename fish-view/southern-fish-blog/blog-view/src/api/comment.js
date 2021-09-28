import { get, post } from '@/utils/request'

// 评论列表
export const getCommentList = p => get('/blog/comment/community', p);
// 保存评论
export const saveCommentInfo = p => post('/blog/comment/save', p);

// 保存个人兴趣
export const savePersonalInterest = p => post('/blog/comment/interest/save', p);
// 我的收藏
export const getPersonalCollection = p => get('/blog/comment/personal/collection', p);
// 评论审核和个人评论
export const getPersonalComment = p => get('/blog/comment/personal/comment', p);

