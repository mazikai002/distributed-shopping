import request from '@/utils/request'

// 获取秒杀商品列表
export function getSeckillList() {
  return request({
    url: '/api/v2/seckill/list',
    method: 'get'
  })
}

// 执行秒杀
export function doSeckill(data) {
  return request({
    url: '/api/v2/seckill/do',
    method: 'post',
    data
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
export function getStock(voucherId) {
  return request({
    url: `/api/v2/seckill/stock/${voucherId}`,
    method: 'get'
  })
} 