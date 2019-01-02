package com.xcompany.jhonline.module.publish.activity;

import android.content.Context;
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
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.publish.CheckboxItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增服务过的项目
 */
public class PublishServicedProjectActivity extends BaseActivity {
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

    private List<Model> checkboxItemBeanList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviced_project_add);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener(){
        checkboxItemBeanList.add(new CheckboxItemBean());
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
                Model model = new CheckboxItemBean();
                checkboxItemBeanList.add(model);
                projectItemAdapter.notifyDataSetChanged();
                projectItemList.setSelection(checkboxItemBeanList.size() - 1);
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
            return checkboxItemBeanList.size();
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
            final CheckboxItemBean checkboxItemBean =  (CheckboxItemBean)checkboxItemBeanList.get(position);
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.activity_serviced_project_item, null);
                myViewHolder = new MyViewHolder(convertView);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.delProjectLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkboxItemBeanList.remove(position);
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

        for(int i = 0;i < checkboxItemBeanList.size(); i++){
            View view = projectItemList.getChildAt(i);
            EditText editText = view.findViewById(R.id.projectNameEdit);
            String projectName = editText.getText().toString();
            if(null != projectName && !projectName.trim().equals("")){
                projectNameList.add(projectName);
            }
        }
        for(String name : projectNameList){
            System.out.println("projectName:*************************" + name);
        }
    }
}
