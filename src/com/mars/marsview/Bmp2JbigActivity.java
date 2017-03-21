package com.mars.marsview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.DeviceView;
import com.mars.marsview.view.FloatView;

public class Bmp2JbigActivity extends BaseActivity implements OnClickListener {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private Button bmg2jbigBt;
	private DeviceView myDeviceView;

	private FloatView floatView;
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mLayout;
	private long startTime;
	// 声明屏幕的宽高
	float x, y;
	int top;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_bmp2jbig);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		bmg2jbigBt = (Button) this.findViewById(R.id.bmg2jbigBt);
		myDeviceView = (DeviceView) this.findViewById(R.id.myDeviceView);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		bmg2jbigBt.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		mContext = this;

	}

	@Override
	public void callFunc() {
		title_tv.setText("Bmp2jbig");
		myDeviceView.setTips("正在连接设备...");
		createWindowManager();
		createDesktopLayout();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Utils.closeActivity(this, 0);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private boolean stopFlag = true;

	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.title_backlayout:
				Utils.closeActivity(this, 0);
				break;
			case R.id.bmg2jbigBt:
				if (stopFlag) {
					stopFlag = false;
				} else {
					stopFlag = true;
				}
				myDeviceView.setStopFlag(stopFlag);
//				showDesk();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建悬浮窗体
	 */
	private void createDesktopLayout() {
		floatView = new FloatView(this);
		floatView.setOnTouchListener(new OnTouchListener() {
			float mTouchStartX;
			float mTouchStartY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 获取相对屏幕的坐标，即以屏幕左上角为原点
				x = event.getRawX();
				y = event.getRawY() - top; // 25是系统状态栏的高度
				Log.i("startP", "startX" + mTouchStartX + "====startY"
						+ mTouchStartY);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// 获取相对View的坐标，即以此View左上角为原点
					mTouchStartX = event.getX();
					mTouchStartY = event.getY();
					Log.i("startP", "startX" + mTouchStartX + "====startY"
							+ mTouchStartY);
					long end = System.currentTimeMillis() - startTime;
					// 双击的间隔在 300ms以下
					if (end < 300) {
						closeDesk();
					}
					startTime = System.currentTimeMillis();
					break;
				case MotionEvent.ACTION_MOVE:
					// 更新浮动窗口位置参数
					mLayout.x = (int) (x - mTouchStartX);
					mLayout.y = (int) (y - mTouchStartY);
					mWindowManager.updateViewLayout(v, mLayout);
					break;
				case MotionEvent.ACTION_UP:

					// 更新浮动窗口位置参数
					mLayout.x = (int) (x - mTouchStartX);
					mLayout.y = (int) (y - mTouchStartY);
					mWindowManager.updateViewLayout(v, mLayout);

					// 可以在此记录最后一次的位置

					mTouchStartX = mTouchStartY = 0;
					break;
				}
				return true;
			}
		});
	}

	/**
	 * 显示DesktopLayout
	 */
	private void showDesk() {
		mWindowManager.addView(floatView, mLayout);
		finish();
	}

	/**
	 * 关闭DesktopLayout
	 */
	private void closeDesk() {
		mWindowManager.removeView(floatView);
		finish();
	}

	/**
	 * 设置WindowManager
	 */
	private void createWindowManager() {
		// 取得系统窗体
		mWindowManager = (WindowManager) getApplicationContext()
				.getSystemService("window");

		// 窗体的布局样式
		mLayout = new WindowManager.LayoutParams();

		// 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
		mLayout.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

		// 设置窗体焦点及触摸：
		// FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
		mLayout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

		// 设置显示的模式
		mLayout.format = PixelFormat.RGBA_8888;

		// 设置对齐的方法
		mLayout.gravity = Gravity.TOP | Gravity.LEFT;

		// 设置窗体宽度和高度
		mLayout.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mLayout.height = WindowManager.LayoutParams.WRAP_CONTENT;

	}

}
