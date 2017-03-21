package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.ScratchcardView;

public class ScratchCardViewActivity extends BaseActivity implements
		OnClickListener {

	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private ScratchcardView scratchCardView;
	private Button resetBt;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_scratchcardview);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		scratchCardView = (ScratchcardView) this
				.findViewById(R.id.scratchCardView);
		resetBt = (Button) this.findViewById(R.id.resetBt);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		resetBt.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		mContext = this;

	}

	@Override
	public void callFunc() {
		title_tv.setText("�ι���");
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
		case R.id.resetBt:
			scratchCardView.setReset(true);
			RandomPrize();
			break;
		}
	}

	private void RandomPrize() {
		double d = Math.random() * 100;
		if (d < 50) {
			scratchCardView.setmText("ɧ��,����������ߣ����,�ٽ�����!");
		} else if (d < 80) {
			scratchCardView.setmText("��ѽ,���Ե�,����һ��΢Ц!");
		} else if (d < 95) {
			scratchCardView.setmText("�Բ�,����,��500w����������˻�!");
		} else {
			scratchCardView.setmText("��λ����,���д���,�Ҿ������!");
		}
	}
}
