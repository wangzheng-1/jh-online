package com.xcompany.jhonline.module.publish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
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
import com.xcompany.jhonline.utils.GlideUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.CleanEditText;

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

/**
 * Created by xieliang on 2019/1/1 10:14
 */
public class QualitySupplierFormActivity extends AppCompatActivity {


    @BindView(R.id.et_name)
    CleanEditText etName;
    @BindView(R.id.tv_cid)
    TextView tvCid;
    @BindView(R.id.ll_cid)
    LinearLayout llCid;
    @BindView(R.id.rg_delivery)
    RadioGroup rgDelivery;
    @BindView(R.id.rg_door)
    RadioGroup rgDoor;
    @BindView(R.id.rg_loan)
    RadioGroup rgLoan;
    @BindView(R.id.ll_add_image)
    LinearLayout llAddImage;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
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
    List<String> cid = new ArrayList<>();
    List<String> classid = new ArrayList<>();
    List<String> register = new ArrayList<>();
    private String shopimg;
    List<City> provinceList = null;
    List<List<City>> provinceAndCityList = null;
    List<List<List<City>>> provinceAndCityAndAddList = null;
    private City contacts_pid;
    private City contacts_aid;
    private City contacts_cid;
    private static String SELECT_MEDIA_TYPE = "selectMediaType";
    private QualitySupplierFormActivity that = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_supplier_form);
        ButterKnife.bind(this);
        provinceList = CityUtil.getProvinceList();
        provinceAndCityList = CityUtil.getCityList();
        provinceAndCityAndAddList = CityUtil.getDistrictList();
    }

    @OnClick({R.id.ll_cid, R.id.ll_address, R.id.tv_submit, R.id.ll_add_image, R.id.tv_upload})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_cid:
                intent = new Intent(this, SupplierProductActivity.class);
                startActivityForResult(intent, 1007);
                break;
            case R.id.ll_address:
                showAddressPickerView();
                break;
            case R.id.tv_submit:
                submit();
                break;
            case R.id.ll_add_image:
                intent = new Intent(this, PhotoSelectActivity.class);
                intent.putExtra(SELECT_MEDIA_TYPE, 0);
                intent.putExtra(IMAGE_NUM, 1);
                startActivityForResult(intent, 888);
                break;
            case R.id.tv_upload:
                intent = new Intent(this, PhotoSelectActivity.class);
                intent.putExtra(SELECT_MEDIA_TYPE, 0);
                intent.putExtra(IMAGE_NUM, 1);
                startActivityForResult(intent, 888);
                break;
        }
    }

    OptionsPickerView addressPickerView;

    private void showAddressPickerView() {

        if (addressPickerView == null) {
            addressPickerView = new OptionsPickerBuilder(QualitySupplierFormActivity.this, new OnOptionsSelectListener() {
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
            String cname = data.getStringExtra("cname");
            cid = (List<String>) data.getExtras().get("cid");
            classid = (List<String>) data.getExtras().get("classid");
            register = (List<String>) data.getExtras().get("register");
            tvCid.setText(cname);
        } else if (requestCode == 888 && resultCode == RESULT_OK) {
            MediaBaseBeanSerial result = (MediaBaseBeanSerial) data.getSerializableExtra(EXTRA_SELECTED_PHOTOS);
            List<MediaBaseBean> mediaBaseBeanList = result.getMediaBaseBeanList();
            if (mediaBaseBeanList != null && mediaBaseBeanList.size() > 0) {
                String storeImagePath = mediaBaseBeanList.get(0).getUrl();
                if (!TextUtils.isEmpty(storeImagePath)) {
                    setImageUrl(storeImagePath);
                }
            }

        }
    }

    private ProgressDialog dialog = null;

    private void submit() {
        if (!checkFromOK()) {
            T.showToast(QualitySupplierFormActivity.this, "表单信息未填写完整，无法提交");
            return;
        }
        dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        HttpParams params = new HttpParams();
        params.put("name", etName.getText().toString());
        params.putUrlParams("cid", cid);
        params.putUrlParams("classid", classid);
        params.putUrlParams("register", register);
        params.put("delivery", (String) findViewById(rgDelivery.getCheckedRadioButtonId()).getTag());
        params.put("door", (String) findViewById(rgDoor.getCheckedRadioButtonId()).getTag());
        params.put("loan", (String) findViewById(rgLoan.getCheckedRadioButtonId()).getTag());
        params.put("shopimg", shopimg);
        params.put("explain", etExplain.getText().toString());
        params.put("linkman", etLinkman.getText().toString());
        params.put("telephone", UserService.getInstance().getMobile());
        params.put("contacts_pid", contacts_pid.getId());
        params.put("contacts_aid", contacts_aid.getId());
        params.put("contacts_cid", contacts_cid.getId());
        params.put("uid", UserService.getInstance().getUid());
        params.put("grade", "");//无此字段，但接口必须传

        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Supplier/supplierAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(QualitySupplierFormActivity.this, "发布成功");
                        QualitySupplierFormActivity.this.finish();

                    }

                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(QualitySupplierFormActivity.this, response.getException().getMessage());
                    }
                });
    }

    private boolean checkFromOK() {
        if (StringUtil.isEmpty(etName.getText().toString())
                || cid == null || cid.size() == 0
                || classid == null || classid.size() == 0
                || register == null || register.size() == 0
                || StringUtil.isEmpty(shopimg)
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

    public void setImageUrl(String imageUrl) {
        dialog = ProgressDialog.show(this, "", "正在上传，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        File file = new File(imageUrl);
        Map<String, Object> params = new HashMap<>();
        params.put("type", "Supplier");
        params.put("file", file);
        DataRequestUtil.upLoadFile("Public/upload", params, new FileNetCallBack<JHResponse<String>>() {
            @Override
            public void done(JHResponse<String> result, Exception e) {
                runOnUiThread(() -> {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (e == null) {
                        Log.i("JHOnline", result.getMsg());
                        that.shopimg = result.getMsg();
                        llAddImage.setVisibility(View.GONE);
                        ivImage.setVisibility(View.VISIBLE);
                        tvUpload.setVisibility(View.VISIBLE);
                        GlideUtil.LoaderImage(that, that.shopimg, ivImage, false);
                    } else {
                        T.showToast(that, e.getMessage());
                    }
                });

            }
        });
    }
}
