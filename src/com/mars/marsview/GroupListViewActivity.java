package com.mars.marsview;

import java.util.ArrayList;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mars.marsview.adapter.MyGroupListViewAapter;
import com.mars.marsview.entity.Category;
import com.mars.marsview.entity.GroupItem;
import com.mars.marsview.utils.Utils;

public class GroupListViewActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {
	private ListView mygroupListview;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext = null;
	private MyGroupListViewAapter mAdapter;
	private ArrayList<Category> listData = new ArrayList<Category>();

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_mygroup);
	}

	@Override
	public void initUI() {
		mygroupListview = (ListView) this.findViewById(R.id.mygroupListview);
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		mygroupListview.setOnItemClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
		initData();
		mAdapter = new MyGroupListViewAapter(mContext,listData);
	}

	private void initData() {
		Category categoryBasketballPlayer = new Category("NBA球员");
		categoryBasketballPlayer.addItem(new GroupItem("Dywane Wade","http://www.sinaimg.cn/ty/nba/player/2008/3708.jpg"));
		categoryBasketballPlayer.addItem(new GroupItem("Kobe Bryant","http://www.sinaimg.cn/ty/nba/player/2008/3118.jpg"));
		categoryBasketballPlayer.addItem(new GroupItem("LeBron James","http://www.sinaimg.cn/ty/nba/player/2008/3704.jpg"));
		categoryBasketballPlayer.addItem(new GroupItem("Russell Westbrook","http://www.sinaimg.cn/ty/nba/player/2008/4390.jpg"));
		categoryBasketballPlayer.addItem(new GroupItem("James Harden","http://www.sinaimg.cn/ty/nba/player/2008/4563.jpg"));
        Category categoryFootballPlayer= new Category("足球运动员");
        categoryFootballPlayer.addItem(new GroupItem("Cristiano Ronaldo",""));
        categoryFootballPlayer.addItem(new GroupItem("Lionel Messi",""));
        categoryFootballPlayer.addItem(new GroupItem("Wayne Rooney",""));
        categoryFootballPlayer.addItem(new GroupItem("Zinedine Yazid Zidane",""));
        categoryFootballPlayer.addItem(new GroupItem("Ronaldinho Gaúcho",""));
        categoryFootballPlayer.addItem(new GroupItem("Ronaldo Luiz",""));
        categoryFootballPlayer.addItem(new GroupItem("Luís Figo",""));
        categoryFootballPlayer.addItem(new GroupItem("Mesut Özil",""));
        categoryFootballPlayer.addItem(new GroupItem("郑智 ",""));
        categoryFootballPlayer.addItem(new GroupItem("李毅",""));
        Category categoryTrackAndFieldAthletes= new Category("田径运动员");
        categoryTrackAndFieldAthletes.addItem(new GroupItem("Tyson Gay",""));
        categoryTrackAndFieldAthletes.addItem(new GroupItem("刘翔",""));
        listData.add(categoryBasketballPlayer);
        listData.add(categoryFootballPlayer);
        listData.add(categoryTrackAndFieldAthletes);
	}

	@Override
	public void callFunc() {
		title_tv.setText("分组显示 ListView");
		mygroupListview.setAdapter(mAdapter);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

}
