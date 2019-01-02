package com.xcompany.jhonline.module.publish.activity;

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
                break;
        }
    }
}
