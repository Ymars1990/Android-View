package com.mars.marsview.utils;

public class Bmp2JbigMannger {
	static {
		try {
			System.loadLibrary("bmptojbig");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static native byte[] getByteDataFromc(byte[] bmpData, int width, int heiht,
			int []lenghth);
}
