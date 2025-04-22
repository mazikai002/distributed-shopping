import React from 'react';
import { Layout, Menu, Button } from 'antd';
import { useNavigate } from 'react-router-dom';
import { UserOutlined, ShoppingCartOutlined } from '@ant-design/icons';

const { Header } = Layout;

const AppHeader: React.FC = () => {
  const navigate = useNavigate();

  return (
    <Header style={{ position: 'fixed', zIndex: 1, width: '100%' }}>
      <div className="logo" />
      <Menu
        theme="dark"
        mode="horizontal"
        defaultSelectedKeys={['1']}
        style={{ lineHeight: '64px' }}
      >
        <Menu.Item key="1" onClick={() => navigate('/')}>
          首页
        </Menu.Item>
        <Menu.Item key="2" onClick={() => navigate('/goods')}>
          商品
        </Menu.Item>
        <Menu.Item key="3" onClick={() => navigate('/cart')}>
          <ShoppingCartOutlined />
          购物车
        </Menu.Item>
        <Menu.Item key="4" style={{ float: 'right' }}>
          <Button
            type="primary"
            icon={<UserOutlined />}
            onClick={() => navigate('/login')}
          >
            登录
          </Button>
        </Menu.Item>
      </Menu>
    </Header>
  );
};

export default AppHeader; 