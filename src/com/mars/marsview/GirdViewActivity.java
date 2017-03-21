package com.mars.marsview;

import java.util.ArrayList;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.adapter.GridViewAdapter;
import com.mars.marsview.entity.GridViewInfo;
import com.mars.marsview.entity.Images;
import com.mars.marsview.utils.Utils;

public class GirdViewActivity extends BaseActivity {
	private Context mContext;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private GridView myGirdView;
	private ArrayList<GridViewInfo> data;
	private GridViewAdapter adapter;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_girdveiw);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		myGirdView = (GridView)this.findViewById(R.id.myGirdView);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
		data = new ArrayList<>();
		for(int i=0;i<Images.imageThumbUrls.length;i++){
			data.add(new GridViewInfo("Image "+i,Images.imageThumbUrls[i]));
		}
		adapter = new GridViewAdapter(mContext, data);
	}

	@Override
	public void callFunc() {
		title_tv.setText("GridView");
		myGirdView.setAdapter(adapter);
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
