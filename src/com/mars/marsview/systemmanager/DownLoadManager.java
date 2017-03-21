package com.mars.marsview.systemmanager;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class DownLoadManager {
	// ��װapk
	public static void installApk(Context mContext, File file) {
		Intent intent = new Intent();
		// ִ�ж���
		intent.setAction(Intent.ACTION_VIEW);
		// ִ�е���������
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);
	}
}