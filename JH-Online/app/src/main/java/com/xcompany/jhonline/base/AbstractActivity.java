/**
 *
 * 所有Activity对应公共基础类，用于处理标题相关等
 *
 */
package com.xcompany.jhonline.base;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xcompany.jhonline.R;

public abstract class AbstractActivity extends BaseActivity{

	// 自定义标题的实现空间
	protected RelativeLayout titleLayout;

	/** 标题左边的返回按钮 */
	protected ImageButton homeBtn;

	/** 标题文本内容 */
	protected TextView titleText;
	/** 副标题文本内容 */
	protected TextView titleChildText;

	/** 标题右边图形操作按钮 */
	protected ImageView rightButton;

	/** 标题右边文本操作按钮 */
	protected TextView rightText;

	/** activity的布局页面 */
	public LinearLayout mainLayout;

	/** activity的内容 */
	private LinearLayout contentLayout;

	/** 标题左右按钮click监听器实现 ，子类如果有特殊需求可以装饰该实现 */
	protected OnClickListener mTitleButtonOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.homeButton:
				// 返回键功能,直接结束当前activity,返回到上一activity.
				handleLeftHomeButton();
				break;
			case R.id.rightButton:
				// 右边操作按钮点击事件
				handleRightImageButton();
				break;
			case R.id.rightText:
				// 打开搜索股票对话框
				handleRightTextButton();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mainLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.activity_base_main_layout, null);

		contentLayout = (LinearLayout) mainLayout
				.findViewById(R.id.main_up_layout);
		onAppCreate(savedInstanceState);

		//标题栏显示
		if (!onCreateTitleView()){
			createTitleView();
		}
		else{
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					loadData();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();

	}
	/**
	 * 布局设置完成后加载数据
	 */
	protected void loadData() {

	};

	protected void createTitleView() {

		if (!onCreateTitleView()) {
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			int titleLayoutId = R.layout.activity_base_title;
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					titleLayoutId);
			titleLayout =  findViewById(R.id.screen);
			titleText =  findViewById(R.id.titleText);
			titleChildText = findViewById(R.id.titleChildText);
			homeBtn = findViewById(R.id.homeButton);
			rightButton = findViewById(R.id.rightButton);
			rightText = findViewById(R.id.rightText);
			setLeftHomeButtonText(homeBtn);
			setRightImageButton(rightButton);
			setRightTextButton(rightText);

			if (homeBtn != null) {
				homeBtn.setOnClickListener(mTitleButtonOnClickListener);
			}
			if (rightButton != null) {
				rightButton.setOnClickListener(mTitleButtonOnClickListener);
			}
			if (rightText != null) {
				rightButton.setOnClickListener(mTitleButtonOnClickListener);
			}
		}

	}

	/**
	 *  创建标题栏，默认不创建
	 * @return
	 */
	protected boolean onCreateTitleView() {
		return false;
	}

	@Override
	public void setContentView(int layoutResID) {

		LayoutInflater li = LayoutInflater.from(this);
		contentLayout.removeAllViews();
		li.inflate(layoutResID, contentLayout);
		super.setContentView(mainLayout);
	}

	@Override
	public void setContentView(View view) {
		contentLayout.addView(view);
		super.setContentView(mainLayout);

	}

	protected OnClickListener getHomeBtnListener() {
		return mTitleButtonOnClickListener;
	}

	/**
	 * Replace onCreate method. 为什么要提供这个函数呢? 因为试用自定义Title的话需要先设置Activity的view,
	 * 然后才能设置自定义Title, 所以这里使用这个函数用来设置具体Activity的view.
	 *
	 * @param savedInstanceState
	 */
	public abstract void onAppCreate(Bundle savedInstanceState);


	Handler handler = new Handler();


	/**
	 * 显示一个提示信息,例如 添加(删除)自选股成功后 提升用户操作成功
	 *
	 * @param message
	 */
	public void showToast(int message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示一个提示信息,例如 添加(删除)自选股成功后 提升用户操作成功
	 *
	 * @param message
	 */
	public void showToast(String message) {
		Toast toast = Toast.makeText(this.getApplicationContext(),
				message, Toast.LENGTH_SHORT);
		toast.show();
	}

	/***
	 * 设置Title的文字
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		if (titleText != null){
			titleText.setText(title);
		}
	}

	public void setChildTitle(String childTitle) {
		if (titleChildText != null)
			titleChildText.setText(childTitle);
	}

	/**
	 * 设置左边返回按钮文本,背景
	 */
	public void setLeftHomeButtonText(ImageView homeBtn) {
		if (null != homeBtn) {
			homeBtn.setImageResource(R.drawable.back_icon);
		}
	}

	/**
	 * 设置右边图片按钮
	 */
	public void setRightImageButton(ImageView rightButton) {

	}

	/**
	 * 设置右边图片按钮
	 */
	public void setRightTextButton(TextView rightButton) {

	}

	public void handleLeftHomeButton() {
		onBackPressed();
	}

	public void handleRightImageButton() {

	}

	public void handleRightTextButton() {

	}

}
