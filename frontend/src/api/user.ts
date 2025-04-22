import request from '../utils/request';

export interface LoginParams {
  username: string;
  password: string;
}

export interface RegisterParams {
  username: string;
  password: string;
  email: string;
  phone: string;
}

export interface ApiResponse<T> {
  code: number;
  message: string;
  data: T;
}

export interface TokenData {
  token: string;
}

export interface UserInfo {
  id: number;
  username: string;
  email: string;
  avatar?: string;
}

export const login = (data: LoginParams) => {
  return request.post<ApiResponse<TokenData>>('/v1/user/login', data);
};

export const getUserInfo = () => {
  return request.get<ApiResponse<UserInfo>>('/v1/user/info');
};

export const register = (data: RegisterParams) => {
  return request.post<ApiResponse<TokenData>>('/v1/user/register', data);
};

export const logout = () => {
  return request.post<ApiResponse<null>>('/v1/user/logout');
}; 