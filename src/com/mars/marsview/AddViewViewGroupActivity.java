package com.mars.marsview;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.AddViewViewGroup;

public class AddViewViewGroupActivity extends BaseActivity implements
		OnClickListener {
	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private AddViewViewGroup myAddViewVieGroup;
	private Button addView;
	private	EditText tagEdt;
	private int btIndex = 0;
	private String Tag = null;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_add_viewgroup);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		myAddViewVieGroup = (AddViewViewGroup) this
				.findViewById(R.id.myAddViewVieGroup);
		addView = (Button) this.findViewById(R.id.addView);
		tagEdt = (EditText) this.findViewById(R.id.tagEdt);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		addView.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;

	}

	@Override
	public void callFunc() {
		title_tv.setText("AddView ViewGroup");
		myAddViewVieGroup.setColumNum(1);
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
		case R.id.addView:
			Tag = tagEdt.getText().toString();
			if(Utils.isNotEmpty(Tag)){
			addView();
			}else{
				ToastManager.show(mContext, "标签不能为空", 0);
			}
			break;
		}
	}

	private void addView() {
		ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		final Button bt = new Button(mContext);
		bt.setText(Tag);
		// bt.setHeight(Utils.dp2px(40, this));
		bt.setGravity(Gravity.CENTER);
		bt.setBackgroundResource(R.drawable.button_selector);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myAddViewVieGroup.removeView(bt);
				btIndex--;
			}
		});
		myAddViewVieGroup.addView(bt, lp);
		btIndex++;
		Tag = null;
		tagEdt.setText("");
	}
}
