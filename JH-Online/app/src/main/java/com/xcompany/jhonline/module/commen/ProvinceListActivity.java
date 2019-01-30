package com.xcompany.jhonline.module.commen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzheng on 2018/12/15 03:23
 */
public class ProvinceListActivity extends AppCompatActivity {


    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.provinceListView)
    ListView provinceListView;
    //最外面一层 分组名
    private List<City> provinceList;
    //自定义的适配器
    private ProvinceListAdapter provinceListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_list);
        ButterKnife.bind(this);
        provinceList = new ArrayList<>();
        //创建适配器
        provinceListAdapter = new ProvinceListAdapter(this);
        provinceListView.setAdapter(provinceListAdapter);
        provinceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = provinceList.get(position);
                Intent intent = new Intent();
                intent.putExtra("city", city);
                setResult(RESULT_OK,intent);
                finish();
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
                        provinceList = response.body().getMsg();
                        provinceListAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Response<JHResponse<List<City>>> response) {
//                        T.showToast(ProvinceListActivity.this, response.getException().getMessage());
                    }
                });
    }

    class ProvinceListAdapter extends BaseAdapter {
        private Context context;

        public ProvinceListAdapter(Context context) {
            this.context = context;
        }

        public List<City> getMdatas() {
            return provinceList;
        }

        public void setDate(List<City> mdatas) {
            provinceList = mdatas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return provinceList != null ? provinceList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return provinceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_city_list, null);
                holder = new MyViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }
            City bean = provinceList.get(position);
            holder.text.setText(NullCheck.check(bean.getName()));
            return convertView;
        }

        class MyViewHolder {
            @BindView(R.id.text)
            TextView text;
            MyViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
