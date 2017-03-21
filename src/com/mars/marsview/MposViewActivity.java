package com.mars.marsview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.CircleView;
import com.mars.marsview.view.DeviceView;
import com.mars.marsview.view.TrangleView;
import com.mars.marsview.view.ViewClickEffect;

public class MposViewActivity extends BaseActivity implements OnClickListener{
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private TextView researchDevice;
	private TextView bindDevice;
	private DeviceView myDeviceView;
	private CircleView binddevice_step1_circleveiw;
	private CircleView binddevice_step2_circleveiw;
	private CircleView binddevice_step3_circleveiw;
	private TrangleView binddevice_step1_trangleView;
	private TrangleView binddevice_step2_trangleView;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_bindmpos);
//		setContentView(R.layout.activity_mposview);

		
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		researchDevice = (TextView)this.findViewById(R.id.researchDevice);
		bindDevice = (TextView)this.findViewById(R.id.bindDevice);
		myDeviceView = (DeviceView)this.findViewById(R.id.mpos_deviceView);
		
		binddevice_step1_circleveiw = (CircleView)this.findViewById(R.id.binddevice_step1_circleveiw);
		binddevice_step2_circleveiw = (CircleView)this.findViewById(R.id.binddevice_step2_circleveiw);
		binddevice_step3_circleveiw = (CircleView)this.findViewById(R.id.binddevice_step3_circleveiw);
		
		binddevice_step1_trangleView = (TrangleView)this.findViewById(R.id.binddevice_step1_trangleView);
		binddevice_step2_trangleView = (TrangleView)this.findViewById(R.id.binddevice_step2_trangleView);
		
		binddevice_step1_trangleView.setShowInDymatic(false);
		binddevice_step2_trangleView.setShowInDymatic(false);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		researchDevice.setOnClickListener(this);
		bindDevice.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
	}
	private boolean stopFlag = true;
	@Override
	public void callFunc() {
		title_tv.setText("Mpos");
		myDeviceView.setTips("正在搜索...");
		ViewClickEffect.Click(researchDevice);
		ViewClickEffect.Click(bindDevice);

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Utils.closeActivity(this, 0);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	private int index = 0;
	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.title_backlayout:
				Utils.closeActivity(this, 0);
				break;
			case R.id.researchDevice:
				if (stopFlag) {
					stopFlag = false;
				} else {
					stopFlag = true;
				}
				myDeviceView.setStopFlag(stopFlag);
//				showDesk();
				break;
			case R.id.bindDevice:
				//绑定设备操作
				index++;
				timelineView(index);
				Utils.LogShow("index",""+index);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void timelineView(int index){
		switch (index%4) {
		case 1:
			binddevice_step1_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.mpos));
			binddevice_step2_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.unselected));
			binddevice_step3_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.unselected));			
			binddevice_step1_trangleView.setmTrangleView_color(mContext.getResources().getColor(R.color.unselected));			
			binddevice_step2_trangleView.setmTrangleView_color(mContext.getResources().getColor(R.color.unselected));
			binddevice_step1_trangleView.setShowInDymatic(false);
			binddevice_step2_trangleView.setShowInDymatic(false);
			break;
		case 2:
			binddevice_step1_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.mpos));
			binddevice_step2_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.mpos));
			binddevice_step3_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.unselected));
			binddevice_step1_trangleView.setmTrangleView_color(mContext.getResources().getColor(R.color.mpos));
			binddevice_step2_trangleView.setmTrangleView_color(mContext.getResources().getColor(R.color.unselected));
			binddevice_step1_trangleView.setShowInDymatic(true);
			binddevice_step2_trangleView.setShowInDymatic(false);
			break;
		case 3:
			binddevice_step1_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.mpos));
			binddevice_step2_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.mpos));
			binddevice_step3_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.mpos));
			binddevice_step1_trangleView.setmTrangleView_color(mContext.getResources().getColor(R.color.mpos));
			binddevice_step2_trangleView.setmTrangleView_color(mContext.getResources().getColor(R.color.mpos));
			binddevice_step1_trangleView.setShowInDymatic(false);
			binddevice_step2_trangleView.setShowInDymatic(true);
			break;
		default:
			binddevice_step1_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.unselected));
			binddevice_step2_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.unselected));
			binddevice_step3_circleveiw.setmCircleview_color(mContext.getResources().getColor(R.color.unselected));
			binddevice_step1_trangleView.setmTrangleView_color(mContext.getResources().getColor(R.color.unselected));
			binddevice_step2_trangleView.setmTrangleView_color(mContext.getResources().getColor(R.color.unselected));
			binddevice_step1_trangleView.setShowInDymatic(false);
			binddevice_step2_trangleView.setShowInDymatic(false);
			break;
		}
	}
}
