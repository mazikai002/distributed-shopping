import axios, { AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import { message } from 'antd';
import { history } from './history';

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.REACT_APP_API_URL || '/api',
  timeout: 5000,
});

// 请求拦截器
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.set('Authorization', `Bearer ${token}`);
    }
    return config;
  },
  (error) => {
    console.error('Request error:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse) => {
    return response.data;
  },
  (error) => {
    console.error('Response error:', error);
    if (error.response) {
      const { status } = error.response;
      
      if (status === 401) {
        message.error('登录已过期，请重新登录');
        localStorage.removeItem('token');
        history.push('/login');
      } else if (status === 403) {
        message.error('没有权限访问');
      } else if (status === 404) {
        message.error('请求的资源不存在');
      } else {
        message.error(error.response.data?.message || '请求失败');
      }
    } else {
      message.error('网络错误，请稍后重试');
    }
    return Promise.reject(error);
  }
);

export default service; 