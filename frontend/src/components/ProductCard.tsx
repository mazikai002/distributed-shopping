import React from 'react';
import { Card, Button, Rate } from 'antd';
import { useNavigate } from 'react-router-dom';
import { ShoppingCartOutlined } from '@ant-design/icons';

interface ProductCardProps {
  id: string | number;
  title: string;
  price: number;
  originalPrice?: number;
  imageUrl: string;
  shop?: string;
  rating?: number;
  salesCount?: number;
  isNew?: boolean;
  hasDiscount?: boolean;
  discount?: string;
}

const ProductCard: React.FC<ProductCardProps> = ({
  id,
  title,
  price,
  originalPrice,
  imageUrl,
  shop,
  rating = 5,
  salesCount,
  isNew = false,
  hasDiscount = false,
  discount,
}) => {
  const navigate = useNavigate();

  const handleProductClick = () => {
    navigate(`/product/${id}`);
  };

  const handleAddToCart = (e: React.MouseEvent) => {
    e.stopPropagation();
    // 这里可以添加将商品添加到购物车的逻辑
    console.log(`商品 ${id} 添加到购物车`);
  };

  return (
    <Card
      className="product-card"
      hoverable
      cover={
        <div className="product-image-container" onClick={handleProductClick}>
          <img alt={title} src={imageUrl} className="product-image" />
          {isNew && <div className="product-tag new-tag">新品</div>}
          {hasDiscount && <div className="product-tag discount-tag">{discount}</div>}
        </div>
      }
      bodyStyle={{ padding: '12px' }}
    >
      <div className="product-price" onClick={handleProductClick}>
        <span className="price-symbol">¥</span>
        <span className="price-value">{price.toFixed(2)}</span>
        {originalPrice && (
          <span className="original-price">¥{originalPrice.toFixed(2)}</span>
        )}
      </div>
      <div className="product-title" onClick={handleProductClick}>
        {title}
      </div>
      {shop && (
        <div className="product-shop">
          <span>{shop}</span>
        </div>
      )}
      <div className="product-footer">
        <div className="product-rating">
          <Rate disabled defaultValue={rating} className="small-rate" />
          {salesCount && <span className="sales-count">已售{salesCount}件</span>}
        </div>
        <Button
          type="primary"
          shape="circle"
          icon={<ShoppingCartOutlined />}
          size="small"
          className="add-to-cart-btn"
          onClick={handleAddToCart}
        />
      </div>
    </Card>
  );
};

export default ProductCard; 