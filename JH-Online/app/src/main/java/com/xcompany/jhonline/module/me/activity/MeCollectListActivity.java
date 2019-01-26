package com.xcompany.jhonline.module.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.XListViewActivity;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.publish.PublishTypeItemBean;
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
import com.xcompany.jhonline.module.publish.adapter.PublishAdapter;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

import java.util.List;

public class MeCollectListActivity extends XListViewActivity {

    private PublishAdapter publishAdapter;


    @Override
    public void onAppCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_me_collect_list);
        xRecyclerView = this.findViewById(R.id.collectListView);
    }

    @Override
    public void getDataItems(int page, Callback callback) {

        OkGo.<JHResponse<List<PublishTypeItemBean>>>post(ReleaseConfig.baseUrl() + "User/colllist?p=" + page)
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
                        T.showToast(MeCollectListActivity.this, response.getException().getMessage());
                        callback.setDataItems(null);
                    }
                });
    }

    @Override
    public void initAdapter() {
        publishAdapter = new PublishAdapter(MeCollectListActivity.this, SourceDateList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MeCollectListActivity.this);
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
                        intent.setClass(MeCollectListActivity.this,QualityTeamDetailActivity.class);
                    }
                    else if(type == 1){
                        intent.setClass(MeCollectListActivity.this,TenderDetailActivity.class);
                    }
                    else if(type == 2){
                        intent.setClass(MeCollectListActivity.this,RentingDetailActivity.class);

                    } else if(type == 3){
                        intent.setClass(MeCollectListActivity.this,RentSeekingDetailActivity.class);

                    } else if(type == 4){
                        intent.setClass(MeCollectListActivity.this,QualitySupplierDetailActivity.class);

                    } else if(type == 5){
                        intent.setClass(MeCollectListActivity.this,PurchaseDetailActivity.class);

                    } else if(type == 6){
                        intent.setClass(MeCollectListActivity.this,RegistrationDetailActivity.class);

                    } else if(type == 7){
                        intent.setClass(MeCollectListActivity.this,QualificationHandleDetailActivity.class);

                    } else if(type == 8){
                        intent.setClass(MeCollectListActivity.this,HiringDetailActivity.class);

                    } else if(type == 9){
                        T.showToast(MeCollectListActivity.this,"无详情页面");
                        return;

                    } else if(type == 10){
                        T.showToast(MeCollectListActivity.this,"无详情页面");
                        return;


                    } else if(type == 11){
                        T.showToast(MeCollectListActivity.this,"无详情页面");
                        return;

                    } else if(type == 12){
                        intent.setClass(MeCollectListActivity.this,SiteMatchingDetailActivity.class);

                    } else if(type == 13){
                        intent.setClass(MeCollectListActivity.this,BlackListDetailActivity.class);
                    }
                }
                intent.putExtra("id", publishTypeItemBean.getList().getId());
                intent.putExtra("status", publishTypeItemBean.getList().getStatus()+"");
                startActivity(intent);
            }
        });
    }


}
