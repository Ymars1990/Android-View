package com.mars.marsview;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.utils.TimeCallFunction;
import com.mars.marsview.utils.Utils;

public class TouchBlankActivity extends BaseActivity {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private Context mContext;
	private LinearLayout view[][];
	private int flag[][];
	private TimeCallFunction tcFunc;
	private Random random;
	private int result = -1;
	private int id[][];
	private Set set;
	private Set scoreSet;
	private int level = 4;
	private int score = 0;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_touchblank);
	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		id = new int[][] {
				{ R.id.view01, R.id.view02, R.id.view03, R.id.view04 },
				{ R.id.view11, R.id.view12, R.id.view13, R.id.view14 },
				{ R.id.view21, R.id.view22, R.id.view23, R.id.view24 },
				{ R.id.view31, R.id.view32, R.id.view33, R.id.view34 },
				{ R.id.view41, R.id.view42, R.id.view43, R.id.view44 },
				{ R.id.view51, R.id.view52, R.id.view53, R.id.view54 }, };
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				Utils.LogShow("i j", i + " " + j);
				view[i][j] = (LinearLayout) this.findViewById(id[i][j]);
			}
		}
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				view[i][j].setOnClickListener(this);
			}
		}
	}

	@Override
	public void initParams() {
		mContext = this;
		set = new HashSet<Integer>();
		scoreSet = new HashSet<Integer>();
		flag = new int[6][4];
		view = new LinearLayout[6][4];
		random = new Random();
	}

	@Override
	public void callFunc() {
		title_tv.setText("TouchBlank");
		tcFunc = new TimeCallFunction(10 * 1000, 1000) {
			@Override
			public void callTimerTaskFuc() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						initFlag();
						initViewBg();
					}
				});
			}
		};
		tcFunc.startTimerTask();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		tcFunc.stopTimerTask();
		Utils.LogShow("得分", score);
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
		default:
			for (Object obj : set) {
				if (obj instanceof Integer) {
					int aa = (Integer) obj;
					if (v.getId() == id[aa / 4][aa % 4]) {
						if (!scoreSet.contains(obj)) {
							Utils.LogShow("touch", "击中目标");
							score++;
							scoreSet.add(obj);
						}
					} else {
						// Utils.LogShow("touch", "未击中目标");
					}
				}

			}
			break;
		}
	}

	private void initViewBg() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				if (flag[i][j] == 1) {
					view[i][j].setBackgroundColor(Color.BLACK);
				} else {
					view[i][j].setBackgroundColor(Color.WHITE);
				}
			}
		}

	}

	private void initFlag() {
		set.clear();
		while (set.size() < level) {
			result = random.nextInt(24);
			if (!set.contains(result)) {
				set.add(result);
				// Utils.LogShow("random", "添加成功");
			}
		}
		Utils.LogShow("set size", "" + set.size());
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				if (set.contains(i * 4 + j)) {
					flag[i][j] = 1;
				} else {
					flag[i][j] = 0;
				}
			}
		}
	}
}
