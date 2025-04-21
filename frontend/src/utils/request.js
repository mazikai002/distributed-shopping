import axios from 'axios'
import { message } from 'ant-design-vue'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const { code, msg, data } = response.data
    if (code === 200) {
      return response.data
    } else {
      message.error(msg || '请求失败')
      return Promise.reject(new Error(msg || '请求失败'))
    }
  },
  error => {
    if (error.response) {
      const { status, data } = error.response
      if (status === 401) {
        localStorage.removeItem('token')
        window.location.href = '/login'
      }
      message.error(data.message || '请求失败')
    } else {
      message.error('网络错误，请稍后重试')
    }
    return Promise.reject(error)
  }
)

export default request 