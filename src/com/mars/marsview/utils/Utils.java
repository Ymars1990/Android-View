package com.mars.marsview.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.mars.marsview.R;
import com.mars.marsview.entity.AppInfor;

public class Utils {

	public static Bitmap drawableToBitamp(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap();
	}

	public static Bitmap readBitMap(String url) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(url));
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			// opt.inSampleSize = computeSampleSize(opt, -1, 128*128);
			Utils.LogShow("当前文件总大小", "" + fis.available());
			if (fis.available() > 1024 * 1024 * 2) {
				opt.inSampleSize = 4;
			}
			return BitmapFactory.decodeStream(fis, null, opt);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static float dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (dipValue * scale + 0.5f);
	}

	public static String stringFilter(String str, String substr, int FilterType) {
		// 只允许字母和数字
		String regEx = null;
		switch (FilterType) {
		case 1:
			// regEx = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
			regEx = "[\u4e00-\u9fa5\\w]+";
			break;
		case 2:
			regEx = "[0-9]+";// 如有遗漏请自行添加，号码段增加
			break;
		case 3:
			regEx = "[@a-zA-Z0-9_.]+$";
			break;
		default:
			break;
		}
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(substr);
		if (!m.matches()) {
			substr = "";
			Log.i("substr", "非法字符");
		}

		return str.concat(substr);
	}

	// 验证字符串不为空
	public static boolean isNotEmpty(String str) {
		return str != null && !str.equals("");
	}

	// 验证手机格式
	public static boolean isPhoneNumber(String str) {
		return str.length() == 11;
	}

	// 验证邮箱
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static void closeActivity(Activity activity, int style) {
		activity.finish();
		switch (style) {
		case 0:
			activity.overridePendingTransition(R.anim.push_right_out,
					R.anim.push_right_in);
			break;
		case 1:
			activity.overridePendingTransition(0, R.anim.push_down_out);
			break;
		default:
			break;
		}
	}

	public static void startActivity(Activity activity, Intent intent) {
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);

	}

	// 验证手机
	public static boolean isPhone(String phone) {
		Pattern p = Pattern
				.compile("^(13[0-9]|147|15[0-9]|18[0-9]|17[0-9])\\d{8}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	// 判断输入框是否为空
	public static int tipsAnimation(EditText edView, int judgeCondition) {
		String content = edView.getText().toString();
		TranslateAnimation translate = new TranslateAnimation(-20, 20, 0, 0);
		translate.setDuration(100);
		translate.setRepeatCount(3);
		translate.setRepeatMode(Animation.REVERSE);
		if (!Utils.isNotEmpty(content)) {
			edView.startAnimation(translate);
			return 1;// 为空
		}
		switch (judgeCondition) {
		case 0:// 判断是否为手机号码
			if (!isPhone(content)) {
				edView.startAnimation(translate);
				return 2;// 判断条件不满足
			}
			break;
		case 1:// 判断是否为有邮箱格式
			if (!isEmail(content)) {
				edView.startAnimation(translate);
				return 2;// 判断条件不满足
			}
			break;
		default:
			break;
		}
		return 0;
	}

	// 获取系统时间
	public static String getSysTime(Context mcontent) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	// 打印log
	public static void LogShow(String tag, Object obj) {
		if (obj instanceof String) {
			Log.i(tag, (String) obj);
		} else if (obj instanceof Integer) {
			Log.i(tag, "" + ((Integer) obj).intValue());
		} else if (obj instanceof Long) {
			Log.i(tag, "" + (Long) obj);
		} else if (obj instanceof Boolean) {
			Log.i(tag, "" + (Boolean) obj);
		} else if (obj instanceof Float) {
			Log.i(tag, "" + (Float) obj);
		}
	}

	// 获取app版本信息
	public static void getCurrentVersion(Context mContext) {
		try {
			PackageInfo pkinfo = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0);
			AppInfor.curVersionName = pkinfo.versionName;
			AppInfor.curVersionCode = pkinfo.versionCode;
			AppInfor.versionNum = AppInfor.curVersionName.replace(".", "");
			LogShow("curVersionName:", "" + AppInfor.curVersionName);
			LogShow("curVersionCode:", "" + AppInfor.curVersionCode);
			LogShow("versionNum:", "" + AppInfor.versionNum);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static int getWindowsWidth(Activity mContext) {
		WindowManager manager = mContext.getWindowManager();
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		int width2 = outMetrics.widthPixels;
		return width2;
	}

	public static int getWindowsHeight(Activity mContext) {
		WindowManager manager = mContext.getWindowManager();
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		int height = outMetrics.heightPixels;
		return height;
	}


	public static String transfloat2(float price) {
		DecimalFormat decimalFormat = new DecimalFormat("##0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(price);
		return p;
	}

	public static int dp2px(float dpVaule, Activity activity) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVaule, activity.getResources().getDisplayMetrics());
	}

	public static int sp2px(int spVaule, Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVaule, context.getResources().getDisplayMetrics());
	}
	public static int getAndroidOSVersion()
    {
     	 int osVersion;
     	 try
     	 {
     		osVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
     	 }
     	 catch (NumberFormatException e)
     	 {
     		osVersion = 0;
     	 }
     	 
     	 return osVersion;
   }
}
