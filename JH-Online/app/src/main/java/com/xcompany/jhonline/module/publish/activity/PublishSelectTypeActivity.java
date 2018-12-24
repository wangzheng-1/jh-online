package com.xcompany.jhonline.module.publish.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.module.publish.adapter.PublishTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishSelectTypeActivity extends BaseActivity {

    @BindView(R.id.backHomeLayout)
    LinearLayout backHomeLayout;
    @BindView(R.id.titleLayout)
    RelativeLayout titleLayout;
    @BindView(R.id.typeListView)
    ListView typeListView;

    PublishTypeAdapter publishTypeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_select_type);
        ButterKnife.bind(this);
        initAdapter();

    }
    private void initAdapter(){
        List<Model> models = getData();
        publishTypeAdapter = new PublishTypeAdapter(this,models);
        typeListView.setAdapter(publishTypeAdapter);
    }

    private List<Model> getData(){
        List<Model> models = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            models.add(new Model());
        }
        return models;
    }


    @OnClick({R.id.backHomeLayout, R.id.reportTitleText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                onBackPressed();
                break;
        }
    }



}
