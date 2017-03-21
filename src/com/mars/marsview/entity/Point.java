package com.mars.marsview.entity;

public class Point {
	private float x;
	private float y;
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}
	public Point(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
}
