package com.xcompany.jhonline.base;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.List;

// 实现上拉更多，下拉刷新，实现IXListViewListener接口
public abstract class XListViewActivity extends AbstractActivity implements XRecyclerView.LoadingListener{

	public XRecyclerView xRecyclerView;

	protected List<Model> SourceDateList = new ArrayList<>();

	// 分页数量
	protected int limit = 20;

	//页码
	protected int offset = 0;

	public interface Callback< T extends Model>{
		void setDataItems(List<T> items);
	}

	public abstract void getDataItems(int start, int limit,Callback callback);

	@Override
	protected void loadData() {
		onRefresh();
	}

	@SuppressLint("HandlerLeak")
	protected Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == -100) {
				T.showToast(XListViewActivity.this,msg.obj.toString());

				return;
			}
			List<Model> items = (List<Model>) msg.obj;
			// 判断是否是上拉更多，上拉更多时保持当前操作的显示位置
			if (msg.what == 2) {
				SourceDateList.addAll(items);
				xRecyclerView.getAdapter().notifyDataSetChanged();
				// 完毕后去掉header、footer
				xRecyclerView.refreshComplete();
				return;
			}
			//下拉刷新
			if (msg.what == 3) {
				SourceDateList = items;
				initAdapter();
				xRecyclerView.refreshComplete();
				return;

			}
			//首次加载
			if(msg.what == 1) {
				SourceDateList = items;
				initAdapter();
				xRecyclerView.refreshComplete();
				return;
			}

		}
	};

	// 初始化适配器
	public abstract void initAdapter();

	// 下拉刷新
	@Override
	public void onRefresh() {

		getDataItems(0, limit, new Callback<Model>() {
			@Override
			public void setDataItems(List<Model> items) {
				if (items.size() == 0) {
					T.showToast(XListViewActivity.this,"暂无数据，请刷新尝试...");
				}
				offset = items.size();
				Message msg = new Message();
				msg.what = 3;
				msg.obj = items;
				mHandler.sendMessage(msg);
			}
		});

	}

	// 上拉更多
	@Override
	public void onLoadMore() {

		getDataItems(offset, limit, new Callback<Model>() {

			@Override
			public void setDataItems(List<Model> items) {
				if (items.size() == 0) {
					T.showToast(XListViewActivity.this,"暂无数据，请刷新尝试...");
				}
				offset += items.size();
				//最后一页不足一页的处理
				Message msg = new Message();
				msg.what = 2;
				msg.obj = items;
				mHandler.sendMessage(msg);
			}
		});
	}

}
