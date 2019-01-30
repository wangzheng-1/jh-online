package com.xcompany.jhonline.module.home.buildMaterial.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.PurchaseDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.DetailCommonUtils;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.MultipleTextView;
import com.xcompany.jhonline.widget.ProjectToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/12/8 10:15
 */
public class PurchaseDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_contacts)
    MultipleTextView tvContacts;
    @BindView(R.id.tv_linkman)
    MultipleTextView tvLinkman;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.toolbar)
    ProjectToolbar toolbar;
    @BindView(R.id.top_image)
    ImageView topImage;
    @BindView(R.id.top_fix)
    TextView topFix;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    private String id;
    private PurchaseDetail detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        DetailCommonUtils.checkStatus(this);
        DetailCommonUtils.saveAndUnSave(this, id, "5");
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<PurchaseDetail>>post(ReleaseConfig.baseUrl() + "Supplier/purchaseDetails")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<PurchaseDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<PurchaseDetail>> response) {
                        detail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<PurchaseDetail>> response) {
//                        T.showToast(PurchaseDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        if(TextUtils.equals(detail.getIssue(),"0")){
            toolbar.setToolbar_right("取消收藏");
        }else {
            toolbar.setToolbar_right("收藏");
        }
        if(TextUtils.equals(detail.getSign(),"0")){
            tvTelephone.setText(NullCheck.check(detail.getTelephone()));
        }else {
            tvTelephone.setText("查看电话");
        }
        tvContacts.setContentText(detail.getContacts());
        tvLinkman.setContentText(detail.getLinkman());
        tvExplain.setText(NullCheck.check(detail.getExplain()));
    }

    @OnClick({R.id.tv_telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_telephone:
                if(TextUtils.equals(detail.getSign(),"0")){
                    String telephone = detail.getTelephone();
                    if (!TextUtils.isEmpty(telephone)) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("查看电话需要消耗5积分");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", ((dialog, which) -> {
                        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "User/consume")
                                .params("uid", UserService.getInstance().getUid())
                                .params("fid", id)
                                .params("type", "5")
                                .execute(new JHCallback<JHResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<JHResponse<String>> response) {
                                        detail.setSign("0");
                                        tvTelephone.setText(NullCheck.check(detail.getTelephone()));
                                        String telephone = detail.getTelephone();
                                        if (!TextUtils.isEmpty(telephone)) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<JHResponse<String>> response) {
                                        T.showToast(PurchaseDetailActivity.this, "积分不足！");
                                    }
                                });
                    }));
                    builder.show();
                }
                break;
        }
    }
}
