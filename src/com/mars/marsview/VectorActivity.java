package com.mars.marsview;

import com.mars.marsview.utils.Utils;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VectorActivity extends BaseActivity implements OnClickListener {

	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private ImageView img ;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_vector);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		img = (ImageView)this.findViewById(R.id.vectorImg);
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
		title_tv.setText("Vector");
		((Animatable) img.getDrawable()).start();
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
}
