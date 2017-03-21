package com.mars.marsview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;

public class VideoPlayActivity extends BaseActivity implements OnClickListener,
		OnErrorListener {

	private TextView title_tv;
	private LinearLayout title_backlayout;
	private LinearLayout playLayout;
	private RelativeLayout title_layout;
	private Context mContext;
	private Button videoViewPlay;
	private Button androidViewPlay;
	private VideoView myVideoView;
	private Uri uri;

	private static int playType = 1;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_videoplay);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		title_layout = (RelativeLayout) this.findViewById(R.id.title_layout);
		playLayout = (LinearLayout) this.findViewById(R.id.playLayout);
		videoViewPlay = (Button) this.findViewById(R.id.videoViewPlay);
		androidViewPlay = (Button) this.findViewById(R.id.androidViewPlay);
		myVideoView = (VideoView) this.findViewById(R.id.myVideoView);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		videoViewPlay.setOnClickListener(this);
		androidViewPlay.setOnClickListener(this);
	}

	@Override
	public void initParams() {
		mContext = this;
		uri = Uri
				.parse("http://www.cccpay.cn/downloads/android/beta/eason.flv");
	}

	@Override
	public void callFunc() {
		title_tv.setText("Video Play");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
		
			if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				videoPlayFinish();
			} else {
				Utils.closeActivity(this, 0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_backlayout:
			Utils.closeActivity(this, 0);
			break;
		case R.id.videoViewPlay:
			playType = 1;
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		case R.id.androidViewPlay:
			playType = 2;
			androidViewPlay();
			break;
		}
	}

	@Override
	protected void onResume() {
		/**
		 * 设置为横屏
		 */
		if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			myVideoView.setVisibility(View.VISIBLE);
			title_layout.setVisibility(View.GONE);
			playLayout.setVisibility(View.GONE);
			Utils.LogShow("playType", ""+playType);
			switch (playType) {
			case 1:
				videoViewPlay();
				break;
			case 2:
				androidViewPlay();
				break;
			default:
				break;
			}

		}
		super.onResume();
	}

	private void videoViewPlay() {
		myVideoView.setMediaController(new MediaController(this));
		myVideoView.setVideoURI(uri);
		myVideoView.start();
		myVideoView.requestFocus();
		myVideoView.setOnErrorListener(this);
		myVideoView
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						// 播放结束后的动作
						videoPlayFinish();

					}
				});
	}

	private void androidViewPlay() {
		// 调用系统自带的播放器
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(uri, "video/*");
		startActivity(intent);
	}

	private void mediaPlayerPlay() {

	}

	private void videoPlayFinish() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		myVideoView.setVisibility(View.GONE);
		title_layout.setVisibility(View.VISIBLE);
		playLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		switch (what) {
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			ToastManager.show(mContext, "发生未知错误", 0);
			break;
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			ToastManager.show(mContext, "媒体服务器死机", 0);
			break;
		default:
			ToastManager.show(mContext, "onError+" + what, 0);
			break;
		}
		switch (extra) {
		case MediaPlayer.MEDIA_ERROR_IO:
			// io读写错误
			ToastManager.show(mContext, "文件或网络相关的IO操作错误", 0);
			break;
		case MediaPlayer.MEDIA_ERROR_MALFORMED:
			// 文件格式不支持
			ToastManager.show(mContext,  "比特流编码标准或文件不符合相关规范", 0);
			break;
		case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
			// 一些操作需要太长时间来完成,通常超过3 - 5秒。
			ToastManager.show(mContext,"操作超时", 0);
			break;
		case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
			// 比特流编码标准或文件符合相关规范,但媒体框架不支持该功能
			ToastManager.show(mContext,"比特流编码标准或文件符合相关规范,但媒体框架不支持该功能", 0);
			break;
		default:
			
			ToastManager.show(mContext,"onError+" + extra, 0);
			break;
		}
		return false;
	}
}
