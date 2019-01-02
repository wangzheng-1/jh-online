package com.xcompany.jhonline.module.publish.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布证照挂靠信息
 */
public class PublishCertificateAnchoredActivity extends BaseActivity {

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
    @BindView(R.id.anchoredTypeEdit)
    EditText anchoredTypeEdit;
    @BindView(R.id.yesRadio)
    RadioButton yesRadio;
    @BindView(R.id.noRadio)
    RadioButton noRadio;
    @BindView(R.id.radioGroup_gender)
    RadioGroup radioGroupGender;
    @BindView(R.id.certificateNameEdit)
    EditText certificateNameEdit;
    @BindView(R.id.anchoredDateText)
    TextView anchoredDateText;
    @BindView(R.id.selectAnchoredDateLayout)
    LinearLayout selectAnchoredDateLayout;
    @BindView(R.id.anchoredPriceEdit)
    EditText anchoredPriceEdit;
    @BindView(R.id.addressText)
    TextView addressText;
    @BindView(R.id.selectAddressLayout)
    LinearLayout selectAddressLayout;
    @BindView(R.id.linkmanEdit)
    EditText linkmanEdit;
    @BindView(R.id.addExplanationEdit)
    EditText addExplanationEdit;
    @BindView(R.id.publishSubmitText)
    TextView publishSubmitText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_anchored_publish);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.selectAnchoredDateLayout, R.id.selectAddressLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                this.finish();
                onBackPressed();
                break;
            case R.id.selectTypeLayout:
                break;
            case R.id.selectAnchoredDateLayout:
                break;
            case R.id.selectAddressLayout:
                break;
            case R.id.publishSubmitText:
                break;
        }
    }
}
