package com.xcompany.jhonline.module.publish.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
 * 发布劳务招聘 正在招工
 */
public class PublishRecruitingActivity extends BaseActivity {

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
    @BindView(R.id.projectNameEdit)
    EditText projectNameEdit;
    @BindView(R.id.projectAddressText)
    TextView projectAddressText;
    @BindView(R.id.selectProjectAddressLayout)
    LinearLayout selectProjectAddressLayout;
    @BindView(R.id.workCategoryListView)
    XRecyclerView workCategoryListView;
    @BindView(R.id.personNumEdit)
    EditText personNumEdit;
    @BindView(R.id.validateDateText)
    TextView validateDateText;
    @BindView(R.id.selectValidateDateLayout)
    LinearLayout selectValidateDateLayout;
    @BindView(R.id.linkmanEdit)
    EditText linkmanEdit;
    @BindView(R.id.addExplanationEdit)
    EditText addExplanationEdit;
    @BindView(R.id.publishSubmitText)
    TextView publishSubmitText;

    CheckboxItemAdapter checkboxItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiting_publish);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        List<Model> categoryList = getWorkCategoryData();
        checkboxItemAdapter = new CheckboxItemAdapter(this.getApplicationContext(),categoryList);
        workCategoryListView.setAdapter(checkboxItemAdapter);
        workCategoryListView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        workCategoryListView.setLoadingMoreEnabled(false);
        workCategoryListView.setPullRefreshEnabled(false);
    }

    private List<Model> getWorkCategoryData(){
        List<Model> categoryList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            CheckboxItemBean checkboxItemBean = new CheckboxItemBean();
            checkboxItemBean.setId(i);
            checkboxItemBean.setName("工种" + i);
            categoryList.add(checkboxItemBean);
        }
        return categoryList;
    }

    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.selectProjectAddressLayout, R.id.selectValidateDateLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                this.finish();
                onBackPressed();
                break;
            case R.id.selectTypeLayout:
                break;
            case R.id.selectProjectAddressLayout:
                break;
            case R.id.selectValidateDateLayout:
                break;
            case R.id.publishSubmitText:
                break;
        }
    }
}
