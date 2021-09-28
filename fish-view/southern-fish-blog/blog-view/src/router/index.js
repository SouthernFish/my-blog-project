import Vue from 'vue'
import VueRouter from 'vue-router'
import {publicPath, routerMode} from '@/config'

Vue.use(VueRouter)
export const constantRoutes = [
  // 首页
  {
    path: '/',
    alias: '/index',
    name: 'index',
    component: () =>import('@/views/common-authority/index'),
  },
  // 错误页
  {
    path: '/401',
    name: '401',
    component: () =>import('@/views/error/401'),
  },
  {
    path: '/404',
    name: '404',
    component: () =>import('@/views/error/404'),
  },

  // 关于我
  {
    path: '/about',
    name: 'about',
    component: () =>import('@/views/common-authority/aboutContent'),
  },
  // 登录
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/index'),
    hidden: true,
  },
  // 注册
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/login/register'),
    hidden: true,
  },

  // 留言板 放到文章下
  // {
  //   path: '/message/board',
  //   name: 'messageBoard',
  //   component: () => import('@/views/common-authority/messageBoard'),
  //   hidden: true,
  // },

  // 个人中心
  {
    path: '/personal/center',
    name: 'personalCenter',
    component: () => import('@/views/common-authority/personalCenter'),
    hidden: true,
  },

  // 归档  文章时间线
  {
    path: '/article/line',
    name: 'articleLine',
    component: () =>import('@/views/common-authority/articleLine'),
  },
  // 文章详情
  {
    path: '/article/detail',
    name: 'articleDetail',
    component: () =>import('@/views/common-authority/articleDetail'),
  }, 

  // 管理平台
  {
    path: '/plat/login',
    name: 'platLogin',
    component: () => import('@/views/login/platLogin'),
    hidden: true,
  },
  {
    path: '/panel',
    name: 'panel',
    component: () =>import('@/views/admin-authority/blogPanel'),
    meta: {requireAuth: true}
  },
  {
    path: '/article/editor',
    name: 'articleEditor',
    component: () =>import('@/views/admin-authority/articleEditor'),
  }, 
]

export const asyncRoutes = []

const router = new VueRouter({
  base: publicPath,
  mode: routerMode,
  scrollBehavior: () => ({
    y: 0,
  }),
  routes: constantRoutes,
})

// 允许路由重复点击
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) {
    return originalPush.call(this, location, onResolve, onReject)
  }
  return originalPush.call(this, location).catch((err) => err)
}

export function resetRouter() {
  router.matcher = new VueRouter({
    base: publicPath,
    mode: routerMode,
    scrollBehavior: () => ({
      y: 0,
    }),
    routes: constantRoutes,
  }).matcher
}

// beforeEach函数有三个参数：
// to:router即将进入的路由对象 （这个对象中包含name，params，meta等属性）
// from:当前导航即将离开的路由 （这个对象中包含name，params，meta等属性）
// next:Function,进行管道中的一个钩子，如果执行完了，则导航的状态就是 confirmed （确认的）；否则为false，终止导航。
// next(): 进行管道中的下一个钩子。如果全部钩子执行完了，则导航的状态就是 confirmed （确认的）。
// next(false): 中断当前的导航。如果浏览器的 URL 改变了（可能是用户手动或者浏览器后退按钮），那么 URL 地址会重置到 from 路由对应的地址。
// next(’/’) 或者 next({ path: ‘/’ }): 跳转到一个不同的地址。当前的导航被中断，然后 进行一个新的导航。
// next(error): (2.4.0+) 如果传入 next 的参数是一个 Error 实例，则导航会被终止且该错误会被传递给 router.onError() 注册过的回调。


router.beforeEach(function(to, from, next) {
  if (to.meta.requireAuth && !localStorage.getItem("TONG-BLOG-LOGIN-TOKEN")) {
    return next("/plat/login")
  } else {
    return next()
  }
})

export default router
