package com.mars.marsview.adapter;

import java.util.Random;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SamplePagerAdapter extends PagerAdapter {  
    private final Random random = new Random();  
    private int mSize;  

    public SamplePagerAdapter() {  
        mSize = 10;  
    }  

    public SamplePagerAdapter(int count) {  
        mSize = count;  
    }  

    @Override  
    public int getCount() {  
        return mSize;  
    }  

    @Override  
    public boolean isViewFromObject(View view, Object object) {  
        return view == object;  
    }  

    @Override  
    public void destroyItem(ViewGroup view, int position, Object object) {  
        view.removeView((View) object);  
    }  

    @Override  
    public Object instantiateItem(ViewGroup view, int position) {  
        TextView textView = new TextView(view.getContext());  

        textView.setText(position + 1 + "");  
        textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));  
        textView.setGravity(Gravity.CENTER);  
        textView.setTextColor(Color.WHITE);  
        textView.setTextSize(50);  
        view.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);  

        return textView;  
    }  

    // Ôö¼Óitem  
    public void addItem() {  
        mSize++;  
        notifyDataSetChanged();  
    }  

    // É¾³ýitem  
    public void removeItem() {  
        mSize--;  
        mSize = mSize < 0 ? 0 : mSize;  

        notifyDataSetChanged();  
    }  
}  
