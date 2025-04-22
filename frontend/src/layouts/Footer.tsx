import React from 'react';
import { Layout, Row, Col } from 'antd';

const { Footer } = Layout;

// 帮助中心链接数据
const helpLinks = [
  { title: '购物指南', links: ['购物流程', '会员介绍', '生活旅行', '常见问题', '大家电', '联系客服'] },
  { title: '配送方式', links: ['上门自提', '211限时达', '配送服务查询', '配送费收取标准', '海外配送'] },
  { title: '支付方式', links: ['货到付款', '在线支付', '分期付款', '公司转账', '银行电汇'] },
  { title: '售后服务', links: ['售后政策', '价格保护', '退款说明', '返修/退换货', '取消订单'] },
  { title: '特色服务', links: ['夺宝岛', 'DIY装机', '延保服务', '京东E卡', '京东通信'] },
];

// 底部导航链接
const footerNavLinks = [
  '关于我们', '联系我们', '联系客服', '合作招商', '商家帮助', '营销中心',
  '友情链接', '销售联盟', '风险监测', '隐私政策', '京东公益', 'English Site'
];

export const AppFooter: React.FC = () => {
  const currentYear = new Date().getFullYear();
  
  return (
    <Footer style={{ padding: 0, backgroundColor: '#fff', marginTop: 20 }}>
      {/* 服务保障区域 */}
      <div className="service-guarantee" style={{ borderBottom: '1px solid #e6e6e6', padding: '30px 0' }}>
        <Row justify="center" gutter={[0, 16]}>
          <Col span={4} style={{ textAlign: 'center' }}>
            <div style={{ fontSize: 18, fontWeight: 'bold', color: '#444' }}>
              <i className="anticon" style={{ marginRight: 10 }}>★</i>
              品类齐全，轻松购物
            </div>
          </Col>
          <Col span={4} style={{ textAlign: 'center' }}>
            <div style={{ fontSize: 18, fontWeight: 'bold', color: '#444' }}>
              <i className="anticon" style={{ marginRight: 10 }}>★</i>
              多仓直发，极速配送
            </div>
          </Col>
          <Col span={4} style={{ textAlign: 'center' }}>
            <div style={{ fontSize: 18, fontWeight: 'bold', color: '#444' }}>
              <i className="anticon" style={{ marginRight: 10 }}>★</i>
              正品行货，精致服务
            </div>
          </Col>
          <Col span={4} style={{ textAlign: 'center' }}>
            <div style={{ fontSize: 18, fontWeight: 'bold', color: '#444' }}>
              <i className="anticon" style={{ marginRight: 10 }}>★</i>
              天天低价，畅选无忧
            </div>
          </Col>
        </Row>
      </div>
      
      {/* 帮助中心区域 */}
      <div className="help-center" style={{ borderBottom: '1px solid #e6e6e6', padding: '30px 0' }}>
        <Row justify="center">
          {helpLinks.map((group, index) => (
            <Col key={index} span={4}>
              <h4 style={{ fontSize: 14, fontWeight: 'bold', marginBottom: 15 }}>{group.title}</h4>
              <ul style={{ listStyle: 'none', padding: 0, margin: 0 }}>
                {group.links.map((link, i) => (
                  <li key={i} style={{ lineHeight: '2' }}>
                    <a href="#" style={{ color: '#666', fontSize: 12 }}>{link}</a>
                  </li>
                ))}
              </ul>
            </Col>
          ))}
          <Col span={4}>
            <h4 style={{ fontSize: 14, fontWeight: 'bold', marginBottom: 15 }}>京东自营覆盖区县</h4>
            <p style={{ color: '#666', fontSize: 12, lineHeight: '1.8' }}>
              京东已向全国2661个区县提供自营配送服务，支持货到付款、POS机刷卡和售后上门服务。
            </p>
            <a href="#" style={{ color: '#c81623', fontSize: 12 }}>查看详情 &gt;</a>
          </Col>
        </Row>
      </div>
      
      {/* 底部导航 */}
      <div className="footer-nav" style={{ borderBottom: '1px solid #e6e6e6', padding: '15px 0', textAlign: 'center' }}>
        <div style={{ fontSize: 12, color: '#666' }}>
          {footerNavLinks.map((link, index) => (
            <React.Fragment key={index}>
              <a href="#" style={{ color: '#666' }}>{link}</a>
              {index < footerNavLinks.length - 1 && <span style={{ margin: '0 10px' }}>|</span>}
            </React.Fragment>
          ))}
        </div>
      </div>
      
      {/* 版权信息 */}
      <div className="copyright" style={{ padding: '15px 0', textAlign: 'center' }}>
        <p style={{ margin: '5px 0', fontSize: 12, color: '#999' }}>
          Copyright © 2004-{currentYear} 京东JD.com 版权所有
        </p>
        <p style={{ margin: '5px 0', fontSize: 12, color: '#999' }}>
          京ICP备11041704号-15 | 京公网安备11010802028762号
        </p>
      </div>
    </Footer>
  );
};

export default AppFooter; 