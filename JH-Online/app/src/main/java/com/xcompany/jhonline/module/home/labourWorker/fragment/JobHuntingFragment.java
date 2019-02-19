package com.xcompany.jhonline.module.home.labourWorker.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.model.home.JobHunting;
import com.xcompany.jhonline.module.home.buildMaterial.activity.PurchaseDetailActivity;
import com.xcompany.jhonline.module.home.labourWorker.adapter.JobHuntingAdapter;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.SharedPreferenceUtil;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xieliang on 2018/12/5 22:45
 */
public class JobHuntingFragment extends BaseFragment {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    private JobHuntingAdapter mAdapter;
    private List<JobHunting> mdatas = new ArrayList<>();
    private String cityId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_empty_list;
    }

    @Override
    protected void initEventAndData() {
        cityId = SharedPreferenceUtil.getCityId(mContext);
        mAdapter = new JobHuntingAdapter(mContext, mdatas);
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
        }));
        mAdapter.setOnPhoneClickListener(((position, bean, holder) -> {
            if (TextUtils.equals(bean.getSign(), "0")) {
                String telephone = bean.getTelephone();
                if (!TextUtils.isEmpty(telephone)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提示");
                builder.setMessage("查看电话需要消耗5积分");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", ((dialog, which) -> {
                    OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "User/consume")
                            .params("uid", UserService.getInstance().getUid())
                            .params("fid", bean.getId())
                            .params("type", "9")
                            .execute(new JHCallback<JHResponse<String>>() {
                                @Override
                                public void onSuccess(Response<JHResponse<String>> response) {
                                    mdatas.get(position).setSign("0");
                                    mAdapter.setDatas(mdatas);
                                    String telephone = bean.getTelephone();
                                    if (!TextUtils.isEmpty(telephone)) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onError(Response<JHResponse<String>> response) {
                                    T.showToast(mContext, "积分不足！");
                                }
                            });
                }));
                builder.show();
            }
        }));
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<List<JobHunting>>>post(ReleaseConfig.baseUrl() + "worker/workerList")
                .tag(this)
                .params("Cityid", cityId)
                .params("type", 1)
                .execute(new JHCallback<JHResponse<List<JobHunting>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<JobHunting>>> response) {
                        mdatas = response.body().getMsg();
                        mAdapter.setDatas(mdatas);
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onError(Response<JHResponse<List<JobHunting>>> response) {
//                        T.showToast(mContext, response.getException().getMessage());
                        if(mRecyclerView!=null){
                            mRecyclerView.refreshComplete();
                        }
                    }
                });
    }

}
