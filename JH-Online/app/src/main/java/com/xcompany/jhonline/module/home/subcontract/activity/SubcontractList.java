package com.xcompany.jhonline.module.home.subcontract.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.module.home.subcontract.adapter.ProductPageAdapter;
import com.xcompany.jhonline.module.publish.fragment.PublishFragment;
import com.xcompany.jhonline.module.report.fragment.ReportFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/11/26 22:57
 */
public class SubcontractList extends AppCompatActivity {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    List<Fragment> fragments = new ArrayList<>();
    ProductPageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcontractlist);
        ButterKnife.bind(this);
        fragments.add(new PublishFragment());
        fragments.add(new ReportFragment());
        viewpager.setOffscreenPageLimit(2);
        mAdapter = new ProductPageAdapter(getSupportFragmentManager(), fragments, this);
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
                tvTitle.setTextColor(SubcontractList.this.getResources().getColor(R.color.color0072FF));
                tab.getCustomView().findViewById(R.id.tab_item_indicator).setVisibility(View.VISIBLE);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tvTitle = tab.getCustomView().findViewById(R.id.tv_title);
                tvTitle.setTextColor(SubcontractList.this.getResources().getColor(R.color.color999999));
                tab.getCustomView().findViewById(R.id.tab_item_indicator).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
