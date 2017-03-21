package com.mars.marsview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mars.marsview.adapter.TimelineListAapter;
import com.mars.marsview.entity.TimeLine;
import com.mars.marsview.utils.Utils;

public class TimeLineActivity extends BaseActivity implements OnClickListener {

	private TextView title_tv;
	private LinearLayout title_backlayout;
	private ListView timeline_lv;
	private TimelineListAapter mAdapter;
	private List<TimeLine> data;
	private Context mContext;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_timeline);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		timeline_lv = (ListView) this.findViewById(R.id.timeline_lv);
		timeline_lv.setAdapter(mAdapter);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
		data = new ArrayList<TimeLine>();
		for (int i = 0; i < 16; i++) {
			switch (i) {
			case 0:
				TimeLine mtLine1 = new TimeLine("Step" + (i + 1), "ÌîÐ´ÊÖ»úºÅ", 1);
				data.add(mtLine1);
				break;
			case 1:
				TimeLine mtLine2 = new TimeLine("Step" + (i + 1), "ÌîÐ´ÑéÖ¤Âë", 1);
				data.add(mtLine2);
				break;
			case 2:
				TimeLine mtLine3 = new TimeLine("Step" + (i + 1), "ÉèÖÃÃÜÂë", 1);
				data.add(mtLine3);
				break;
			case 3:
				TimeLine mtLine4 = new TimeLine("Step" + (i + 1), "ÉÏ´«Í¼Æ¬", 1);
				data.add(mtLine4);
				break;
			case 4:
				TimeLine mtLine5 = new TimeLine("Step" + (i + 1), "µÈ´ýÉóºË", 1);
				data.add(mtLine5);
				break;
			case 5:
				TimeLine mtLine6 = new TimeLine("Step" + (i + 1), "ÌîÐ´ÊÖ»úºÅ", 1);
				data.add(mtLine6);
				break;
			case 6:
				TimeLine mtLine7 = new TimeLine("Step" + (i + 1), "ÌîÐ´ÑéÖ¤Âë", 1);
				data.add(mtLine7);
				break;
			case 7:
				TimeLine mtLine8 = new TimeLine("Step" + (i + 1), "ÉèÖÃÃÜÂë", 1);
				data.add(mtLine8);
				break;
			case 8:
				TimeLine mtLine9 = new TimeLine("Step" + (i + 1), "ÉÏ´«Í¼Æ¬", 0);
				data.add(mtLine9);
				break;
			case 9:
				TimeLine mtLine10 = new TimeLine("Step" + (i + 1), "µÈ´ýÉóºË", 0);
				data.add(mtLine10);
				break;
			case 10:
				TimeLine mtLine11 = new TimeLine("Step" + (i + 1), "ÉÏ´«Í¼Æ¬", 0);
				data.add(mtLine11);
				break;
			case 11:
				TimeLine mtLine12 = new TimeLine("Step" + (i + 1), "µÈ´ýÉóºË", 0);
				data.add(mtLine12);
			case 12:
				TimeLine mtLine13= new TimeLine("Step" + (i + 1), "ÉÏ´«Í¼Æ¬", 0);
				data.add(mtLine13);
				break;
			case 13:
				TimeLine mtLine14 = new TimeLine("Step" + (i + 1), "µÈ´ýÉóºË", 0);
				data.add(mtLine14);
				break;
			case 14:
				TimeLine mtLine15 = new TimeLine("Step" + (i + 1), "ÉÏ´«Í¼Æ¬", 0);
				data.add(mtLine15);
				break;
			case 15:
				TimeLine mtLine16 = new TimeLine("Step" + (i + 1), "µÈ´ýÉóºË", 0);
				data.add(mtLine16);
				break;
			default:
				break;
			}
		}
		mAdapter = new TimelineListAapter(mContext, data);
	}

	@Override
	public void callFunc() {
		title_tv.setText("TimeLine");
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
