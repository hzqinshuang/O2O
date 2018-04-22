package com.qinshuang.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	public static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	/**
	 * 将CommonsMutipartFile类型文件转换为File类型，并且返回转换后的File
	 * 
	 * @return
	 */
	public static File transferCommonsMutipartFiletoFile(CommonsMultipartFile cFile) {
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}

	/**
	 * 将用户上传的图片编辑为缩略图，并且返回新增图片相对路径
	 * 
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 * @return
	 */
	public static String generateTumbnail(File thumbnail, String targetAddr) {
		// 返回修改后图片的随机名称
		String realFileName = getRandomFileName();
		// 返回修改后图片的扩展名称
		String extension = getFileExtension(thumbnail);
		// 创建修改图片保存的目录
		makeDirPath(targetAddr);
		// 修改图片的绝对路径
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is:" + relativeAddr);
		File dest = new File(PathUtil.getImgBasePah() + relativeAddr);
		logger.debug("current complete is:" + PathUtil.getImgBasePah() + relativeAddr);
		try {
			Thumbnails.of(thumbnail).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;
	}

	/**
	 * 创建目标路径所涉及到的目录，即/Users/coco/Pictures/image/notebook.jpeg
	 * User，coco，Pictures，image这四个文件夹都要创建
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePah() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

	}

	private static String getFileExtension(File cFile) {
		String originalFileName = cFile.getName();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
	 * 
	 * @return
	 */
	private static String getRandomFileName() {
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date()) + rannum;
		return nowTimeStr;
	}

	public static void main(String[] args) {
		// 获取水印图片的路径，通过获取当前线程，再获取当前类加载器，再获取资源的路径
		try {
			Thumbnails.of(new File("/Users/coco/Pictures/image/notebook.jpeg")).size(400, 400)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
					.outputQuality(0.8f).toFile("/Users/coco/Pictures/image/newnotebook.jpeg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
