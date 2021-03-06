package com.xcompany.jhonline.module.publish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.report.MediaBaseBean;
import com.xcompany.jhonline.model.report.MediaBaseBeanSerial;
import com.xcompany.jhonline.module.report.activity.PhotoSelectActivity;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.CaseView;
import com.xcompany.jhonline.widget.ProjectToolbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.EXTRA_SELECTED_PHOTOS;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.IMAGE_NUM;

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
    private CaseView current = null;
    private static String SELECT_MEDIA_TYPE = "selectMediaType";
    private static final int RC_CHOOSE_PHOTO = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_case);
        ButterKnife.bind(this);
        if (mdatas.size() == 0) {
            addForm(false);
        }
        toolbar.setOnRightClickListener(v -> {
            if (mdatas.size() < 3) {
                boolean flag = mdatas.size() != 0;
                addForm(flag);
            } else {
                T.showToast(this, "最多添加3个案例");
            }
        });
    }

    public void addForm(boolean isDelete) {
        CaseView view = new CaseView(this, isDelete);
        llParent.addView(view.mView);
        mdatas.add(view);
        view.setOnDeleteListener(() -> {
            mdatas.remove(view);
            llParent.removeView(view.mView);
        });
        view.setOnImageListener(caseView -> {
            current = caseView;
            Intent intent = new Intent(this, PhotoSelectActivity.class);
            intent.putExtra(SELECT_MEDIA_TYPE, 0);
            intent.putExtra(IMAGE_NUM, 1);
            startActivityForResult(intent, RC_CHOOSE_PHOTO);
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
                        imgUrl.add(v.getImageUrl());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            MediaBaseBeanSerial result = (MediaBaseBeanSerial) data.getSerializableExtra(EXTRA_SELECTED_PHOTOS);
            List<MediaBaseBean> mediaBaseBeanList = result.getMediaBaseBeanList();
            if (mediaBaseBeanList != null && mediaBaseBeanList.size() > 0) {
                String storeImagePath = mediaBaseBeanList.get(0).getUrl();
                if (current != null && !TextUtils.isEmpty(storeImagePath)) {
                    current.setImageUrl(storeImagePath);
                }
            }

        }
    }
}
