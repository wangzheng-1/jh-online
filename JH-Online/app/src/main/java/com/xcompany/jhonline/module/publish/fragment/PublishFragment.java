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
import com.xcompany.jhonline.model.publish.PublishTypeItemBean;
import com.xcompany.jhonline.model.report.Moment;
import com.xcompany.jhonline.module.home.blacklist.activity.BlackListDetailActivity;
import com.xcompany.jhonline.module.home.buildMaterial.activity.PurchaseDetailActivity;
import com.xcompany.jhonline.module.home.buildMaterial.activity.QualitySupplierDetailActivity;
import com.xcompany.jhonline.module.home.certificate.acitivity.QualificationHandleDetailActivity;
import com.xcompany.jhonline.module.home.certificate.acitivity.RegistrationDetailActivity;
import com.xcompany.jhonline.module.home.equipment.activity.RentSeekingDetailActivity;
import com.xcompany.jhonline.module.home.equipment.activity.RentingDetailActivity;
import com.xcompany.jhonline.module.home.labourWorker.activity.HiringDetailActivity;
import com.xcompany.jhonline.module.home.siteMatching.activity.SiteMatchingDetailActivity;
import com.xcompany.jhonline.module.home.subcontract.activity.QualityTeamDetailActivity;
import com.xcompany.jhonline.module.home.subcontract.activity.TenderDetailActivity;
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
        OkGo.<JHResponse<List<PublishTypeItemBean>>>post(ReleaseConfig.baseUrl() + "User/release?p=" + page)
                .tag(this)
                .params("uid",UserService.getInstance().getUid())
//                .params("uid","28")
                .execute(new JHCallback<JHResponse<List<PublishTypeItemBean>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<PublishTypeItemBean>>> response) {
                        List<PublishTypeItemBean> resultList = response.body().getMsg();
                        callback.setDataItems(resultList);
                    }

                    @Override
                    public void onError(Response<JHResponse<List<PublishTypeItemBean>>> response) {
                        T.showToast(PublishFragment.this.getActivity(), response.getException().getMessage());
                        callback.setDataItems(null);
                    }
                });
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
                PublishTypeItemBean publishTypeItemBean = (PublishTypeItemBean)bean;
                Intent intent = new Intent();
                Integer type = publishTypeItemBean.getType();
                if(type != null){
                    if(type == 0){
                        intent.setClass(PublishFragment.this.getContext(),QualityTeamDetailActivity.class);
                    }
                    else if(type == 1){
                        intent.setClass(PublishFragment.this.getContext(),TenderDetailActivity.class);
                    }
                    else if(type == 2){
                        intent.setClass(PublishFragment.this.getContext(),RentingDetailActivity.class);

                    } else if(type == 3){
                        intent.setClass(PublishFragment.this.getContext(),RentSeekingDetailActivity.class);

                    } else if(type == 4){
                        intent.setClass(PublishFragment.this.getContext(),QualitySupplierDetailActivity.class);

                    } else if(type == 5){
                        intent.setClass(PublishFragment.this.getContext(),PurchaseDetailActivity.class);

                    } else if(type == 6){
                        intent.setClass(PublishFragment.this.getContext(),RegistrationDetailActivity.class);

                    } else if(type == 7){
                        intent.setClass(PublishFragment.this.getContext(),QualificationHandleDetailActivity.class);

                    } else if(type == 8){
                        intent.setClass(PublishFragment.this.getContext(),HiringDetailActivity.class);

                    } else if(type == 9){
                        T.showToast(PublishFragment.this.getContext(),"无详情页面");
                        return;

                    } else if(type == 10){
                        T.showToast(PublishFragment.this.getContext(),"无详情页面");
                        return;


                    } else if(type == 11){
                        T.showToast(PublishFragment.this.getContext(),"无详情页面");
                        return;

                    } else if(type == 12){
                        intent.setClass(PublishFragment.this.getContext(),SiteMatchingDetailActivity.class);

                    } else if(type == 13){
                        intent.setClass(PublishFragment.this.getContext(),BlackListDetailActivity.class);
                    }
                }
                intent.putExtra("id", publishTypeItemBean.getList().getId());
                intent.putExtra("status", publishTypeItemBean.getList().getStatus());
                startActivity(intent);
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
