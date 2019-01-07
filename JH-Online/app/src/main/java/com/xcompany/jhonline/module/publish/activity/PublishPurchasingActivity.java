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

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.module.home.base.CityListActivity;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布正在采购
 */

public class PublishPurchasingActivity extends BaseActivity {

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
    @BindView(R.id.purchaseNameEdit)
    EditText purchaseNameEdit;
    @BindView(R.id.deliveryAddressText)
    TextView deliveryAddressText;
    @BindView(R.id.selectDeliveryAddressLayout)
    LinearLayout selectDeliveryAddressLayout;
    @BindView(R.id.linkmanEdit)
    EditText linkmanEdit;
    @BindView(R.id.addExplanationEdit)
    EditText addExplanationEdit;
    @BindView(R.id.publishSubmitText)
    TextView publishSubmitText;
    /**
     *  送货城市地址ID
     */
    private City city = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing_publish);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.backHomeLayout, R.id.selectTypeLayout, R.id.selectDeliveryAddressLayout, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                onBackPressed();
                break;
            case R.id.selectTypeLayout:
                Intent intent2 = new Intent(PublishPurchasingActivity.this,PublishTypeActivity.class);
                startActivityForResult(intent2,5);
                break;
            case R.id.selectDeliveryAddressLayout:
                Intent intent = new Intent(PublishPurchasingActivity.this,CityListActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.publishSubmitText:
                submit();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1002) {
            city = (City) data.getExtras().get("city");
            deliveryAddressText.setText(city.getName());
        }
    }

    private ProgressDialog dialog = null;

    private void submit(){
        if(!checkFromOK()){
            T.showToast(PublishPurchasingActivity.this, "表单信息未填写完整，无法提交");
            return;
        }
        dialog = ProgressDialog.show(this, "", "正在提交，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Map<String,String> params = new HashMap<>();
        params.put("name",purchaseNameEdit.getText().toString());  //名称
        params.put("contacts_aid",city.getId());  //城市
        params.put("contacts_pid",city.getPid());  //省份
        params.put("explain",addExplanationEdit.getText().toString());  //其他说明
        params.put("linkman",linkmanEdit.getText().toString());  // 联系人
        params.put("telephone",UserService.getInstance().getMobile());  //电话
        params.put("uid",UserService.getInstance().getUid());  //用户ID

        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Supplier/PurchaseAddLogic")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishPurchasingActivity.this, "发布成功");
                        PublishPurchasingActivity.this.finish();

                    }
                    @Override
                    public void onError(Response<JHResponse<String>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(PublishPurchasingActivity.this, response.getException().getMessage());
                    }
                });
    }
    //表单必填项校验
    private boolean checkFromOK(){
        if(StringUtil.isEmpty(purchaseNameEdit.getText().toString())
                || city == null
                || StringUtil.isEmpty(addExplanationEdit.getText().toString())
                || StringUtil.isEmpty(linkmanEdit.getText().toString())
                ){
            return false;
        }
        return true;
    }
}
