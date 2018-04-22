package com.qinshuang.o2o.dao;

import com.qinshuang.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 新增店铺
	 * @param shop
	 * @return 
	 */
	int insertShop(Shop shop);
	/**
	 * 修改店铺
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
