package com.mars.marsview;

import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.NumTipSeekBar;
import com.mars.marsview.view.NumTipSeekBar.OnProgressChangeListener;

public class SeekBarActivity extends BaseActivity implements OnClickListener,OnProgressChangeListener{
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private TextView showSeekbar_progress;
	private ImageView addImg;
	private ImageView subtractImg;
	private NumTipSeekBar seekBar;
	@Override
	public void onCreate() {
		setContentView(R.layout.activity_seekbar);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this.findViewById(R.id.title_backlayout);
		showSeekbar_progress = (TextView) this.findViewById(R.id.showSeekbar_progress);
		addImg = (ImageView) this.findViewById(R.id.addImg);
		subtractImg = (ImageView) this.findViewById(R.id.subtractImg);
		seekBar = (NumTipSeekBar) this.findViewById(R.id.seekBar);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		addImg.setOnClickListener(this);
		subtractImg.setOnClickListener(this);
		seekBar.setOnProgressChangeListener(this);
	}

	@Override
	public void initParams() {

	}

	@Override
	public void callFunc() {
		title_tv.setText("SeekBar");
		showSeekbar_progress.setText(""+seekBar.getSelectProgress());
		Utils.readBitMap(Environment
			.getExternalStorageDirectory().getPath() + "/JLTPay/"+"20160721144828.png");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Utils.closeActivity(this,0);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_backlayout:
			Utils.closeActivity(this,0);
			break;
		case R.id.addImg:
			seekBar.setSelectProgress(seekBar.getSelectProgress()+1);
			showSeekbar_progress.setText(""+seekBar.getSelectProgress());
			break;
		case R.id.subtractImg:
			seekBar.setSelectProgress(seekBar.getSelectProgress()-1);
			showSeekbar_progress.setText(""+seekBar.getSelectProgress());
			break;
		default:
			break;
		}
	}
	@Override
	public void onChange(int selectProgress, int id) {
		showSeekbar_progress.setText(""+selectProgress);
	}
}
