package com.mars.marsview;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.entity.AppInfor;
import com.mars.marsview.entity.FinalVar;
import com.mars.marsview.entity.UpdataInfo;
import com.mars.marsview.entity.UpdataInfoParser;
import com.mars.marsview.utils.SharedPreferencesMannager;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.TipsDiaolg;

public class LauncherActivity extends BaseActivity implements OnClickListener {
	private Context mContext = null;
	private Message msg;
	private TextView version_Tv;
	private TextView lanucher_appname_tv;
	private LinearLayout showAd_layout;
	private boolean isADShow = false;
	// ���� ����
	private UpdataInfo info;
	private String localVersion;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_launcher);

	}

	@Override
	public void initUI() {
		version_Tv = (TextView) this.findViewById(R.id.lanucher_version_tv);
		lanucher_appname_tv = (TextView) this
				.findViewById(R.id.lanucher_appname_tv);
		showAd_layout = (LinearLayout) this.findViewById(R.id.showAd_layout);
	}

	@Override
	public void registerEvent() {

	}

	@Override
	public void initParams() {
		mContext = this;
		Utils.getCurrentVersion(mContext);

		if (Utils.isNotEmpty(AppInfor.curVersionName)) {
			localVersion = AppInfor.curVersionName;
		}
		SharedPreferencesMannager.SharedPreferences(mContext);
	}

	@Override
	public void callFunc() {
		version_Tv.setText("�汾:" + AppInfor.curVersionName);
		lanucher_appname_tv.setText("��Ȧ");
		if (isADShow) {
			showAd_layout.setBackgroundResource(R.drawable.ad);
		}
		try {
			CheckVersionTask cv = new CheckVersionTask();
			new Thread(cv).start();
		} catch (Exception e) {
			goHomeActivity();
			e.printStackTrace();
		}
	}

	private void goHomeActivity() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				Intent intent = new Intent(LauncherActivity.this,
						MainActivity.class);
				Utils.startActivity(LauncherActivity.this, intent);
				Utils.closeActivity(LauncherActivity.this, 0);
			}
		};
		timer.schedule(task, 1000);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case FinalVar.UPDATA_NONEED:
				Utils.LogShow("�汾����", "����Ҫ����");
				goHomeActivity();
				break;
			case FinalVar.UPDATA_CLIENT:
				// �Ի���֪ͨ�û���������
				Utils.LogShow("�汾����", "��Ҫ����");
				showUpdateDialog("ϵͳ��ʾ", "����µ����°汾��");
				break;
			case FinalVar.GET_UNDATAINFO_ERROR:
				// ��������ʱ
				Utils.LogShow("�汾����", "��ȡ������������Ϣʧ��");
				goHomeActivity();
				break;
			case FinalVar.DOWN_ERROR:
				// ����apkʧ��
				Utils.LogShow("�汾����", "�����°汾ʧ��");
				goHomeActivity();
				break;
			}
		}
	};

	private TipsDiaolg tipsDialog = null;

	// �Ի���
	public void showUpdateDialog(String title, String message) {
		tipsDialog = new TipsDiaolg(this);
		tipsDialog.builder().setCancelable(false);
		tipsDialog.setBtOnclickListener(this);
		tipsDialog.setTitleText(title);
		tipsDialog.setContentText(message);
		tipsDialog.setViewVisiable(0, 1, 0, 1);
		tipsDialog.show();
	}

	private DownloadManager downloadManager;

	private void sysTemDownloadApk() {
		// ������������
		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(info.getUrl()));
		Utils.LogShow("server", info.getUrl());
		request.setAllowedOverRoaming(false);// ���������Ƿ��������

		// �����ļ����ͣ����������ؽ������Զ��򿪸��ļ�
		MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
		String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap
				.getFileExtensionFromUrl(info.getUrl()));
		request.setMimeType(mimeString);

		// ��֪ͨ������ʾ��Ĭ�Ͼ�����ʾ��
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
		request.setVisibleInDownloadsUi(true);
		request.setTitle("marsview.apk");
		// sdcard��Ŀ¼�µ�download�ļ��У���������
		request.setDestinationInExternalPublicDir("/download/", "marsview.apk");
		// request.setDestinationInExternalFilesDir(),Ҳ�����Լ��ƶ�����·��
		// ����������������ض���
		downloadManager = (DownloadManager) mContext
				.getSystemService(Context.DOWNLOAD_SERVICE);
		// �������ض��к��������񷵻�һ��long�͵�id��
		// ͨ����id����ȡ��������������ȵȣ�������Դ���п������ķ���
		FinalVar.mTaskId = downloadManager.enqueue(request);
		Utils.LogShow("mTaskId", ""+FinalVar.mTaskId);
	}
	private class CheckVersionTask implements Runnable {
		InputStream is;

		public void run() {
			try {
				String path = mContext.getResources().getString(
						R.string.serverurl);
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				int responseCode = conn.getResponseCode();
				if (responseCode == 200) {
					// �ӷ��������һ��������
					is = conn.getInputStream();
				}
				info = UpdataInfoParser.getUpdataInfo(is);
				if (info.getVersion().equals(localVersion)) {
					Message msg = new Message();
					msg.what = FinalVar.UPDATA_NONEED;
					handler.sendMessage(msg);
				} else {
					Log.i("�汾����", "�汾�Ų���ͬ ");
					Message msg = new Message();
					msg.what = FinalVar.UPDATA_CLIENT;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				Message msg = new Message();
				msg.what = FinalVar.GET_UNDATAINFO_ERROR;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tipsBtCancel:
			if (tipsDialog != null) {
				tipsDialog.dismiss();
			}
			goHomeActivity();
			break;
		case R.id.tipsBtConfirm:
			if (tipsDialog != null) {
				tipsDialog.dismiss();
			}
			// downLoadApk();
			sysTemDownloadApk();
			goHomeActivity();
			break;
		default:
			break;
		}
	}

}
