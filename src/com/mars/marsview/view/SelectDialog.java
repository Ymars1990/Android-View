package com.mars.marsview.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mars.marsview.R;
import com.mars.marsview.utils.Utils;

public class SelectDialog {
	private Context context = null;
	private Dialog dialog = null;
	private ScrollView selectDialogScrollView = null;
	private TextView selectDialogCancel = null;
	private LinearLayout selectDialogLayout = null;
	private Display display;

	private List<SelectItem> ItemList;

	public SelectDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public SelectDialog builder() {
		// ��ȡDialog����
		View view = LayoutInflater.from(context).inflate(R.layout.selectdialog,
				null);
		// ����Dialog��С���Ϊ��Ļ���
		view.setMinimumWidth(display.getWidth());
		selectDialogScrollView = (ScrollView) view
				.findViewById(R.id.selectDialogScrollView);
		selectDialogLayout = (LinearLayout) view
				.findViewById(R.id.selectDialogLayout);
		selectDialogCancel = (TextView) view
				.findViewById(R.id.selectDialogCancel);
		selectDialogCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		// ����Dialog���ֺͲ���
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);
		return this;
	}

	// ���÷��ؼ��Ƿ�ر�dialog
	public void setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
	}

	// �����ⲿ�����ȡ��
	public void setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
	}

	// ����ѡ��Item
	public void addSelectItem(String Item, OnSelcetItemClickListener listener) {
		if (ItemList == null) {
			ItemList = new ArrayList<SelectItem>();
		}
		ItemList.add(new SelectItem(Item, listener));
	}

	// ѡ����
	public class SelectItem {
		String description;
		OnSelcetItemClickListener itemClickListener;

		public SelectItem(String description,
				OnSelcetItemClickListener itemClickListener) {
			this.description = description;
			this.itemClickListener = itemClickListener;
		}
	}

	// ����¼������ӿ�
	public interface OnSelcetItemClickListener {
		void onClick(int which);
	}

	// ���ò���
	private void setSheetItems() {
		if (ItemList == null || ItemList.size() <= 0) {
			return;
		}
		int size = ItemList.size();

		// �߶ȿ��ƣ�����ѽ���취
		// �����Ŀ�����ʱ����Ƹ߶�
		if (size >= 7) {
			LayoutParams params = (LayoutParams) selectDialogScrollView
					.getLayoutParams();
			params.height = display.getHeight() / 2;
			selectDialogScrollView.setLayoutParams(params);
		}
		// ѭ�������Ŀ
		for (int i = 1; i <= size; i++) {
			final int index = i;
			SelectItem selectItem = ItemList.get(i - 1);
			final OnSelcetItemClickListener listener = (OnSelcetItemClickListener) selectItem.itemClickListener;

			TextView textView = new TextView(context);
			textView.setText(selectItem.description);
			textView.setTextSize(15);
			textView.setGravity(Gravity.CENTER);
			if (size == 1) {
				textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
			} else {
				if (i == 1) {
					textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
				} else if (i < size) {
					textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
				} else {
					textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
				}
			}
			// ����TextView������ɫ
			textView.setTextColor(Color.parseColor("blue"));
			textView.setTextColor(context.getResources().getColor(
					R.color.bgblur));

			// �߶�
			float scale = context.getResources().getDisplayMetrics().density;
			int height = (int) (45 * scale + 0.5f);
			textView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, height));
			// ����¼�
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onClick(index);
					dismiss();
				}
			});
			selectDialogLayout.addView(textView);
			if (i > 0 && i < size) {
				View divideLineView = null;
				divideLineView = new View(context);
				divideLineView.setBackgroundResource(R.color.dividingLine);
				divideLineView.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, Utils.dp2px(0.5f,
								(Activity) context)));
				selectDialogLayout.addView(divideLineView);
			}
		}
	}

	// ��ʾDialog
	public void show() {
		setSheetItems();
		dialog.show();
	}

	// �ر�Dialog
	public void dismiss() {
		if (isShow()) {
			dialog.dismiss();
		}
	}

	public boolean isShow() {
		return dialog != null && dialog.isShowing();
	}

}
