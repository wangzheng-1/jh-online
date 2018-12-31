package com.xcompany.jhonline.module.publish.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.EditText;
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
 * 发布技管正在招聘信息
 */
public class PublishSkillRecruitingActivity extends BaseActivity {


    CheckboxItemAdapter checkboxItemAdapter;
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
    @BindView(R.id.titleNameEdit)
    EditText titleNameEdit;
    @BindView(R.id.companyRadio)
    RadioButton companyRadio;
    @BindView(R.id.companyNameEdit)
    EditText companyNameEdit;
    @BindView(R.id.privateRadio)
    RadioButton privateRadio;
    @BindView(R.id.privateNameEdit)
    EditText privateNameEdit;
    @BindView(R.id.radioGroup_gender)
    RadioGroup radioGroupGender;
    @BindView(R.id.recruitDepartListView)
    XRecyclerView recruitDepartListView;
    @BindView(R.id.otherPostEdit)
    EditText otherPostEdit;
    @BindView(R.id.addressText)
    TextView addressText;
    @BindView(R.id.selectAddressLayout)
    LinearLayout selectAddressLayout;
    @BindView(R.id.projectAreaEdit)
    EditText projectAreaEdit;
    @BindView(R.id.linkmanEdit)
    EditText linkmanEdit;
    @BindView(R.id.projectServiceExplanationEdit)
    EditText projectServiceExplanationEdit;
    @BindView(R.id.addExplanationEdit)
    EditText addExplanationEdit;
    @BindView(R.id.publishSubmitText)
    TextView publishSubmitText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_recruiting_publish);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<Model> categoryList = getWorkCategoryData();
        checkboxItemAdapter = new CheckboxItemAdapter(this.getApplicationContext(), categoryList);
        recruitDepartListView.setAdapter(checkboxItemAdapter);
        recruitDepartListView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recruitDepartListView.setLoadingMoreEnabled(false);
        recruitDepartListView.setPullRefreshEnabled(false);
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


    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.companyRadio, R.id.privateRadio, R.id.selectAddressLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                this.finish();
                onBackPressed();
                break;
            case R.id.selectTypeLayout:
                break;
            case R.id.companyRadio:
                break;
            case R.id.privateRadio:
                break;
            case R.id.selectAddressLayout:
                break;
            case R.id.publishSubmitText:
                break;
        }
    }
}
