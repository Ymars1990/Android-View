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
		//刷新Widget  回调执行
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		//widget 插件被移除，回调
		Intent intent = new Intent(context, MarsWidgetService.class);
		context.stopService(intent);
	}
	@Override
	public void onDisabled(Context context) {
		//widget 最后一个本插件被移除，回调
		super.onDisabled(context);
	}
	@Override
	public void onEnabled(Context context) {
		//widget 插件被添加到屏幕，回调
		super.onEnabled(context);
		Intent intent = new Intent(context, MarsWidgetService.class);
		context.startService(intent);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}
}
