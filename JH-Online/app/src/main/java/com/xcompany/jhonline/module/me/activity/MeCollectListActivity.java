package com.xcompany.jhonline.module.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.XListViewActivity;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.me.MeCollectBean;
import com.xcompany.jhonline.module.home.subcontract.activity.QualityTeamDetailActivity;
import com.xcompany.jhonline.module.home.subcontract.adapter.QualityTeamAdapter;
import com.xcompany.jhonline.module.me.adapter.MeCollectAdapter;

import java.util.ArrayList;
import java.util.List;

public class MeCollectListActivity extends XListViewActivity {


    private MeCollectAdapter meCollectAdapter;


    @Override
    public void onAppCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_me_collect_list);
        xRecyclerView = this.findViewById(R.id.collectListView);
    }


    @Override
    public void getDataItems(int start, int limit, Callback callback) {
        List<MeCollectBean> data = new ArrayList<>();
        for(int i = 0; i<20 ;i++){
            data.add(new MeCollectBean("分包单位"));
        }
        callback.setDataItems(data);
    }

    @Override
    public void initAdapter() {
        meCollectAdapter = new MeCollectAdapter(MeCollectListActivity.this, SourceDateList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MeCollectListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setAdapter(meCollectAdapter);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(this);
        meCollectAdapter.setOnItemClickListener(new MeCollectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Model bean, RecyclerView.ViewHolder holder) {

            }
        });


    }


}
