import React from 'react';
import { Row, Col, Carousel, Card, Button, Tabs, Divider } from 'antd';
import { RightOutlined, FireOutlined, ClockCircleOutlined, CrownOutlined } from '@ant-design/icons';
import CategoryMenu from '../components/CategoryMenu';
import ProductCard from '../components/ProductCard';

const { TabPane } = Tabs;

// 模拟数据
const seckillProducts = [
  { id: 1, title: 'AKSERIES男士卫衣连帽秋冬款加绒加厚', price: 79.9, originalPrice: 199, imageUrl: 'https://via.placeholder.com/200x200', shop: '品牌旗舰店', rating: 4.8, salesCount: 2100, hasDiscount: true, discount: '4折' },
  { id: 2, title: '苹果充电器20W快充PD快充', price: 69.9, originalPrice: 129, imageUrl: 'https://via.placeholder.com/200x200', shop: '数码配件专营店', rating: 4.9, salesCount: 5600, hasDiscount: true, discount: '5.4折' },
  { id: 3, title: '华为手环8 智能运动手环', price: 249, originalPrice: 399, imageUrl: 'https://via.placeholder.com/200x200', shop: '华为官方旗舰店', rating: 4.9, salesCount: 1800, hasDiscount: true, discount: '6.2折' },
  { id: 4, title: '小米移动电源10000mAh', price: 79, originalPrice: 99, imageUrl: 'https://via.placeholder.com/200x200', shop: '小米专卖店', rating: 4.7, salesCount: 4200, hasDiscount: true, discount: '8折' },
];

const newProducts = [
  { id: 5, title: '新款笔记本电脑15.6英寸', price: 3999, imageUrl: 'https://via.placeholder.com/200x200', shop: '电脑专营店', rating: 4.5, salesCount: 320, isNew: true },
  { id: 6, title: '智能扫地机器人家用全自动', price: 1299, imageUrl: 'https://via.placeholder.com/200x200', shop: '智能家居旗舰店', rating: 4.6, salesCount: 560, isNew: true },
  { id: 7, title: '全自动咖啡机家用小型', price: 899, imageUrl: 'https://via.placeholder.com/200x200', shop: '厨房电器专营', rating: 4.8, salesCount: 230, isNew: true },
  { id: 8, title: '蓝牙耳机无线双耳入耳式', price: 199, imageUrl: 'https://via.placeholder.com/200x200', shop: '音乐设备专营', rating: 4.7, salesCount: 1800, isNew: true },
];

const popularProducts = [
  { id: 9, title: '全棉四件套纯棉床上用品', price: 299, imageUrl: 'https://via.placeholder.com/200x200', shop: '家纺旗舰店', rating: 4.9, salesCount: 8900 },
  { id: 10, title: '不锈钢炒锅家用炒菜锅', price: 129, imageUrl: 'https://via.placeholder.com/200x200', shop: '厨具专营店', rating: 4.8, salesCount: 4500 },
  { id: 11, title: '儿童益智积木拼装玩具', price: 89, imageUrl: 'https://via.placeholder.com/200x200', shop: '玩具专营店', rating: 4.6, salesCount: 2300 },
  { id: 12, title: '保温杯男女士便携水杯', price: 59, imageUrl: 'https://via.placeholder.com/200x200', shop: '日用品专营', rating: 4.7, salesCount: 6700 },
];

const Home: React.FC = () => {
  return (
    <div className="jd-home">
      {/* 顶部区域：分类菜单 + 轮播图 */}
      <Row className="top-section" gutter={[16, 16]}>
        <Col xs={24} sm={24} md={6} lg={5} xl={4}>
          <CategoryMenu className="home-category-menu" />
        </Col>
        
        <Col xs={24} sm={24} md={12} lg={13} xl={15}>
          <Carousel autoplay className="main-carousel">
            <div>
              <img src="https://via.placeholder.com/960x320" alt="促销活动1" style={{ width: '100%' }} />
            </div>
            <div>
              <img src="https://via.placeholder.com/960x320" alt="促销活动2" style={{ width: '100%' }} />
            </div>
            <div>
              <img src="https://via.placeholder.com/960x320" alt="促销活动3" style={{ width: '100%' }} />
            </div>
          </Carousel>
        </Col>
        
        <Col xs={24} sm={24} md={6} lg={6} xl={5}>
          <Card className="user-card">
            <div className="user-info">
              <div className="avatar">
                <img src="https://via.placeholder.com/50x50" alt="用户头像" />
              </div>
              <div className="welcome">
                <h4>Hi，欢迎来到京东！</h4>
                <div className="login-btns">
                  <Button type="primary" size="small">登录</Button>
                  <Button size="small">注册</Button>
                </div>
              </div>
            </div>
            
            <div className="user-benefits">
              <div className="benefit-item">
                <div className="benefit-icon">券</div>
                <div className="benefit-text">领券</div>
              </div>
              <div className="benefit-item">
                <div className="benefit-icon">金</div>
                <div className="benefit-text">领金豆</div>
              </div>
              <div className="benefit-item">
                <div className="benefit-icon">Plus</div>
                <div className="benefit-text">会员</div>
              </div>
            </div>
            
            <div className="news">
              <h4>商城快讯</h4>
              <ul className="news-list">
                <li>春季大促销，全场5折起</li>
                <li>新品上市，限时免邮</li>
                <li>积分商城上线，豪礼等你拿</li>
              </ul>
            </div>
          </Card>
        </Col>
      </Row>
      
      {/* 秒杀区域 */}
      <div className="section seckill-section">
        <div className="section-header">
          <div className="section-title">
            <FireOutlined /> 
            <h2>京东秒杀</h2>
            <div className="countdown">
              <ClockCircleOutlined />
              <span>距离结束 12:34:56</span>
            </div>
          </div>
          <a className="more-link">
            更多秒杀 <RightOutlined />
          </a>
        </div>
        
        <Row gutter={[16, 16]}>
          {seckillProducts.map(product => (
            <Col xs={24} sm={12} md={8} lg={6} key={product.id}>
              <ProductCard {...product} />
            </Col>
          ))}
        </Row>
      </div>
      
      {/* 新品推荐区域 */}
      <div className="section new-products-section">
        <div className="section-header">
          <div className="section-title">
            <h2>新品推荐</h2>
          </div>
          <a className="more-link">
            更多新品 <RightOutlined />
          </a>
        </div>
        
        <Row gutter={[16, 16]}>
          {newProducts.map(product => (
            <Col xs={24} sm={12} md={8} lg={6} key={product.id}>
              <ProductCard {...product} />
            </Col>
          ))}
        </Row>
      </div>
      
      {/* 热销榜单区域 */}
      <div className="section popular-section">
        <div className="section-header">
          <div className="section-title">
            <CrownOutlined />
            <h2>热销榜单</h2>
          </div>
          <a className="more-link">
            更多热销 <RightOutlined />
          </a>
        </div>
        
        <Row gutter={[16, 16]}>
          {popularProducts.map((product, index) => (
            <Col xs={24} sm={12} md={8} lg={6} key={product.id}>
              <div className="rank-product">
                <div className={`rank-number ${index < 3 ? 'top-rank' : ''}`}>{index + 1}</div>
                <ProductCard {...product} />
              </div>
            </Col>
          ))}
        </Row>
      </div>
    </div>
  );
};

export default Home; 