/**
 * @description 登录、获取用户信息、退出登录、清除accessToken逻辑，不建议修改
 */

 import Vue from 'vue'
 import { getUserInfo, login, logout } from '@/api/user'
 import { getAccessToken, removeAccessToken, setAccessToken } from '@/utils/accessToken'
 import { resetRouter } from '@/router'
 import { title, tokenName, operatorType } from '@/config'
 
 const state = () => ({
   accessToken: getAccessToken(),
   username: '',
   avatar: '',
   operatorType: '',
 })
 const getters = {
   accessToken: (state) => state.accessToken,
   username: (state) => state.operatorName,
   avatar: (state) => state.avatar,
   operatorType: (state) => state.operatorType
 }
 const mutations = {
   setAccessToken(state, accessToken) {
     state.accessToken = accessToken
     setAccessToken(accessToken)
   },
   setUsername(state, username) {
     state.username = username
   },
   setAvatar(state, avatar) {
     state.avatar = avatar
   },
   setOperatorType(state, operatorType) {
    state.operatorType = operatorType
  },
 }
 const actions = {
   async login({ commit }, userInfo) {
     const { data } = await login(userInfo)
    //  console.log("user login ", data)
     const accessToken = data[tokenName]
     if (accessToken) {
       commit('setAccessToken', accessToken)
       commit('setOperatorType', data.operator.operatorType)
       //将用户的operatorType存入localstorage
       localStorage.setItem(operatorType, data.operator.operatorType) 
      //  console.log("localStorage ", localStorage)
       const hour = new Date().getHours()
       const thisTime =
         hour < 8
           ? '早上好'
           : hour <= 11
             ? '上午好'
             : hour <= 13
               ? '中午好'
               : hour < 18
                 ? '下午好'
                 : '晚上好'
       Vue.prototype.$baseNotify(`欢迎登录${title}`, `${thisTime}！`)
     } else {
       Vue.prototype.$baseMessage(
         `登录接口异常，未正确返回${tokenName}...`,
         'error'
       )
     }
   },

   async getUserInfo({ commit, state }) {
     const { data } = await getUserInfo(state.accessToken)
     if (!data) {
       Vue.prototype.$baseMessage('验证失败，请重新登录...', 'error')
       return false
     }
     let {operatorName, operatorAccount, avatar, operatorType } = data
     if (operatorAccount && operatorType) {
        commit('setUsername', operatorName)
        commit('setOperatorType', operatorType)
        commit('setAvatar', avatar)
        return true
     } else {
       Vue.prototype.$baseMessage('用户信息获取异常', 'error')
       return false
     }
   },
   async logout({ dispatch }) {
     await logout(state.accessToken)
     await dispatch('resetAccessToken')
     await resetRouter()
     localStorage.removeItem(operatorType)
   },
   resetAccessToken({ commit }) {
     commit('setAccessToken', '')
     removeAccessToken()
     localStorage.removeItem(operatorType)
   },
 }
 export default { state, getters, mutations, actions }
 