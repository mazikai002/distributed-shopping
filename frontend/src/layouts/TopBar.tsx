import React from 'react';
import { Row, Col, Menu, Dropdown, Space } from 'antd';
import { DownOutlined, EnvironmentOutlined, UserOutlined, QuestionCircleOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';

const TopBar: React.FC = () => {
  const navigate = useNavigate();

  const locationMenu = (
    <Menu items={[
      { key: '1', label: '北京' },
      { key: '2', label: '上海' },
      { key: '3', label: '广州' },
      { key: '4', label: '深圳' },
      { key: '5', label: '杭州' },
    ]} />
  );

  const customerServiceMenu = (
    <Menu items={[
      { key: '1', label: '帮助中心' },
      { key: '2', label: '在线客服' },
      { key: '3', label: '电话客服' },
      { key: '4', label: '邮箱客服' },
    ]} />
  );

  const siteNavMenu = (
    <Menu items={[
      { key: '1', label: '网站导航' },
      { key: '2', label: '商城首页' },
      { key: '3', label: '秒杀专场' },
      { key: '4', label: '特价商品' },
      { key: '5', label: '活动中心' },
    ]} />
  );
  
  return (
    <div className="top-bar">
      <Row justify="space-between" align="middle">
        <Col>
          <Space size={16}>
            <Dropdown overlay={locationMenu} trigger={['click']}>
              <a onClick={e => e.preventDefault()}>
                <Space>
                  <EnvironmentOutlined />
                  北京
                  <DownOutlined />
                </Space>
              </a>
            </Dropdown>
            
            <a onClick={() => navigate('/login')}>
              <Space>
                <UserOutlined />
                登录
              </Space>
            </a>
            
            <a onClick={() => navigate('/register')}>注册</a>
          </Space>
        </Col>
        
        <Col>
          <Space size={16}>
            <a onClick={() => navigate('/orders')}>我的订单</a>
            
            <a onClick={() => navigate('/cart')}>购物车</a>
            
            <Dropdown overlay={customerServiceMenu} trigger={['hover']}>
              <a onClick={e => e.preventDefault()}>
                <Space>
                  <QuestionCircleOutlined />
                  客户服务
                  <DownOutlined />
                </Space>
              </a>
            </Dropdown>
            
            <Dropdown overlay={siteNavMenu} trigger={['hover']}>
              <a onClick={e => e.preventDefault()}>
                <Space>
                  网站导航
                  <DownOutlined />
                </Space>
              </a>
            </Dropdown>
          </Space>
        </Col>
      </Row>
    </div>
  );
};

export default TopBar; 