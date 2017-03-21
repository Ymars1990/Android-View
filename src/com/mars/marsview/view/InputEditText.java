package com.mars.marsview.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.mars.marsview.utils.Utils;

public abstract class InputEditText implements TextWatcher {
	private int FilterType = 0;// 1、普通输入（中文、数字、英文字母） 2、手机号码 3、邮箱
	private EditText editView = null;
	private int length = -1;

	public InputEditText(int FilterType, EditText editView, int length) {
		this.FilterType = FilterType;
		this.editView = editView;
		this.length = length;
	}

	@Override
	public void afterTextChanged(Editable s) {
		if(Utils.isNotEmpty(editView.getText().toString())){
			showDelImg(true);
		}else{
			showDelImg(false);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// Log.i("start",""+ start);
		// Log.i("count", ""+count);
		String editable = editView.getText().toString();
		Log.i("editable", "" + editable);
		String str = null;
		switch (FilterType) {
		case 1:
			str = Utils.stringFilter(editable.substring(0, start),
					editable.substring(start, start + count), FilterType);
			break;
		case 2:
			str = Utils.stringFilter(editable.substring(0, start),
					editable.substring(start, start + count), FilterType);
			break;
		case 3:
			str = Utils.stringFilter(editable.substring(0, start),
					editable.substring(start, start + count), FilterType);
			break;
		default:
			break;
		}
		Log.i("length", "" + length);
		Log.i("str", "" + str);
		if (length > 0 && str.length() > length) {
			str = str.substring(0, length);
		}
		if (!editable.equals(str)) {
			editView.setText(str);
			// 设置新的光标所在位置
			editView.setSelection(str.length());
		}
	}
	
	public abstract void showDelImg(boolean flag);
}
