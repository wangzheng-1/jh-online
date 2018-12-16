package com.xcompany.jhonline.module.home.subcontract.fragment;

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
import com.xcompany.jhonline.module.home.subcontract.activity.QualityTeamDetailActivity;
import com.xcompany.jhonline.module.home.subcontract.adapter.QualityTeamAdapter;
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
public class QualityTeamFragment extends BaseFragment {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.menu1)
    MenuButton menu1;
    @BindView(R.id.menu2)
    MenuButton menu2;
    @BindView(R.id.menu3)
    MenuButton menu3;
    @BindView(R.id.menu4)
    MenuButton menu4;
    private QualityTeamAdapter mAdapter;
    private List<String> mdatas = new ArrayList<>();
    String url = "https://c.y.qq.com/splcloud/fcgi-bin/gethotkey.fcg";
    private PopupWindow window;
    private PopupWindow window2;
    private PopupWindow window3;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quality_team;
    }

    @Override
    protected void initEventAndData() {
        for (int i = 0; i < 20; i++) {
            mdatas.add("蓝色" + i);
        }
        mAdapter = new QualityTeamAdapter(mContext, mdatas);
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
        mAdapter.setOnItemClickListener(new QualityTeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String bean, RecyclerView.ViewHolder holder) {
                Intent intent = new Intent(mContext, QualityTeamDetailActivity.class);
                startActivity(intent);
            }
        });
        initMuenuData();
    }

    List<Dictionary> txgc = new ArrayList<>();
    private int curTxgc;
    List<Dictionary> zyjs = new ArrayList<>();
    private int curZyjs;
    List<Dictionary> fxlw = new ArrayList<>();
    private int curFxlw;
    List<Dictionary> sbaz = new ArrayList<>();
    private int curSbaz;

    private void initMuenuData() {
        //特殊工程
        txgc.add(new Dictionary("1", "地基工程类"));
        txgc.add(new Dictionary("2", "土方工程类"));
        txgc.add(new Dictionary("3", "圆建绿化类"));
        txgc.add(new Dictionary("4", "管网工程类"));
        txgc.add(new Dictionary("5", "钢结构工程"));
        txgc.add(new Dictionary("6", "人防工程类"));
        txgc.add(new Dictionary("7", "消防工程类"));
        //专业技术
        zyjs.add(new Dictionary("1", "防水"));
        zyjs.add(new Dictionary("2", "爆破"));
        zyjs.add(new Dictionary("3", "幕墙"));
        zyjs.add(new Dictionary("4", "环氧地坪"));
        zyjs.add(new Dictionary("5", "干挂石材类"));
        zyjs.add(new Dictionary("6", "GRC/罗马柱"));
        zyjs.add(new Dictionary("7", "护坡/档口"));
        //分项劳务
        fxlw.add(new Dictionary("1", "劳务公司"));
        fxlw.add(new Dictionary("2", "木工班组"));
        fxlw.add(new Dictionary("3", "钢筋班组"));
        fxlw.add(new Dictionary("4", "泥水班组"));
        fxlw.add(new Dictionary("5", "砼工班组"));
        fxlw.add(new Dictionary("6", "外架班组"));
        fxlw.add(new Dictionary("7", "水电班组"));
        fxlw.add(new Dictionary("8", "油漆涂料"));
        //设备安装
        sbaz.add(new Dictionary("1", "水暖管道安装"));
        sbaz.add(new Dictionary("2", "电梯安装工程"));
        sbaz.add(new Dictionary("3", "护栏门窗安装"));
        sbaz.add(new Dictionary("4", "智能弱点工程"));
        sbaz.add(new Dictionary("5", "制冷工程安装"));
        sbaz.add(new Dictionary("6", "电气工程安装"));
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

    @OnClick({R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4})
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
            case R.id.menu3:
                if (window2 == null) {
                    initPopuptWindow2();
                } else if (window2.isShowing()) {
                    window2.dismiss();
                } else {
                    window2.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                            0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
                    menu3.setChecked(true);
                }
                break;
            case R.id.menu4:
                if (window3 == null) {
                    initPopuptWindow3();
                } else if (window3.isShowing()) {
                    window3.dismiss();
                } else {
                    window3.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                            0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
                    menu4.setChecked(true);
                }
                break;
        }
    }

    public void initPopuptWindow() {
        View view = View.inflate(mContext, R.layout.view_subcontract_menu,
                null);
        initPopView(view);
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

    private void initPopView(View view) {
        initMenuButton(view, R.id.txgc, R.id.txgc_child, txgc, curTxgc);
        initMenuButton(view, R.id.zyjs, R.id.zyjs_child, zyjs, curZyjs);
        initMenuButton(view, R.id.fxlw, R.id.fxlw_child, fxlw, curFxlw);
        initMenuButton(view, R.id.sbaz, R.id.sbaz_child, sbaz, curSbaz);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 200);
        view.setLayoutParams(params);
    }

    private void initMenuButton(View view, int btn, int child, List<Dictionary> data, int current) {
        MenuButton button = view.findViewById(btn);
        MenuGridView grid = view.findViewById(child);
        grid.setDictionaries(data, current);
        button.setOnClickListener(v -> {
            if (grid.getVisibility() == View.VISIBLE) {
                grid.setVisibility(View.GONE);
                button.setChecked(false);
            } else {
                grid.setVisibility(View.VISIBLE);
                button.setChecked(true);
            }
        });

    }

    private int curCredit;

    public void initPopuptWindow2() {
        View view = View.inflate(mContext, R.layout.view_credit_menu,
                null);
        MenuButton unlimited = view.findViewById(R.id.unlimited);
        MenuButton desc = view.findViewById(R.id.desc);
        MenuButton asc = view.findViewById(R.id.asc);
        unlimited.setOnClickListener(v -> {
            unlimited.setChecked(true);
            desc.setChecked(false);
            asc.setChecked(false);
            curCredit = 0;
        });

        desc.setOnClickListener(v -> {
            unlimited.setChecked(false);
            desc.setChecked(true);
            asc.setChecked(false);
            curCredit = 1;
        });

        asc.setOnClickListener(v -> {
            unlimited.setChecked(false);
            desc.setChecked(false);
            asc.setChecked(true);
            curCredit = 2;
        });
        LinearLayout layout = new LinearLayout(mContext);
        layout.addView(view);
        layout.setBackgroundColor(Color.argb(120, 0, 0, 0));
        window2 = new PopupWindow(layout, DensityUtils.getScreenW(mContext), DensityUtils.getScreenHnobar(mContext, -134));
        window2.setFocusable(true);
        window2.setBackgroundDrawable(new BitmapDrawable());
        window2.setOutsideTouchable(true);
        layout.setOnClickListener(v -> window2.dismiss());
        window2.setOnDismissListener(() -> menu3.setChecked(false));
        window2.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
        menu3.setChecked(true);
    }

    private int curAdvance;

    public void initPopuptWindow3() {
        View view = View.inflate(mContext, R.layout.view_advance_fund_menu,
                null);
        MenuButton advance_yes = view.findViewById(R.id.advance_yes);
        MenuButton advance_no = view.findViewById(R.id.advance_no);
        advance_yes.setOnClickListener(v -> {
            advance_yes.setChecked(true);
            advance_no.setChecked(false);
            curAdvance = 0;
        });

        advance_no.setOnClickListener(v -> {
            advance_yes.setChecked(false);
            advance_no.setChecked(true);
            curAdvance = 1;
        });
        LinearLayout layout = new LinearLayout(mContext);
        layout.addView(view);
        layout.setBackgroundColor(Color.argb(120, 0, 0, 0));
        window3 = new PopupWindow(layout, DensityUtils.getScreenW(mContext), DensityUtils.getScreenHnobar(mContext, -134));
        window3.setFocusable(true);
        window3.setBackgroundDrawable(new BitmapDrawable());
        window3.setOutsideTouchable(true);
        layout.setOnClickListener(v -> window3.dismiss());
        window3.setOnDismissListener(() -> menu4.setChecked(false));
        window3.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
        menu4.setChecked(true);
    }
}
