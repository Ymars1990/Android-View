package com.mars.marsview.view;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View.MeasureSpec;

import com.mars.marsview.R;
import com.mars.marsview.entity.FrameAnimation;
import com.mars.marsview.entity.Sprite;

public class GameSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	// 屏幕宽高
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;

	private Context mContext;
	private SurfaceHolder mHolder;
	// 最大帧数 (1000 / 30)
	private static final int DRAW_INTERVAL = 30;

	private DrawThread mDrawThread;
	private FrameAnimation[] spriteAnimations;
	private Sprite mSprite;
	private int spriteWidth = 0;
	private int spriteHeight = 0;
	private float spriteSpeed = (float) ((500 * SCREEN_WIDTH / 480) * 0.001);
	private int row = 4;
	private int col = 4;

	public GameSurfaceView(Context context) {
		this(context, null);
		init(context);
	}

	public GameSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public GameSurfaceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		initResources();
		mSprite = new Sprite(spriteAnimations, 0, 0, spriteWidth, spriteHeight,
				spriteSpeed);
	}

/*	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			SCREEN_WIDTH = specSize;
		}
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			SCREEN_HEIGHT = specSize;
		}
	}*/

	private void initResources() {
		Bitmap[][] spriteImgs = generateBitmapArray(mContext,
				R.drawable.sprite, row, col);
		spriteAnimations = new FrameAnimation[row];
		for (int i = 0; i < row; i++) {
			Bitmap[] spriteImg = spriteImgs[i];
			FrameAnimation spriteAnimation = new FrameAnimation(spriteImg,
					new int[] { 150, 150, 150, 150 }, true);
			spriteAnimations[i] = spriteAnimation;
		}
	}

	public Bitmap decodeBitmapFromRes(Context context, int resourseId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;

		InputStream is = context.getResources().openRawResource(resourseId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	public Bitmap createBitmap(Context context, Bitmap source, int row,
			int col, int rowTotal, int colTotal) {
		Bitmap bitmap = Bitmap.createBitmap(source,
				(col - 1) * source.getWidth() / colTotal,
				(row - 1) * source.getHeight() / rowTotal, source.getWidth()
						/ colTotal, source.getHeight() / rowTotal);
		return bitmap;
	}

	public Bitmap[][] generateBitmapArray(Context context, int resourseId,
			int row, int col) {
		Bitmap bitmaps[][] = new Bitmap[row][col];
		Bitmap source = decodeBitmapFromRes(context, resourseId);
		this.spriteWidth = source.getWidth() / col;
		this.spriteHeight = source.getHeight() / row;
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				bitmaps[i - 1][j - 1] = createBitmap(context, source, i, j,
						row, col);
			}
		}
		if (source != null && !source.isRecycled()) {
			source.recycle();
			source = null;
		}
		return bitmaps;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (null == mDrawThread) {
			mDrawThread = new DrawThread();
			mDrawThread.start();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if (null != mDrawThread) {
			mDrawThread.stopThread();
		}
	}

	private class DrawThread extends Thread {
		public boolean isRunning = false;

		public DrawThread() {
			isRunning = true;
		}

		public void stopThread() {
			isRunning = false;
			boolean workIsNotFinish = true;
			while (workIsNotFinish) {
				try {
					this.join();// 保证run方法执行完毕
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				workIsNotFinish = false;
			}
		}

		public void run() {
			long deltaTime = 0;
			long tickTime = 0;
			tickTime = System.currentTimeMillis();
			while (isRunning) {
				Canvas canvas = null;
				try {
					synchronized (mHolder) {
						canvas = mHolder.lockCanvas();
						// 设置方向
						mSprite.setDirection();
						// 更新精灵位置
						mSprite.updatePosition(deltaTime);
						drawSprite(canvas);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (null != mHolder) {
						mHolder.unlockCanvasAndPost(canvas);
					}
				}

				deltaTime = System.currentTimeMillis() - tickTime;
				if (deltaTime < DRAW_INTERVAL) {
					try {
						Thread.sleep(DRAW_INTERVAL - deltaTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				tickTime = System.currentTimeMillis();
			}

		}
	}

	private void drawSprite(Canvas canvas) {
		// 清屏操作
		canvas.drawColor(Color.BLACK);
		mSprite.draw(canvas);
	}

}