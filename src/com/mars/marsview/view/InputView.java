package com.mars.marsview.view;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.marsview.R;
import com.mars.marsview.utils.Utils;

public class InputView extends LinearLayout {
	private TextView inputOptionTips = null;
	private EditText inputText = null;
	private ImageView inputDelete = null;
	private ImageView inputOptionImg = null;
	private View divideLine;
	InputEditText inputedittext = null;
	
	public InputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.inputdelete, this);
		inputOptionTips = (TextView) findViewById(R.id.inputOptionTips);
		inputText = (EditText) findViewById(R.id.inputText);
		divideLine = (View) findViewById(R.id.divideLine);
		inputDelete = (ImageView) findViewById(R.id.inputDelete);
		inputOptionImg = (ImageView) findViewById(R.id.inputOptionImg);
	}

	public InputView(Context context) {
		super(context, null);
	}

	// ��һ����չ�ķ���������view�Ŀɼ���
	public void setViewVisiable(int OptionTipsVisiable, int OptionImgVisiable,
			int OptionDivideLine) {// 0�����ɼ�
		// 1���ɼ�
		if (OptionTipsVisiable == 0) {
			inputOptionTips.setVisibility(View.GONE);
		} else {
			inputOptionTips.setVisibility(View.VISIBLE);
		}
		if (OptionImgVisiable == 0) {
			inputOptionImg.setVisibility(View.GONE);
		} else {
			inputOptionImg.setVisibility(View.VISIBLE);
		}
		if (OptionDivideLine == 0) {
			divideLine.setVisibility(View.GONE);
		} else {
			divideLine.setVisibility(View.VISIBLE);
		}
	}

	private boolean pwdflag = false;
	private boolean isSetPwdTye = false;

	public void setPwdInputType(boolean pwdflag) {
		this.pwdflag = pwdflag;
		if (pwdflag) {
			isSetPwdTye = true;
			inputText.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		} else {
			inputText.setInputType(InputType.TYPE_CLASS_TEXT);
		}
		inputText.setSelection(getInputText().length());
	}

	private boolean delDisable = false;

	public void setInputDelete(int resId) {
		inputDelete.setImageResource(resId);
		delDisable = true;
	}

	public void setInputOptionImg(int resId) {
		inputOptionImg.setImageResource(resId);
	}

	public void setEdtDigits(int digitsType, int length) {// digitsType:1������0-9
															// 2����ֹ����Ƿ�֧��(0-9��a-z��A-Z���»���)
															// 3��������֤
		inputedittext = new InputEditText(digitsType, inputText, length){

			@Override
			public void showDelImg(boolean flag) {
				if(flag){
					inputDelete.setVisibility(View.VISIBLE);
				}else{
					inputDelete.setVisibility(View.GONE);
				}
			}
			
		};
		inputText.addTextChangedListener(inputedittext);
	}

	public void setHint(String hintText) {
		inputText.setHint(hintText);
	}

	public void setInputOption(String text) {
		inputOptionTips.setText(text);
	}

	public void setInputLimitLenth(int Lenth) {
		inputText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				Lenth) });
	}

	public void setInputText(String text) {// length=-1,���޳��� ,0����� X���Զ��峤��
		inputText.setText(text);
	}

	public String getInputText() {
		return inputText.getText().toString();
	}

	public void registerEvents() {
		inputDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!delDisable) {
					setInputText("");
				} else {
					if (isSetPwdTye) {
						Utils.LogShow("Length",""+ getInputText().length());

						if (pwdflag) {
							setPwdInputType(false);
						} else {
							setPwdInputType(true);
						}
					}
				}
			}
		});
	}
}
