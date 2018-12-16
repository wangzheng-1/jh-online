package com.xcompany.jhonline.module.home.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcompany.jhonline.R;

import java.util.List;

/**
 * Created by xieliang on 2018/11/27 22:28
 */
public class PageAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragments;
    private String[] tabTitle;

    public PageAdapter(FragmentManager fm, List<Fragment> fragments, String[] tabTitle, Context context) {
        super(fm);
        this.fragments = fragments;
        this.tabTitle = tabTitle;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_tab_title, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(tabTitle[position]);
        if (position == 0) {
            tvTitle.setTextColor(context.getResources().getColor(R.color.color0072FF));
            view.findViewById(R.id.tab_item_indicator).setVisibility(View.VISIBLE);
        } else {
            tvTitle.setTextColor(context.getResources().getColor(R.color.color999999));
            view.findViewById(R.id.tab_item_indicator).setVisibility(View.INVISIBLE);
        }
        return view;
    }

}
