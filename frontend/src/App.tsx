import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { Layout } from 'antd';
import TopBar from './layouts/TopBar';
import Header from './layouts/Header';
import Footer from './layouts/Footer';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import './App.css';

const { Content } = Layout;

const App: React.FC = () => {
  return (
    <Layout className="layout">
      <TopBar />
      <Header />
      <Content className="main-content">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          {/* 添加更多路由 */}
        </Routes>
      </Content>
      <Footer />
    </Layout>
  );
};

export default App; 