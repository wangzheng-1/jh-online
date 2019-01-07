package com.xcompany.jhonline.module.publish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.CityUtil;
import com.xcompany.jhonline.utils.DateUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        setContentView(R.layout.activity_certificate_anchored_publish);
        ButterKnife.bind(this);
        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
        provinceAndCityAndAddList = CityUtil.getDistrictList();

    }

    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.selectAnchoredDateLayout, R.id.selectAddressLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                this.finish();
                onBackPressed();
                break;
            case R.id.selectTypeLayout:
                Intent intent = new Intent(PublishCertificateAnchoredActivity.this,PublishTypeActivity.class);
                startActivityForResult(intent,5);
                break;
            case R.id.selectAnchoredDateLayout:
                showTimePickerView();
                break;
            case R.id.selectAddressLayout:
                showCityOptionsPickerView();
                break;
            case R.id.publishSubmitText:
                submit();
                break;
        }
    }
    //时间选择器
    TimePickerView pvTime;

    private void showTimePickerView(){
        if(pvTime == null){
            pvTime = new TimePickerBuilder(PublishCertificateAnchoredActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    deadLineDate.setTime(date);
                    anchoredDateText.setText(DateUtil.dateParseStr(deadLineDate.getTime()));
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
            cityOptionsPickerView = new OptionsPickerBuilder(PublishCertificateAnchoredActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    province = provinceList.get(options1);
                    city = provinceAndCityList.get(options1).get(option2);
                    district = provinceAndCityAndAddList.get(options1).get(option2).get(options3);
                    addressText.setText(province.getName() + "  " + city.getName() + "  " + district.getName());

                }
            }).build();
            cityOptionsPickerView.setPicker(provinceList, provinceAndCityList, provinceAndCityAndAddList);
        }
        cityOptionsPickerView.show();

    }


    private ProgressDialog dialog = null;

    private void submit(){
        if(!checkFromOK()){
            T.showToast(PublishCertificateAnchoredActivity.this, "表单信息未填写完整，无法提交");
            return;
        }
        dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Map<String,String> params = new HashMap<>();
        params.put("cid","55");  //名称
        params.put("name",certificateNameEdit.getText().toString());  //名称
        params.put("deadline",anchoredDateText.getText().toString());  //挂靠时长
        params.put("price",anchoredPriceEdit.getText().toString());  //价格
        params.put("explain",addExplanationEdit.getText().toString());  //其他说明
        params.put("linkman",linkmanEdit.getText().toString());  // 联系人
        params.put("telephone",UserService.getInstance().getMobile());  //电话
        params.put("contacts_pid",province.getId());  //省份
        params.put("contacts_aid",city.getId());  // 城市
        params.put("contacts_cid",district.getId());  //区域
        params.put("uid",UserService.getInstance().getUid());  //用户ID


        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Licence/PurchaseAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishCertificateAnchoredActivity.this, "发布成功");
                        PublishCertificateAnchoredActivity.this.finish();

                    }
                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishCertificateAnchoredActivity.this, response.getException().getMessage());
                    }
                });
    }
    //表单必填项校验
    private boolean checkFromOK(){
        if(StringUtil.isEmpty(certificateNameEdit.getText().toString())  //证件名称
                || city == null
                || province == null
                || StringUtil.isEmpty(anchoredDateText.getText().toString())  //挂靠时长
                || StringUtil.isEmpty(anchoredPriceEdit.getText().toString())
                || StringUtil.isEmpty(addExplanationEdit.getText().toString()) // 说明
                || StringUtil.isEmpty(linkmanEdit.getText().toString()) // 联系人
                ){
            return false;
        }
        return true;
    }

}
