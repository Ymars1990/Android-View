package com.mars.marsview;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.adapter.PagerAdapter;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.ViewPager;
import com.mars.marsview.view.ViewPager.OnPageChangeListener;
import com.mars.marsview.view.ViewPager.PageTransformer;

public class HorizontalViewpagerActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener, PageTransformer {
	private ViewPager mViewPager;
	private VerticalFragementPagerAdapter mAdapter;
	private List<View> pagers = new ArrayList<View>();
	private TextView title_tv;
	private LinearLayout title_backlayout;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_horizontalviewpager);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);

		mViewPager = (ViewPager) this.findViewById(R.id.myViewpager);
		View view1 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.viewpager1, null);
		View view2 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.viewpager2, null);
		View view3 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.viewpager3, null);
		pagers.add(new View(this));
		pagers.add(view1);
		pagers.add(view2);
		pagers.add(view3);
		pagers.add(new View(this));
		mAdapter = new VerticalFragementPagerAdapter();
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(1);
	}

	@Override
	public void registerEvent() {
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setPageTransformer(false, this);
		title_backlayout.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		// TODO Auto-generated method stub

	}

	@Override
	public void callFunc() {
		title_tv.setText("HorizontalViewpager");

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

	private class VerticalFragementPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object o) {
			return view == o;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(pagers.get(position), 0);
			return pagers.get(position);

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		Log.i("当前位置", "" + position);
		Log.i("当前位置positionOffset", "" + positionOffset);
		Log.i("当前位置positionOffsetPixels", "" + positionOffsetPixels);

	}

	@Override
	public void onPageSelected(int position) {
		if (position == 0) {
			mViewPager.setCurrentItem(pagers.size() - 2, false);
		} else if (position == pagers.size() - 1)
			mViewPager.setCurrentItem(1, false);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}

	// viewpager 切换动画
	@Override
	public void transformPage(View page, float position) {
		float normalizedposition = Math.abs(Math.abs(position) - 1);
		Utils.LogShow("position", "" + position);
		Utils.LogShow("normalizedposition", "" + normalizedposition);
		page.setScaleX(normalizedposition / 2 + 0.5f);
		page.setScaleY(normalizedposition / 2 + 0.5f);
		page.setRotationY(position * -30);
	}

}
