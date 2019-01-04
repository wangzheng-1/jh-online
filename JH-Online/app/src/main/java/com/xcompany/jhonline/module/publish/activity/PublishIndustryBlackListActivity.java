package com.xcompany.jhonline.module.publish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.model.report.MediaBaseBean;
import com.xcompany.jhonline.model.report.MediaBaseBeanSerial;
import com.xcompany.jhonline.module.report.activity.PhotoSelectActivity;
import com.xcompany.jhonline.network.DataRequestUtil;
import com.xcompany.jhonline.network.FileNetCallBack;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.CityUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.EXTRA_SELECTED_PHOTOS;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.IMAGE_NUM;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.SELECT_MEDIA_TYPE;

/**
 * 发布行业黑名单信息
 */
public class PublishIndustryBlackListActivity extends BaseActivity {

    /**
     * 选择照片视频
     */
    private static final int RC_CHOOSE_PHOTO = 1;


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

    private String storeImagePath;
    private String storeImageUrl;

    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;
    List<List<List<City>>> provinceAndCityAndAddList = null;

    //地址  省
    City province;
    //地址  市
    City city;
    //地址  区
    City district;

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
                showCityOptionsPickerView();
                break;
            case R.id.uploadStoreImageLayout:
                selectStoreImage();
                break;
            case R.id.publishSubmitText:
                dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                if(!checkFromOK()){
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    T.showToast(PublishIndustryBlackListActivity.this, "表单信息未填写完整，无法提交");
                    return;
                }
                uploadImage();
                break;
        }
    }


    //省市区选择器
    OptionsPickerView cityOptionsPickerView;
    private void showCityOptionsPickerView(){

        if(cityOptionsPickerView == null){
            cityOptionsPickerView = new OptionsPickerBuilder(PublishIndustryBlackListActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    province = provinceList.get(options1);
                    city = provinceAndCityList.get(options1).get(option2);
                    district = provinceAndCityAndAddList.get(options1).get(option2).get(options3);
                    addressText.setText(province.getName() + "  " + city.getName() + "  " + district.getName());

                }
            }).build();
            if(provinceList == null ){
                provinceList = CityUtil.getProvinceList();
            }
            if(provinceAndCityList == null){
                provinceAndCityList = CityUtil.getCityList();
            }
            if(provinceAndCityAndAddList == null){
                provinceAndCityAndAddList = CityUtil.getDistrictList();
            }
            cityOptionsPickerView.setPicker(provinceList, provinceAndCityList, provinceAndCityAndAddList);

        }
        cityOptionsPickerView.show();
    }


    private void selectStoreImage(){
        Intent intent = new Intent(PublishIndustryBlackListActivity.this, PhotoSelectActivity.class);
        intent.putExtra(SELECT_MEDIA_TYPE,0);
        intent.putExtra(IMAGE_NUM,1);
        intent.putExtra(SELECT_MEDIA_TYPE,1);
        startActivityForResult(intent,RC_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            MediaBaseBeanSerial result = (MediaBaseBeanSerial)data.getSerializableExtra(EXTRA_SELECTED_PHOTOS);
            List<MediaBaseBean> mediaBaseBeanList = result.getMediaBaseBeanList();
            //选择的视频
            if(mediaBaseBeanList != null && mediaBaseBeanList.size() > 0){
                storeImagePath = mediaBaseBeanList.get(0).getUrl();
                storeImageView.setImageURI(Uri.fromFile(new File(storeImagePath)));
            }

        }
    }

    private ProgressDialog dialog = null;

    private void submit(){
        Map<String,String> params = new HashMap<>();
        params.put("name",alertMainEdit.getText().toString());  //名称
        if(!StringUtil.isEmpty(storeImageUrl)){
            params.put("register", storeImageUrl);  //队伍图片
        }
        params.put("area",getArea());  //省份
        params.put("explain",explainEdit.getText().toString());  //其他说明
        params.put("linkman",linkmanEdit.getText().toString());  // 联系人
        params.put("telephone",UserService.getInstance().getMobile());  //电话
        params.put("uid",UserService.getInstance().getUid());  //用户ID
        params.put("port","3");  // 联系人


        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Black/blackAdd")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishIndustryBlackListActivity.this, "发布成功");
                        PublishIndustryBlackListActivity.this.finish();

                    }
                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishIndustryBlackListActivity.this, response.getException().getMessage());
                    }
                });
    }
    //表单必填项校验
    private boolean checkFromOK(){
        if(StringUtil.isEmpty(alertMainEdit.getText().toString())  //警惕主体
                || StringUtil.isEmpty(explainEdit.getText().toString()) // 说明
                || StringUtil.isEmpty(linkmanEdit.getText().toString()) // 联系人
                || province == null
                || city == null
                ){
            return false;
        }
        return true;
    }
    /**
     * 上传图片
     */
    private void uploadImage(){

        File file = new File(storeImagePath);
        Map<String,Object> params = new HashMap<>();
        params.put("type","Licence");
        params.put("file",file);

        DataRequestUtil.<JHResponse<String>>upLoadFile("Public/upload", params, new FileNetCallBack<JHResponse<String>>() {
            @Override
            public void done(JHResponse<String> result, Exception e) {
                if(e == null){
                    storeImageUrl = result.getMsg();
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
                else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    T.showToast(PublishIndustryBlackListActivity.this, e.getMessage());
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int msgWhat = msg.what;
            switch (msgWhat){
                case 1:
                    submit();
                    break;
                default:
                    break;
            }
        }
    };

    private String getArea(){
        List<String> areaList = new ArrayList<>();
        areaList.add(province.getName());
        areaList.add(city.getName());
        areaList.add(district.getName());
        String temp = JSON.toJSONString(areaList);
        return temp.substring(2,temp.length()-2);
    }
}