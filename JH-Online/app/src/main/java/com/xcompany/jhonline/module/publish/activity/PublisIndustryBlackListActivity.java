package com.xcompany.jhonline.module.publish.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布行业黑名单信息
 */
public class PublisIndustryBlackListActivity extends BaseActivity {


    @BindView(R.id.backHomeLayout)
    LinearLayout backHomeLayout;
    @BindView(R.id.reportTitleText)
    TextView reportTitleText;
    @BindView(R.id.titleLayout)
    RelativeLayout titleLayout;
    @BindView(R.id.levelOneTitleText)
    TextView levelOneTitleText;
    @BindView(R.id.levelTwoTitleText)
    TextView levelTwoTitleText;
    @BindView(R.id.levelThreeTitleText)
    TextView levelThreeTitleText;
    @BindView(R.id.typeLevelLayout)
    LinearLayout typeLevelLayout;
    @BindView(R.id.selectTypeLayout)
    LinearLayout selectTypeLayout;
    @BindView(R.id.alertMainEdit)
    EditText alertMainEdit;
    @BindView(R.id.addressText)
    TextView addressText;
    @BindView(R.id.selectAddressLayout)
    LinearLayout selectAddressLayout;
    @BindView(R.id.complainManEdit)
    EditText complainManEdit;
    @BindView(R.id.storeImageView)
    ImageView storeImageView;
    @BindView(R.id.uploadStoreImageLayout)
    FrameLayout uploadStoreImageLayout;
    @BindView(R.id.linkmanEdit)
    EditText linkmanEdit;
    @BindView(R.id.explainEdit)
    EditText explainEdit;
    @BindView(R.id.publishSubmitText)
    TextView publishSubmitText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_black_list_publish);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.selectAddressLayout, R.id.uploadStoreImageLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                onBackPressed();
                this.finish();
                break;
            case R.id.selectTypeLayout:
                break;
            case R.id.selectAddressLayout:
                break;
            case R.id.uploadStoreImageLayout:
                break;
            case R.id.publishSubmitText:
                break;
        }
    }
}
