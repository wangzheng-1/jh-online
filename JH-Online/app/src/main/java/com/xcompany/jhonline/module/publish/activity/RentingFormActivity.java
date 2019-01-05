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
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.CityUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.CleanEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2019/1/1 10:14
 */
public class RentingFormActivity extends AppCompatActivity {

    @BindView(R.id.ll_cid)
    LinearLayout llCid;
    @BindView(R.id.et_name)
    CleanEditText etName;
    @BindView(R.id.tv_cid)
    TextView tvCid;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_linkman)
    CleanEditText etLinkman;
    @BindView(R.id.et_explain)
    EditText etExplain;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private String cid;
    List<String> register = new ArrayList<>();
    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;
    List<List<List<City>>> provinceAndCityAndAddList = null;
    private City contacts_pid;
    private City contacts_aid;
    private City contacts_cid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renting_form);
        ButterKnife.bind(this);
        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
        provinceAndCityAndAddList = CityUtil.getDistrictList();
    }

    @OnClick({R.id.ll_cid, R.id.ll_address, R.id.tv_submit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_cid:
                intent = new Intent(this, RentingProductActivity.class);
                startActivityForResult(intent, 1007);
                break;
            case R.id.ll_address:
                showAddressPickerView();
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    OptionsPickerView addressPickerView;

    private void showAddressPickerView() {

        if (addressPickerView == null) {
            addressPickerView = new OptionsPickerBuilder(RentingFormActivity.this, new OnOptionsSelectListener() {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1007 && resultCode == 1008) {
            cid = data.getStringExtra("cid");
            String cname = data.getStringExtra("cname");
            register = (List<String>) data.getExtras().get("register");
            tvCid.setText(cname);
        }
    }

    private ProgressDialog dialog = null;

    private void submit() {
        if (!checkFromOK()) {
            T.showToast(RentingFormActivity.this, "表单信息未填写完整，无法提交");
            return;
        }
        dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        HttpParams params = new HttpParams();
        params.put("name", etName.getText().toString());
        params.put("cid", cid);
        params.putUrlParams("register",register);
        params.put("explain", etExplain.getText().toString());
        params.put("linkman", etLinkman.getText().toString());
        params.put("telephone", UserService.getInstance().getMobile());
        params.put("contacts_pid", contacts_pid.getId());
        params.put("contacts_aid", contacts_aid.getId());
        params.put("contacts_cid", contacts_cid.getId());
        params.put("uid", UserService.getInstance().getUid());
        params.put("contacts","");//页面没有的字段，但接口不传不行

        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Lease/leaseAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(RentingFormActivity.this, "发布成功");
                        RentingFormActivity.this.finish();

                    }

                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(RentingFormActivity.this, response.getException().getMessage());
                    }
                });
    }

    private boolean checkFromOK() {
        if (StringUtil.isEmpty(etName.getText().toString())
                || cid == null
                || register == null || register.size() == 0
                || StringUtil.isEmpty(etExplain.getText().toString())
                || StringUtil.isEmpty(etLinkman.getText().toString())
                || contacts_pid == null
                || contacts_aid == null
                || contacts_cid == null
                ) {
            return false;
        }
        return true;
    }
}
