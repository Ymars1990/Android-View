package com.mars.marsview;

import java.io.File;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;

public class ClockViewActivity extends BaseActivity implements OnClickListener {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private TextView ledTextView;
	private TextView ledTextViewbg;
	private static final String FONT_DIGITAL_7 = "fonts" + File.separator
			+ "digital-7.ttf";
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0x01:
				ledTextView.setText(Utils.getSysTime(mContext));
				handler.sendEmptyMessageDelayed(0x01, 1000);
				break;
			default:
				break;
			}
		}
	};
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_clockview);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		ledTextView = (TextView) this.findViewById(R.id.ledTextView);
		ledTextViewbg = (TextView) this.findViewById(R.id.ledTextViewbg);
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
		title_tv.setText("ClockView");
		handler.sendEmptyMessageDelayed(0x01, 1000);
		AssetManager assets = getAssets();
		final Typeface font = Typeface.createFromAsset(assets, FONT_DIGITAL_7);
		ledTextView.setTypeface(font);
		ledTextViewbg.setTypeface(font);
		ledTextView.setText(Utils.getSysTime(mContext));
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
		default:
			break;
		}
	}
}
