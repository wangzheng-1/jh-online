package com.xcompany.jhonline.module.home.card.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.module.publish.activity.RentingFormActivity;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.CityUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.CleanEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/12/13 00:26
 */
public class AddCardActivity extends AppCompatActivity {
    @BindView(R.id.et_name)
    CleanEditText etName;
    @BindView(R.id.submit)
    TextView submit;
    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;
    List<List<List<City>>> provinceAndCityAndAddList = null;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_company)
    CleanEditText etCompany;
    @BindView(R.id.et_phone)
    CleanEditText etPhone;
    @BindView(R.id.et_email)
    CleanEditText etEmail;
    @BindView(R.id.et_business)
    CleanEditText etBusiness;
    private City contacts_pid;
    private City contacts_aid;
    private City contacts_cid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_add);
        ButterKnife.bind(this);
        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
        provinceAndCityAndAddList = CityUtil.getDistrictList();
    }

    @OnClick({R.id.et_name, R.id.submit, R.id.ll_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_name:
                break;
            case R.id.submit:
                submit();
                break;
            case R.id.ll_address:
                showAddressPickerView();
                break;
        }
    }

    OptionsPickerView addressPickerView;

    private void showAddressPickerView() {

        if (addressPickerView == null) {
            addressPickerView = new OptionsPickerBuilder(AddCardActivity.this, new OnOptionsSelectListener() {
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

    private ProgressDialog dialog = null;

    private void submit() {
        if (!checkFromOK()) {
            T.showToast(AddCardActivity.this, "表单信息未填写完整，无法提交");
            return;
        }
        dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        HttpParams params = new HttpParams();
        params.put("name", etName.getText().toString());
        params.put("port", 3);
        params.put("business", etBusiness.getText().toString());
        params.put("company", etCompany.getText().toString());
        params.put("email", etEmail.getText().toString());
        params.put("telephone", etPhone.getText().toString());
        params.put("uid", UserService.getInstance().getUid());
        params.put("image", "222");

        List<String> areaList = new ArrayList<>();
        areaList.add(contacts_pid.getName());
        areaList.add(contacts_aid.getName());
        areaList.add(contacts_cid.getName());
        params.put("area", StringUtil.join(areaList,","));
        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Forum/cardAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(AddCardActivity.this, "生成成功");
                        Intent intent = new Intent(AddCardActivity.this, CardDetailActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(AddCardActivity.this, response.getException().getMessage());
                    }
                });
    }

    private boolean checkFromOK() {
        if (StringUtil.isEmpty(etName.getText().toString())
                || contacts_pid == null
                || contacts_aid == null
                || contacts_cid == null
                || StringUtil.isEmpty(etPhone.getText().toString())
                || StringUtil.isEmpty(etBusiness.getText().toString())
                ) {
            return false;
        }
        return true;
    }
}
