package com.mars.marsview.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class ImageMannager {
	public static final String dir = "Text2Img";
	public static final String ACCESS_ERROR = "access_error";
	public static final String FILE_ERROR = "file_error";
	public static final String SAVE_ERROR = "save_error";

	public static byte[] saveFileHighQuality(Bitmap bm) {
		String fileName = "测试.bmp";
		/*
		 * if (!Environment.MEDIA_MOUNTED.equals(Environment
		 * .getExternalStorageState())) { return null; } File file = new
		 * File(Environment.getExternalStorageDirectory() + File.separator + dir
		 * + File.separator + fileName); System.out.println(file.getPath()); if
		 * (!file.getParentFile().exists()) { file.getParentFile().mkdirs(); }
		 * FileOutputStream out = null; try { out = new FileOutputStream(file);
		 * } catch (FileNotFoundException e) { e.printStackTrace(); return null;
		 * }
		 */

		int w = bm.getWidth();
		int h = bm.getHeight();
		int[] pixels = new int[w * h];
		bm.getPixels(pixels, 0, w, 0, 0, w, h);

		byte[] rgb = addBMP_RGB_888(pixels, w, h);
		byte[] header = addBMPImageHeader(rgb.length);
		byte[] infos = addBMPImageInfosHeader(w, h);

		byte[] buffer = new byte[rgb.length];
		// System.arraycopy(header, 0, buffer, 0, header.length);
		// System.arraycopy(infos, 0, buffer, 14, infos.length);
		System.arraycopy(rgb, 0, buffer, 0, rgb.length);
		/*
		 * try { out.write(buffer); out.flush(); out.close(); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); return null; }
		 */

		Utils.LogShow("bitmap数据", Utils.bytesToHexString(buffer));

		return buffer;
	}

	public static byte[] getBitmapbyte(Bitmap bmp) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();// 初始化一个流对象
		bmp.compress(CompressFormat.PNG, 100, output);// 把bitmap100%高质量压缩 到
														// output对象
		byte[] result = output.toByteArray();// 转换成功了
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		bmp.recycle();// 自由选择是否进行回收
		return result;
	}

	public static byte[] addBMP_RGB_888(int[] b, int w, int h) {
		int len = b.length;
		byte[] buffer = new byte[w * h * 4];
		int offset = 0;
		for (int i = len - 1; i >= w; i -= w) {
			int end = i, start = i - w + 1;
			for (int j = start; j <= end; j++) {
				buffer[offset] = (byte) (b[j] >> 0);
				buffer[offset + 1] = (byte) (b[j] >> 8);
				buffer[offset + 2] = (byte) (b[j] >> 16);
				buffer[offset + 3] = (byte) (b[j] >> 24);
				offset += 4;
			}
		}
		return buffer;
	}

	// BMP文件信息头
	public static byte[] addBMPImageInfosHeader(int w, int h) {
		byte[] buffer = new byte[40];
		// 这个是固定的 BMP 信息头要40个字节
		buffer[0] = 0x28;
		buffer[1] = 0x00;
		buffer[2] = 0x00;
		buffer[3] = 0x00;
		// 宽度 地位放在序号前的位置 高位放在序号后的位置
		buffer[4] = (byte) (w >> 0);
		buffer[5] = (byte) (w >> 8);
		buffer[6] = (byte) (w >> 16);
		buffer[7] = (byte) (w >> 24);
		// 长度 同上
		buffer[8] = (byte) (h >> 0);
		buffer[9] = (byte) (h >> 8);
		buffer[10] = (byte) (h >> 16);
		buffer[11] = (byte) (h >> 24);
		// 总是被设置为1
		buffer[12] = 0x01;
		buffer[13] = 0x00;
		// 比特数 像素 32位保存一个比特 这个不同的方式(ARGB 32位 RGB24位不同的!!!!)
		buffer[14] = 0x20;
		buffer[15] = 0x00;
		// 0-不压缩 1-8bit位图
		// 2-4bit位图 3-16/32位图
		// 4 jpeg 5 png
		buffer[16] = 0x00;
		buffer[17] = 0x00;
		buffer[18] = 0x00;
		buffer[19] = 0x00;
		// 说明图像大小
		buffer[20] = 0x00;
		buffer[21] = 0x00;
		buffer[22] = 0x00;
		buffer[23] = 0x00;
		// 水平分辨率
		buffer[24] = 0x00;
		buffer[25] = 0x00;
		buffer[26] = 0x00;
		buffer[27] = 0x00;
		// 垂直分辨率
		buffer[28] = 0x00;
		buffer[29] = 0x00;
		buffer[30] = 0x00;
		buffer[31] = 0x00;
		// 0 使用所有的调色板项
		buffer[32] = 0x00;
		buffer[33] = 0x00;
		buffer[34] = 0x00;
		buffer[35] = 0x00;
		// 不开颜色索引
		buffer[36] = 0x00;
		buffer[37] = 0x00;
		buffer[38] = 0x00;
		buffer[39] = 0x00;
		return buffer;
	}

	// BMP文件头
	public static byte[] addBMPImageHeader(int size) {
		byte[] buffer = new byte[14];
		// magic number 'BM'
		buffer[0] = 0x42;
		buffer[1] = 0x4D;
		// 记录大小
		buffer[2] = (byte) (size >> 0);
		buffer[3] = (byte) (size >> 8);
		buffer[4] = (byte) (size >> 16);
		buffer[5] = (byte) (size >> 24);
		buffer[6] = 0x00;
		buffer[7] = 0x00;
		buffer[8] = 0x00;
		buffer[9] = 0x00;
		buffer[10] = 0x36;
		buffer[11] = 0x00;
		buffer[12] = 0x00;
		buffer[13] = 0x00;
		return buffer;
	}
}
