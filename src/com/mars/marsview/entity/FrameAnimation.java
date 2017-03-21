package com.mars.marsview.entity;

import android.graphics.Bitmap;

public class FrameAnimation {
	/** 动画显示的需要的资源 */
	private Bitmap[] bitmaps;
	/** 动画每帧显示的时间 */
	private int[] duration;
	/** 动画上一帧显示的时间 */
	protected Long lastBitmapTime;
	/** 动画显示的索引值，防止数组越界 */
	protected int step;
	/** 动画是否重复播放 */
	protected boolean repeat;
	/** 动画重复播放的次数 */
	protected int repeatCount;

	/**
	 *  * @param bitmap:显示的图片<br/>
	 *  * @param duration:图片显示的时间<br/>
	 *  * @param repeat:是否重复动画过程<br/>
	 *  
	 */
	public FrameAnimation(Bitmap[] bitmaps, int duration[], boolean repeat) {
		this.bitmaps = bitmaps;
		this.duration = duration;
		this.repeat = repeat;
		lastBitmapTime = null;
		step = 0;
	}

	public Bitmap nextFrame() {
		// 判断step是否越界
		if (step >= bitmaps.length) {
			// 如果不无限循环
			if (!repeat) {
				return null;
			} else {
				lastBitmapTime = null;
			}
		}

		if (null == lastBitmapTime) {
			// 第一次执行
			lastBitmapTime = System.currentTimeMillis();
			return bitmaps[step = 0];
		}

		// 第X次执行
		long nowTime = System.currentTimeMillis();
		if (nowTime - lastBitmapTime <= duration[step]) {
			// 如果还在duration的时间段内,则继续返回当前Bitmap
			// 如果duration的值小于0,则表明永远不失效,一般用于背景
			return bitmaps[step];
		}
		lastBitmapTime = nowTime;
		return bitmaps[step++];// 返回下一Bitmap
	}

}
