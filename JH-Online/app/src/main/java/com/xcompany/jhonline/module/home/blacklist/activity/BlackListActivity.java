package com.xcompany.jhonline.module.home.blacklist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.Black;
import com.xcompany.jhonline.module.home.blacklist.adapter.BlackListAapter;
import com.xcompany.jhonline.module.publish.activity.PublishIndustryBlackListActivity;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/12/12 23:36
 */
public class BlackListActivity extends AppCompatActivity {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    private BlackListAapter mAdapter;
    private List<Black> mdatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balck_list);
        ButterKnife.bind(this);
        mAdapter = new BlackListAapter(this, mdatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        View view = View.inflate(this, R.layout.view_divide_10, null);
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
        mAdapter.setOnItemClickListener((position, bean, holder) -> {
            Intent intent = new Intent(BlackListActivity.this, BlackListDetailActivity.class);
            intent.putExtra("id", bean.getId());
            startActivity(intent);
        });
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<List<Black>>>post(ReleaseConfig.baseUrl() + "black/blackList")
                .tag(this)
                .execute(new JHCallback<JHResponse<List<Black>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Black>>> response) {
                        mdatas = response.body().getMsg();
                        mAdapter.setDatas(mdatas);
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onError(Response<JHResponse<List<Black>>> response) {
                        T.showToast(BlackListActivity.this, response.getException().getMessage());
                    }
                });
    }

    @OnClick({R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                Intent intent = new Intent(this, PublishIndustryBlackListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
