package com.mars.marsview.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.mars.marsview.view.GameSurfaceView;

public class Sprite {
	public static final int DOWN = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;

	public float x;
	public float y;
	public int width;
	public int height;
	// ���������ٶ�
	public double speed;
	// ���鵱ǰ���߷���
	public int direction;
	// �����ĸ�����Ķ���
	public FrameAnimation[] frameAnimations;

	public Sprite(FrameAnimation[] frameAnimations, int positionX,
			int positionY, int width, int height, float speed) {
		this.frameAnimations = frameAnimations;
		this.x = positionX;
		this.y = positionY;
		this.width = width;
		this.height = height;
		this.speed = speed;
	}

	public void updatePosition(long deltaTime) {
		switch (direction) {
		case LEFT:
			// ��������ƶ��ٶȲ��ܻ������ܵ�Ӱ��,ÿ֡������Ҫ�ƶ��ľ���Ϊ���ƶ��ٶ�*ʱ����
			this.x = this.x - (float) (this.speed * deltaTime);
			break;
		case DOWN:
			this.y = this.y + (float) (this.speed * deltaTime);
			break;
		case RIGHT:
			this.x = this.x + (float) (this.speed * deltaTime);
			break;
		case UP:
			this.y = this.y - (float) (this.speed * deltaTime);
			break;
		}
	}

	/**
	 * ���ݾ���ĵ�ǰλ���ж��Ƿ�ı����߷���
	 */
	public void setDirection() {
		if (this.x <= 0
				&& (this.y + this.height) < GameSurfaceView.SCREEN_HEIGHT) {
			if (this.x < 0)
				this.x = 0;
			this.direction = Sprite.DOWN;
		} else if ((this.y + this.height) >= GameSurfaceView.SCREEN_HEIGHT
				&& (this.x + this.width) < GameSurfaceView.SCREEN_WIDTH) {
			if ((this.y + this.height) > GameSurfaceView.SCREEN_HEIGHT)
				this.y = GameSurfaceView.SCREEN_HEIGHT - this.height;
			this.direction = Sprite.RIGHT;
		} else if ((this.x + this.width) >= GameSurfaceView.SCREEN_WIDTH
				&& this.y > 0) {
			if ((this.x + this.width) > GameSurfaceView.SCREEN_WIDTH)
				this.x = GameSurfaceView.SCREEN_WIDTH - this.width;
			this.direction = Sprite.UP;
		} else {
			if (this.y < 0)
				this.y = 0;
			this.direction = Sprite.LEFT;
		}

	}

	public void draw(Canvas canvas) {
		FrameAnimation frameAnimation = frameAnimations[this.direction];
		Bitmap bitmap = frameAnimation.nextFrame();
		if (null != bitmap) {
			canvas.drawBitmap(bitmap, x, y, null);
		}
	}
}
