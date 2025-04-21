<template>
  <div class="home-container">
    <div class="header">
      <div class="header-content">
        <h1>分布式秒杀系统</h1>
        <div class="user-info">
          <span>欢迎，{{ userStore.user?.username }}</span>
          <a-button type="link" @click="handleLogout">退出登录</a-button>
        </div>
      </div>
    </div>
    <div class="main-content">
      <h2>欢迎来到分布式秒杀系统</h2>
      <p>这里是系统首页</p>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background-color: #f0f2f5;
}

.header {
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 16px 0;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header h1 {
  margin: 0;
  color: #1890ff;
  font-size: 24px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 16px;
}

.main-content {
  max-width: 1200px;
  margin: 40px auto;
  padding: 24px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 16px;
  color: #333;
}
</style> 