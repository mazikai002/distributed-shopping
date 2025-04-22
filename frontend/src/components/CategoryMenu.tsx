import React, { useState } from 'react';
import { Menu } from 'antd';
import { useNavigate } from 'react-router-dom';
import {
  LaptopOutlined,
  MobileOutlined,
  TabletOutlined,
  SkinOutlined,
  HomeOutlined,
  GiftOutlined,
  CarOutlined,
  BulbOutlined,
  ToolOutlined,
  BookOutlined
} from '@ant-design/icons';

interface CategoryMenuProps {
  className?: string;
}

const CategoryMenu: React.FC<CategoryMenuProps> = ({ className }) => {
  const navigate = useNavigate();
  const [selectedKey, setSelectedKey] = useState<string>('');
  
  const categories = [
    {
      key: '1',
      icon: <LaptopOutlined />,
      label: '家用电器',
      children: ['电视', '空调', '洗衣机', '冰箱', '厨卫大电', '厨房小电', '生活电器', '个护健康']
    },
    {
      key: '2',
      icon: <MobileOutlined />,
      label: '手机/运营商/数码',
      children: ['手机通讯', '运营商', '手机配件', '摄影摄像', '数码配件']
    },
    {
      key: '3',
      icon: <TabletOutlined />,
      label: '电脑/办公',
      children: ['电脑整机', '电脑配件', '外设产品', '游戏设备', '网络产品', '办公设备', '文具']
    },
    {
      key: '4',
      icon: <HomeOutlined />,
      label: '家居/家装/厨具',
      children: ['厨具', '家纺', '生活日用', '家装软饰', '灯具', '家具', '建材']
    },
    {
      key: '5',
      icon: <SkinOutlined />,
      label: '男装/女装/童装/内衣',
      children: ['女装', '男装', '内衣', '配饰', '童装', '童鞋']
    },
    {
      key: '6',
      icon: <GiftOutlined />,
      label: '美妆/个护清洁/宠物',
      children: ['面部护肤', '香水彩妆', '个人护理', '洗护清洁', '宠物生活']
    },
    {
      key: '7',
      icon: <BookOutlined />,
      label: '图书/文娱/电子书',
      children: ['图书', '童书', '教材教辅', '电子书', '音像', '文娱']
    },
    {
      key: '8',
      icon: <CarOutlined />,
      label: '汽车/汽车用品',
      children: ['汽车整车', '汽车用品', '汽车维修保养', '汽车音响改装', '车载电器']
    },
    {
      key: '9',
      icon: <ToolOutlined />,
      label: '男鞋/运动/户外',
      children: ['运动鞋包', '运动服饰', '健身训练', '骑行运动', '户外鞋服', '户外装备']
    },
    {
      key: '10',
      icon: <BulbOutlined />,
      label: '家电/玩具/乐器',
      children: ['玩具', '乐器', '游戏设备', '游戏/电玩', '手办']
    }
  ];

  const handleMenuClick = (categoryKey: string, subCategory?: string) => {
    setSelectedKey(categoryKey);
    if (subCategory) {
      navigate(`/category/${categoryKey}/${encodeURIComponent(subCategory)}`);
    } else {
      navigate(`/category/${categoryKey}`);
    }
  };

  return (
    <div className={`category-menu ${className || ''}`}>
      <Menu
        mode="vertical"
        selectedKeys={[selectedKey]}
        className="category-menu-list"
      >
        {categories.map(category => (
          <Menu.Item
            key={category.key}
            icon={category.icon}
            onClick={() => handleMenuClick(category.key)}
            className="category-menu-item"
          >
            <div className="category-item-content">
              <span>{category.label}</span>
              <div className="category-sub-menu">
                <div className="sub-menu-content">
                  <h4>{category.label}分类</h4>
                  <ul className="sub-category-list">
                    {category.children.map((subCategory, index) => (
                      <li key={index}>
                        <a onClick={() => handleMenuClick(category.key, subCategory)}>
                          {subCategory}
                        </a>
                      </li>
                    ))}
                  </ul>
                </div>
              </div>
            </div>
          </Menu.Item>
        ))}
      </Menu>
    </div>
  );
};

export default CategoryMenu; 