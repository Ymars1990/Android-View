package com.mars.marsview.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.mars.marsview.R;
import com.mars.marsview.widget.MarsWidget;

public class MarsWidgetService extends Service {
	private Timer mTimer;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��HH:mm:ss");

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				updateWidget();
				// Utils.LogShow("Servcie", "����ִ��ˢ�²���...");
			}
		}, 0, 1000);
	}

	private String StringData() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "������";
		} else if ("2".equals(mWay)) {
			mWay = "����һ";
		} else if ("3".equals(mWay)) {
			mWay = "���ڶ�";
		} else if ("4".equals(mWay)) {
			mWay = "������";
		} else if ("5".equals(mWay)) {
			mWay = "������";
		} else if ("6".equals(mWay)) {
			mWay = "������";
		} else if ("7".equals(mWay)) {
			mWay = "������";
		}
		return mWay;
	}

	private void updateWidget() {
		String time = sdf.format(new Date());
		RemoteViews rv = new RemoteViews(getPackageName(), R.layout.marswidget);
		// Utils.LogShow("getPackageName", getPackageName());
		rv.setTextViewText(R.id.timeWidget, time.substring(11, time.length()));
		rv.setTextViewText(R.id.dateWidget, time.substring(0, 11));
		rv.setTextViewText(R.id.weekDayWidget, StringData());
		rv.setTextViewText(R.id.lauarWidget,LunarCalendar.getLunar(2, time));
		AppWidgetManager manager = AppWidgetManager
				.getInstance(getApplicationContext());
		ComponentName provider = new ComponentName(getApplicationContext(),
				MarsWidget.class);
		manager.updateAppWidget(provider, rv);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mTimer = null;
	}
}
