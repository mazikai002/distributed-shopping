import React from 'react';
import { Layout } from 'antd';

const { Footer } = Layout;

const AppFooter: React.FC = () => {
  return (
    <Footer style={{ textAlign: 'center' }}>
      Distributed Shopping ©{new Date().getFullYear()} Created by Your Company
    </Footer>
  );
};

export default AppFooter; 