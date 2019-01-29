package com.xcompany.jhonline.module.publish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
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
import com.xcompany.jhonline.network.DataRequestUtil;
import com.xcompany.jhonline.network.FileNetCallBack;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.CityUtil;
import com.xcompany.jhonline.utils.DateUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    List<Category> categoryList = new ArrayList<>();

    List<Model> checkItemList = new ArrayList<>();

    String checkedCategory;


    private Calendar deadLineDate = Calendar.getInstance();

    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;
    List<List<List<City>>> provinceAndCityAndAddList = null;

    City province;
    City city;
    City district;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiting_publish);
        ButterKnife.bind(this);
        initView();
        getMenuData();
        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
        provinceAndCityAndAddList = CityUtil.getDistrictList();
    }
    private void initView(){
        checkboxItemAdapter = new CheckboxItemAdapter(this.getApplicationContext(),checkItemList);
        workCategoryListView.setAdapter(checkboxItemAdapter);
        workCategoryListView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        workCategoryListView.setLoadingMoreEnabled(false);
        workCategoryListView.setPullRefreshEnabled(false);
    }

    public void getMenuData() {
        OkGo.<JHResponse<List<Category>>>post(ReleaseConfig.baseUrl() + "class/classList")
                .tag(this)
                .params("type", 2)
                .params("pid", 0)
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

    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.selectProjectAddressLayout, R.id.selectValidateDateLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                this.finish();
                onBackPressed();
                break;
            case R.id.selectTypeLayout:
                Intent intent = new Intent(PublishRecruitingActivity.this,PublishTypeActivity.class);
                startActivityForResult(intent,5);
                break;
            case R.id.selectProjectAddressLayout:
                showCityOptionsPickerView();
                break;
            case R.id.selectValidateDateLayout:
                showTimePickerView();
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
                    T.showToast(PublishRecruitingActivity.this, "表单信息未填写完整，无法提交");
                    return;
                }
                submit();
                break;
        }
    }

    //时间选择器
    TimePickerView pvTime;

    private void showTimePickerView(){
        if(pvTime == null){
            pvTime = new TimePickerBuilder(PublishRecruitingActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    deadLineDate.setTime(date);
                    validateDateText.setText(DateUtil.dateParseStr(deadLineDate.getTime()));
                    if(pvTime != null && pvTime.isShowing()){
                        pvTime.dismiss();
                    }
                }
            }).build();
        }
        pvTime.setDate(deadLineDate);
        pvTime.show();
    }

    //省市区选择器
    OptionsPickerView cityOptionsPickerView;
    private void showCityOptionsPickerView(){

        if(cityOptionsPickerView == null){
            cityOptionsPickerView = new OptionsPickerBuilder(PublishRecruitingActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    province = provinceList.get(options1);
                    city = provinceAndCityList.get(options1).get(option2);
                    district = provinceAndCityAndAddList.get(options1).get(option2).get(options3);
                    projectAddressText.setText(province.getName() + "  " + city.getName() + "  " + district.getName());

                }
            }).build();
            cityOptionsPickerView.setPicker(provinceList, provinceAndCityList, provinceAndCityAndAddList);
        }
        cityOptionsPickerView.show();

    }




    private ProgressDialog dialog = null;

    private void submit(){
        Map<String,String> params = new HashMap<>();
        params.put("cid[]",checkedCategory);  //名称
        params.put("title",projectNameEdit.getText().toString());  //名称
        params.put("number",JSON.toJSONString(Arrays.asList(personNumEdit.getText().toString())));  //人数
        params.put("deadline",validateDateText.getText().toString());  //有效期
        params.put("port","3");  //端口
        params.put("explain",addExplanationEdit.getText().toString());  //其他说明
        params.put("linkman",linkmanEdit.getText().toString());  // 联系人
        params.put("contacts_pid",province.getId());  //省份
        params.put("contacts_aid",city.getId());  // 城市
        params.put("contacts_cid",district.getId());  //区域
        params.put("telephone",UserService.getInstance().getMobile());  //电话
        params.put("uid",UserService.getInstance().getUid());  //用户ID

        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Worker/workerAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishRecruitingActivity.this, "发布成功");
                        PublishRecruitingActivity.this.finish();

                    }
                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishRecruitingActivity.this, response.getException().getMessage());
                    }
                });
    }
    //表单必填项校验
    private boolean checkFromOK(){
        if(StringUtil.isEmpty(projectNameEdit.getText().toString())  //证件名称
                || StringUtil.isEmpty(checkedCategory) //工种为空
                || StringUtil.isEmpty(validateDateText.getText().toString())  //有效期
                || StringUtil.isEmpty(addExplanationEdit.getText().toString())  //说明
                || StringUtil.isEmpty(linkmanEdit.getText().toString()) // 联系人
                || province == null
                || city == null
                ){
            return false;
        }
        return true;
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
            checkedCategory = JSON.toJSONString(checkedItemList);
        }
        else {
            checkedCategory = null;
        }
    }





}
