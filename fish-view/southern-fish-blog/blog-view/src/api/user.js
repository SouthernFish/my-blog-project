import request from '@/utils/request'
import { get, post } from '@/utils/request'
import { encryptedData } from '@/utils/encrypt'
import { loginRSA, tokenName } from '@/config'

export async function login(data) {
  if (loginRSA) {
    data = await encryptedData(data)
  }
  return request({
    url: 'blog/operator/login',
    method: 'post',
    data,
  })
}

export function getUserInfo(accessToken) {
  return request({
    url: 'blog/operator/cache/operator',
    method: 'get',
    data: {
      [tokenName]: accessToken,
    },
  })
}

export function logout() {
  return request({
    url: 'blog/operator/loginout',
    method: 'post',
  })
}

export function register(data) {
  return request({
    url: 'blog/operator/register',
    method: 'post',
    data,
  })
}
// 根据token获取用户信息
export const getUserInfoByToken = p => get('/blog/operator/cache/operator', p);
// 根据根据ID获取用户信息
export const getUserInfoById = p => get('/blog/operator/userinfo', p);
// 修改密码
export const modifyPassword = p => post('blog/operator/modify/password', p);
// 修改登录用户基本信息
export const modifyUserInfo = p => post('blog/operator/userinfo/modify', p);
// 增加人气值
export const addPopularity = p => post('blog/operator/add/popularity', p)