package com.mars.marsview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.GameSurfaceView;

public class SurfaceViewActivity extends BaseActivity implements
		OnClickListener {

	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private GameSurfaceView mGameSurfaceView;
	@Override
	public void onCreate() {
		/**/
		setContentView(R.layout.activity_surfaceview);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		mGameSurfaceView = (GameSurfaceView)this.findViewById(R.id.gamesurfaceView);
		DisplayMetrics outMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		GameSurfaceView.SCREEN_WIDTH = outMetrics.widthPixels;
		GameSurfaceView.SCREEN_HEIGHT = outMetrics.heightPixels-Utils.dp2px(50.0f, this);
		Utils.LogShow("SCREEN_WIDTH", ""+GameSurfaceView.SCREEN_WIDTH);
		Utils.LogShow("SCREEN_HEIGHT", ""+GameSurfaceView.SCREEN_HEIGHT);
		GameSurfaceView gameView = new GameSurfaceView(this);
		setContentView(gameView);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		mContext = this;
	}

	@Override
	public void callFunc() {
		title_tv.setText("SurfaceView");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Utils.closeActivity(this, 0);
			return true;
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
}
