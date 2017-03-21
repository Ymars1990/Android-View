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
	// ������Ļ�Ŀ��
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
		myDeviceView.setTips("���������豸...");
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
	 * ������������
	 */
	private void createDesktopLayout() {
		floatView = new FloatView(this);
		floatView.setOnTouchListener(new OnTouchListener() {
			float mTouchStartX;
			float mTouchStartY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// ��ȡ�����Ļ�����꣬������Ļ���Ͻ�Ϊԭ��
				x = event.getRawX();
				y = event.getRawY() - top; // 25��ϵͳ״̬���ĸ߶�
				Log.i("startP", "startX" + mTouchStartX + "====startY"
						+ mTouchStartY);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// ��ȡ���View�����꣬���Դ�View���Ͻ�Ϊԭ��
					mTouchStartX = event.getX();
					mTouchStartY = event.getY();
					Log.i("startP", "startX" + mTouchStartX + "====startY"
							+ mTouchStartY);
					long end = System.currentTimeMillis() - startTime;
					// ˫���ļ���� 300ms����
					if (end < 300) {
						closeDesk();
					}
					startTime = System.currentTimeMillis();
					break;
				case MotionEvent.ACTION_MOVE:
					// ���¸�������λ�ò���
					mLayout.x = (int) (x - mTouchStartX);
					mLayout.y = (int) (y - mTouchStartY);
					mWindowManager.updateViewLayout(v, mLayout);
					break;
				case MotionEvent.ACTION_UP:

					// ���¸�������λ�ò���
					mLayout.x = (int) (x - mTouchStartX);
					mLayout.y = (int) (y - mTouchStartY);
					mWindowManager.updateViewLayout(v, mLayout);

					// �����ڴ˼�¼���һ�ε�λ��

					mTouchStartX = mTouchStartY = 0;
					break;
				}
				return true;
			}
		});
	}

	/**
	 * ��ʾDesktopLayout
	 */
	private void showDesk() {
		mWindowManager.addView(floatView, mLayout);
		finish();
	}

	/**
	 * �ر�DesktopLayout
	 */
	private void closeDesk() {
		mWindowManager.removeView(floatView);
		finish();
	}

	/**
	 * ����WindowManager
	 */
	private void createWindowManager() {
		// ȡ��ϵͳ����
		mWindowManager = (WindowManager) getApplicationContext()
				.getSystemService("window");

		// ����Ĳ�����ʽ
		mLayout = new WindowManager.LayoutParams();

		// ���ô�����ʾ���͡���TYPE_SYSTEM_ALERT(ϵͳ��ʾ)
		mLayout.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

		// ���ô��役�㼰������
		// FLAG_NOT_FOCUSABLE(���ܻ�ð������뽹��)
		mLayout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

		// ������ʾ��ģʽ
		mLayout.format = PixelFormat.RGBA_8888;

		// ���ö���ķ���
		mLayout.gravity = Gravity.TOP | Gravity.LEFT;

		// ���ô����Ⱥ͸߶�
		mLayout.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mLayout.height = WindowManager.LayoutParams.WRAP_CONTENT;

	}

}
