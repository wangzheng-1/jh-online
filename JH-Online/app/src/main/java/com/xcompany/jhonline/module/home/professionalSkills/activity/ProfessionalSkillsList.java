package com.xcompany.jhonline.module.home.professionalSkills.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.module.home.base.BaseFragmentListActivity;
import com.xcompany.jhonline.module.home.base.PageAdapter;
import com.xcompany.jhonline.module.home.professionalSkills.fragment.ProfessionalSkillsFragment;
import com.xcompany.jhonline.module.home.subcontract.fragment.QualityTeamFragment;
import com.xcompany.jhonline.module.home.subcontract.fragment.TenderFragment;
import com.xcompany.jhonline.widget.ProjectToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/12/10 22:47
 */
public class ProfessionalSkillsList extends AppCompatActivity {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    PageAdapter mAdapter;

    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        String[] labels = getLabels();
        for (String label : labels) {
            fragments.add(ProfessionalSkillsFragment.newInstance(label));
        }
        return fragments;
    }

    protected String[] getLabels() {
        return new String[]{"植筋", "打爆膜", "搭内架", "杂活清包", "墙体打点挂网", "烧焊（拦网片）", "墙体保温", "本地帮工队", "其他技能"};
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_skills_list);
        ButterKnife.bind(this);
        viewpager.setOffscreenPageLimit(getLabels().length);
        mAdapter = new PageAdapter(getSupportFragmentManager(), getFragments(), getLabels(), this);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);
        for (int i = 0; i < tablayout.getTabCount(); i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            View view = mAdapter.getTabView(i);
            tab.setCustomView(view);
        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tvTitle = tab.getCustomView().findViewById(R.id.tv_title);
                tvTitle.setTextColor(ProfessionalSkillsList.this.getResources().getColor(R.color.color0072FF));
                tab.getCustomView().findViewById(R.id.tab_item_indicator).setVisibility(View.VISIBLE);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tvTitle = tab.getCustomView().findViewById(R.id.tv_title);
                tvTitle.setTextColor(ProfessionalSkillsList.this.getResources().getColor(R.color.color999999));
                tab.getCustomView().findViewById(R.id.tab_item_indicator).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
