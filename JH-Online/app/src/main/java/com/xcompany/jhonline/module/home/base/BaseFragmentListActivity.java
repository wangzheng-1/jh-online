package com.xcompany.jhonline.module.home.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.widget.ProjectToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/11/26 22:57
 */
public abstract class BaseFragmentListActivity extends AppCompatActivity {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.toolbar)
    ProjectToolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    PageAdapter mAdapter;


    protected abstract List<Fragment> getFragments();

    protected abstract String[] getLabels();

    protected abstract String getPageTitle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment_list);
        ButterKnife.bind(this);
        toolbar.setToolbar_title(getPageTitle());
        viewpager.setOffscreenPageLimit(2);
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
                tvTitle.setTextColor(BaseFragmentListActivity.this.getResources().getColor(R.color.color0072FF));
                tab.getCustomView().findViewById(R.id.tab_item_indicator).setVisibility(View.VISIBLE);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tvTitle = tab.getCustomView().findViewById(R.id.tv_title);
                tvTitle.setTextColor(BaseFragmentListActivity.this.getResources().getColor(R.color.color999999));
                tab.getCustomView().findViewById(R.id.tab_item_indicator).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
