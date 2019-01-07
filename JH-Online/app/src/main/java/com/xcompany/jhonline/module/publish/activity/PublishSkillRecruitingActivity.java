package com.xcompany.jhonline.module.publish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.base.Category;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.model.publish.CheckboxItemBean;
import com.xcompany.jhonline.module.publish.adapter.CheckboxItemAdapter;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.CityUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;

    //地址  省
    City province;
    //地址  市
    City city;

    List<Category> categoryList = new ArrayList<>();

    List<Model> checkItemList = new ArrayList<>();

    String checkedPositions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_recruiting_publish);
        ButterKnife.bind(this);
        initView();
        getMenuData();
        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
    }

    private void initView() {
        checkboxItemAdapter = new CheckboxItemAdapter(this.getApplicationContext(),checkItemList);
        recruitDepartListView.setAdapter(checkboxItemAdapter);
        recruitDepartListView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recruitDepartListView.setLoadingMoreEnabled(false);
        recruitDepartListView.setPullRefreshEnabled(false);
    }

    public void getMenuData() {
        OkGo.<JHResponse<List<Category>>>post(ReleaseConfig.baseUrl() + "class/classList")
                .tag(this)
                .params("type", 3)
                .params("pid", 40)
                .execute(new JHCallback<JHResponse<List<Category>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Category>>> response) {
                        categoryList = response.body().getMsg();
                        for(Category category : categoryList){
                            CheckboxItemBean model = new CheckboxItemBean();
                            model.setId(category.getId());
                            model.setName(category.getName());
                            model.setCheck(false);
                            checkItemList.add(model);
                        }
                        checkboxItemAdapter.notifyDataSetChanged();
                    }
                });
    }

    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.companyRadio, R.id.privateRadio, R.id.selectAddressLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                this.finish();
                onBackPressed();
                break;
            case R.id.selectTypeLayout:
                Intent intent = new Intent(PublishSkillRecruitingActivity.this,PublishTypeActivity.class);
                startActivityForResult(intent,5);
                break;
            case R.id.companyRadio:
                if(companyRadio.isChecked()){
                    companyNameEdit.setVisibility(View.VISIBLE);
                }
                else{
                    companyNameEdit.setVisibility(View.GONE);
                }
                break;
            case R.id.privateRadio:
                if(companyRadio.isChecked()){
                    companyNameEdit.setVisibility(View.VISIBLE);
                }
                else{
                    companyNameEdit.setVisibility(View.GONE);
                }
                break;
            case R.id.selectAddressLayout:
                showCityOptionsPickerView();
                break;
            case R.id.publishSubmitText:
                dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                getCheckedCategory();
                if(!checkFromOK()){
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    T.showToast(PublishSkillRecruitingActivity.this, "表单信息未填写完整，无法提交");
                    return;
                }
                submit();
                break;
        }
    }
    //省市区选择器
    OptionsPickerView cityOptionsPickerView;
    private void showCityOptionsPickerView(){

        if(cityOptionsPickerView == null){
            cityOptionsPickerView = new OptionsPickerBuilder(PublishSkillRecruitingActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    province = provinceList.get(options1);
                    city = provinceAndCityList.get(options1).get(option2);
                    addressText.setText(province.getName() + "  " + city.getName() );

                }
            }).build();
            if(provinceList == null ){
                provinceList = CityUtil.getProvinceList();
            }
            if(provinceAndCityList == null){
                provinceAndCityList = CityUtil.getCityList();
            }
            cityOptionsPickerView.setPicker(provinceList, provinceAndCityList);

        }
        cityOptionsPickerView.show();

    }

    private void getCheckedCategory(){
        List<String> checkedItemList = new ArrayList<>();
        for(Model model :checkItemList){
            CheckboxItemBean checkboxItemBean = (CheckboxItemBean)model;
            if(checkboxItemBean.isCheck()){
                checkedItemList.add(checkboxItemBean.getId());
            }
        }
        if(checkedItemList.size() > 0){
            checkedPositions = StringUtil.join(checkedItemList,",");
        }
        else{
            checkedPositions = null;
        }
    }


    private ProgressDialog dialog = null;

    private void submit(){
        Map<String,String> params = new HashMap<>();
        params.put("cid",checkedPositions);  //名称
        params.put("title",titleNameEdit.getText().toString());  //名称
        if(!StringUtil.isEmpty(companyNameEdit.getText().toString())){
            params.put("name",companyNameEdit.getText().toString());  //人数
        }
        if(!StringUtil.isEmpty(otherPostEdit.getText().toString())){
            params.put("class",otherPostEdit.getText().toString());  //其他
        }
        params.put("contacts_pid",province.getId());  //省份
        params.put("contacts_aid",city.getId());  // 城市
        params.put("summary",projectServiceExplanationEdit.getText().toString());  //概述
        params.put("explain",addExplanationEdit.getText().toString());  //其他说明
        params.put("linkman",linkmanEdit.getText().toString());  // 联系人
        params.put("telephone",UserService.getInstance().getMobile());  //电话
        params.put("uid",UserService.getInstance().getUid());  //用户ID

        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Recruit/recruitAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishSkillRecruitingActivity.this, "发布成功");
                        PublishSkillRecruitingActivity.this.finish();

                    }
                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishSkillRecruitingActivity.this, response.getException().getMessage());
                    }
                });
    }
    //表单必填项校验
    private boolean checkFromOK(){
        if(StringUtil.isEmpty(titleNameEdit.getText().toString())  //证件名称
                || StringUtil.isEmpty(checkedPositions) //工种为空
                || StringUtil.isEmpty(projectServiceExplanationEdit.getText().toString())  //项目概述
                || StringUtil.isEmpty(addExplanationEdit.getText().toString())  //说明
                || StringUtil.isEmpty(linkmanEdit.getText().toString()) // 联系人
                || province == null
                || city == null
                ){
            return false;
        }
        return true;
    }
}
