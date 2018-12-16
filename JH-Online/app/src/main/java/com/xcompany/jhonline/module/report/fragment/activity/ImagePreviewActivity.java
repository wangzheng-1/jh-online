package com.xcompany.jhonline.module.report.fragment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.report.ImageMsg;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2017/4/14.
 */

public class ImagePreviewActivity extends BaseActivity {
    private LinearLayout back;
    private TextView tvcancle;
    private List<String> list;
    private List<ImageView> imagelist = new ArrayList<>();
    private ViewPager vp;
    private TextView num;
    private TextView status;
    private ImageView mCheckBox;
    private int count=0;
    private List<Integer> intlist=new ArrayList<>();
    private HashMap<Integer,String> map=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepreview);
        Intent intent = getIntent();
        list = (List<String>) intent.getSerializableExtra("list");
        num = (TextView) findViewById(R.id.tv_num);
        status = (TextView) findViewById(R.id.tv_status);
        mCheckBox = (ImageView) findViewById(R.id.mCheckBox);
        tvcancle = (TextView) findViewById(R.id.tv_cancel);
        back = (LinearLayout) findViewById(R.id.back);
        vp = (ViewPager) findViewById(R.id.vp);
        num.setText("1/"+list.size());
        for (int i = 0; i < list.size(); i++) {
            intlist.add(i);
            map.put(i,"true");
            final String url = list.get(i);
            final ImageView view = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(ImagePreviewActivity.this).load(url)
                            .into(view);
                }
            });
            imagelist.add(view);
        }
        MyAdapter adapter = new MyAdapter(imagelist);
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String s = map.get(position);
                if(s.equals("true")){
                    mCheckBox.setImageResource(R.mipmap.select_ok);
                    status.setText("已选择");
                }else{
                    mCheckBox.setImageResource(R.mipmap.select_no);
                    status.setText("已取消");
                }
                count=position;
                num.setText((position + 1) + "/" + list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = map.get(count);
                if (s.equals("true")) {
                    map.put(count,"false");
                    status.setText("已取消");
                    mCheckBox.setImageResource(R.mipmap.select_no);
                }else{
                    status.setText("已选择");
                    map.put(count,"true");
                    mCheckBox.setImageResource(R.mipmap.select_ok);

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intlist.size()!=0) {
                    for (int i = 0; i < map.size(); i++) {
                        String s = map.get(i);
                        if(s.equals("false")){
                            list.remove(i);
                        }
                    }
                }
                EventBus.getDefault().post(new ImageMsg(list));
                finish();
            }
        });
    }

    class MyAdapter extends PagerAdapter {
        private List<ImageView> list;

        public MyAdapter(List<ImageView> list) {
            this.list = list;
        }

        public void delete(int position) {
            list.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = list.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = list.get(position);
            container.removeView(view);
        }
    }
}
