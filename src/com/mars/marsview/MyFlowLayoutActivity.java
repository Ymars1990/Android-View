package com.mars.marsview;

import java.util.Random;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.BGAFlowLayout;
import com.mars.marsview.view.TagLayout;

public class MyFlowLayoutActivity extends BaseActivity implements
		OnClickListener {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private String[] mVals = new String[] { "bingo", "googol", "apple",
			"bingoogolapple", "helloworld" };
	private BGAFlowLayout mFlowLayout;
	private EditText mTagEt;
	private Button add;
	private String[] mDatas = new String[] { "QQ", "视频", "放开那三国", "电子书", "酒店",
			"单机", "小说", "斗地主", "优酷", "网游", "WIFI万能钥匙", "播放器", "捕鱼达人2", "机票",
			"游戏", "熊出没之熊大快跑", "美图秀秀", "浏览器", "单机游戏", "我的世界", "电影电视", "QQ空间",
			"旅游", "免费游戏", "2048", "刀塔传奇", "壁纸", "节奏大师", "锁屏", "装机必备", "天天动听",
			"备份", "网盘", "海淘网", "大众点评", "爱奇艺视频", "腾讯手机管家", "百度地图", "猎豹清理大师",
			"谷歌地图", "hao123上网导航", "京东", "youni有你", "万年历-农历黄历", "支付宝钱包" };
	private TagLayout mLayout;
	private int[] colors = { Color.GRAY, Color.LTGRAY, Color.RED, Color.YELLOW,
			Color.BLUE };

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_myflowlayout);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		mTagEt = (EditText) findViewById(R.id.et_main_tag);
		mFlowLayout = (BGAFlowLayout) findViewById(R.id.flowlayout);
		add = (Button) findViewById(R.id.add);

		mLayout = (TagLayout) findViewById(R.id.myTaglayout);
		mLayout.setPadding(10, 10, 10, 10);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		add.setOnClickListener(this);
		mFlowLayout.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		// TODO Auto-generated method stub

	}

	public void initData() {
		for (int i = 0; i < mVals.length; i++) {
			mFlowLayout.addView(getLabel(mVals[i]),
					new ViewGroup.MarginLayoutParams(
							ViewGroup.MarginLayoutParams.WRAP_CONTENT,
							ViewGroup.MarginLayoutParams.WRAP_CONTENT));
		}

		Random rdm = new Random();
		for (int i = 0; i < mDatas.length; i++) {
			TextView view = new TextView(this);
			view.setText(mDatas[i]);
			view.setBackgroundColor(colors[4]);
			view.setTextColor(Color.WHITE);
			view.setPadding(5, 5, 5, 5);
			view.setGravity(Gravity.CENTER);
			view.setTextSize(rdm.nextInt(10) + 16);
			view.setOnClickListener(this);
			// view.setTextSize(15);
			mLayout.addView(view);
		}
	}

	private TextView getLabel(String text) {
		TextView label = new TextView(this);
		label.setTextColor(Color.WHITE);
		label.setBackgroundResource(R.drawable.selector_tag);
		label.setGravity(Gravity.CENTER);
		label.setSingleLine(true);
		int padding = BGAFlowLayout.dp2px(this, 5);
		label.setEllipsize(TextUtils.TruncateAt.END);
		label.setPadding(padding, padding, padding, padding);
		label.setText(text);
		return label;
	}

	@Override
	public void callFunc() {
		title_tv.setText("MyFlowLayout");
		initData();
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
		case R.id.add:
			String tag = mTagEt.getText().toString().trim();
			if (!TextUtils.isEmpty(tag)) {
				mFlowLayout.addView(getLabel(tag),
						new ViewGroup.MarginLayoutParams(
								ViewGroup.MarginLayoutParams.WRAP_CONTENT,
								ViewGroup.MarginLayoutParams.WRAP_CONTENT));
			}
			mTagEt.setText("");
			break;
		default:
			Utils.LogShow("嘿嘿", "点了");
			break;
		}
	}

}
