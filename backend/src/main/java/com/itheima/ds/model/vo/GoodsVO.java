package com.itheima.ds.model.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.itheima.ds.model.entity.Goods;

/**
 * 商品VO，扩展了秒杀相关属性
 */
public class GoodsVO extends Goods {
	private static final long serialVersionUID = 1L;
	
	private Goods goods;
	
	private BigDecimal seckillPrice;
	
	private Integer stockCount;
	
	private Date startDate;
	
	private Date endDate;

	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}


	public BigDecimal getSeckillPrice() {
		return seckillPrice;
	}

	public void setSeckillPrice(BigDecimal seckillPrice) {
		this.seckillPrice = seckillPrice;
	}
	
	
	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "GoodsVO{" +
				"id=" + getId() +
				", goodsName='" + getGoodsName() + '\'' +
				", seckillPrice=" + seckillPrice +
				", stockCount=" + stockCount +
				", startDate=" + startDate +
				", endDate=" + endDate +
				'}';
	}
}