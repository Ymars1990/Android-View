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
			// 在广播中取出下载任务的id
			long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
			Utils.LogShow("下载管理器完成下载时ID", ""+id);
			if (id == FinalVar.mTaskId) {
				query.setFilterById(id);
				Cursor c = manager.query(query);
				if (c.moveToFirst()) {
					// 获取文件下载路径
					String filename = c
							.getString(c
									.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
					// 如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
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