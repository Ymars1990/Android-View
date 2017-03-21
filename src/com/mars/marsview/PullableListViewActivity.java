package com.mars.marsview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mars.marsview.adapter.MyListViewAdapter;
import com.mars.marsview.utils.MyListener;
import com.mars.marsview.utils.MyListener.PullCallBack;
import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.PullToRefreshLayout;
import com.mars.marsview.view.TipsDiaolg;

public class PullableListViewActivity extends BaseActivity implements
		OnClickListener, PullCallBack {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private ListView listView;
	private PullToRefreshLayout ptrl;
	private boolean isFirstIn = false;
	private Context mContext;
	private long deletePosition = -1;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_pullablelistview);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);

		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		ptrl.setOnRefreshListener(new MyListener(this));
		listView = (ListView) findViewById(R.id.content_view);
		initListView();
	}

	/**
	 * ListView��ʼ������
	 */
	private void initListView() {
		List<String> items = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			items.add("������item " + i);
		}
		MyListViewAdapter adapter = new MyListViewAdapter(this, items);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				showExitDialog("ɾ��",
						"ȷ��ɾ��" + parent.getAdapter().getItemId(position));
				deletePosition = (int) parent.getAdapter().getItemId(position);
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(PullableListViewActivity.this,
						" Click on " + parent.getAdapter().getItemId(position),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private TipsDiaolg tipsDialog = null;

	// �Ի���
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
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		// ��һ�ν����Զ�ˢ��
		if (isFirstIn) {
			ptrl.autoRefresh();
			isFirstIn = false;
		}
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
	}

	@Override
	public void callFunc() {
		title_tv.setText("ListView pullAble");
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
		case R.id.tipsBtCancel:
			tipsDialog.dismiss();
			break;
		case R.id.tipsBtConfirm:
			if (deletePosition >= 0) {
				ToastManager.show(mContext, "����ɾ��" + deletePosition,0);
			}
			tipsDialog.dismiss();
			break;
		default:
			break;
		}

	}

	@Override
	public void onLoadMoreTask() {
		// �ص����ظ��� д���صĺ���
		ToastManager.show(this, "�ص����ظ���",0);
	}

	@Override
	public void onRefreshTask() {
		// �ص�ˢ�� дˢ�º���
		ToastManager.show(this, "�ص�ˢ��",0);
	}

	@Override
	public void onLoadMoreFinish() {
		// �ص����ظ��� д���صĺ���
		ToastManager.show(this, "�ص��������",0);
	}

	@Override
	public void onRefreshFinish() {
		ToastManager.show(this, "�ص�ˢ�����",0);
	}
}
