package com.xcompany.jhonline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.xcompany.jhonline.module.home.HomeFragment;
import com.xcompany.jhonline.module.me.MeFragment;
import com.xcompany.jhonline.module.publish.PublishFragment;
import com.xcompany.jhonline.module.report.ReportFragment;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.HomeRadioButtonView;
import com.xcompany.jhonline.widget.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_bottom)
    LinearLayout llBottom;
    @BindView(R.id.viewpager)
    NoScrollViewPager mViewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<HomeRadioButtonView> radios = new ArrayList<>();
    private MainPagerAdapter pageAdapter;
    private int curIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        for (int i = 0; i < llBottom.getChildCount(); i++) {
            HomeRadioButtonView btn = (HomeRadioButtonView) llBottom.getChildAt(i);
            final int flag = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(flag, false);
                }
            });
            radios.add(btn);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (curIndex == position) return;
                curIndex = position;
                changeBtn(curIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragments.add(new HomeFragment());
        fragments.add(new ReportFragment());
        fragments.add(new PublishFragment());
        fragments.add(new MeFragment());
        pageAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(pageAdapter);
    }

    private void changeBtn(int index) {
        for (int i = 0; i < radios.size(); i++) {
            if (i == index) {
                radios.get(i).setChecked(true);
            } else {
                radios.get(i).setChecked(false);
            }
        }
    }

    long firstClickBack;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondClickBack = System.currentTimeMillis();
            if (secondClickBack - firstClickBack > 1500) {
                T.showToast(this, "再按一次退出江湖");
                firstClickBack = secondClickBack;
                return true;
            } else {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}