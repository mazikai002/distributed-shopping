import React from 'react';
import { Typography, Row, Col, Card, Button } from 'antd';
import { ShoppingOutlined } from '@ant-design/icons';

const { Title, Paragraph } = Typography;

const Home: React.FC = () => {
  return (
    <div className="home-container">
      <Row gutter={[16, 16]} justify="center">
        <Col span={24}>
          <Title level={2} className="text-center">
            欢迎来到分布式秒杀商城系统
          </Title>
          <Paragraph className="text-center">
            基于 Spring Boot + MySQL + Redis + RocketMQ + Elasticsearch + MinIO 构建的高性能秒杀系统
          </Paragraph>
        </Col>
      </Row>
      
      <Row gutter={[16, 16]} justify="center" style={{ marginTop: '2rem' }}>
        <Col xs={24} sm={12} md={8} lg={6}>
          <Card 
            title="商品浏览" 
            hoverable
            cover={<div style={{ padding: '2rem 0', textAlign: 'center' }}><ShoppingOutlined style={{ fontSize: '3rem' }} /></div>}
          >
            <Paragraph>
              查看我们最新的商品列表，包括热门秒杀商品和限时特惠
            </Paragraph>
            <Button type="primary" block>
              浏览商品
            </Button>
          </Card>
        </Col>
        
        <Col xs={24} sm={12} md={8} lg={6}>
          <Card 
            title="秒杀活动" 
            hoverable
            cover={<div style={{ padding: '2rem 0', textAlign: 'center' }}><ShoppingOutlined style={{ fontSize: '3rem' }} /></div>}
          >
            <Paragraph>
              参与我们的秒杀活动，抢购限量商品，享受超值优惠
            </Paragraph>
            <Button type="primary" danger block>
              参与秒杀
            </Button>
          </Card>
        </Col>
        
        <Col xs={24} sm={12} md={8} lg={6}>
          <Card 
            title="个人中心" 
            hoverable
            cover={<div style={{ padding: '2rem 0', textAlign: 'center' }}><ShoppingOutlined style={{ fontSize: '3rem' }} /></div>}
          >
            <Paragraph>
              查看您的订单历史，管理个人信息，跟踪物流状态
            </Paragraph>
            <Button type="default" block>
              进入个人中心
            </Button>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default Home; 