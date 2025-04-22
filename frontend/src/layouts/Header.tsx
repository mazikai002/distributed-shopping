import React, { useState } from 'react';
import { Layout, Input, Button, Badge, Row, Col, Menu, Dropdown } from 'antd';
import { useNavigate } from 'react-router-dom';
import { ShoppingCartOutlined, UserOutlined, DownOutlined, SearchOutlined } from '@ant-design/icons';

const { Header } = Layout;
const { Search } = Input;

const AppHeader: React.FC = () => {
  const navigate = useNavigate();
  const [searchValue, setSearchValue] = useState('');

  const handleSearch = (value: string) => {
    console.log('搜索:', value);
    navigate(`/search?keyword=${value}`);
  };

  const categoryItems = [
    { key: '1', label: '家用电器' },
    { key: '2', label: '手机/运营商/数码' },
    { key: '3', label: '电脑/办公' },
    { key: '4', label: '家居/家装/厨具' },
    { key: '5', label: '男装/女装/童装/内衣' },
    { key: '6', label: '美妆/个护清洁/宠物' },
    { key: '7', label: '女鞋/箱包/钟表/珠宝' },
    { key: '8', label: '男鞋/运动/户外' },
    { key: '9', label: '汽车/汽车用品' },
    { key: '10', label: '母婴/玩具乐器' },
  ];

  const userMenu = (
    <Menu items={[
      { key: '1', label: '我的订单' },
      { key: '2', label: '我的收藏' },
      { key: '3', label: '个人信息' },
      { key: '4', label: '退出登录' },
    ]} />
  );

  return (
    <Header className="main-header">
      <div className="header-content">
        <Row align="middle" className="header-main">
          <Col span={4}>
            <div className="logo" onClick={() => navigate('/')}>
              <img src="/logo.svg" alt="分布式秒杀商城" height="40" />
            </div>
          </Col>
          
          <Col span={12}>
            <div className="search-container">
              <Search
                placeholder="请输入搜索关键词"
                allowClear
                enterButton={<Button type="primary" icon={<SearchOutlined />}>搜索</Button>}
                size="large"
                value={searchValue}
                onChange={(e) => setSearchValue(e.target.value)}
                onSearch={handleSearch}
                className="search-box"
              />
              <div className="hot-words">
                <a>秒杀</a>
                <a>优惠券</a>
                <a>新品上市</a>
                <a>电子产品</a>
                <a>家居好物</a>
              </div>
            </div>
          </Col>
          
          <Col span={8}>
            <Row justify="end" gutter={24}>
              <Col>
                <Dropdown overlay={userMenu} trigger={['hover']}>
                  <a className="user-menu" onClick={e => e.preventDefault()}>
                    <UserOutlined />
                    <span>我的账户</span>
                    <DownOutlined />
                  </a>
                </Dropdown>
              </Col>
              <Col>
                <Badge count={5} offset={[10, 0]}>
                  <Button 
                    type="primary" 
                    icon={<ShoppingCartOutlined />} 
                    size="large"
                    className="cart-button"
                    onClick={() => navigate('/cart')}
                  >
                    购物车
                  </Button>
                </Badge>
              </Col>
            </Row>
          </Col>
        </Row>
        
        <Row className="nav-menu-container">
          <Col span={4}>
            <div className="category-menu-trigger">
              <span>全部商品分类</span>
            </div>
          </Col>
          <Col span={20}>
            <Menu mode="horizontal" className="main-menu" selectedKeys={['1']}>
              <Menu.Item key="1" onClick={() => navigate('/')}>首页</Menu.Item>
              <Menu.Item key="2" onClick={() => navigate('/seckill')}>秒杀</Menu.Item>
              <Menu.Item key="3" onClick={() => navigate('/new')}>新品</Menu.Item>
              <Menu.Item key="4" onClick={() => navigate('/special')}>特价</Menu.Item>
              <Menu.Item key="5" onClick={() => navigate('/popular')}>热销榜</Menu.Item>
              <Menu.Item key="6" onClick={() => navigate('/brand')}>品牌</Menu.Item>
            </Menu>
          </Col>
        </Row>
      </div>
    </Header>
  );
};

export default AppHeader; 