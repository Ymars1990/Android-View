package com.mars.marsview.systemmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownLoadService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	/*
	 * ���ﷵ��״̬������ֵ���ֱ���:
	 * 1��START_STICKY�����������������ʱ��ɱ����ϵͳ���������Ϊstarted״̬�����ǲ������䴫�ݵ�Intent����
	 * ��֮��ϵͳ�᳢�����´�������; 2��START_NOT_STICKY�����������������ʱ��ɱ��������û���µ�Intent���󴫵ݹ����Ļ���
	 * ϵͳ���������Ϊstarted״̬�� ����ϵͳ�������´�������ֱ��startService(Intent intent)�����ٴα�����;
	 * 3��START_REDELIVER_INTENT�����������������ʱ��ɱ�����������ڸ�һ��ʱ����Զ�������
	 * �������һ�����ݵ�Intent���󽫻��ٴδ��ݹ�����
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// ִ���ļ������ػ��߲��ŵȲ���
		// ȥִ��һ�����ز���
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
