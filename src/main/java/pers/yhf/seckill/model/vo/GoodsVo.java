package pers.yhf.seckill.vo;

import java.math.BigDecimal;
import java.util.Date;

import pers.yhf.seckill.domain.Goods;

/**
 * 秒杀商品VO
 * @author Yin_22
 *
 */
public class GoodsVo extends Goods{
	
	private Goods goods;
	
	private  BigDecimal seckillPrice;
	
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

}
