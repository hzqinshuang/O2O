package com.qinshuang.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qinshuang.o2o.BaseTest;
import com.qinshuang.o2o.dto.ShopExecution;
import com.qinshuang.o2o.entity.Area;
import com.qinshuang.o2o.entity.PersonInfo;
import com.qinshuang.o2o.entity.Shop;
import com.qinshuang.o2o.entity.ShopCategory;
import com.qinshuang.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testAddShop() {
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
		shop.setShopName("qinshuang炸鸡店");
		shop.setShopAddr("大观路131");
		shop.setShopDesc("超级美味炸鸡");
		shop.setPhone("1898471282");
		shop.setAdvice("更加便宜");
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setPriority(1);
		File shopImg=new File("/Users/coco/Pictures/image/notebook.jpeg");
		ShopExecution se=shopService.addShop(shop, shopImg);
		assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
	}
}
