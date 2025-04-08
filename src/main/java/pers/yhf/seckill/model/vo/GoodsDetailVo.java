package pers.yhf.seckill.vo;

import pers.yhf.seckill.domain.SeckillUser;

public class GoodsDetailVo {

	private int miaoshaStatus = 0;
	
    private int remainSeconds = 0;
    
    private GoodsVo goods;
    
    private SeckillUser user;
    

	public SeckillUser getUser() {
		return user;
	}

	public void setUser(SeckillUser user) {
		this.user = user;
	}

	public int getMiaoshaStatus() {
		return miaoshaStatus;
	}

	public void setMiaoshaStatus(int miaoshaStatus) {
		this.miaoshaStatus = miaoshaStatus;
	}

	public int getRemainSeconds() {
		return remainSeconds;
	}

	public void setRemainSeconds(int remainSeconds) {
		this.remainSeconds = remainSeconds;
	}

	public GoodsVo getGoods() {
		return goods;
	}

	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}
	
}
