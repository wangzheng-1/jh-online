package com.xcompany.jhonline.base;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.List;

public abstract class ListBaseFragment extends BaseFragment implements XRecyclerView.LoadingListener {

    XRecyclerView xRecyclerView;

    protected List<Object> SourceDateList = new ArrayList<>();
    protected RecyclerView.Adapter adapter;

    // 分页数量
    protected int limit = 20;

    //页码
    protected int offset = 0;

    public interface Callback{
        void setDataItems(List<Object> items);
    }

    public abstract void getDataItems(int start, int limit,Callback callback);


    protected void loadData() {

        getDataItems(offset, limit, new Callback() {
            @Override
            public void setDataItems(List<Object> items) {
                if (items.size() == 0) {
                    T.showToast(mContext,"暂无数据，请刷新尝试...");
                }
                offset = items.size();
                Message msg = new Message();
                msg.what = 1;
                msg.obj = items;
                mHandler.sendMessage(msg);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    protected Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == -100) {
                T.showToast(ListBaseFragment.this.getActivity(),msg.obj.toString());

                return;
            }
            List<Object> items = (List<Object>) msg.obj;
            // 判断是否是上拉更多，上拉更多时保持当前操作的显示位置
            if (msg.what == 2) {
                SourceDateList.addAll(items);
                adapter.notifyDataSetChanged();
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
    protected void initAdapter() {

    }

    // 下拉刷新
    @Override
    public void onRefresh() {

        getDataItems(0, limit, new Callback() {
            @Override
            public void setDataItems(List<Object> items) {
                if (items.size() == 0) {
                    T.showToast(ListBaseFragment.this.getActivity(),"暂无数据，请刷新尝试...");
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

        getDataItems(offset, limit, new Callback() {

            @Override
            public void setDataItems(List<Object> items) {
                if (items.size() == 0) {
                    T.showToast(ListBaseFragment.this.getActivity(),"暂无数据，请刷新尝试...");
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
