package com.qinshuang.o2o.service;

import java.io.File;

import com.qinshuang.o2o.dto.ShopExecution;
import com.qinshuang.o2o.entity.Shop;

public interface ShopService {
	ShopExecution addShop(Shop shop,File shopImg);
}
