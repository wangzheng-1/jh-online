package com.xcompany.jhonline.module.home.equipment.activity;

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
import com.xcompany.jhonline.model.home.RentSeekingDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.DetailCommonUtils;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.ProjectToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/12/8 10:15
 */
public class RentSeekingDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_linkman)
    TextView tvLinkman;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_entryTime)
    TextView tvEntryTime;
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
    private RentSeekingDetail rentingDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_seeking_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        DetailCommonUtils.checkStatus(this);
        DetailCommonUtils.saveAndUnSave(this, id, "3");
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<RentSeekingDetail>>post(ReleaseConfig.baseUrl() + "Lease/LeaseDetails")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<RentSeekingDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<RentSeekingDetail>> response) {
                        rentingDetail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<RentSeekingDetail>> response) {
                        T.showToast(RentSeekingDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        if (TextUtils.equals(rentingDetail.getIssue(), "0")) {
            toolbar.setToolbar_right("取消收藏");
        } else {
            toolbar.setToolbar_right("收藏");
        }
        tvLinkman.setText(NullCheck.check("联系人：", rentingDetail.getLinkman()));
        if(TextUtils.equals(rentingDetail.getSign(),"0")){
            tvTelephone.setText(NullCheck.check(rentingDetail.getTelephone()));
        }else {
            tvTelephone.setText("查看电话");
        }
        tvContacts.setText(NullCheck.check("地址：", rentingDetail.getContacts()));
        tvEntryTime.setText(NullCheck.check("信息有效期：", rentingDetail.getEntryTime()));
        tvExplain.setText(NullCheck.check(rentingDetail.getExplain()));
    }

    @OnClick({R.id.tv_telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_telephone:
                if (TextUtils.equals(rentingDetail.getSign(), "0")) {
                    String telephone = rentingDetail.getTelephone();
                    if (!TextUtils.isEmpty(telephone)) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("查看电话需要消耗5积分");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", ((dialog, which) -> {
                        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "User/consume")
                                .params("uid", UserService.getInstance().getUid())
                                .params("fid", id)
                                .params("type", "3")
                                .execute(new JHCallback<JHResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<JHResponse<String>> response) {
                                        rentingDetail.setSign("0");
                                        tvTelephone.setText(NullCheck.check(rentingDetail.getTelephone()));
                                        String telephone = rentingDetail.getTelephone();
                                        if (!TextUtils.isEmpty(telephone)) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<JHResponse<String>> response) {
                                        T.showToast(RentSeekingDetailActivity.this, "积分不足！");
                                    }
                                });
                    }));
                    builder.show();
                }
                break;
        }
    }
}
