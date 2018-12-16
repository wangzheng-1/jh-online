package com.xcompany.jhonline.module.home.base;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideApp;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.model.home.MusicHotKey;
import com.xcompany.jhonline.module.home.blacklist.activity.BlackListActivity;
import com.xcompany.jhonline.module.home.buildMaterial.activity.BuildMaterialList;
import com.xcompany.jhonline.module.home.card.activity.AddCardActivity;
import com.xcompany.jhonline.module.home.certificate.acitivity.CertificateList;
import com.xcompany.jhonline.module.home.equipment.activity.EquipmentList;
import com.xcompany.jhonline.module.home.labourWorker.activity.LabourWorkerList;
import com.xcompany.jhonline.module.home.professionalSkills.activity.ProfessionalSkillsList;
import com.xcompany.jhonline.module.home.siteMatching.activity.SiteMatchingList;
import com.xcompany.jhonline.module.home.subcontract.activity.SubcontractList;
import com.xcompany.jhonline.module.home.technical.activity.TechnicalList;
import com.xcompany.jhonline.network.ApiResponse;
import com.xcompany.jhonline.network.JsonCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xieliang on 2018/11/21 11:47
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    View headViwe;
    ViewPager viewPager;
    private HomeAdapter mAdapter;
    List<String> mdatas = new ArrayList<>();
    String url = "https://c.y.qq.com/splcloud/fcgi-bin/gethotkey.fcg";
    private int[] imageUrl = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private List<ImageView> imageList;
    private LinearLayout dotGroup;//小圆点
    private boolean isSwitchPager = true; //默认不切换
    private int previousPosition = 0; //默认为0
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEventAndData() {
        for (int i = 0; i < 20; i++) {
            mdatas.add("蓝色" + i);
        }
        mAdapter = new HomeAdapter(mContext, mdatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        headViwe = View.inflate(mContext, R.layout.view_home_head, null);
        initHeadView();
        mRecyclerView.addHeaderView(headViwe);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    public void getData() {
        OkGo.<ApiResponse<MusicHotKey>>get(url)
                .tag(this)
                .execute(new JsonCallback<ApiResponse<MusicHotKey>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<MusicHotKey>> response) {
                        MusicHotKey data = response.body().getData();
                        List<String> list = new ArrayList<>();
                        for (MusicHotKey.HotkeyBean bean : data.getHotkey()) {
                            list.add(bean.getK());
                        }
                        mAdapter.setDatas(list);
                        mRecyclerView.refreshComplete();
                    }
                });
    }

    public void initHeadView() {
        headViwe.findViewById(R.id.entrance1).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SubcontractList.class);
            mContext.startActivity(intent);
        });
        headViwe.findViewById(R.id.entrance2).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EquipmentList.class);
            mContext.startActivity(intent);
        });
        headViwe.findViewById(R.id.entrance3).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BuildMaterialList.class);
            mContext.startActivity(intent);
        });
        headViwe.findViewById(R.id.entrance4).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CertificateList.class);
            mContext.startActivity(intent);
        });
        headViwe.findViewById(R.id.entrance5).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProfessionalSkillsList.class);
            mContext.startActivity(intent);
        });
        headViwe.findViewById(R.id.entrance6).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, LabourWorkerList.class);
            mContext.startActivity(intent);
        });
        headViwe.findViewById(R.id.entrance7).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, TechnicalList.class);
            mContext.startActivity(intent);
        });
        headViwe.findViewById(R.id.entrance8).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddCardActivity.class);
            mContext.startActivity(intent);
        });
        headViwe.findViewById(R.id.entrance9).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SiteMatchingList.class);
            mContext.startActivity(intent);
        });
        headViwe.findViewById(R.id.entrance10).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BlackListActivity.class);
            mContext.startActivity(intent);
        });
        viewPager = headViwe.findViewById(R.id.viewPager);
        dotGroup = headViwe.findViewById(R.id.dot_group);
        initViewPagerData();
        viewPager.setAdapter(new MyViewPager());

        //设置当前viewPager要显示的第几个条目
        int item = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageList.size());
        viewPager.setCurrentItem(item);

        //把第一个小圆点设置成白色，显示第一个TExtView内容
        dotGroup.getChildAt(previousPosition).setEnabled(true);
        //设置viewPager滑动监听事件
        viewPager.addOnPageChangeListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isSwitchPager) {
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();

    }

    private void initViewPagerData() {
        imageList = new ArrayList<>();
        ImageView im;
        View dotView;
        for (int i = 0; i < imageUrl.length; i++) {
            im = new ImageView(mContext);
            GlideApp.with(this).load(imageUrl[i])
                    .centerCrop()
                    .into(im);
            imageList.add(im);
            //准备小圆点数据
            dotView = new View(mContext);
            dotView.setBackgroundResource(R.drawable.view_dot);
            //设置小圆点宽和高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            //设置每个小圆点之间的距离
            if (i != 0) {
                params.leftMargin = 15;
            }
            dotView.setLayoutParams(params);
            //设置小圆点状态
            dotView.setEnabled(false);
            //把dotView加入到线性布局中
            dotGroup.addView(dotView);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newPostion = position % imageList.size();
        //取出postion的位置小圆点设置为true
        dotGroup.getChildAt(newPostion).setEnabled(true);
        //把一个小圆点设置为false
        dotGroup.getChildAt(previousPosition).setEnabled(false);
        previousPosition = newPostion;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyViewPager extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        //初始化每个条目要显示的内容
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int newPostion = position % imageList.size();
            //获取到条目要显示的内容imageView
            ImageView img = imageList.get(newPostion);
            container.addView(img);
            return img;
        }

        //是否复用当前view
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
