import request from './request'

// 获取秒杀商品列表
export function getSeckillList() {
  return request({
    url: '/v2/seckill/list',
    method: 'get'
  })
}

// 执行秒杀
export function doSeckill(params) {
  return request({
    url: '/v2/seckill/do',
    method: 'post',
    data: params
  })
}

// 查询秒杀结果
export function querySeckillResult(orderNo) {
  return request({
    url: `/v2/seckill/query/${orderNo}`,
    method: 'get'
  })
}

// 获取商品库存
export function getStock(productId) {
  return request({
    url: `/v2/seckill/stock/${productId}`,
    method: 'get'
  })
} 