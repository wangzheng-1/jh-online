package com.xcompany.jhonline.module.publish.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布正在采购
 */

public class PublishPurchasingActivity extends BaseActivity {

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
    @BindView(R.id.purchaseNameEdit)
    EditText purchaseNameEdit;
    @BindView(R.id.deliveryAddressText)
    TextView deliveryAddressText;
    @BindView(R.id.selectDeliveryAddressLayout)
    LinearLayout selectDeliveryAddressLayout;
    @BindView(R.id.linkmanEdit)
    EditText linkmanEdit;
    @BindView(R.id.addExplanationEdit)
    EditText addExplanationEdit;
    @BindView(R.id.publishSubmitText)
    TextView publishSubmitText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing_publish);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.selectDeliveryAddressLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                onBackPressed();
                break;
            case R.id.selectTypeLayout:
                break;
            case R.id.selectDeliveryAddressLayout:
                break;
            case R.id.publishSubmitText:
                break;
        }
    }
}
