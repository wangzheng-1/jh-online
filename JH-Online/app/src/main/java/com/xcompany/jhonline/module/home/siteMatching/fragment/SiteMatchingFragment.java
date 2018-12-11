package com.xcompany.jhonline.module.home.siteMatching.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.xcompany.jhonline.module.home.siteMatching.activity.SiteMatchingDetailActivity;
import com.xcompany.jhonline.module.home.siteMatching.adapter.SiteMatchingAapter;
import com.xcompany.jhonline.network.ApiResponse;
import com.xcompany.jhonline.network.JsonCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xieliang on 2018/12/5 22:45
 */
public class SiteMatchingFragment extends BaseFragment {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    private SiteMatchingAapter mAdapter;
    private List<String> mdatas = new ArrayList<>();
    String url = "https://c.y.qq.com/splcloud/fcgi-bin/gethotkey.fcg";
    private String type = "装载运输服务";

    public static SiteMatchingFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putSerializable("type", type);
        SiteMatchingFragment fragment = new SiteMatchingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_empty_list;
    }

    @Override
    protected void initEventAndData() {
        if (getArguments() != null) {
            if (null != getArguments().get("type")) {
                type = (String) getArguments().get("type");
            }
        }
        for (int i = 0; i < 20; i++) {
            mdatas.add(type + i);
        }
        mAdapter = new SiteMatchingAapter(mContext, mdatas);
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
        mAdapter.setOnItemClickListener(new SiteMatchingAapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String bean, RecyclerView.ViewHolder holder) {
                Intent intent = new Intent(mContext, SiteMatchingDetailActivity.class);
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
