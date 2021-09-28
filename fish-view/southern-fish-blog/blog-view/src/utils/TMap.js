
export function loadTMap() {
  return new Promise(function (resolve, reject) {
    window.init = function () {
      resolve(qq)
    }
    var script = document.createElement('script')
    script.type = 'text/javascript'
    script.src = 'https://map.qq.com/api/js?v=2.exp&callback=init&key=2UUBZ-4PJ3Q-IUI5T-GXZHU-WZDJF-BGFGW'
    script.onerror = reject
    document.head.appendChild(script)
  })
}
