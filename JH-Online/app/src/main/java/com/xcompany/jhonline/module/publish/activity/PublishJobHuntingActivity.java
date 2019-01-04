package com.xcompany.jhonline.module.publish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.base.Category;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.model.publish.CheckboxItemBean;
import com.xcompany.jhonline.model.report.MediaBaseBean;
import com.xcompany.jhonline.model.report.MediaBaseBeanSerial;
import com.xcompany.jhonline.module.publish.adapter.CheckboxItemAdapter;
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
 * 发布劳务招聘 正在找活
 */
public class PublishJobHuntingActivity extends BaseActivity {
    /**
     * 选择照片视频
     */
    private static final int RC_CHOOSE_PHOTO = 1;

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
    @BindView(R.id.workCategoryListView)
    XRecyclerView workCategoryListView;
    @BindView(R.id.personNumEdit)
    EditText personNumEdit;
    @BindView(R.id.intentAddressText)
    TextView intentAddressText;
    @BindView(R.id.selectIntentAddressLayout)
    LinearLayout selectIntentAddressLayout;
    @BindView(R.id.teamImageView)
    ImageView teamImageView;
    @BindView(R.id.teamImageLayout)
    FrameLayout teamImageLayout;
    @BindView(R.id.linkmanEdit)
    EditText linkmanEdit;
    @BindView(R.id.addExplanationEdit)
    EditText addExplanationEdit;
    @BindView(R.id.publishSubmitText)
    TextView publishSubmitText;


    private String teamImagePath;
    private String teamImageUrl;

    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;
    List<List<List<City>>> provinceAndCityAndAddList = null;

    //地址  省
    City province;
    //地址  市
    City city;
    //地址  区
    City district;

    List<Category> categoryList = new ArrayList<>();

    List<Model> checkItemList = new ArrayList<>();

    String checkedCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jod_hunting_publish);
        ButterKnife.bind(this);
        initView();

        getMenuData();
        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
        provinceAndCityAndAddList = CityUtil.getDistrictList();
    }

    private void initView() {
        checkboxItemAdapter = new CheckboxItemAdapter(this.getApplicationContext(), checkItemList);
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

    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.selectIntentAddressLayout, R.id.teamImageLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                this.finish();
                onBackPressed();
                break;
            case R.id.selectTypeLayout:
                break;
            case R.id.selectIntentAddressLayout:
                showCityOptionsPickerView();
                break;
            case R.id.teamImageLayout:
                selectStoreImage();
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
                    T.showToast(PublishJobHuntingActivity.this, "表单信息未填写完整，无法提交");
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
            cityOptionsPickerView = new OptionsPickerBuilder(PublishJobHuntingActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    province = provinceList.get(options1);
                    city = provinceAndCityList.get(options1).get(option2);
                    district = provinceAndCityAndAddList.get(options1).get(option2).get(options3);
                    intentAddressText.setText(province.getName() + "  " + city.getName() + "  " + district.getName());

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
                System.out.println("provinceAndCityAndAddList size is **************** " + provinceAndCityAndAddList == null ? -1 : provinceAndCityAndAddList.size());
            }
            cityOptionsPickerView.setPicker(provinceList, provinceAndCityList, provinceAndCityAndAddList);

        }
        cityOptionsPickerView.show();

    }


    private void selectStoreImage(){
        Intent intent = new Intent(PublishJobHuntingActivity.this, PhotoSelectActivity.class);
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
                teamImagePath = mediaBaseBeanList.get(0).getUrl();
                teamImageView.setImageURI(Uri.fromFile(new File(teamImagePath)));
            }

        }
    }

    private ProgressDialog dialog = null;

    private void submit(){
        Map<String,String> params = new HashMap<>();
        params.put("cid",checkedCategory);  //所选工种
        params.put("name",titleNameEdit.getText().toString());  //名称
        params.put("register",teamImageUrl);  //队伍图片
        params.put("number",personNumEdit.getText().toString());  //人数
        params.put("contacts_pid",province.getId());  //省份
        params.put("contacts_aid",city.getId());  // 城市
        params.put("contacts_cid",district.getId());  //区域
        params.put("explain",addExplanationEdit.getText().toString());  //其他说明
        params.put("linkman",linkmanEdit.getText().toString());  // 联系人
        params.put("telephone",UserService.getInstance().getMobile());  //电话
        params.put("uid",UserService.getInstance().getUid());  //用户ID

        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Worker/workAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishJobHuntingActivity.this, "发布成功");
                        PublishJobHuntingActivity.this.finish();

                    }
                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishJobHuntingActivity.this, response.getException().getMessage());
                    }
                });
    }
    //表单必填项校验
    private boolean checkFromOK(){
        if(StringUtil.isEmpty(titleNameEdit.getText().toString())  //证件名称
                || StringUtil.isEmpty(checkedCategory) //  所选工种
                || StringUtil.isEmpty(personNumEdit.getText().toString()) // 人数
                || StringUtil.isEmpty(teamImagePath) // 队伍图片
                || StringUtil.isEmpty(addExplanationEdit.getText().toString())  //说明
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

        File file = new File(teamImagePath);
        Map<String,Object> params = new HashMap<>();
        params.put("type","Supplier");
        params.put("file",file);


        DataRequestUtil.<JHResponse<String>>upLoadFile("Public/upload", params, new FileNetCallBack<JHResponse<String>>() {
            @Override
            public void done(JHResponse<String> result, Exception e) {
                if(e == null){
                    teamImageUrl = result.getMsg();
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
                else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    T.showToast(PublishJobHuntingActivity.this, e.getMessage());
                }
            }
        });
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
        else{
            checkedCategory = null;
        }
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

}
