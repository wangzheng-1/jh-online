package com.xcompany.jhonline.module.home.siteMatching.activity;

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
import com.xcompany.jhonline.app.GlideApp;
import com.xcompany.jhonline.model.home.SiteMatchingDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.DetailCommonUtils;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.ProjectToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/12/8 10:15
 */
public class SiteMatchingDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_linkman)
    TextView tvLinkman;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.toolbar)
    ProjectToolbar toolbar;
    @BindView(R.id.top_image)
    ImageView topImage;
    @BindView(R.id.top_fix)
    TextView topFix;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    private String id;
    private SiteMatchingDetail siteMatchingDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_matching_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        DetailCommonUtils.checkStatus(this);
        DetailCommonUtils.saveAndUnSave(this, id, "12");
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<SiteMatchingDetail>>post(ReleaseConfig.baseUrl() + "Serve/serveOne")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<SiteMatchingDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<SiteMatchingDetail>> response) {
                        siteMatchingDetail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<SiteMatchingDetail>> response) {
                        T.showToast(SiteMatchingDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        if (TextUtils.equals(siteMatchingDetail.getIssue(), "0")) {
            toolbar.setToolbar_right("取消收藏");
        } else {
            toolbar.setToolbar_right("收藏");
        }
        if (TextUtils.equals(siteMatchingDetail.getSign(), "0")) {
            tvTelephone.setText(NullCheck.check(siteMatchingDetail.getTelephone()));
        } else {
            tvTelephone.setText("查看电话");
        }
        tvContacts.setText(NullCheck.check("服务地区：", siteMatchingDetail.getContacts()));
        tvLinkman.setText(NullCheck.check("联系人：", siteMatchingDetail.getLinkman()));
        tvExplain.setText(NullCheck.check(siteMatchingDetail.getExplain()));
        List<String> list = siteMatchingDetail.getImages();
        if (list.size() == 0) {
            image1.setVisibility(View.INVISIBLE);
            image2.setVisibility(View.INVISIBLE);
            image3.setVisibility(View.INVISIBLE);
        } else if (list.size() == 1) {
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.INVISIBLE);
            image3.setVisibility(View.INVISIBLE);
            GlideApp.with(this).load(list.get(0))
                    .centerCrop()
                    .into(image1);
        } else if (list.size() == 2) {
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.VISIBLE);
            image3.setVisibility(View.INVISIBLE);
            GlideApp.with(this).load(list.get(0))
                    .centerCrop()
                    .into(image1);
            GlideApp.with(this).load(list.get(1))
                    .centerCrop()
                    .into(image2);
        } else {
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.VISIBLE);
            image3.setVisibility(View.VISIBLE);
            GlideApp.with(this).load(list.get(0))
                    .centerCrop()
                    .into(image1);
            GlideApp.with(this).load(list.get(1))
                    .centerCrop()
                    .into(image2);
            GlideApp.with(this).load(list.get(2))
                    .centerCrop()
                    .into(image2);
        }
    }

    @OnClick({R.id.tv_telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_telephone:
                if (TextUtils.equals(siteMatchingDetail.getSign(), "0")) {
                    String telephone = siteMatchingDetail.getTelephone();
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
                                .params("type", "12")
                                .execute(new JHCallback<JHResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<JHResponse<String>> response) {
                                        siteMatchingDetail.setSign("0");
                                        tvTelephone.setText(NullCheck.check(siteMatchingDetail.getTelephone()));
                                        String telephone = siteMatchingDetail.getTelephone();
                                        if (!TextUtils.isEmpty(telephone)) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<JHResponse<String>> response) {
                                        T.showToast(SiteMatchingDetailActivity.this, "积分不足！");
                                    }
                                });
                    }));
                    builder.show();
                }
                break;
        }
    }
}
