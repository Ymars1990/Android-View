package com.mars.marsview.entity;

public class GridViewInfo {
	private String imgUrl;
	private String playerName;

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl
	 *            the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public GridViewInfo(String playerName, String imgUrl) {
		super();
		this.imgUrl = imgUrl;
		this.playerName = playerName;
	}

}
