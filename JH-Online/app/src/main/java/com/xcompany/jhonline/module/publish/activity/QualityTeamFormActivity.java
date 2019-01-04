package com.xcompany.jhonline.module.publish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Category;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.CityUtil;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.CleanEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;

/**
 * Created by xieliang on 2019/1/1 10:14
 */
public class QualityTeamFormActivity extends AppCompatActivity {
    @BindView(R.id.tv_cid)
    TextView tvCid;
    @BindView(R.id.et_name)
    CleanEditText etName;
    @BindView(R.id.tv_inventory)
    TextView tvInventory;
    @BindView(R.id.et_pay)
    CleanEditText etPay;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_example)
    TextView tvExample;
    @BindView(R.id.et_idea)
    CleanEditText etIdea;
    @BindView(R.id.et_enounce)
    CleanEditText etEnounce;
    @BindView(R.id.tv_native)
    TextView tvNative;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_explain)
    EditText etExplain;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_cid)
    LinearLayout llCid;
    @BindView(R.id.et_linkman)
    CleanEditText etLinkman;
    @BindView(R.id.ll_inventory)
    LinearLayout llInventory;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.ll_example)
    LinearLayout llExample;
    @BindView(R.id.ll_native)
    LinearLayout llNative;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_telephone)
    CleanEditText etTelephone;
    private Category cid;
    private Category inventory;
    List<String> entry = new ArrayList<>();
    List<String> illustrate = new ArrayList<>();
    List<String> imgUrl = new ArrayList<>();
    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;
    List<List<List<City>>> provinceAndCityAndAddList = null;
    private City contacts_pid;
    private City contacts_aid;
    private City contacts_cid;

    private City service_pid;
    private City service_aid;
    private City service_cid;

    private City native_pid;
    private City native_aid;
    private City native_cid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_team_form);
        ButterKnife.bind(this);
        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
        provinceAndCityAndAddList = CityUtil.getDistrictList();
        cid = (Category) getIntent().getExtras().get("cid");
        inventory = (Category) getIntent().getExtras().get("inventory");
        tvCid.setText(NullCheck.check("分包承建商信息 匹配优质班组 ", cid.getName()));
        tvInventory.setText(NullCheck.check(inventory.getName()));
    }

    @OnClick({R.id.ll_cid, R.id.ll_inventory, R.id.ll_example, R.id.ll_address, R.id.ll_service, R.id.ll_native, R.id.tv_submit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_cid:
                intent = new Intent(this, PublishTypeActivity.class);
                startActivityForResult(intent, 1003);
                break;
            case R.id.ll_inventory:
                intent = new Intent(this, InventoryActivity.class);
                startActivityForResult(intent, 1005);
                break;
            case R.id.ll_example:
                intent = new Intent(this, ConstructionCaseActivity.class);
                startActivityForResult(intent, 1007);
                break;
            case R.id.ll_address:
                showAddressPickerView();
                break;
            case R.id.ll_service:
                showServicePickerView();
                break;
            case R.id.ll_native:
                showNativePickerView();
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    OptionsPickerView addressPickerView;

    private void showAddressPickerView() {

        if (addressPickerView == null) {
            addressPickerView = new OptionsPickerBuilder(QualityTeamFormActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    contacts_pid = provinceList.get(options1);
                    contacts_aid = provinceAndCityList.get(options1).get(option2);
                    contacts_cid = provinceAndCityAndAddList.get(options1).get(option2).get(options3);
                    tvAddress.setText(contacts_pid.getName() + "  " + contacts_aid.getName() + "  " + contacts_cid.getName());

                }
            }).build();
            addressPickerView.setPicker(provinceList, provinceAndCityList, provinceAndCityAndAddList);
        }
        addressPickerView.show();

    }

    OptionsPickerView servicePickerView;

    private void showServicePickerView() {

        if (servicePickerView == null) {
            servicePickerView = new OptionsPickerBuilder(QualityTeamFormActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    service_pid = provinceList.get(options1);
                    service_aid = provinceAndCityList.get(options1).get(option2);
                    service_cid = provinceAndCityAndAddList.get(options1).get(option2).get(options3);
                    tvService.setText(service_pid.getName() + "  " + service_aid.getName() + "  " + service_cid.getName());

                }
            }).build();
            servicePickerView.setPicker(provinceList, provinceAndCityList, provinceAndCityAndAddList);
        }
        servicePickerView.show();

    }

    OptionsPickerView nativePickerView;

    private void showNativePickerView() {

        if (nativePickerView == null) {
            nativePickerView = new OptionsPickerBuilder(QualityTeamFormActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    native_pid = provinceList.get(options1);
                    native_aid = provinceAndCityList.get(options1).get(option2);
                    native_cid = provinceAndCityAndAddList.get(options1).get(option2).get(options3);
                    tvNative.setText(native_pid.getName() + "  " + native_aid.getName() + "  " + native_cid.getName());

                }
            }).build();
            nativePickerView.setPicker(provinceList, provinceAndCityList, provinceAndCityAndAddList);
        }
        nativePickerView.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1003 && resultCode == 1004) {
            cid = (Category) data.getExtras().get("cid");
            tvCid.setText(NullCheck.check("分包承建商信息 匹配优质班组 ", cid.getName()));

            inventory = (Category) data.getExtras().get("inventory");
            tvInventory.setText(NullCheck.check(inventory.getName()));

        } else if (requestCode == 1005 && resultCode == 1006) {
            cid = (Category) data.getExtras().get("cid");
            tvCid.setText(NullCheck.check("分包承建商信息 匹配优质班组 ", cid.getName()));

            inventory = (Category) data.getExtras().get("inventory");
            tvInventory.setText(NullCheck.check(inventory.getName()));
        } else if (requestCode == 1007 && resultCode == 1008) {
            entry = (List<String>) data.getExtras().get("entry");
            illustrate = (List<String>) data.getExtras().get("illustrate");
            imgUrl = (List<String>) data.getExtras().get("imgUrl");
            String text = "";
            for (int i = 0; i < entry.size(); i++) {
                if (i != 0) {
                    text += ",";
                }
                text += entry.get(i);
            }
            tvExample.setText(text);
        }
    }

    private ProgressDialog dialog = null;

    private void submit() {
        if (!checkFromOK()) {
            T.showToast(QualityTeamFormActivity.this, "表单信息未填写完整，无法提交");
            return;
        }
        dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        HttpParams params = new HttpParams();
        params.put("cid", cid.getId());
        params.put("name", etName.getText().toString());
        params.put("inventory", inventory.getName());
        params.put("pay", etPay.getText().toString());
        params.put("service", service_pid + "," + service_aid + "," + service_cid);
        params.putUrlParams("entry", entry);
        params.putUrlParams("illustrate", illustrate);
        params.putUrlParams("imgUrl", imgUrl);
        params.put("explain", etExplain.getText().toString());
        params.put("idea", etIdea.getText().toString());
        params.put("enounce", etEnounce.getText().toString());
        params.put("linkman", etLinkman.getText().toString());
        params.put("telephone", etTelephone.getText().toString());
        params.put("native", native_cid.getId());
        params.put("contacts_pid", contacts_pid.getId());
        params.put("contacts_aid", contacts_aid.getId());
        params.put("contacts_cid", contacts_cid.getId());
        params.put("uid", UserService.getInstance().getUid());

        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "builder/builderAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(QualityTeamFormActivity.this, "发布成功");
                        QualityTeamFormActivity.this.finish();

                    }

                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(QualityTeamFormActivity.this, response.getException().getMessage());
                    }
                });
    }

    private boolean checkFromOK() {
        if (StringUtil.isEmpty(etName.getText().toString())
                || cid == null
                || inventory == null
                || service_pid == null
                || service_aid == null
                || service_cid == null
                || entry == null
                || imgUrl == null
                || StringUtil.isEmpty(etExplain.getText().toString())
                || StringUtil.isEmpty(etLinkman.getText().toString())
                || StringUtil.isEmpty(etTelephone.getText().toString())
                || native_cid == null
                || contacts_pid == null
                || contacts_aid == null
                || contacts_cid == null
                ) {
            return false;
        }
        return true;
    }
//    public MultipartBody multipartBodyData(IdentityHashMap<String,String> identityHashMap){
//        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        if(identityHashMap ==null || identityHashMap.size() ==0){
//            return requestBody.build();
//        }
//
//        for (String key :identityHashMap.keySet()){
//            requestBody.addFormDataPart(key,identityHashMap.get(key));
//
//        }
//
//        return requestBody.build();
//    }
}
