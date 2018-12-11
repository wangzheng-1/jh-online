package com.xcompany.jhonline.module.home.subcontract.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.model.home.MusicHotKey;
import com.xcompany.jhonline.module.home.subcontract.activity.TenderDetailActivity;
import com.xcompany.jhonline.module.home.subcontract.adapter.TenderAdapter;
import com.xcompany.jhonline.network.ApiResponse;
import com.xcompany.jhonline.network.JsonCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xieliang on 2018/12/5 22:45
 */
public class TenderFragment extends BaseFragment {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    private TenderAdapter mAdapter;
    private List<String> mdatas = new ArrayList<>();
    String url = "https://c.y.qq.com/splcloud/fcgi-bin/gethotkey.fcg";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tender;
    }

    @Override
    protected void initEventAndData() {
        for (int i = 0; i < 20; i++) {
            mdatas.add("团风县马槽庙镇百沙河治理工程" + i);
        }
        mAdapter = new TenderAdapter(mContext, mdatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        View view = View.inflate(mContext, R.layout.view_divide_10, null);
        mRecyclerView.addHeaderView(view);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoadMore() {
            }
        });
        mAdapter.setOnItemClickListener(new TenderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String bean, RecyclerView.ViewHolder holder) {
                Intent intent = new Intent(mContext, TenderDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getData() {
        OkGo.<ApiResponse<MusicHotKey>>get(url)
                .tag(this)
                .execute(new JsonCallback<ApiResponse<MusicHotKey>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<MusicHotKey>> response) {
                        MusicHotKey data = response.body().getData();
                        List<String> list = new ArrayList<>();
                        for (MusicHotKey.HotkeyBean bean : data.getHotkey()) {
                            list.add(bean.getK());
                        }
                        mAdapter.setDatas(list);
                        mRecyclerView.refreshComplete();
                    }
                });
    }
}
