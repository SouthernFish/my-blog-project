import { get, post } from '@/utils/request'
import request from '@/utils/request'

// 获取目录信息
export const getCategoryList = p => get('/blog/log/category/list', p);
// 保存目录信息
export const saveCategoryInfo = p => post('/blog/log/category/save', p);

// 获取标签信息
export const getTagList = p => get('/blog/log/tag/list', p);
// 保存标签信息
export const saveTagInfo = p => post('/blog/log/tag/save', p);

// 获取文章信息
export const getArticleList = p => get('/blog/log/article/list', p);
// 获取文章信息
export const getArticleInfo = p => get('/blog/log/article/info', p);
// 保存文章信息
export const saveArticleInfo = p => post('/blog/log/article/save', p);
// 文章统计数据
export const getStatistics = p => get('/blog/log/article/statistics', p)
// 文章时间线列表
export const getLineList = p => get('/blog/log/article/line', p)
// 保存删除的图片信息
export const saveDeleteImageInfo = p => post('/blog/log/delete/image', p);



