package com.mars.marsview.entity;

public class Cake {

	private String title;
	private float weight;
	private int color;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	public Cake(String title, float weight, int color) {
		super();
		this.title = title;
		this.weight = weight;
		this.color = color;
	}


	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}


	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}


	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	
}
