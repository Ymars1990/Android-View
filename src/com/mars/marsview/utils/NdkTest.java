package com.mars.marsview.utils;

public class NdkTest {
	static {
		try {
			System.loadLibrary("hello");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static native String getStringFromc();
}
