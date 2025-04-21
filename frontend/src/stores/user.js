import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, getUserInfo, logout } from '@/api/user'
import router from '@/router'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref({})

  // 登录
  async function loginAction(loginForm) {
    try {
      const res = await login(loginForm)
      if (res.code === 200) {
        token.value = res.data.token
        localStorage.setItem('token', res.data.token)
        await router.push('/')
      }
      return res
    } catch (error) {
      return Promise.reject(error)
    }
  }

  // 获取用户信息
  async function getUserInfoAction() {
    try {
      const res = await getUserInfo()
      if (res.code === 200) {
        userInfo.value = res.data
      }
      return res
    } catch (error) {
      return Promise.reject(error)
    }
  }

  // 登出
  async function logoutAction() {
    try {
      await logout()
      token.value = ''
      userInfo.value = {}
      localStorage.removeItem('token')
      router.push('/login')
    } catch (error) {
      return Promise.reject(error)
    }
  }

  return {
    token,
    userInfo,
    loginAction,
    getUserInfoAction,
    logoutAction
  }
}) 