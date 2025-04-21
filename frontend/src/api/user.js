import request from '@/utils/request'

// 用户登录
export function login(data) {
  return request({
    url: '/v1/user/login',
    method: 'post',
    data
  })
}

// 获取用户信息
export function getUserInfo() {
  return request({
    url: '/v1/user/info',
    method: 'get'
  })
}

// 用户注册
export function register(data) {
  return request({
    url: '/v1/user/register',
    method: 'post',
    data
  })
}

// 用户登出
export function logout() {
  return request({
    url: '/v1/user/logout',
    method: 'post'
  })
}

// 发送验证码
export function sendVerificationCode(phone) {
  return request({
    url: '/api/v1/user/send-code',
    method: 'post',
    data: { phone }
  })
} 