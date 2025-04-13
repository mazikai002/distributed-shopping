<template>
  <div class="app-container">
    <header class="header">
      <div class="header-top">
        <div class="container">
          <el-row justify="space-between" align="middle">
            <el-col :span="12">
              <div class="location">
                <el-icon><Location /></el-icon>
                北京
              </div>
            </el-col>
            <el-col :span="12">
              <div class="user-menu">
                <template v-if="!isLoggedIn">
                  <el-button link>请登录</el-button>
                  <el-button link>免费注册</el-button>
                </template>
                <template v-else>
                  <span>欢迎，{{ username }}</span>
                </template>
                <el-divider direction="vertical" />
                <el-button link>我的订单</el-button>
                <el-button link>购物车</el-button>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="header-main container">
        <div class="logo">
          <img src="@/assets/logo.png" alt="Logo" />
        </div>
        <div class="search">
          <el-input
            v-model="searchQuery"
            placeholder="请输入搜索关键词"
            class="search-input"
          >
            <template #append>
              <el-button type="primary">搜索</el-button>
            </template>
          </el-input>
        </div>
      </div>
    </header>

    <main class="main-content">
      <router-view></router-view>
    </main>

    <footer class="footer">
      <div class="container">
        <el-row justify="space-between">
          <el-col :span="6">
            <h4>购物指南</h4>
            <ul>
              <li>购物流程</li>
              <li>会员介绍</li>
              <li>常见问题</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>配送方式</h4>
            <ul>
              <li>上门自提</li>
              <li>快递配送</li>
              <li>配送费用</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>支付方式</h4>
            <ul>
              <li>货到付款</li>
              <li>在线支付</li>
              <li>分期付款</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>售后服务</h4>
            <ul>
              <li>退款说明</li>
              <li>退换货流程</li>
              <li>联系客服</li>
            </ul>
          </el-col>
        </el-row>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Location } from '@element-plus/icons-vue'

const isLoggedIn = ref(false)
const username = ref('')
const searchQuery = ref('')
</script>

<style>
.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 15px;
}

.header {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-top {
  background-color: #f5f5f5;
  padding: 8px 0;
  font-size: 12px;
}

.location {
  display: flex;
  align-items: center;
  gap: 4px;
}

.user-menu {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 8px;
}

.header-main {
  padding: 20px 0;
  display: flex;
  align-items: center;
  gap: 40px;
}

.logo img {
  height: 40px;
}

.search {
  flex: 1;
  max-width: 500px;
}

.main-content {
  flex: 1;
  padding: 20px 0;
}

.footer {
  background-color: #f5f5f5;
  padding: 40px 0;
  margin-top: auto;
}

.footer h4 {
  margin-bottom: 20px;
  font-size: 16px;
  font-weight: bold;
}

.footer ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer li {
  margin-bottom: 10px;
  font-size: 14px;
  color: #666;
  cursor: pointer;
}

.footer li:hover {
  color: #e4393c;
}
</style>
