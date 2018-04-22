package com.qinshuang.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qinshuang.o2o.BaseTest;
import com.qinshuang.o2o.entity.Area;
import com.qinshuang.o2o.entity.PersonInfo;
import com.qinshuang.o2o.entity.Shop;
import com.qinshuang.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopDao;

	@Test
	@Ignore
	public void testInsertShop() {
		Area area = new Area();
		PersonInfo owner = new PersonInfo();
		ShopCategory shopCategory = new ShopCategory();
		area.setAreaId(1);
		owner.setUserId(1L);
		shopCategory.setShopCategoryId(1L);
		Shop shop = new Shop();
		shop.setArea(area);
		shop.setOwner(owner);
		shop.setShopCategory(shopCategory);
		shop.setShopName("coco炸鸡店");
		shop.setShopAddr("大观路130");
		shop.setShopDesc("超级美味炸鸡");
		shop.setPhone("1898471282");
		shop.setAdvice("更加便宜");
		shop.setEnableStatus(1);
		shop.setPriority(1);
		shop.setShopImg("商店图片");
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		assertEquals(1,shopDao.insertShop(shop));
	}
	@Test
	public void testUpdateShop() {
		Shop shop=new Shop();
		shop.setShopId(1L);
		shop.setShopName("coco炸鸡店");
		shop.setShopAddr("大观路130");
		shop.setShopDesc("coco美味炸鸡");
		shop.setPhone("15990018515");
		shop.setAdvice("更加便宜,更加多！");
		shop.setLastEditTime(new Date());
		assertEquals(1,shopDao.updateShop(shop));
	}
}
