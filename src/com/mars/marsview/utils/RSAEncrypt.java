package com.mars.marsview.utils;

public class RSAEncrypt {
	static {
		try {
			System.loadLibrary("rsapublickeyencrypt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static native String RSAPublickeyEncrypt(String publicKey,String output);
		
}
