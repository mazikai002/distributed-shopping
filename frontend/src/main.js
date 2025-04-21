import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import App from './App.vue'
import router from './router'

// 创建全局样式
const style = document.createElement('style')
style.textContent = `
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
  
  html, body {
    height: 100%;
    width: 100%;
  }
  
  #app {
    height: 100%;
    width: 100%;
  }
`
document.head.appendChild(style)

const app = createApp(App)

// 注册全局组件和插件
app.use(createPinia())
app.use(router)
app.use(Antd)

// 挂载应用
app.mount('#app')
