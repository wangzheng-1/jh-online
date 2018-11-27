package com.xcompany.jhonline.module.home.subcontract.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xcompany.jhonline.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/11/26 22:57
 */
public class SubcontractList extends AppCompatActivity {
    @BindView(R.id.recycler_list)
    XRecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcontractlist);
        ButterKnife.bind(this);
    }
}
