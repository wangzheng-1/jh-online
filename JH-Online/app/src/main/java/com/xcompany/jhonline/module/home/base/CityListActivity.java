package com.xcompany.jhonline.module.home.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.SharedPreferenceUtil;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/12/15 03:23
 */
public class CityListActivity extends AppCompatActivity {
    @BindView(R.id.currentCity)
    TextView currentCity;
    @BindView(R.id.expandable_listview)
    ExpandableListView expandableListView;
    //最外面一层 分组名
    private List<City> groupArray;
    //最外面一层 分组下面的详情
    private List<List<City>> childArray;
    //自定义的适配器
    private ExpandableAdapter expandableAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);
        currentCity.setText(SharedPreferenceUtil.getCityName(this));
        expandableListView.setGroupIndicator(null);
        groupArray = new ArrayList<>();
        childArray = new ArrayList<>();
        //创建适配器
        expandableAdapter = new ExpandableAdapter(this);
        expandableListView.setAdapter(expandableAdapter);
//        mAdapter = new CityListAdapter(this, mdatas);
//        listView.setAdapter(mAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                City city = mdatas.get(position);
//                Intent intent = new Intent();
//                intent.putExtra("city", city);
//                setResult(1002, intent);
//                finish();
//            }
//        });
        //分组的点击事件
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
                //如果分组被打开 直接关闭
                if (expandableListView.isGroupExpanded(groupPosition)) {
                    expandableListView.collapseGroup(groupPosition);
                } else if (childArray.get(groupPosition).isEmpty()) {

                    City city = groupArray.get(groupPosition);
                    OkGo.<JHResponse<List<City>>>post(ReleaseConfig.baseUrl() + "class/area")
                            .tag(this)
                            .params("pid", city.getId())
                            .execute(new JHCallback<JHResponse<List<City>>>() {
                                @Override
                                public void onSuccess(Response<JHResponse<List<City>>> response) {
                                    List<City> cities = response.body().msg;
                                    childArray.get(groupPosition).clear();
                                    childArray.get(groupPosition).addAll(cities);
                                    expandableAdapter.notifyDataSetChanged();
                                    //打开分组
                                    expandableListView.expandGroup(groupPosition, true);
                                }

                                @Override
                                public void onError(Response<JHResponse<List<City>>> response) {
                                    T.showToast(CityListActivity.this, response.getException().getMessage());
                                }
                            });
                } else {
                    expandableListView.expandGroup(groupPosition, true);
                }
                return true;
            }
        });

        //详情的点击事件
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                List<City> childModels = childArray.get(groupPosition);
                if (childModels != null) {
                    City childModel = childModels.get(childPosition);
                    Intent intent = new Intent();
                    intent.putExtra("city", childModel);
                    setResult(1002, intent);
                    finish();
                }
                return false;
            }
        });
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<List<City>>>post(ReleaseConfig.baseUrl() + "class/area")
                .tag(this)
                .params("pid", 0)
                .execute(new JHCallback<JHResponse<List<City>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<City>>> response) {
                        groupArray = response.body().getMsg();
                        for (int i = 0; i < groupArray.size(); i++) {
                            List<City> tempArray = new ArrayList<>();
                            childArray.add(tempArray);
                        }
                        expandableAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<JHResponse<List<City>>> response) {
                        T.showToast(CityListActivity.this, response.getException().getMessage());
                    }
                });
    }

    class ExpandableAdapter extends BaseExpandableListAdapter {
        private LayoutInflater mInflater;

        public ExpandableAdapter(Context context) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupArray.get(groupPosition);
        }

        public Object getChild(int groupPosition, int childPosition) {
            return childArray.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        @Override
        public int getGroupCount() {
            return groupArray.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childArray.get(groupPosition).size();
        }


        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_city_list, parent, false);
            }
            TextView tv_title = convertView.findViewById(R.id.text);
            City city = groupArray.get(groupPosition);
            tv_title.setText(city.getName());
            ImageView iv_tip = convertView.findViewById(R.id.iv_chilld);
            if (isExpanded) {
                iv_tip.setImageResource(R.drawable.yjjx2);
            } else {
                iv_tip.setImageResource(R.drawable.yjjx);
            }
            return convertView;
        }

        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_city_list_child, parent, false);
            }
            TextView tv_text = convertView.findViewById(R.id.text);
            City city = childArray.get(groupPosition).get(childPosition);
            tv_text.setText(city.getName());
            return convertView;
        }

        public boolean hasStableIds() {
            // 是否指定分组视图及其子视图的ID对应的后台数据改变也会保持该ID。
            return true;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // 指定位置的子视图是否可选择。
            return true;
        }

        @Override
        public void onGroupExpanded(int groupPosition) {
            super.onGroupExpanded(groupPosition);
            for (int i = 0; i < expandableAdapter.getGroupCount(); i++) {
                if (groupPosition != i) {
                    expandableListView.collapseGroup(i);
                }
            }

        }
    }
}
