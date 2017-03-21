package com.mars.marsview.utils;

import android.os.Handler;
import android.os.Message;

import com.mars.marsview.view.PullToRefreshLayout;
import com.mars.marsview.view.PullToRefreshLayout.OnRefreshListener;

public class MyListener implements OnRefreshListener {
	PullCallBack pullcallback;
	public MyListener(PullCallBack pullcallback) {
		this.pullcallback = pullcallback;
	}
	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		pullcallback.onRefreshTask();
		// ����ˢ�²���
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// ǧ������˸��߿ؼ�ˢ�������Ŷ��
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				pullcallback.onRefreshFinish();
			}
		}.sendEmptyMessageDelayed(0, 2000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		pullcallback.onLoadMoreTask();
		// ���ز���
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// ǧ������˸��߿ؼ����������Ŷ��
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);	
				pullcallback.onLoadMoreFinish();
			}
		}.sendEmptyMessageDelayed(0, 2000);
	}
	public interface PullCallBack {
		void onLoadMoreTask();
		void onLoadMoreFinish();
		void onRefreshTask();
		void onRefreshFinish();
	};
}
