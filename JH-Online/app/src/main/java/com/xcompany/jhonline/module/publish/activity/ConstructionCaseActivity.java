package com.xcompany.jhonline.module.publish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.publish.Case;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.CaseView;
import com.xcompany.jhonline.widget.ProjectToolbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2019/1/1 23:04
 */
public class ConstructionCaseActivity extends AppCompatActivity {
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.toolbar)
    ProjectToolbar toolbar;
    private List<CaseView> mdatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_case);
        ButterKnife.bind(this);
        if (mdatas.size() == 0) {
            addForm(null, false);
        }
        toolbar.setOnRightClickListener(v -> {
            if (mdatas.size() < 3) {
                boolean flag = mdatas.size() != 0;
                addForm(null, flag);
            } else {
                T.showToast(this, "最多添加3个案例");
            }
        });
    }

    public void addForm(Case mcase, boolean isDelete) {
        CaseView view = new CaseView(this, isDelete);
        llParent.addView(view.mView);
        mdatas.add(view);
        view.setOnDeleteListener(() -> {
            mdatas.remove(view);
            llParent.removeView(view.mView);
        });
    }

    @OnClick({R.id.ll_parent, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_parent:
                break;
            case R.id.tv_submit:
                List<String> entry = new ArrayList<>();
                List<String> illustrate = new ArrayList<>();
                List<String> imgUrl = new ArrayList<>();
                for (int i = 0; i < mdatas.size(); i++) {
                    CaseView v = mdatas.get(i);
                    if (!v.check()) {
                        T.showToast(this, "请将表单填写完整！");
                        return;
                    } else {
                        entry.add(v.getEntry());
                        illustrate.add(v.getIllustrate());
                        imgUrl.add("https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a363e53.jpg");
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("entry", (Serializable) entry);
                intent.putExtra("illustrate", (Serializable) illustrate);
                intent.putExtra("imgUrl", (Serializable) imgUrl);
                setResult(1008, intent);
                finish();
                break;
        }
    }
}
