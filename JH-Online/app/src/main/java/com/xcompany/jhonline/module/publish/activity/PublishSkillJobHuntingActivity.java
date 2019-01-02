package com.xcompany.jhonline.module.publish.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.publish.CheckboxItemBean;
import com.xcompany.jhonline.module.publish.adapter.CheckboxItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布技管正在求职
 */
public class PublishSkillJobHuntingActivity extends BaseActivity {


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
    @BindView(R.id.nameEdit)
    EditText nameEdit;
    @BindView(R.id.headImageView)
    ImageView headImageView;
    @BindView(R.id.headImageLayout)
    FrameLayout headImageLayout;
    @BindView(R.id.levelJuniorMiddleRadio)
    RadioButton levelJuniorMiddleRadio;
    @BindView(R.id.levelHighMiddleRadio)
    RadioButton levelHighMiddleRadio;
    @BindView(R.id.levelJuniorCollegeRadio)
    RadioButton levelJuniorCollegeRadio;
    @BindView(R.id.levelColleagueRadio)
    RadioButton levelColleagueRadio;
    @BindView(R.id.levelMasterRadio)
    RadioButton levelMasterRadio;
    @BindView(R.id.levelDoctorRadio)
    RadioButton levelDoctorRadio;
    @BindView(R.id.levelHighDoctorRadio)
    RadioButton levelHighDoctorRadio;
    @BindView(R.id.radioGroup_gender)
    RadioGroup radioGroupGender;
    @BindView(R.id.employDurationEdit)
    EditText employDurationEdit;
    @BindView(R.id.positionListView)
    XRecyclerView positionListView;
    @BindView(R.id.otherPositionEdit)
    EditText otherPositionEdit;
    @BindView(R.id.intentAddressText)
    TextView intentAddressText;
    @BindView(R.id.selectAddressLayout)
    LinearLayout selectAddressLayout;
    @BindView(R.id.paymentEdit)
    EditText paymentEdit;
    @BindView(R.id.linkmanEdit)
    EditText linkmanEdit;
    @BindView(R.id.servicedProjectText)
    TextView servicedProjectText;
    @BindView(R.id.selectServicedProjectLayout)
    LinearLayout selectServicedProjectLayout;
    @BindView(R.id.addExplanationEdit)
    EditText addExplanationEdit;
    @BindView(R.id.publishSubmitText)
    TextView publishSubmitText;

    CheckboxItemAdapter checkboxItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_job_hunting_publish);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<Model> categoryList = getWorkCategoryData();
        checkboxItemAdapter = new CheckboxItemAdapter(this.getApplicationContext(), categoryList);
        positionListView.setAdapter(checkboxItemAdapter);
        positionListView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        positionListView.setLoadingMoreEnabled(false);
        positionListView.setPullRefreshEnabled(false);
    }

    private List<Model> getWorkCategoryData() {
        List<Model> categoryList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CheckboxItemBean checkboxItemBean = new CheckboxItemBean();
            checkboxItemBean.setId(i);
            checkboxItemBean.setName("工种" + i);
            categoryList.add(checkboxItemBean);
        }
        return categoryList;
    }


    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.headImageLayout, R.id.selectAddressLayout, R.id.selectServicedProjectLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                break;
            case R.id.selectTypeLayout:
                break;
            case R.id.headImageLayout:
                break;
            case R.id.selectAddressLayout:
                break;
            case R.id.selectServicedProjectLayout:
                break;
            case R.id.publishSubmitText:
                break;
        }
    }
}
