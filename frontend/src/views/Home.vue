<template>
  <div class="home">
    <!-- 广告轮播图 -->
    <div class="banner-container">
      <el-carousel :interval="5000" height="500px">
        <el-carousel-item v-for="(item, index) in banners" :key="index">
          <img :src="item.imageUrl" :alt="item.title" class="banner-image" />
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 商品分类导航 -->
    <div class="category-nav container">
      <el-menu mode="horizontal" :default-active="activeCategory">
        <el-menu-item v-for="(category, index) in categories" 
                      :key="index" 
                      :index="category.id">
          {{ category.name }}
        </el-menu-item>
      </el-menu>
    </div>

    <!-- 秒杀专区 -->
    <div class="seckill-section container">
      <div class="section-header">
        <h2>
          <el-icon><Timer /></el-icon>
          秒杀专区
        </h2>
        <div class="countdown">
          距离结束：{{ countdownTime }}
        </div>
      </div>
      <el-row :gutter="20">
        <el-col v-for="(product, index) in seckillProducts" 
                :key="index" 
                :span="4">
          <el-card :body-style="{ padding: '0px' }" class="product-card">
            <img :src="product.imageUrl" class="product-image" />
            <div class="product-info">
              <h3>{{ product.name }}</h3>
              <div class="price">
                <span class="current-price">¥{{ product.seckillPrice }}</span>
                <span class="original-price">¥{{ product.originalPrice }}</span>
              </div>
              <el-button type="danger" size="small" @click="handleSeckill(product.id)">立即抢购</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 商品列表 -->
    <div class="product-list container">
      <div class="section-header">
        <h2>热门商品</h2>
      </div>
      <el-row :gutter="20">
        <el-col v-for="(product, index) in products" 
                :key="index" 
                :span="6">
          <el-card :body-style="{ padding: '0px' }" class="product-card">
            <img :src="product.imageUrl" class="product-image" />
            <div class="product-info">
              <h3>{{ product.name }}</h3>
              <p class="description">{{ product.description }}</p>
              <div class="price">¥{{ product.price }}</div>
              <el-button type="primary" size="small">加入购物车</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Timer } from '@element-plus/icons-vue'
import { getSeckillList, doSeckill, getStock } from '@/api/seckill'
import { ElMessage, ElLoading } from 'element-plus'

// 模拟数据
const banners = ref([
  {
    imageUrl: 'https://via.placeholder.com/1920x500',
    title: '广告1'
  },
  {
    imageUrl: 'https://via.placeholder.com/1920x500',
    title: '广告2'
  },
  {
    imageUrl: 'https://via.placeholder.com/1920x500',
    title: '广告3'
  }
])

const categories = ref([
  { id: '1', name: '手机数码' },
  { id: '2', name: '电脑办公' },
  { id: '3', name: '家用电器' },
  { id: '4', name: '食品生鲜' },
  { id: '5', name: '服装鞋包' }
])

const activeCategory = ref('1')

const loading = ref(false)
const seckillProducts = ref([])
const products = ref([])

// 获取秒杀商品列表
const fetchSeckillProducts = async () => {
  const loadingInstance = ElLoading.service({
    text: '加载中...'
  })
  try {
    const res = await getSeckillList()
    if (res.data) {
      seckillProducts.value = res.data
    } else {
      // 如果后端未启动或返回数据为空，使用模拟数据
      seckillProducts.value = [
        {
          id: 1,
          imageUrl: 'https://via.placeholder.com/200x200',
          name: '示例商品1',
          seckillPrice: 999,
          originalPrice: 1999,
          stock: 100
        },
        {
          id: 2,
          imageUrl: 'https://via.placeholder.com/200x200',
          name: '示例商品2',
          seckillPrice: 888,
          originalPrice: 1888,
          stock: 50
        }
      ]
    }
  } catch (error) {
    console.error('获取秒杀商品列表失败:', error)
    ElMessage.error('获取商品列表失败，已显示示例数据')
    // 使用模拟数据
    seckillProducts.value = [
      {
        id: 1,
        imageUrl: 'https://via.placeholder.com/200x200',
        name: '示例商品1',
        seckillPrice: 999,
        originalPrice: 1999,
        stock: 100
      },
      {
        id: 2,
        imageUrl: 'https://via.placeholder.com/200x200',
        name: '示例商品2',
        seckillPrice: 888,
        originalPrice: 1888,
        stock: 50
      }
    ]
  } finally {
    loadingInstance.close()
  }
}

// 执行秒杀
const handleSeckill = async (productId) => {
  try {
    const res = await doSeckill({ productId })
    if (res.code === 200) {
      ElMessage.success('抢购成功！')
      // 更新商品库存
      await updateStock(productId)
    }
  } catch (error) {
    ElMessage.error(error.message || '抢购失败，请重试')
  }
}

// 更新商品库存
const updateStock = async (productId) => {
  try {
    const res = await getStock(productId)
    const product = seckillProducts.value.find(item => item.id === productId)
    if (product) {
      product.stock = res.data
    }
  } catch (error) {
    console.error('获取库存信息失败:', error)
  }
}

const countdownTime = ref('00:00:00')

// 倒计时逻辑
const updateCountdown = () => {
  const now = new Date()
  const end = new Date()
  end.setHours(23, 59, 59)
  
  const diff = end - now
  const hours = Math.floor(diff / (1000 * 60 * 60))
  const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60))
  const seconds = Math.floor((diff % (1000 * 60)) / 1000)
  
  countdownTime.value = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`
}

onMounted(() => {
  fetchSeckillProducts()
  updateCountdown()
  setInterval(updateCountdown, 1000)
})
</script>

<style scoped>
.banner-container {
  margin-bottom: 20px;
}

.banner-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.category-nav {
  margin-bottom: 20px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-header h2 {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #e4393c;
  margin: 0;
}

.countdown {
  color: #e4393c;
  font-size: 18px;
  font-weight: bold;
}

.product-card {
  margin-bottom: 20px;
  transition: all 0.3s;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.product-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.product-info {
  padding: 14px;
}

.product-info h3 {
  margin: 0 0 8px;
  font-size: 14px;
  height: 40px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.description {
  color: #999;
  font-size: 12px;
  margin-bottom: 8px;
}

.price {
  color: #e4393c;
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}

.current-price {
  color: #e4393c;
  font-size: 20px;
  font-weight: bold;
  margin-right: 8px;
}

.original-price {
  color: #999;
  font-size: 14px;
  text-decoration: line-through;
}

.seckill-section {
  margin-bottom: 40px;
}

.product-list {
  margin-bottom: 40px;
}
</style> 