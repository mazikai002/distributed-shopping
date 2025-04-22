import React from 'react';
import { Form, Input, Button, Card, message } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { login, LoginParams } from '../api/user';

const Login: React.FC = () => {
  const navigate = useNavigate();
  const [form] = Form.useForm();

  const onFinish = async (values: LoginParams) => {
    try {
      const result = await login(values);
      message.success('登录成功');
      localStorage.setItem('token', result.data.data.token);
      navigate('/');
    } catch (error: any) {
      message.error(error.message || '登录失败，请重试');
    }
  };

  return (
    <div style={{ 
      display: 'flex', 
      justifyContent: 'center', 
      alignItems: 'center', 
      minHeight: 'calc(100vh - 64px - 70px)',
      background: '#f0f2f5'
    }}>
      <Card title="用户登录" style={{ width: 400 }}>
        <Form
          form={form}
          name="login"
          onFinish={onFinish}
          autoComplete="off"
        >
          <Form.Item
            name="username"
            rules={[{ required: true, message: '请输入用户名' }]}
          >
            <Input prefix={<UserOutlined />} placeholder="用户名" />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[{ required: true, message: '请输入密码' }]}
          >
            <Input.Password prefix={<LockOutlined />} placeholder="密码" />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" block>
              登录
            </Button>
          </Form.Item>

          <Form.Item>
            <Button type="link" onClick={() => navigate('/register')} block>
              还没有账号？立即注册
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
};

export default Login; 