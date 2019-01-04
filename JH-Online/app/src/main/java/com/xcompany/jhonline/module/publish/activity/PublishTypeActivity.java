package com.xcompany.jhonline.module.publish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xcompany.jhonline.R;
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

    }

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

    private void initBtn2() {
        View view = View.inflate(this, R.layout.view_type_child2, null);
        view.findViewById(R.id.entrance1).setOnClickListener(v -> {
            startActivity(new Intent(PublishTypeActivity.this, RentSeekingActivity.class));
        });
        typeButton2.addChild(view);
        TypeButton2 child = view.findViewById(R.id.entrance2);
        TypeGridView grandson = new TypeGridView(this, 0, 1);
        grandson.setOnItemClickListener(category -> {
            Intent intent= new Intent(this,RentingFormActivity.class);
            startActivity(intent);
        });
        child.addChild(grandson.mView);
    }

    private void initBtn3() {
        View view = View.inflate(this, R.layout.view_type_child3, null);
        typeButton3.addChild(view);
    }

    private void initBtn4() {
        View view = View.inflate(this, R.layout.view_type_child4, null);
        view.findViewById(R.id.entrance1).setOnClickListener(v -> {
            startActivity(new Intent(PublishTypeActivity.this, PublishCertificateAnchoredActivity.class));
        });
        typeButton4.addChild(view);
    }

    private void initBtn5() {
        View view = View.inflate(this, R.layout.view_type_child5, null);
        typeButton5.addChild(view);
        TypeButton2 child = view.findViewById(R.id.entrance2);
        TypeGridView grandson = new TypeGridView(this, 2, 0);
        child.addChild(grandson.mView);
    }

    private void initBtn6() {
        View view = View.inflate(this, R.layout.view_type_child6, null);
        typeButton6.addChild(view);
        TypeButton2 child = view.findViewById(R.id.entrance1);
        TypeGridView grandson = new TypeGridView(this, 3, 40);
        child.addChild(grandson.mView);
        TypeButton2 child2 = view.findViewById(R.id.entrance2);
        TypeGridView grandson2 = new TypeGridView(this, 3, 40);
        child2.addChild(grandson2.mView);
    }

    private void initBtn7() {
        TypeGridView gridView = new TypeGridView(this, 1, 0);
        typeButton7.addChild(gridView.mView);
    }

    private void initBtn8() {
        TypeGridView gridView = new TypeGridView(this, 7, 0);
        typeButton8.addChild(gridView.mView);
    }

}
