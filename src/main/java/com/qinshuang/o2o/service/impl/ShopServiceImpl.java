package com.qinshuang.o2o.service.impl;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qinshuang.o2o.dao.ShopDao;
import com.qinshuang.o2o.dto.ShopExecution;
import com.qinshuang.o2o.entity.Shop;
import com.qinshuang.o2o.enums.ShopStateEnum;
import com.qinshuang.o2o.execeptions.ShopOperationException;
import com.qinshuang.o2o.service.ShopService;
import com.qinshuang.o2o.util.ImageUtil;
import com.qinshuang.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, File shopImg) {
		//判断空值
		if(shop==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//给店铺信息赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加店铺信息
			int effectedNum=shopDao.insertShop(shop);
			if(effectedNum<=0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				if(shopImg!=null) {
					//存储图片
					try {
						addShopImg(shop,shopImg);
					}catch(Exception e) {
						throw new ShopOperationException("addShopImg error:"+e.getMessage());
					}
					//跟新店铺的图片地址
					effectedNum=shopDao.updateShop(shop);
					if(effectedNum<=0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		}catch(Exception e) {
			throw new ShopOperationException("addShop error:"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}
	private void addShopImg(Shop shop, File shopImg) {
		//获取shop图片目录的相对值路径
		String dest=PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr=ImageUtil.generateTumbnail(shopImg, dest);
		shop.setShopImg(shopImgAddr);
	}

}
