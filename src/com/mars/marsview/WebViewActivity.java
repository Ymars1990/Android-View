package com.mars.marsview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mars.marsview.utils.ToastManager;
import com.mars.marsview.utils.Utils;

@SuppressLint("JavascriptInterface")
public class WebViewActivity extends BaseActivity implements OnClickListener {
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private WebView webView = null;
	public Handler handler = new Handler();
	private Button javacalljs_bt;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_webview);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);
		javacalljs_bt = (Button) this.findViewById(R.id.javacalljs_bt);
		webView = (WebView) this.findViewById(R.id.webView);
		// 设置字符集编码
		webView.getSettings().setDefaultTextEncodingName("GBK");
		// 开启JavaScript支持
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new JsOperation(this), "client");
		webView.setWebChromeClient(new WebChromeClient() {
		});
		// 加载assets目录下的文件
//		String url = "http://192.188.8.119/onlinepay/register/register.jsp";
		String url = "file:///android_asset/index.html ";
		webView.loadUrl(url);
	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		javacalljs_bt.setOnClickListener(this);

	}

	@Override
	public void initParams() {
		// TODO Auto-generated method stub

	}

	@Override
	public void callFunc() {
		title_tv.setText("WebView");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (webView.canGoBack()) {
				webView.goBack();
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
		case R.id.javacalljs_bt:
			ToastManager.show(this, "正在调用js",0);
			handler.post(new Runnable() {
				public void run() {
					webView.loadUrl("javascript:dialog()");
				}
			});
			break;
		default:
			break;
		}
	}

	private class JsOperation {
		Activity mActivity;

		public JsOperation(Activity activity) {
			mActivity = activity;
		}

		// 测试方法
		@JavascriptInterface
		public void test() {
			Toast.makeText(mActivity, "test", Toast.LENGTH_SHORT).show();
		}

		public void dialog() {

		}
	}

}
