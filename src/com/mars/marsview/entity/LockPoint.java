package com.mars.marsview.entity;

public class LockPoint {

	public static int STATE_NORMAL = 0;
	public static int STATE_ERROR = 1;
	public static int STATE_CHECK = 2;
	public float x;
	public float y;
	public int state = 0; // 记录当前点的状态
	public int index = 0;// 记录当前点索引
	
    public LockPoint(float x, float y, int index) {  
        this.setX(x);  
        this.setY(y);  
        this.index = index;  
    } 
    public int getColNum() {  
        return (index - 1) % 3;  
    }  
      
    public int getRowNum() {  
        return (index - 1) / 3;  
    }
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	} 
}
