package com.xcompany.jhonline.module.home.labourWorker.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.model.home.Dictionary;
import com.xcompany.jhonline.model.home.MusicHotKey;
import com.xcompany.jhonline.module.home.labourWorker.activity.HiringDetailActivity;
import com.xcompany.jhonline.module.home.labourWorker.adapter.HiringAdapter;
import com.xcompany.jhonline.network.ApiResponse;
import com.xcompany.jhonline.network.JsonCallback;
import com.xcompany.jhonline.utils.DensityUtils;
import com.xcompany.jhonline.widget.MenuButton;
import com.xcompany.jhonline.widget.MenuGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/11/28 23:02
 */
public class HiringFragment extends BaseFragment {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.menu1)
    MenuButton menu1;
    @BindView(R.id.menu2)
    MenuButton menu2;
    private HiringAdapter mAdapter;
    private List<String> mdatas = new ArrayList<>();
    String url = "https://c.y.qq.com/splcloud/fcgi-bin/gethotkey.fcg";
    private PopupWindow window;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hiring;
    }

    @Override
    protected void initEventAndData() {
        for (int i = 0; i < 20; i++) {
            mdatas.add("蓝色" + i);
        }
        mAdapter = new HiringAdapter(mContext, mdatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        View view = View.inflate(mContext, R.layout.view_divide_10, null);
        mRecyclerView.addHeaderView(view);
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
        mAdapter.setOnItemClickListener(new HiringAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String bean, RecyclerView.ViewHolder holder) {
                Intent intent = new Intent(mContext, HiringDetailActivity.class);
                startActivity(intent);
            }
        });
        initMuenuData();
    }

    List<Dictionary> lwgz = new ArrayList<>();
    private int curLwgz;

    private void initMuenuData() {
        lwgz.add(new Dictionary("1", "木磨工"));
        lwgz.add(new Dictionary("2", "架子工"));
        lwgz.add(new Dictionary("3", "钢筋工"));
        lwgz.add(new Dictionary("4", "泥瓦工"));
        lwgz.add(new Dictionary("5", "水电工"));
        lwgz.add(new Dictionary("6", "电焊工"));
        lwgz.add(new Dictionary("7", "铝模工"));
        lwgz.add(new Dictionary("8", "杂工"));
        lwgz.add(new Dictionary("9", "电工"));
        lwgz.add(new Dictionary("10", "油漆工"));
        lwgz.add(new Dictionary("11", "机械操作工"));
        lwgz.add(new Dictionary("12", "塔司信号工"));
        lwgz.add(new Dictionary("13", "其他"));
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

    @OnClick({R.id.menu1, R.id.menu2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu1:
                if (window == null) {
                    initPopuptWindow();
                } else if (window.isShowing()) {
                    window.dismiss();
                } else {
                    window.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                            0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
                    menu1.setChecked(true);
                }
                break;
            case R.id.menu2:
                break;
        }
    }

    public void initPopuptWindow() {
        View view = View.inflate(mContext, R.layout.view_renting_menu,
                null);
        MenuGridView grid = view.findViewById(R.id.menu_grid);
        grid.setDictionaries(lwgz, curLwgz);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 200);
        view.setLayoutParams(params);
        LinearLayout layout = new LinearLayout(mContext);
        layout.addView(view);
        layout.setBackgroundColor(Color.argb(120, 0, 0, 0));
        window = new PopupWindow(layout, DensityUtils.getScreenW(mContext), DensityUtils.getScreenHnobar(mContext, -134));
        window.setFocusable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        layout.setOnClickListener(v -> window.dismiss());
        window.setOnDismissListener(() -> menu1.setChecked(false));
        window.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
        menu1.setChecked(true);
    }

}
