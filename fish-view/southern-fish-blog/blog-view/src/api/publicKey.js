import request from '@/utils/request'

export function getPublicKey() {
  return request({
    url: 'blog/operator/publicKey',
    method: 'get',
  })
}
