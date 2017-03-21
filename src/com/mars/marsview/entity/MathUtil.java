package com.mars.marsview.entity;

public class MathUtil {
	public static double distance(float startx, float starty, float endx,
			float endy) {

		return Math.sqrt(Math.pow(endx - startx, 2)
				+ Math.pow(endy - starty, 2));
	}

	public static double pointTotoDegrees(double x, double y) {
		return Math.toDegrees(Math.atan2(x, y));
	}

	public static boolean checkInRound(float sx, float sy, float r, float x,
			float y) {
		return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
	}
}
