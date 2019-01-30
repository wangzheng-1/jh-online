package com.xcompany.jhonline.module.home.certificate.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.model.home.Registration;
import com.xcompany.jhonline.module.home.certificate.acitivity.RegistrationDetailActivity;
import com.xcompany.jhonline.module.home.certificate.adapter.RegistrationAdapter;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.SharedPreferenceUtil;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xieliang on 2018/12/5 22:45
 */
public class RegistrationFragment extends BaseFragment {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    private RegistrationAdapter mAdapter;
    private List<Registration> mdatas = new ArrayList<>();
    private String cityId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rent_seeking;
    }

    @Override
    protected void initEventAndData() {
        cityId = SharedPreferenceUtil.getCityId(mContext);
        mAdapter = new RegistrationAdapter(mContext, mdatas);
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
            Intent intent = new Intent(mContext, RegistrationDetailActivity.class);
            intent.putExtra("id", bean.getId());
            startActivity(intent);
        }));
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<List<Registration>>>post(ReleaseConfig.baseUrl() + "Licence/licenceList")
                .tag(this)
                .params("Cityid", cityId)
                .params("type", 0)
                .execute(new JHCallback<JHResponse<List<Registration>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Registration>>> response) {
                        mdatas = response.body().getMsg();
                        mAdapter.setDatas(mdatas);
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onError(Response<JHResponse<List<Registration>>> response) {
//                        T.showToast(mContext, response.getException().getMessage());
                    }
                });
    }
}
