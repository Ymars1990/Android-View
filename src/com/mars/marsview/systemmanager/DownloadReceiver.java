package com.mars.marsview.systemmanager;

import java.io.File;

import com.mars.marsview.entity.FinalVar;
import com.mars.marsview.utils.Utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

public class DownloadReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		DownloadManager manager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);
		switch (intent.getAction()) {
		case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
			DownloadManager.Query query = new DownloadManager.Query();
			// �ڹ㲥��ȡ�����������id
			long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
			Utils.LogShow("���ع������������ʱID", ""+id);
			if (id == FinalVar.mTaskId) {
				query.setFilterById(id);
				Cursor c = manager.query(query);
				if (c.moveToFirst()) {
					// ��ȡ�ļ�����·��
					String filename = c
							.getString(c
									.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
					// ����ļ�����Ϊ�գ�˵���Ѿ������ˣ��õ��ļ�������ﶼ��
					Utils.LogShow("filename", filename);
					if (filename != null) {
						DownLoadManager.installApk(context, new File(filename));
					}
				}
			}
			break;
		case DownloadManager.ACTION_NOTIFICATION_CLICKED:
			break;
		default:

			break;
		}

	}
}