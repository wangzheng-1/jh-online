package com.xcompany.jhonline.module.home.labourWorker.activity;

import android.support.v4.app.Fragment;

import com.xcompany.jhonline.module.home.base.BaseFragmentListActivity;
import com.xcompany.jhonline.module.home.labourWorker.fragment.HiringFragment;
import com.xcompany.jhonline.module.home.labourWorker.fragment.JobHuntingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieliang on 2018/11/26 22:57
 */
public class LabourWorkerList extends BaseFragmentListActivity {
    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HiringFragment());
        fragments.add(new JobHuntingFragment());
        return fragments;
    }

    @Override
    protected String[] getLabels() {
        return new String[]{"正在招工", "正在找活"};
    }

    @Override
    protected String getPageTitle() {
        return "劳务工人";
    }
}
