package com.xcompany.jhonline.module.home.labourWorker.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.HiringDetail;
import com.xcompany.jhonline.module.home.buildMaterial.activity.PurchaseDetailActivity;
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
public class HiringDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.contacts)
    TextView contacts;
    @BindView(R.id.classname)
    TextView classname;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.deadline)
    TextView deadline;
    @BindView(R.id.linkman)
    TextView linkman;
    @BindView(R.id.telephone)
    TextView telephone;
    @BindView(R.id.explain)
    TextView explain;
    @BindView(R.id.toolbar)
    ProjectToolbar toolbar;
    @BindView(R.id.top_image)
    ImageView topImage;
    @BindView(R.id.top_fix)
    TextView topFix;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    private String id;
    private HiringDetail hiringDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiring_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        DetailCommonUtils.checkStatus(this);
        DetailCommonUtils.saveAndUnSave(this, id, "8");
        Spanned str1 = Html.fromHtml("<strong><font color=#FF4500>" + "温馨提示:" + "</font></strong>进场请先核实对方身份，过往工程，拒绝先打路费现象，谨防受骗");
        tvNotice.setText(str1);
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<HiringDetail>>post(ReleaseConfig.baseUrl() + "worker/workerDetails")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<HiringDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<HiringDetail>> response) {
                        hiringDetail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<HiringDetail>> response) {
//                        T.showToast(HiringDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        if(TextUtils.equals(hiringDetail.getIssue(),"0")){
            toolbar.setToolbar_right("取消收藏");
        }else {
            toolbar.setToolbar_right("收藏");
        }
        if(TextUtils.equals(hiringDetail.getSign(),"0")){
            telephone.setText(NullCheck.check(hiringDetail.getTelephone()));
        }else {
            telephone.setText("查看电话");
        }
        name.setText(NullCheck.check("项目名称：", hiringDetail.getName()));
        contacts.setText(NullCheck.check("地址：", hiringDetail.getContacts()));
        List<HiringDetail.CaseBean> caseX = hiringDetail.getCaseX();
        String classNames = "";
        int num = 0;
        for (int i = 0; i < caseX.size(); i++) {
            HiringDetail.CaseBean caseBean = caseX.get(i);
            if (i != 0) classNames += ",";
            classNames += caseBean.getClassname();
            num += Integer.parseInt(caseBean.getNumber());
        }
        classname.setText("工种：" + classNames);
        number.setText("人数：" + num);
        deadline.setText(NullCheck.check("信息有效期至：", hiringDetail.getDeadline()));
        linkman.setText(NullCheck.check("联系人：", hiringDetail.getLinkman()));
        explain.setText(NullCheck.check(hiringDetail.getExplain()));
    }

    @OnClick({R.id.telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.telephone:
                if(TextUtils.equals(hiringDetail.getSign(),"0")){
                    String telephone = hiringDetail.getTelephone();
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
                                .params("type", "8")
                                .execute(new JHCallback<JHResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<JHResponse<String>> response) {
                                        hiringDetail.setSign("0");
                                        telephone.setText(NullCheck.check(hiringDetail.getTelephone()));
                                        String telephone = hiringDetail.getTelephone();
                                        if (!TextUtils.isEmpty(telephone)) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<JHResponse<String>> response) {
                                        T.showToast(HiringDetailActivity.this, "积分不足！");
                                    }
                                });
                    }));
                    builder.show();
                }
                break;
        }
    }
}
