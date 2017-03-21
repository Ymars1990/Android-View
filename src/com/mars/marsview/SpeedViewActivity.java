package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.TimeCallFunction;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.NumTipSeekBar;
import com.mars.marsview.view.NumTipSeekBar.OnProgressChangeListener;
import com.mars.marsview.view.SpeedView;

public class SpeedViewActivity extends BaseActivity implements OnClickListener,
		OnProgressChangeListener,OnTouchListener{

	private Context mContext;

	private TextView title_tv;
	private LinearLayout title_backlayout;
	private SpeedView mSpeedView;
	private NumTipSeekBar seekBar;
	private Button speedDown;
	private Button speedUp;
	private TimeCallFunction tcFunc;
	private float speed = 0.0f;
	private int SpeedTpye = 1;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_speedview);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		mSpeedView = (SpeedView) this.findViewById(R.id.myspeedView01);
		seekBar = (NumTipSeekBar) this.findViewById(R.id.seekBar);
		speedDown = (Button)this.findViewById(R.id.speedDown);
		speedUp = (Button)this.findViewById(R.id.speedUp);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		seekBar.setOnProgressChangeListener(this);
		speedUp.setOnTouchListener(this);
		speedDown.setOnTouchListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
		tcFunc = new TimeCallFunction(-1, 100) {
			@Override
			public void callTimerTaskFuc() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						switch (SpeedTpye) {
						case 1:
							speed-=1.0f;
							break;
						case 2:
							speed+=1.0f;
							break;

						default:
							break;
						}
						if(speed<0){
							speed=0;
						}
						if(speed>180){
							speed=180;
						}
						mSpeedView.setSpeed(speed);
					}
				});
			}
		};
	}

	@Override
	public void callFunc() {
		title_tv.setText("SpeedView");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Utils.closeActivity(this, 0);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_backlayout:
			Utils.closeActivity(this, 0);
			break;
		}
	}

	@Override
	public void onChange(int selectProgress, int id) {
		mSpeedView.setSpeed((float) selectProgress);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//按下
			speed  = mSpeedView.getSpeed();
			switch (v.getId()) {
			case R.id.speedDown:
				SpeedTpye = 1;
				break;
			case R.id.speedUp:
				SpeedTpye = 2;
				break;

			default:
				break;
			}
			tcFunc.startTimerTask();
			break;
		case MotionEvent.ACTION_MOVE:
			//移动
			break;
		case MotionEvent.ACTION_UP:
			//抬起
			tcFunc.stopTimerTask();
			break;
		}
		return true;
	}
}
