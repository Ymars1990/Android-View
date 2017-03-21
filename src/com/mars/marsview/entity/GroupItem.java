package com.mars.marsview.entity;

public class GroupItem {
	private String ItemConent;
	private String ItemConentIcon;
	
	public String getItemConent() {
		return ItemConent;
	}
	public void setItemConent(String itemConent) {
		ItemConent = itemConent;
	}
	public String getItemConentIcon() {
		return ItemConentIcon;
	}
	public void setItemConentIcon(String itemConentIcon) {
		ItemConentIcon = itemConentIcon;
	}
	
	public GroupItem(String itemConent, String itemConentIcon) {
		this.ItemConent = itemConent;
		this.ItemConentIcon = itemConentIcon;
	}
	
}
