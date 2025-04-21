<template>
  <div class="register-container">
    <div class="register-box">
      <h2>用户注册</h2>
      <a-form
        :model="formState"
        @finish="onFinish"
        class="register-form"
      >
        <a-form-item
          name="username"
          :rules="[{ required: true, message: '请输入用户名' }]"
        >
          <a-input
            v-model:value="formState.username"
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
            { min: 6, message: '密码长度不能小于6位' }
          ]"
        >
          <a-input-password
            v-model:value="formState.password"
            placeholder="密码"
            size="large"
          >
            <template #prefix>
              <LockOutlined />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item
          name="phone"
          :rules="[
            { required: true, message: '请输入手机号' },
            { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号' }
          ]"
        >
          <a-input
            v-model:value="formState.phone"
            placeholder="手机号"
            size="large"
          >
            <template #prefix>
              <MobileOutlined />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item
          name="verificationCode"
          :rules="[{ required: true, message: '请输入验证码' }]"
        >
          <div class="verification-code-container">
            <a-input
              v-model:value="formState.verificationCode"
              placeholder="验证码"
              size="large"
              style="width: calc(100% - 120px)"
            >
              <template #prefix>
                <SafetyCertificateOutlined />
              </template>
            </a-input>
            <a-button
              :disabled="!!countdown"
              @click="sendVerificationCode"
              size="large"
              style="width: 110px; margin-left: 10px"
            >
              {{ countdown ? `${countdown}s后重试` : '获取验证码' }}
            </a-button>
          </div>
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
import { UserOutlined, LockOutlined, MobileOutlined, SafetyCertificateOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const countdown = ref(0)

const formState = reactive({
  username: '',
  password: '',
  phone: '',
  verificationCode: ''
})

const startCountdown = () => {
  countdown.value = 60
  const timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer)
    }
  }, 1000)
}

const sendVerificationCode = async () => {
  if (!formState.phone) {
    message.error('请先输入手机号')
    return
  }
  if (!/^1[3-9]\d{9}$/.test(formState.phone)) {
    message.error('请输入正确的手机号')
    return
  }
  
  try {
    await userStore.sendVerificationCode(formState.phone)
    message.success('验证码已发送')
    startCountdown()
  } catch (error) {
    message.error(error.message || '验证码发送失败')
  }
}

const onFinish = async (values) => {
  try {
    loading.value = true
    await userStore.register(values)
    message.success('注册成功')
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

.verification-code-container {
  display: flex;
  align-items: center;
}

.form-footer {
  text-align: center;
  margin-top: 16px;
}

.form-footer a {
  color: #1890ff;
}
</style> 