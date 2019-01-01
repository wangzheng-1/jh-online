package com.xcompany.jhonline.module.publish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
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

    PublishTypeAdapter publishTypeAdapter;
    @BindView(R.id.divideConstructionLayout)
    LinearLayout divideConstructionLayout;
    @BindView(R.id.deviceLeaseLayout)
    LinearLayout deviceLeaseLayout;
    @BindView(R.id.buildingMaterialsTradeLayout)
    LinearLayout buildingMaterialsTradeLayout;
    @BindView(R.id.certificateServiceLayout)
    LinearLayout certificateServiceLayout;
    @BindView(R.id.laborRecruitLayout)
    LinearLayout laborRecruitLayout;
    @BindView(R.id.skillRecruitLayout)
    LinearLayout skillRecruitLayout;
    @BindView(R.id.constructionMatchLayout)
    LinearLayout constructionMatchLayout;
    @BindView(R.id.majorSkillLibraryLayout)
    LinearLayout majorSkillLibraryLayout;
    @BindView(R.id.industryBlackListLayout)
    LinearLayout industryBlackListLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_select_type);
        ButterKnife.bind(this);
//        initAdapter();

    }
//    private void initAdapter(){
//        List<Model> models = getData();
//        publishTypeAdapter = new PublishTypeAdapter(this,models);
//        typeListView.setAdapter(publishTypeAdapter);
//    }

    private List<Model> getData() {
        List<Model> models = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            models.add(new Model());
        }
        return models;
    }


    @OnClick({R.id.backHomeLayout,R.id.divideConstructionLayout, R.id.deviceLeaseLayout, R.id.buildingMaterialsTradeLayout, R.id.certificateServiceLayout, R.id.laborRecruitLayout, R.id.skillRecruitLayout, R.id.constructionMatchLayout, R.id.majorSkillLibraryLayout, R.id.industryBlackListLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                onBackPressed();
                break;
            case R.id.divideConstructionLayout:
                startActivity(new Intent(PublishSelectTypeActivity.this,PublishPurchasingActivity.class));
                break;
            case R.id.deviceLeaseLayout:
                break;
            case R.id.buildingMaterialsTradeLayout:
                break;
            case R.id.certificateServiceLayout:
                break;
            case R.id.laborRecruitLayout:
                break;
            case R.id.skillRecruitLayout:
                break;
            case R.id.constructionMatchLayout:
                break;
            case R.id.majorSkillLibraryLayout:
                break;
            case R.id.industryBlackListLayout:
                break;
        }
    }
}
