package com.xcompany.jhonline.module.home.technical.activity;

import android.support.v4.app.Fragment;

import com.xcompany.jhonline.module.home.base.BaseFragmentListActivity;
import com.xcompany.jhonline.module.home.technical.fragment.ApplicationFragment;
import com.xcompany.jhonline.module.home.technical.fragment.RecruitFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieliang on 2018/11/26 22:57
 */
public class TechnicalList extends BaseFragmentListActivity {
    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecruitFragment());
        fragments.add(new ApplicationFragment());
        return fragments;
    }

    @Override
    protected String[] getLabels() {
        return new String[]{"正在招聘", "正在求职"};
    }

    @Override
    protected String getPageTitle() {
        return "技管人才";
    }
}
