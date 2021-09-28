/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description all模式渲染后端返回路由
 * @param constantRoutes
 * @returns {*}
 */
import fa from "element-ui/src/locale/lang/fa";
import store from '@/store'
import { constantRoutes } from '@/router'

/**
 * 校验是否拥有该菜单权限
 * @param {*} menuId 菜单id（类型：string）
 * @returns boolean
 */
export function hasPermission(menuId) {
  let allPermissions = store.getters['user/permissions'];
  return privateHasPermission(menuId, allPermissions);
}

function privateHasPermission(menuId, permissions) {
  if (permissions && permissions.length > 0) {
    let hasMenu = permissions.some((p) => {
      if (p.id === menuId) {
        return true;
      } else if (p.children && p.children.length > 0) {
        return privateHasPermission(menuId, p.children);
      }
    })
    return hasMenu;
  } else {
    return false;
  }
}

/**
 * 校验是否拥有某路由权限
 * @param {*} route 路由对象
 */
export function hasRoutePermission(route) {
  let isConstantRoute = checkRoute(route, constantRoutes);
  let isAccessRoute = hasPermission(route.meta.menuId);
  if (isConstantRoute || isAccessRoute) {
    return true;
  } else {
    return false;
  }
}

function checkRoute(route, routes) {
  let isRoute = false;
  if (routes && routes.length > 0) {
    isRoute = routes.some((p) => {
      if (p.name === route.name) {
        return true;
      } else if (p.children && p.children.length > 0) {
        return checkRoute(route, p.children);
      }
    })
  }
  return isRoute;
}

/**
 * @description intelligence模式根据permissions数组拦截路由,并按后端返回顺序进行排序操作
 * @param routes:前端配置的路由信息
 * @param permissions:后台返回的用户权限信息
 * @returns {[]}
 */
export function filterAsyncRoutes(routes, permissions) {
  const finallyRoutes = doFilter(routes, permissions)
  let sorted = sortForfilteredAsyncRoutes(finallyRoutes)
  return sorted
}

/**
 * 根据后台返回用户权限进行筛选
 * @param routes:前端配置的路由信息
 * @param permissions:后台返回的用户权限信息
 * @returns {[]|*}
 */
function doFilter(routes, permissions) {
  if (permissions.length == 1 && permissions[0] === 'ADMINISTRATOR') { //超级管理员，显示所有菜单
    return routes
  }
  const finallyRoutes = []
  routes.forEach((route) => {
    const item = { ...route }
    permissions.forEach((p) => {
      if (p.id === item.meta.menuId) {
        if (item.children) {
          item.children = doFilter(item.children, p.children)
        }
        item.__sort_ = p.sort
        finallyRoutes.push(item)
      }
    })
  })
  return finallyRoutes
}

/**
 * 对筛选之后的finallyRoutes进行排序，按__sort_升序排列
 * @param routes
 */
function sortForfilteredAsyncRoutes(finallyRoutes) {
  if (!finallyRoutes || !Array.isArray(finallyRoutes)) {
    return finallyRoutes
  }
  const sortedRoutes = finallyRoutes.sort((a, b) => {
    if (a.__sort_ != undefined && a.__sort_ != null) {
      return a.__sort_ - b.__sort_
    }
    return 0
  })

  sortedRoutes.forEach((route) => {
    if (route.children) {
      route.children = sortForfilteredAsyncRoutes(route.children)
    }
  })
  return sortedRoutes
}
