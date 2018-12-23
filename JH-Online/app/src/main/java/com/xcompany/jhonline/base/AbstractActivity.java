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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		onAppCreate(savedInstanceState);
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

	/**
	 * Replace onCreate method. 为什么要提供这个函数呢? 因为试用自定义Title的话需要先设置Activity的view,
	 * 然后才能设置自定义Title, 所以这里使用这个函数用来设置具体Activity的view.
	 *
	 * @param savedInstanceState
	 */
	public abstract void onAppCreate(Bundle savedInstanceState);


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



}
