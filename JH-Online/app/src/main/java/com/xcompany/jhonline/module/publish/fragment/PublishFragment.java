package com.xcompany.jhonline.module.publish.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.ListBaseFragment;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.publish.PublishItemBean;
import com.xcompany.jhonline.model.report.Moment;
import com.xcompany.jhonline.module.publish.activity.PublishSelectTypeActivity;
import com.xcompany.jhonline.module.publish.activity.PublishTypeActivity;
import com.xcompany.jhonline.module.publish.adapter.PublishAdapter;
import com.xcompany.jhonline.module.report.fragment.ReportFragment;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/11/21 11:47
 */
public class PublishFragment extends ListBaseFragment {

    @BindView(R.id.meShareText)
    TextView meShareText;
    @BindView(R.id.reportTitleText)
    TextView reportTitleText;
    @BindView(R.id.mePublishText)
    TextView mePublishText;

    private PublishAdapter publishAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_publish;
    }

    @Override
    protected void initEventAndData() {
    }

    @Override
    public void getDataItems(int page, Callback callback) {
        OkGo.<JHResponse<List<Moment>>>post(ReleaseConfig.baseUrl() + "User/release?p=" + page)
                .tag(this)
                .params("uid",UserService.getInstance().getUid())
                .execute(new JHCallback<JHResponse<List<Moment>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Moment>>> response) {
                        List<Moment> resultList = response.body().getMsg();
                        callback.setDataItems(resultList);
                    }

                    @Override
                    public void onError(Response<JHResponse<List<Moment>>> response) {
                        T.showToast(PublishFragment.this.getActivity(), response.getException().getMessage());
                        xRecyclerView.refreshComplete();
                    }
                });
        List<PublishItemBean> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(new PublishItemBean("分包单位"));
        }
        callback.setDataItems(data);
    }

    @Override
    public void initAdapter() {
        xRecyclerView = mView.findViewById(R.id.recyclerList);
        publishAdapter = new PublishAdapter(PublishFragment.this.getContext(), SourceDateList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PublishFragment.this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setAdapter(publishAdapter);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(this);
        publishAdapter.setOnItemClickListener(new PublishAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Model bean, RecyclerView.ViewHolder holder) {

            }
        });
    }

    @OnClick({R.id.meShareText, R.id.mePublishText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.meShareText:
                break;
            case R.id.mePublishText:
                Intent intent = new Intent(PublishFragment.this.getContext(),PublishTypeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
