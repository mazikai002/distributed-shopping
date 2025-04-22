import request from '../utils/request';

export interface LoginParams {
  username: string;
  password: string;
}

export interface LoginResult {
  code: number;
  message: string;
  data: {
    token: string;
  };
}

export interface UserInfo {
  id: number;
  username: string;
  email: string;
  avatar?: string;
}

export const login = (data: LoginParams) => {
  return request.post<LoginResult>('/v1/user/login', data);
};

export const getUserInfo = () => {
  return request.get<{ code: number; data: UserInfo }>('/v1/user/info');
};

export const register = (data: LoginParams) => {
  return request.post<LoginResult>('/v1/user/register', data);
};

export const logout = () => {
  return request.post('/v1/user/logout');
}; 