package com.xcompany.jhonline.module.publish.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.CityUtil;
import com.xcompany.jhonline.utils.DateUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.CleanEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2019/1/1 10:14
 */
public class RentSeekingActivity extends AppCompatActivity {

    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;
    List<List<List<City>>> provinceAndCityAndAddList = null;
    @BindView(R.id.et_name)
    CleanEditText etName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_deadline)
    TextView tvDeadline;
    @BindView(R.id.ll_deadline)
    LinearLayout llDeadline;
    @BindView(R.id.et_linkman)
    CleanEditText etLinkman;
    @BindView(R.id.et_explain)
    EditText etExplain;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private City contacts_pid;
    private City contacts_aid;
    private City contacts_cid;
    private Calendar deadLineDate = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_seeking_form);
        ButterKnife.bind(this);
        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
        provinceAndCityAndAddList = CityUtil.getDistrictList();
    }

    @OnClick({R.id.ll_deadline, R.id.ll_address, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_deadline:
                showTimePickerView();
                break;
            case R.id.ll_address:
                showAddressPickerView();
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    //时间选择器
    TimePickerView pvTime;

    private void showTimePickerView() {
        if (pvTime == null) {
            pvTime = new TimePickerBuilder(RentSeekingActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    deadLineDate.setTime(date);
                    tvDeadline.setText(DateUtil.dateParseStr(deadLineDate.getTime()));
                    if (pvTime != null && pvTime.isShowing()) {
                        pvTime.dismiss();
                    }
                }
            }).isDialog(true)//是否显示为对话框样式
                    .build();
        }
        pvTime.setDate(deadLineDate);
        pvTime.show();
    }

    OptionsPickerView addressPickerView;

    private void showAddressPickerView() {

        if (addressPickerView == null) {
            addressPickerView = new OptionsPickerBuilder(RentSeekingActivity.this, new OnOptionsSelectListener() {
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
            T.showToast(RentSeekingActivity.this, "表单信息未填写完整，无法提交");
            return;
        }
        dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        HttpParams params = new HttpParams();
        params.put("name", etName.getText().toString());
        params.put("contacts_pid", contacts_pid.getId());
        params.put("contacts_aid", contacts_aid.getId());
        params.put("contacts_cid", contacts_cid.getId());
        params.put("explain", etExplain.getText().toString());
        params.put("linkman", etLinkman.getText().toString());
        params.put("deadline", tvDeadline.getText().toString());
        params.put("telephone", "123456");
        params.put("uid", UserService.getInstance().getUid());
        params.put("cid", 2);
        params.put("contacts","");//页面没有的字段，但接口不传不行

        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Lease/lendAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(RentSeekingActivity.this, "发布成功");
                        RentSeekingActivity.this.finish();

                    }

                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(RentSeekingActivity.this, response.getException().getMessage());
                    }
                });
    }

    private boolean checkFromOK() {
        if (StringUtil.isEmpty(etName.getText().toString())
                || contacts_pid == null
                || contacts_aid == null
                || contacts_cid == null
                || StringUtil.isEmpty(etExplain.getText().toString())
                || StringUtil.isEmpty(etLinkman.getText().toString())
                || StringUtil.isEmpty(tvDeadline.getText().toString())
                ) {
            return false;
        }
        return true;
    }
}
