package com.xcompany.jhonline.module.publish.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.EXTRA_SELECTED_PHOTOS;

/**
 * 新增服务过的项目
 */
public class PublishServicedProjectActivity extends BaseActivity {

    public static final String SELECTED_PROJECT = "selected_project";

    @BindView(R.id.backHomeLayout)
    LinearLayout backHomeLayout;
    @BindView(R.id.reportTitleText)
    TextView reportTitleText;
    @BindView(R.id.addServiceText)
    TextView addServiceText;
    @BindView(R.id.titleLayout)
    RelativeLayout titleLayout;
    @BindView(R.id.projectItemList)
    ListView projectItemList;
    @BindView(R.id.publishSubmitText)
    TextView publishSubmitText;

    ProjectItemAdapter projectItemAdapter;

    private List<String> servicedProjectList = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviced_project_add);
        servicedProjectList = (List<String>) getIntent().getSerializableExtra(SELECTED_PROJECT);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener(){
        if(servicedProjectList == null || servicedProjectList.size() <= 0){
            servicedProjectList = new ArrayList<>();
            servicedProjectList.add("");
        }
        projectItemAdapter = new ProjectItemAdapter(this.getApplicationContext());
        projectItemList.setAdapter(projectItemAdapter);
        projectItemList.setDividerHeight(0);
    }

    @OnClick({R.id.backHomeLayout, R.id.addServiceText, R.id.publishSubmitText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                this.finish();
                onBackPressed();
                break;
            case R.id.addServiceText:
                servicedProjectList.add("");
                projectItemAdapter.notifyDataSetChanged();
                projectItemList.setSelection(servicedProjectList.size() - 1);
                break;
            case R.id.publishSubmitText:
                saveInfo();
                break;
        }
    }

    class ProjectItemAdapter extends BaseAdapter {

        Context context;


        public ProjectItemAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return servicedProjectList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final MyViewHolder myViewHolder;
            final String  servicedProject = servicedProjectList.get(position);
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.activity_serviced_project_item, null);
                myViewHolder = new MyViewHolder(convertView);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.projectNameEdit.setText(servicedProject);
            myViewHolder.delProjectLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    servicedProjectList.remove(position);
                    ProjectItemAdapter.this.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class MyViewHolder extends XRecyclerView.ViewHolder {
            @BindView(R.id.projectNameEdit)
            EditText projectNameEdit;
            @BindView(R.id.delProjectLayout)
            LinearLayout delProjectLayout;
            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    private void saveInfo(){
        List<String> projectNameList = new ArrayList<>();
        for(int i = 0; i < servicedProjectList.size(); i++){
            View view = projectItemList.getChildAt(i);
            EditText editText = view.findViewById(R.id.projectNameEdit);
            String projectName = editText.getText().toString();
            if(null != projectName && !projectName.trim().equals("")){
                projectNameList.add(projectName);
            }
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_PHOTOS, (Serializable) projectNameList);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
