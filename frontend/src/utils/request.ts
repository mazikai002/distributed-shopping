import axios, { AxiosRequestConfig, AxiosResponse } from 'axios';
import { message } from 'antd';
import { history } from './history';

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.REACT_APP_API_URL || '/api',
  timeout: 5000,
});

// 请求拦截器
service.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers = {
        ...config.headers,
        Authorization: `Bearer ${token}`,
      };
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
    const res = response.data;
    if (res.code !== 200) {
      message.error(res.message || '系统错误');
      
      // 处理特定错误码
      if (res.code === 401) {
        localStorage.removeItem('token');
        history.push('/login');
      }
      
      return Promise.reject(new Error(res.message || '系统错误'));
    }
    return res;
  },
  (error) => {
    console.error('Response error:', error);
    message.error(error.message || '请求失败');
    return Promise.reject(error);
  }
);

export default service; 