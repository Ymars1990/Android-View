package com.mars.marsview;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mars.marsview.adapter.SelectListAapter;
import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.TipsDiaolg;

public class MainActivity extends BaseActivity implements OnItemClickListener,
		OnClickListener {
	private ListView myviewList;
	SelectListAapter mSelectListAapter = null;
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext = null;
	private String selectData[] = new String[] { "SeekBar", "Progressbar",
			"InputEditText", "Dialog", "ViewPager", "ListView", "Imageloader",
			"WebView", "TimeLine", "CakeView", "TextView", "Tempture",
			"RoundImageView", "MyExpandTabView", "MyFlowLayout", "Flowlayout",
			"SignatureView", "RSA", "WheelView", "Indexer", "ClockView",
			"Animation", "SpeedView", "SurfaceView","RingView","ScannSpeedView","LockPattern"
			,"DeviceView","VideoPlay","ScratchcardView","HistogramView","Vector","MPos","WaveView"
			,"DownloadView","GridView","Selector","TouchBlank","ArcMenu","Loading","Timeline"};

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_main);
	}

	@Override
	public void initUI() {
		myviewList = (ListView) this.findViewById(R.id.myviewList);
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		title_backlayout.setVisibility(View.GONE);
		mSelectListAapter = new SelectListAapter(mContext, selectData);
		myviewList.setAdapter(mSelectListAapter);
		setListViewHeightBasedOnChildren(myviewList);
		myviewList.setOnItemClickListener(this);

	}

	@Override
	public void registerEvent() {

	}

	@Override
	public void initParams() {
		mContext = this;
	}

	@Override
	public void callFunc() {
		title_tv.setText("自定义View目录");
		File rootpath = Environment.getRootDirectory();
		File externalstoragepath = Environment.getExternalStorageDirectory();
		Utils.LogShow("Rootpath", "" + rootpath);
		Utils.LogShow("ExternalStoragepath", "" + externalstoragepath);
		StatFs sf = new StatFs(rootpath.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		Utils.LogShow("Rootpath size ", "" + (freeBlocks * blockSize) / 1024
				/ 1024);
		sf = new StatFs(externalstoragepath.getPath());
		// 获取单个数据块的大小(Byte)
		blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		freeBlocks = sf.getAvailableBlocks();
		Utils.LogShow("ExternalStoragepath size ", ""
				+ (freeBlocks * blockSize) / 1024 / 1024);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		switch (position) {
		case 0:
			intent.setClass(mContext, SeekBarActivity.class);
			Utils.LogShow("getFilesDir", getFilesDir().getPath());
			break;
		case 1:
			intent.setClass(mContext, ProgressBarActivity.class);
			break;
		case 2:
			intent.setClass(mContext, InputTextActivity.class);
			break;
		case 3:
			intent.setClass(mContext, ProgressDialogActivity.class);
			break;
		case 4:
			intent.setClass(mContext, ViewPagerShowActivity.class);
			break;
		case 5:
			intent.setClass(mContext, MyListViewActivity.class);
			break;
		case 6:
			intent.setClass(mContext, ImageLoaderActivity.class);
			break;
		case 7:
			intent.setClass(mContext, WebViewActivity.class);
			break;
		case 8:
			intent.setClass(mContext, TimeLineActivity.class);
			break;
		case 9:
			intent.setClass(mContext, CakeViewActivity.class);
			break;
		case 10:
			intent.setClass(mContext, MyTextViewActivity.class);
			break;
		case 11:
			intent.setClass(mContext, TemptureActivity.class);
			break;
		case 12:
			intent.setClass(mContext, MyRoundImageViewActivity.class);
			break;
		case 13:
			intent.setClass(mContext, MyExpandTabViewActivity.class);
			break;
		case 14:
			intent.setClass(mContext, MyFlowLayoutActivity.class);
			break;
		case 15:
			intent.setClass(mContext, FlowlayoutActivity.class);
			break;
		case 16:
			intent.setClass(mContext, SignatureViewActivity.class);
			break;
		case 17:
			intent.setClass(mContext, Bmp2JbigActivity.class);
			break;
		case 18:
			intent.setClass(mContext, WheelViewActivity.class);
			break;
		case 19:
			intent.setClass(mContext, IndexerActivity.class);
			break;
		case 20:
			intent.setClass(mContext, ClockViewActivity.class);
			break;
		case 21:
			intent.setClass(mContext, AnimationActivity.class);
			break;
		case 22:
			intent.setClass(mContext, SpeedViewActivity.class);
			break;
		case 23:
			intent.setClass(mContext, SurfaceViewActivity.class);
			break;
		case 24:
			intent.setClass(mContext, CircleRingActivity.class);
			break;
		case 25:
			intent.setClass(mContext, ScannerSpeedViewActivity.class);
			break;
		case 26:
			intent.setClass(mContext, LockPatternViewActivity.class);
			break;
		case 27:
			intent.setClass(mContext, Bmp2JbigActivity.class);
			break;
		case 28:
			intent.setClass(mContext, VideoPlayActivity.class);
			break;
		case 29:
			intent.setClass(mContext, ScratchCardViewActivity.class);
			break;
		case 30:
			intent.setClass(mContext, HistogramViewActivity.class);
			break;
		case 31:
			if(Utils.getAndroidOSVersion()>=21){
				intent.setClass(mContext, VectorActivity.class);
			}else{
				ToastManager.show(mContext, "此版块需要Android 5.0及以上可支持", 0);
				return ;
			}
			break;
		case 32:
			intent.setClass(mContext, MposViewActivity.class);
			break;
		case 33:
			intent.setClass(mContext, WaveViewActivity.class);
			break;
		case 34:
			intent.setClass(mContext, DownloadViewActivity.class);
			break;
		case 35:
			intent.setClass(mContext, GirdViewActivity.class);
			break;
		case 36:
			intent.setClass(mContext, SelectorActivity.class);
			break;
		case 37:
			intent.setClass(mContext, TouchBlankActivity.class);
			break;
		case 38:
			intent.setClass(mContext, ArcMenuActivity.class);
			break;
		case 39:
			intent.setClass(mContext, LoadingActivity.class);
			break;
		case 40:
			intent.setClass(mContext, MyTimelineActivity.class);
			break;
		}
		Utils.startActivity(this, intent);
	}

	private TipsDiaolg tipsDialog = null;

	// 对话框
	public void showExitDialog(String title, String message) {
		tipsDialog = new TipsDiaolg(this);
		tipsDialog.builder().setCancelable(false);
		tipsDialog.setBtOnclickListener(this);
		tipsDialog.setTitleText(title);
		tipsDialog.setContentText(message);
		tipsDialog.setViewVisiable(0, 1, 0, 1);
		tipsDialog.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			showExitDialog("退出", "退出应用？");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tipsBtCancel:
			if (tipsDialog != null) {
				tipsDialog.dismiss();
			}
			break;
		case R.id.tipsBtConfirm:
			if (tipsDialog != null) {
				tipsDialog.dismiss();
			}
			Utils.closeActivity(this, 1);
			break;
		default:
			break;
		}
	}

	private void setListViewHeightBasedOnChildren(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {

			return;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItem = listAdapter.getView(i, null, listView);

			listItem.measure(0, 0);

			totalHeight += listItem.getMeasuredHeight();

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight

		+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		// params.height += 5;// if without this statement,the listview will be

		// a

		// little short

		// listView.getDividerHeight()获取子项间分隔符占用的高度

		// params.height最后得到整个ListView完整显示需要的高度

		listView.setLayoutParams(params);

	}

}
