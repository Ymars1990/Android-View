package com.mars.marsview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mars.marsview.adapter.ImageListViewAdapter;
import com.mars.marsview.entity.Images;
import com.mars.marsview.utils.Utils;

public class ImageLoaderActivity extends BaseActivity implements
		OnClickListener {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private ImageView img_loadershow;
	private ListView img_ListView;
	private ImageListViewAdapter imglistAdapter;
	private Context mContext;
	private List<String> items;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_imageloader);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		img_loadershow = (ImageView) this.findViewById(R.id.img_loadershow);
		img_ListView = (ListView) this.findViewById(R.id.img_ListView);
		imglistAdapter = new ImageListViewAdapter(mContext, items);
		img_ListView.setAdapter(imglistAdapter);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
		items = new ArrayList<String>();
		for (int i = 0; i < Images.imageThumbUrls.length; i++) {
			items.add(Images.imageThumbUrls[i]);
		}
	}

	@Override
	public void callFunc() {
		title_tv.setText("Imageloader");
		Glide.with(mContext)
				.load("http://www.cccpay.cn/downloads/android/beta/test.jpg")
				.placeholder(R.drawable.default_img).into(img_loadershow);
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
