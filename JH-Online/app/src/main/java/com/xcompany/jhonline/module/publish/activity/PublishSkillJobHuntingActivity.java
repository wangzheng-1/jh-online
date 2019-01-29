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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.xcompany.jhonline.widget.MultiLineRadioGroup;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xcompany.jhonline.module.publish.activity.PublishServicedProjectActivity.SELECTED_PROJECT;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.EXTRA_SELECTED_PHOTOS;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.IMAGE_NUM;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.SELECT_MEDIA_TYPE;

/**
 * 发布技管正在求职
 */
public class PublishSkillJobHuntingActivity extends BaseActivity {

    /**
     * 选择照片视频
     */
    private static final int RC_CHOOSE_PHOTO = 1;
    /**
     * 选择服务项目
     */
    private static final int RC_CHOOSE_SERVICED_PROJECT = 2;


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
    MultiLineRadioGroup radioGroupGender;
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

    private String headImagePath;
    private String headImageUrl;


    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;

    //地址  省
    City province;
    //地址  市
    City city;

    private List<String> servicedProjectList;


    List<Category> categoryList = new ArrayList<>();

    List<Model> checkItemList = new ArrayList<>();

    String checkedPositions;

    private int education = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_job_hunting_publish);
        ButterKnife.bind(this);
        initView();
        getMenuData();
        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
    }

    private void initView() {
        checkboxItemAdapter = new CheckboxItemAdapter(PublishSkillJobHuntingActivity.this, checkItemList);
        positionListView.setAdapter(checkboxItemAdapter);
        positionListView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        positionListView.setLoadingMoreEnabled(false);
        positionListView.setPullRefreshEnabled(false);
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


    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.headImageLayout, R.id.selectAddressLayout, R.id.selectServicedProjectLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                PublishSkillJobHuntingActivity.this.finish();
                onBackPressed();
                break;
            case R.id.selectTypeLayout:
                Intent intent = new Intent(PublishSkillJobHuntingActivity.this,PublishTypeActivity.class);
                startActivityForResult(intent,5);
                break;
            case R.id.headImageLayout:
                selectHeadImage();
                break;
            case R.id.selectAddressLayout:
                showCityOptionsPickerView();
                break;
            case R.id.selectServicedProjectLayout:
                selectServicedProject();
                break;
            case R.id.publishSubmitText:
                dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                getCheckedCategory();
                getEducation();
                if(!checkFromOK()){
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    T.showToast(PublishSkillJobHuntingActivity.this, "表单信息未填写完整，无法提交");
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
            cityOptionsPickerView = new OptionsPickerBuilder(PublishSkillJobHuntingActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    province = provinceList.get(options1);
                    city = provinceAndCityList.get(options1).get(option2);
                    intentAddressText.setText(province.getName() + "  " + city.getName());

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


    private void selectServicedProject(){
        Intent intent = new Intent(PublishSkillJobHuntingActivity.this, PublishServicedProjectActivity.class);
        if(servicedProjectList != null && servicedProjectList.size() >0){
            intent.putExtra(SELECTED_PROJECT,(Serializable) servicedProjectList);
        }
        startActivityForResult(intent,RC_CHOOSE_SERVICED_PROJECT);
    }


    private void selectHeadImage(){
        Intent intent = new Intent(PublishSkillJobHuntingActivity.this, PhotoSelectActivity.class);
        intent.putExtra(SELECT_MEDIA_TYPE,0);
        intent.putExtra(IMAGE_NUM,1);
        intent.putExtra(SELECT_MEDIA_TYPE,1);
        startActivityForResult(intent,RC_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == RC_CHOOSE_PHOTO){
                MediaBaseBeanSerial result = (MediaBaseBeanSerial)data.getSerializableExtra(EXTRA_SELECTED_PHOTOS);
                List<MediaBaseBean> mediaBaseBeanList = result.getMediaBaseBeanList();
                if(mediaBaseBeanList != null && mediaBaseBeanList.size() > 0){
                    headImagePath = mediaBaseBeanList.get(0).getUrl();
                    headImageView.setImageURI(Uri.fromFile(new File(headImagePath)));

                }
            }
            else if(requestCode == RC_CHOOSE_SERVICED_PROJECT) {
                servicedProjectList = (List<String>) data.getSerializableExtra(SELECTED_PROJECT);
                if (servicedProjectList != null && servicedProjectList.size() > 0) {
                    servicedProjectText.setText(StringUtil.join(servicedProjectList, ","));
                }
            }
        }
    }

    private ProgressDialog dialog = null;

    private void submit(){
        Map<String,String> params = new IdentityHashMap<>();
        params.put("name",nameEdit.getText().toString());  //名称
        params.put("cid",checkedPositions);  //岗位
        params.put("register[]",headImageUrl);  //头像
        params.put("duration",employDurationEdit.getText().toString());  //从业时长

        params.put("class",otherPositionEdit.getText().toString());  //其他岗位
        params.put("contacts_pid",province.getId());  //省份
        params.put("contacts_aid",city.getId());  // 城市

        if(!StringUtil.isEmpty(otherPositionEdit.getText().toString())){
            params.put("price",paymentEdit.getText().toString());  //薪酬
        }
//        for(String projectName : servicedProjectList){
//            params.put("project[]",projectName);  //服务过的项目
//        }
        params.put("project[]",StringUtil.join(servicedProjectList,","));  //服务过的项目

        params.put("explain",addExplanationEdit.getText().toString());  // 附加说明
        params.put("linkman",linkmanEdit.getText().toString());  // 联系人
        params.put("telephone",UserService.getInstance().getMobile());  //电话
        params.put("uid",UserService.getInstance().getUid());  //用户ID

        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Recruit/jopAddLogic")
                .tag(this)
                .params("education",education)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishSkillJobHuntingActivity.this, "发布成功");
                        PublishSkillJobHuntingActivity.this.finish();

                    }
                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishSkillJobHuntingActivity.this, response.getException().getMessage());
                    }
                });
    }
    //表单必填项校验
    private boolean checkFromOK(){
        if(StringUtil.isEmpty(nameEdit.getText().toString())  //姓名
                || StringUtil.isEmpty(headImagePath) // 头像
                || StringUtil.isEmpty(employDurationEdit.getText().toString()) // 从业时长
                || StringUtil.isEmpty(otherPositionEdit.getText().toString()) // 其他岗位
                || education < 0  //学历
                || StringUtil.isEmpty(checkedPositions)  //岗位
                || servicedProjectList == null || servicedProjectList.size() <= 0   //服务过的项目
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
            checkedPositions = StringUtil.join(checkedItemList,",");
        }
        else{
            checkedPositions = null;
        }
    }
    //获取学历
    private void getEducation(){

        View checkedView = this.findViewById(radioGroupGender.getCheckedRadioButtonId());
        if(checkedView != null){
            String tag = (String) checkedView.getTag();
            education = Integer.parseInt(tag);
        }
        else {
            education = -1;
        }

    }
    /**
     * 上传图片
     */
    private void uploadImage(){

        File file = new File(headImagePath);
        Map<String,Object> params = new HashMap<>();
        params.put("type","Jop");
        params.put("file",file);

        DataRequestUtil.<JHResponse<String>>upLoadFile("Public/upload", params, new FileNetCallBack<JHResponse<String>>() {
            @Override
            public void done(JHResponse<String> result, Exception e) {
                if(e == null){
                    headImageUrl = result.getMsg();
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
                else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    T.showToast(PublishSkillJobHuntingActivity.this, e.getMessage());
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
}
