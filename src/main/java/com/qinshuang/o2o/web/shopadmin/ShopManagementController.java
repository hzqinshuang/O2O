package com.qinshuang.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qinshuang.o2o.dto.ShopExecution;
import com.qinshuang.o2o.entity.PersonInfo;
import com.qinshuang.o2o.entity.Shop;
import com.qinshuang.o2o.enums.ShopStateEnum;
import com.qinshuang.o2o.service.ShopService;
import com.qinshuang.o2o.util.HttpServletRequestUtil;
import com.qinshuang.o2o.util.ImageUtil;
import com.qinshuang.o2o.util.PathUtil;
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	/**
	 * 注册店铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/registershop",method=RequestMethod.POST)
	private Map<String,Object> registerShop(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		//1 接受并且转换前台获得的参数。
		Shop shop=null;
		String shopStr=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			shopStr=HttpServletRequestUtil.getString(request, "shopStr");
			shop=mapper.readValue("shopStr", Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			e.printStackTrace();
			return modelMap;
		}
		CommonsMultipartFile shopImg=null;
		//文件解析器
		CommonsMultipartResolver CommonsMultipartResolver=new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//判断上传的request中是否存在文件流
		if(CommonsMultipartResolver.isMultipart(request)) {
			//如果存在文件流 就转换为MultipartHttpServletRequest类
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
			//获取文件流中名称为shopimg的文件
			shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		
		//2 注册店铺
		if(shop!=null&&shopImg!=null) {
			PersonInfo owner =new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);
			File shopImgFile=new File(PathUtil.getImgBasePah()+ImageUtil.getRandomFileName());
			try {
				shopImgFile.createNewFile();
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			try {
				this.inputStreamToFile(shopImg.getInputStream(), shopImgFile);
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			ShopExecution se=shopService.addShop(shop, shopImgFile);
			if(se.getState()==ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入注册信息");
			return modelMap;
		}
	}
	
	private static void inputStreamToFile(InputStream ins,File file) {
		FileOutputStream os=null;
		try {
			os=new FileOutputStream(file);
			int bytesRead=0;
			byte[] buffer=new byte[1024];
			while((bytesRead=ins.read(buffer))!=-1) {
				os.write(buffer, 0, bytesRead);
			}
		}catch(Exception e) {
			throw new RuntimeException("调用inputStreamToFile失败："+e.getMessage());
		}finally {
			try {
				if(ins!=null) {
					ins.close();
				}if(os!=null) {
					os.close();
				}
			}catch(IOException e) {
				throw new RuntimeException("调用inputStreamToFile关闭io产生异常："+e.getMessage());
			}
			
		}
		

	}
}
