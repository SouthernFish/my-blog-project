package com.tong.service.blog.common.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片工具类
 * 
 * @author chentianjin
 * @date 2019年1月22日
 */
public class ImageUtils {

	// 缩略图默认长宽限制
	private static final int THUMBNAIL_DEFAULT_LIMIT = 400;
	/** 水印透明度  */ 
    private static float alpha = 0.6f;

	private ImageUtils() {
	}

	/**
	 * 根据长宽值缩放
	 * 
	 * @author sunk
	 */
	public static BufferedImage scaleByWh(BufferedImage source, int width, int height) {
		return getBufferedImageLocal(source.getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	/**
	 * 根据长宽限制缩放
	 * <p>
	 * 限制较大的一边
	 * 
	 * @author sunk
	 */
	public static BufferedImage scaleByWhLimit(BufferedImage source, int limit) {
		int scaleW = -1;
		int scaleH = -1;

		if (source.getWidth() > source.getHeight()) {
			scaleW = limit;
		} else {
			scaleH = limit;
		}
		return scaleByWh(source, scaleW, scaleH);
	}

	/**
	 * 根据比例缩放
	 * 
	 * @author sunk
	 */
	public static BufferedImage scaleByRatio(BufferedImage source, double ratio) {

		int w = (int) (source.getWidth() * ratio);
		int h = (int) (source.getHeight() * ratio);
		return scaleByWh(source, w, h);
	}

	/**
	 * 将 Image 转为 BufferedImage
	 * 
	 * @author sunk
	 */
	public static BufferedImage getBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		BufferedImage bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = bufImage.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();

		return bufImage;
	}

	/**
	 * 将 Image 转为 BufferedImage
	 * 
	 * @author sunk
	 */
	public static BufferedImage getBufferedImageLocal(Image img) {

		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage bufImage = gc.createCompatibleImage(img.getWidth(null), img.getHeight(null));

		Graphics2D g2d = bufImage.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();

		return bufImage;
	}

	/**
	 * 获取缩略图
	 * 
	 * @author sunk
	 */
	public static ByteArrayOutputStream getThumbnail(BufferedImage sourceBi, int limit) throws IOException {

		// 缩放
		// 由于Linux上无法使用，顾注释缩放
//		BufferedImage scaledBi = scaleByWhLimit(sourceBi, limit);

		// 压缩
		ByteArrayOutputStream compressedOs = new ByteArrayOutputStream();
		ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
		ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
		jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		jpgWriteParam.setCompressionQuality(0.5f);

		jpgWriter.setOutput(ImageIO.createImageOutputStream(compressedOs));
		jpgWriter.write(null, new IIOImage(sourceBi, null, null), jpgWriteParam);

		return compressedOs;
	}

	/**
	 * 获取缩略图
	 * 
	 * @author sunk
	 */
	public static ByteArrayOutputStream getThumbnail(BufferedImage sourceBi) throws IOException {
		return getThumbnail(sourceBi, THUMBNAIL_DEFAULT_LIMIT);
	}

	/**
	 * 获取缩略图
	 * 
	 * @author sunk
	 */
	public static ByteArrayOutputStream getThumbnail(File sourceFile, int limit) throws IOException {

		return getThumbnail(ImageIO.read(sourceFile), limit);
	}

	/**
	 * 获取缩略图
	 * 
	 * @author sunk
	 */
	public static ByteArrayOutputStream getThumbnail(File source) throws IOException {

		return getThumbnail(ImageIO.read(source));
	}

	/**
	 * 对图片进行旋转
	 *
	 * @param src   被旋转图片
	 * @param angel 旋转角度
	 * @return 旋转后的图片
	 */
	public static BufferedImage Rotate(Image src, int angel) {
		int src_width = src.getWidth(null);
		int src_height = src.getHeight(null);
		// 计算旋转后图片的尺寸
		Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);
		BufferedImage res = null;
		res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = res.createGraphics();
		// 进行转换
		g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

		g2.drawImage(src, null, null);
		return res;
	}

	/**
	 * 计算旋转后的图片
	 *
	 * @param src   被旋转的图片
	 * @param angel 旋转角度
	 * @return 旋转后的图片
	 */
	public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
		// 如果旋转的角度大于90度做相应的转换
		if (angel >= 90) {
			if (angel / 90 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new Rectangle(new Dimension(des_width, des_height));
	}

	/**
	 * 给图片添加水印图片、可设置水印图片旋转角度
	 * 
	 * @param iconPath   水印图片路径
	 * @param srcImgPath 源图片路径
	 * @param targerPath 目标图片路径
	 * @param degree     水印图片旋转角度
	 */
	public static void markImageByIcon(String iconPath, String srcImgPath, String targerPath, Integer degree) {
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));
			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);

			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(srcImgWidth, srcImgHeight, Image.SCALE_SMOOTH), 0, 0, null);
			if (null != degree) {
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}

			ImageIcon imgIcon = null;
			int iconOffsetX = 30, iconOffsetY = 30;
			// 根据图片的大小，选择相应的logo的大小
			if ((srcImgWidth <= 200 && srcImgHeight <= 200)) {
				imgIcon = new ImageIcon(iconPath.replace("logo1.png", "logo3.png"));
				iconOffsetX = 8;
				iconOffsetY = 8;
			} else if ((srcImgWidth <= 750 && srcImgHeight <= 550)) {
				imgIcon = new ImageIcon(iconPath.replace("logo1.png", "logo2.png"));
				iconOffsetX = 25;
				iconOffsetY = 25;
			} else {
				imgIcon = new ImageIcon(iconPath);
			}
			Image img = imgIcon.getImage();
			int iconWidth = img.getWidth(null);
			int iconHeight = img.getHeight(null);

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			// 水印图片的位置
			g.drawImage(img, srcImgWidth - iconWidth - iconOffsetX, srcImgHeight - iconHeight - iconOffsetY, null); // 右下
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();

			os = new FileOutputStream(targerPath);
			ImageIO.write(buffImg, "JPG", os);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		markImageByIcon("C:\\Users\\tk t440s3\\Desktop\\2.png", "C:\\Users\\tk t440s3\\Desktop\\123.png", "C:\\Users\\tk t440s3\\Desktop\\2222.png", null);
	}
}
