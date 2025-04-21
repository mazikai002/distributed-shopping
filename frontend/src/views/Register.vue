<template>
  <div class="register-container">
    <div class="register-box">
      <h2>用户注册</h2>
      <a-form
        :model="registerForm"
        @finish="handleRegister"
        class="register-form"
      >
        <a-form-item
          name="username"
          :rules="[
            { required: true, message: '请输入用户名' },
            { min: 3, message: '用户名至少3个字符' }
          ]"
        >
          <a-input
            v-model:value="registerForm.username"
            placeholder="用户名"
            size="large"
          >
            <template #prefix>
              <UserOutlined />
            </template>
          </a-input>
        </a-form-item>
        <a-form-item
          name="password"
          :rules="[
            { required: true, message: '请输入密码' },
            { min: 6, message: '密码至少6个字符' }
          ]"
        >
          <a-input-password
            v-model:value="registerForm.password"
            placeholder="密码"
            size="large"
          >
            <template #prefix>
              <LockOutlined />
            </template>
          </a-input-password>
        </a-form-item>
        <a-form-item
          name="confirmPassword"
          :rules="[
            { required: true, message: '请确认密码' },
            { validator: validateConfirmPassword }
          ]"
        >
          <a-input-password
            v-model:value="registerForm.confirmPassword"
            placeholder="确认密码"
            size="large"
          >
            <template #prefix>
              <LockOutlined />
            </template>
          </a-input-password>
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            html-type="submit"
            size="large"
            block
            :loading="loading"
          >
            注册
          </a-button>
        </a-form-item>
        <div class="form-footer">
          <router-link to="/login">已有账号？立即登录</router-link>
        </div>
      </a-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: ''
})

const validateConfirmPassword = async (rule, value) => {
  if (value !== registerForm.password) {
    throw new Error('两次输入的密码不一致')
  }
}

const handleRegister = async (values) => {
  try {
    loading.value = true
    await userStore.register({
      username: values.username,
      password: values.password
    })
    message.success('注册成功，请登录')
    router.push('/login')
  } catch (error) {
    message.error(error.message || '注册失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f0f2f5;
}

.register-box {
  width: 100%;
  max-width: 400px;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.register-box h2 {
  text-align: center;
  margin-bottom: 40px;
  color: #1890ff;
}

.register-form {
  width: 100%;
}

.form-footer {
  text-align: center;
  margin-top: 16px;
}

.form-footer a {
  color: #1890ff;
}
</style> 