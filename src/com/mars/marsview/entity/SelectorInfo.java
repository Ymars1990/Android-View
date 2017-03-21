package com.mars.marsview.entity;

public class SelectorInfo {
	private boolean isSelected = false;
	private String selectorDesc;
	public SelectorInfo(boolean isSelected, String selectorDesc) {
		super();
		this.isSelected = isSelected;
		this.selectorDesc = selectorDesc;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SelectorInfo [isSelected=" + isSelected + ", selectorDesc="
				+ selectorDesc + "]";
	}
	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}
	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	/**
	 * @return the selectorDesc
	 */
	public String getSelectorDesc() {
		return selectorDesc;
	}
	/**
	 * @param selectorDesc the selectorDesc to set
	 */
	public void setSelectorDesc(String selectorDesc) {
		this.selectorDesc = selectorDesc;
	}
	
}
