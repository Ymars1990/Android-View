package com.mars.marsview.widget;

import com.mars.marsview.utils.MarsWidgetService;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class MarsWidget extends AppWidgetProvider {
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		//ˢ��Widget  �ص�ִ��
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		//widget ������Ƴ����ص�
		Intent intent = new Intent(context, MarsWidgetService.class);
		context.stopService(intent);
	}
	@Override
	public void onDisabled(Context context) {
		//widget ���һ����������Ƴ����ص�
		super.onDisabled(context);
	}
	@Override
	public void onEnabled(Context context) {
		//widget �������ӵ���Ļ���ص�
		super.onEnabled(context);
		Intent intent = new Intent(context, MarsWidgetService.class);
		context.startService(intent);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}
}
