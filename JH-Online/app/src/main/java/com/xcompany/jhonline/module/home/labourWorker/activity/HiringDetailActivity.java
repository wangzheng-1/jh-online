package com.xcompany.jhonline.module.home.labourWorker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.Hiring;
import com.xcompany.jhonline.model.home.HiringDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

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
    private String id;
    private HiringDetail hiringDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiring_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
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
                        T.showToast(HiringDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        name.setText(NullCheck.check("项目名称：", hiringDetail.getName()));
        contacts.setText(NullCheck.check("地址：",hiringDetail.getContacts()));
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
        telephone.setText(NullCheck.check(hiringDetail.getTelephone()));
        explain.setText(NullCheck.check(hiringDetail.getExplain()));
    }

    @OnClick({R.id.telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.telephone:
                break;
        }
    }
}
