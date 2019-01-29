package com.xcompany.jhonline.module.publish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.CityUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xcompany.jhonline.module.publish.activity.PublishTypeActivity.SELECTED_TYPE_CID;
import static com.xcompany.jhonline.module.publish.activity.PublishTypeActivity.SELECTED_TYPE_NAME;

/**
 * 发布专业技能库信息
 */
public class PublishMajorLibraryActivity extends BaseActivity {


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
    @BindView(R.id.serviceProvinceText)
    TextView serviceProvinceText;
    @BindView(R.id.selectServiceProvinceLayout)
    LinearLayout selectServiceProvinceLayout;
    @BindView(R.id.linkmanEdit)
    EditText linkmanEdit;
    @BindView(R.id.mobileEdit)
    EditText mobileEdit;
    @BindView(R.id.addExplanationEdit)
    EditText addExplanationEdit;
    @BindView(R.id.publishSubmitText)
    TextView publishSubmitText;

    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;
    List<List<List<City>>> provinceAndCityAndAddList = null;

    City province;
    City city;
    City district;

    private String selectTypeCID;
    private String selectTypeName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_library_publish);
        ButterKnife.bind(this);

        selectTypeName = getIntent().getStringExtra(SELECTED_TYPE_NAME);
        selectTypeCID = getIntent().getStringExtra(SELECTED_TYPE_CID);
        levelTwoTitleText.setText(selectTypeName);

        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
        provinceAndCityAndAddList = CityUtil.getDistrictList();

    }


    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.selectServiceProvinceLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                onBackPressed();
                this.finish();
                break;
            case R.id.selectTypeLayout:
                Intent intent = new Intent(PublishMajorLibraryActivity.this,PublishTypeActivity.class);
                startActivityForResult(intent,5);
                break;
            case R.id.selectServiceProvinceLayout:
                showCityOptionsPickerView();
                break;
            case R.id.publishSubmitText:
                submit();
                break;
        }
    }


    //省市区选择器
    OptionsPickerView cityOptionsPickerView;
    private void showCityOptionsPickerView(){

        if(cityOptionsPickerView == null){
            cityOptionsPickerView = new OptionsPickerBuilder(PublishMajorLibraryActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    province = provinceList.get(options1);
                    city = provinceAndCityList.get(options1).get(option2);
                    district = provinceAndCityAndAddList.get(options1).get(option2).get(options3);
                    serviceProvinceText.setText(province.getName() + "  " + city.getName() + "  " + district.getName());

                }
            }).build();
            cityOptionsPickerView.setPicker(provinceList, provinceAndCityList, provinceAndCityAndAddList);
        }
        cityOptionsPickerView.show();

    }


    private ProgressDialog dialog = null;

    private void submit(){
        if(!checkFromOK()){
            T.showToast(PublishMajorLibraryActivity.this, "表单信息未填写完整，无法提交");
            return;
        }
        dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Map<String,String> params = new HashMap<>();
        params.put("cid",selectTypeCID);  //名称
        params.put("name",titleNameEdit.getText().toString());  //名称
        params.put("explain",addExplanationEdit.getText().toString());  //其他说明
        params.put("linkman",linkmanEdit.getText().toString());  // 联系人
        params.put("telephone",mobileEdit.getText().toString());  //电话
        params.put("contacts_pid",province.getId());  //省份
        params.put("contacts_aid",city.getId());  // 城市
        params.put("contacts_cid",district.getId());  //区域
        params.put("uid",UserService.getInstance().getUid());  //用户ID

        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "major/majorAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishMajorLibraryActivity.this, "发布成功");
                        PublishMajorLibraryActivity.this.finish();

                    }
                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishMajorLibraryActivity.this, response.getException().getMessage());
                    }
                });
    }
    //表单必填项校验
    private boolean checkFromOK(){
        if(StringUtil.isEmpty(titleNameEdit.getText().toString())  //证件名称
                || city == null
                || province == null
                || StringUtil.isEmpty(addExplanationEdit.getText().toString()) // 说明
                || StringUtil.isEmpty(linkmanEdit.getText().toString()) // 联系人
                || StringUtil.isEmpty(mobileEdit.getText().toString()) // 电话
                ){
            return false;
        }
        return true;
    }
}
