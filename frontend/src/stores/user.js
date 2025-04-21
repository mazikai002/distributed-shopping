import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi, register as registerApi, getUserInfo } from '@/api/user'

export const useUserStore = defineStore('user', () => {
  const user = ref(null)
  const token = ref(localStorage.getItem('token'))

  const setUser = (userData) => {
    user.value = userData
  }

  const setToken = (tokenValue) => {
    token.value = tokenValue
    localStorage.setItem('token', tokenValue)
  }

  const clearUser = () => {
    user.value = null
    token.value = null
    localStorage.removeItem('token')
  }

  const login = async (credentials) => {
    try {
      const response = await loginApi(credentials)
      const { data } = response
      setToken(data.token)
      setUser(data.user)
      return data
    } catch (error) {
      throw new Error(error.message || '登录失败')
    }
  }

  const register = async (userData) => {
    try {
      const response = await registerApi(userData)
      return response.data
    } catch (error) {
      throw new Error(error.message || '注册失败')
    }
  }

  const logout = () => {
    clearUser()
  }

  const checkAuth = async () => {
    if (!token.value) return false
    try {
      const response = await getUserInfo()
      setUser(response.data)
      return true
    } catch (error) {
      clearUser()
      return false
    }
  }

  return {
    user,
    token,
    login,
    register,
    logout,
    checkAuth
  }
}) 