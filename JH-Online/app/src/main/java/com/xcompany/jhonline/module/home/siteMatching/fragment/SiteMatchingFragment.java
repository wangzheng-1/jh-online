package com.xcompany.jhonline.module.home.siteMatching.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.model.home.SiteMatching;
import com.xcompany.jhonline.module.home.siteMatching.activity.SiteMatchingDetailActivity;
import com.xcompany.jhonline.module.home.siteMatching.adapter.SiteMatchingAapter;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.SharedPreferenceUtil;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by xieliang on 2018/12/5 22:45
 */
public class SiteMatchingFragment extends BaseFragment {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    private SiteMatchingAapter mAdapter;
    private List<SiteMatching> mdatas = new ArrayList<>();
    private String cityId;
    private String cid;

    public static SiteMatchingFragment newInstance(String cid) {
        Bundle args = new Bundle();
        args.putSerializable("cid", cid);
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
        cityId = SharedPreferenceUtil.getCityId(mContext);
        if (getArguments() != null) {
            if (null != getArguments().get("cid")) {
                cid = (String) getArguments().get("cid");
            }
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
        mAdapter.setOnItemClickListener(((position, bean, holder) -> {
            Intent intent = new Intent(mContext, SiteMatchingDetailActivity.class);
            intent.putExtra("id", bean.getId());
            startActivity(intent);
        }));
        getData();
    }

    public void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("Cityid", cityId);
        params.put("cid", cid);
        OkGo.<JHResponse<List<SiteMatching>>>post(ReleaseConfig.baseUrl() + "Serve/serveList")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<List<SiteMatching>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<SiteMatching>>> response) {
                        mdatas = response.body().getMsg();
                        mAdapter.setDatas(mdatas);
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onError(Response<JHResponse<List<SiteMatching>>> response) {
//                        T.showToast(mContext, response.getException().getMessage());
                        if(mRecyclerView!=null){
                            mRecyclerView.refreshComplete();
                        }
                    }
                });
    }
}
