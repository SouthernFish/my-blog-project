import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import './plugins'


Vue.config.productionTip = false
Vue.config.devtools = true

window.alert=function (msg,type){
  if(!type || type==='error') {
    Vue.prototype.$baseMessage(msg, 'error')
  }else if(type==='success'){
    Vue.prototype.$baseNotify(msg ? msg : '操作成功！')
  }
}

new Vue({
  el: '#appId',
  router,
  store,
  render: h => h(App)
})
