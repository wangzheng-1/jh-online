package com.xcompany.jhonline.module.publish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.QualityTeamTypeView;
import com.xcompany.jhonline.widget.TypeButton;
import com.xcompany.jhonline.widget.TypeButton2;
import com.xcompany.jhonline.widget.TypeGridView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/12/31 00:52
 */
public class PublishTypeActivity extends AppCompatActivity {


    @BindView(R.id.type_button1)
    TypeButton typeButton1;
    @BindView(R.id.type_button2)
    TypeButton typeButton2;
    @BindView(R.id.type_button3)
    TypeButton typeButton3;
    @BindView(R.id.type_button4)
    TypeButton typeButton4;
    @BindView(R.id.type_button5)
    TypeButton typeButton5;
    @BindView(R.id.type_button6)
    TypeButton typeButton6;
    @BindView(R.id.type_button7)
    TypeButton typeButton7;
    @BindView(R.id.type_button8)
    TypeButton typeButton8;
    @BindView(R.id.type_button9)
    LinearLayout typeButton9;

    public static final String SELECTED_TYPE_NAME = "SELECTED_TYPE_NAME";
    public static final String SELECTED_TYPE_CID= "SELECTED_TYPE_CID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_type_choose);
        ButterKnife.bind(this);
        initBtn1();
        initBtn2();
        initBtn3();
        initBtn4();
        initBtn5();
        initBtn6();
        initBtn7();
        initBtn8();
        initBtn9();

    }

    /**
     * 1、正在招标项目 2 、匹配优质班组
     */
    private void initBtn1() {
        View view = View.inflate(this, R.layout.view_type_child1, null);
        view.findViewById(R.id.entrance1).setOnClickListener(v -> {
            startActivity(new Intent(PublishTypeActivity.this, TenderFormActivity.class));
        });
        typeButton1.addChild(view);
        TypeButton2 child = view.findViewById(R.id.type_button_quality_team);
        QualityTeamTypeView qualityTeamTypeView = new QualityTeamTypeView(this);
        child.addChild(qualityTeamTypeView.mView);
    }

    /**
     * 1、正在求租 2 、正在出租
     */
    private void initBtn2() {
        View view = View.inflate(this, R.layout.view_type_child2, null);
        view.findViewById(R.id.entrance1).setOnClickListener(v -> {
            startActivity(new Intent(PublishTypeActivity.this, RentSeekingActivity.class));
        });
        typeButton2.addChild(view);
        TypeButton2 child = view.findViewById(R.id.entrance2);
        TypeGridView grandson = new TypeGridView(this, 0, 1, false);
        grandson.setOnItemClickListener(category -> {
            Intent intent = new Intent(this, RentingFormActivity.class);
            startActivity(intent);
        });
        child.addChild(grandson.mView);
    }

    /**
     * 1、优质供应商 2 、正在采购
     */
    private void initBtn3() {
        View view = View.inflate(this, R.layout.view_type_child3, null);
        view.findViewById(R.id.entrance1).setOnClickListener(v -> {
            startActivity(new Intent(this, QualitySupplierFormActivity.class));
        });
        view.findViewById(R.id.entrance2).setOnClickListener(v -> {
            startActivity(new Intent(this, PublishPurchasingActivity.class));
        });
        typeButton3.addChild(view);
    }

    /**
     * 1、证照挂靠 2 、资质办理
     */
    private void initBtn4() {
        View view = View.inflate(this, R.layout.view_type_child4, null);
        view.findViewById(R.id.entrance1).setOnClickListener(v -> {
            startActivity(new Intent(this, PublishCertificateAnchoredActivity.class));
        });
        view.findViewById(R.id.entrance2).setOnClickListener(v -> {
            startActivity(new Intent(this, PublishAptitudeHandleActivity.class));
        });
        typeButton4.addChild(view);
    }

    /**
     * 1、正在找活 2 、正在招工
     */
    private void initBtn5() {
        View view = View.inflate(this, R.layout.view_type_child5, null);
        view.findViewById(R.id.entrance1).setOnClickListener(v -> {
            startActivity(new Intent(this, PublishJobHuntingActivity.class));
        });
        typeButton5.addChild(view);
        TypeButton2 child = view.findViewById(R.id.entrance2);
        TypeGridView grandson = new TypeGridView(this, 2, 0, false);
        child.addChild(grandson.mView);
        grandson.setOnItemClickListener(category -> {
            Intent intent = new Intent(this, PublishRecruitingActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 1、正在求职 2 、正在招聘
     */
    private void initBtn6() {
        View view = View.inflate(this, R.layout.view_type_child6, null);
        typeButton6.addChild(view);
        TypeButton2 child = view.findViewById(R.id.entrance1);
        TypeGridView grandson = new TypeGridView(this, 3, 40, false);
        child.addChild(grandson.mView);
        grandson.setOnItemClickListener(category -> {
            Intent intent = new Intent(this, PublishSkillJobHuntingActivity.class);
            startActivity(intent);
        });
        TypeButton2 child2 = view.findViewById(R.id.entrance2);
        TypeGridView grandson2 = new TypeGridView(this, 3, 40, false);
        child2.addChild(grandson2.mView);
        grandson2.setOnItemClickListener(category -> {
            Intent intent = new Intent(this, PublishSkillRecruitingActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 工地配套信息
     */
    private void initBtn7() {
        TypeGridView gridView = new TypeGridView(this, 1, 0, false);
        typeButton7.addChild(gridView.mView);
        gridView.setOnItemClickListener(category -> {
            Intent intent = new Intent(this, PublishConstructionMatchActivity.class);
            intent.putExtra(SELECTED_TYPE_NAME, category.getName());
            intent.putExtra(SELECTED_TYPE_CID, category.getId());
            startActivity(intent);
        });
    }

    /**
     * 专业技能库信息
     */
    private void initBtn8() {
        TypeGridView gridView = new TypeGridView(this, 7, 0, false);
        typeButton8.addChild(gridView.mView);
        gridView.setOnItemClickListener(category -> {
            Intent intent = new Intent(this, PublishMajorLibraryActivity.class);
            intent.putExtra(SELECTED_TYPE_NAME, category.getName());
            intent.putExtra(SELECTED_TYPE_CID, category.getId());
            startActivity(intent);
        });
    }

    /**
     * 业内黑名单信息
     */
    private void initBtn9() {
        typeButton9.setOnClickListener(v -> {
            Intent intent = new Intent(this, PublishIndustryBlackListActivity.class);
            startActivity(intent);
        });
    }
}
