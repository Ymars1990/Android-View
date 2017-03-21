package com.mars.marsview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mars.marsview.adapter.SortAdapter;
import com.mars.marsview.entity.SortModel;
import com.mars.marsview.utils.Pinyin4jUtil;
import com.mars.marsview.utils.PinyinComparator;
import com.mars.marsview.utils.Utils;
import com.mars.marsview.view.ClearEditText;
import com.mars.marsview.view.SideBar;
import com.mars.marsview.view.SideBar.OnTouchingLetterChangedListener;
import com.mars.marsview.view.animation.ViewEffect;

public class IndexerActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener,OnScrollListener{
	private TextView title_tv;
	private LinearLayout title_backlayout;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private ListView sortListView;
	private Context mContext;
	
	/**
	 * ����ת����ƴ������
	 */

	private List<SortModel> SourceDateList;
	private PinyinComparator pinyinComparator;

	@Override
	public void onCreate() {
		setContentView(R.layout.activity_indexer);

	}

	@Override
	public void initUI() {
		title_tv = (TextView) this.findViewById(R.id.title_titleTv);
		title_backlayout = (LinearLayout) this
				.findViewById(R.id.title_backlayout);

		sideBar = (SideBar) findViewById(R.id.indexer_sidrbar);
		dialog = (TextView) findViewById(R.id.indexer_dialog);
		sideBar.setTextView(dialog);
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		sortListView = (ListView) findViewById(R.id.indexer_litview);

	}

	@Override
	public void registerEvent() {
		title_backlayout.setOnClickListener(this);
		// �����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// ����ĸ�״γ��ֵ�λ��
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});
		sortListView.setOnScrollListener(this);
		sortListView.setOnItemClickListener(this);
		// �������������ֵ�ĸı�����������
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);

	}

	@Override
	public void initParams() {
		mContext = this;
		pinyinComparator = new PinyinComparator();
		SourceDateList = filledData(mContext.getResources().getStringArray(
				R.array.data));
		Collections.sort(SourceDateList, pinyinComparator);
	}

	@Override
	public void callFunc() {
		title_tv.setText("Indexer");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Utils.closeActivity(this, 0);
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
		default:
			break;
		}
	}

	/**
	 * ΪListView�������
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] data) {
		Arrays.sort(data);
		List<SortModel> mSortList = new ArrayList<SortModel>();
		for (int i = 0; i < data.length; i++) {
//			Utils.LogShow("data", data[i]);
			SortModel sortModel = new SortModel();
			sortModel.setName(data[i]);
			// ����ת����ƴ��
			String pinyin = Pinyin4jUtil.converterToSpell(data[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
//			Utils.LogShow("pinyin",pinyin);
			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}
			mSortList.add(sortModel);
		}
		Collections.sort(mSortList, pinyinComparator);
		return mSortList;

	}

	/**
	 * ����������е�ֵ���������ݲ�����ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| Pinyin4jUtil.converterToSpell(name).toLowerCase().startsWith(
								filterStr.toString().toLowerCase())
					) {
					filterDateList.add(sortModel);
				}
			}
		}
		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		   switch (scrollState) {  
           case OnScrollListener.SCROLL_STATE_IDLE: 
        	   //ֹͣ
//        	   mClearEditText.setVisibility(View.VISIBLE);
        	   ViewEffect.setAnimation(2, mClearEditText);
        	   Utils.LogShow("listView","����ֹͣ");
               break;  
           case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:  
        	   //���ڻ���..
        	   Utils.LogShow("listView","����ing");
//        	   mClearEditText.setVisibility(View.GONE);
        	   ViewEffect.setAnimation(1, mClearEditText);
               break;  
           case OnScrollListener.SCROLL_STATE_FLING: 
        	   //��ʼ����
        	   Utils.LogShow("listView","������ʼ");
               break;  
           }  
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}
}
