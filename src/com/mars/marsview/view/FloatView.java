package com.mars.marsview.view;

import com.mars.marsview.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class FloatView extends LinearLayout {

	public FloatView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FloatView(Context context, AttributeSet attrs) {
		this(context, attrs,0);

	}

	public FloatView(Context context) {
		this(context,null);
		initUI(context);
	}
	
	private void initUI(Context context){
		setOrientation(VERTICAL);
		 //…Ë÷√øÌ∏ﬂ
        this.setLayoutParams( new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        
        View view = LayoutInflater.from(context).inflate(  
                R.layout.floatview, null); 
        this.addView(view);
	}
}
