package com.qinshuang.o2o.util;

public class PathUtil {
	//获取系统目录分割符
	private static String seperator=System.getProperty("file.separator");
	/**
	 * 返回上传图片的根路径
	 * @return
	 */
	public static String getImgBasePah() {
		String os=System.getProperty("os.name");
		String basePath="";
		if(os.toLowerCase().startsWith("win")) {
			basePath="D:/projectdev/image/";
		}else {
			basePath="/Users/coco/Pictures/image/";
		}
		basePath=basePath.replaceAll("/", seperator);
		return basePath;
	}
	
	/**
	 * 返回上传图片的子路径
	 * @return
	 */
	public static  String getShopImagePath(long shopId) {
		String imagePath="upload/item/shop/"+shopId+"/";
		return imagePath.replaceAll("/", seperator);
	}
}
