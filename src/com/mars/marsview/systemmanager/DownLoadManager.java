package com.mars.marsview.systemmanager;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class DownLoadManager {
	// 安装apk
	public static void installApk(Context mContext, File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);
	}
}