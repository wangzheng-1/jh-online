package com.xcompany.jhonline.module.home.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideImageLoader;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.model.base.Address;
import com.xcompany.jhonline.model.base.SearchResult;
import com.xcompany.jhonline.model.home.BannerImages;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.model.home.Hiring;
import com.xcompany.jhonline.module.home.blacklist.activity.BlackListActivity;
import com.xcompany.jhonline.module.home.blacklist.activity.BlackListDetailActivity;
import com.xcompany.jhonline.module.home.buildMaterial.activity.BuildMaterialList;
import com.xcompany.jhonline.module.home.buildMaterial.activity.PurchaseDetailActivity;
import com.xcompany.jhonline.module.home.buildMaterial.activity.QualitySupplierDetailActivity;
import com.xcompany.jhonline.module.home.card.activity.AddCardActivity;
import com.xcompany.jhonline.module.home.certificate.acitivity.CertificateList;
import com.xcompany.jhonline.module.home.certificate.acitivity.QualificationHandleDetailActivity;
import com.xcompany.jhonline.module.home.certificate.acitivity.RegistrationDetailActivity;
import com.xcompany.jhonline.module.home.equipment.activity.EquipmentList;
import com.xcompany.jhonline.module.home.equipment.activity.RentSeekingDetailActivity;
import com.xcompany.jhonline.module.home.equipment.activity.RentingDetailActivity;
import com.xcompany.jhonline.module.home.labourWorker.activity.HiringDetailActivity;
import com.xcompany.jhonline.module.home.labourWorker.activity.LabourWorkerList;
import com.xcompany.jhonline.module.home.professionalSkills.activity.ProfessionalSkillsDetailActivity;
import com.xcompany.jhonline.module.home.professionalSkills.activity.ProfessionalSkillsList;
import com.xcompany.jhonline.module.home.siteMatching.activity.SiteMatchingDetailActivity;
import com.xcompany.jhonline.module.home.siteMatching.activity.SiteMatchingList;
import com.xcompany.jhonline.module.home.subcontract.activity.QualityTeamDetailActivity;
import com.xcompany.jhonline.module.home.subcontract.activity.SubcontractList;
import com.xcompany.jhonline.module.home.subcontract.activity.TenderDetailActivity;
import com.xcompany.jhonline.module.home.technical.activity.TechnicalList;
import com.xcompany.jhonline.module.me.activity.MeCollectListActivity;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.SharedPreferenceUtil;
import com.xcompany.jhonline.utils.SnCalUtils;
import com.xcompany.jhonline.utils.T;
import com.youth.banner.Banner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/11/21 11:47
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.search_list)
    ListView searchList;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    private View headView;
    private Banner banner;
    private HomeListAdapter mAdapter;
    private List<Hiring> mdatas = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private String cityId;
    private SearchAdapter searchAdapter;
    private List<SearchResult> searchResults = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (123 == msg.what) {
                String trim = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    searchResults.clear();
                    searchAdapter.notifyDataSetChanged();
                } else {
                    OkGo.<JHResponse<List<SearchResult>>>post(ReleaseConfig.baseUrl() + "index/search")
                            .tag(this)
                            .params("telename", etSearch.getText().toString().trim())
                            .execute(new JHCallback<JHResponse<List<SearchResult>>>() {
                                @Override
                                public void onSuccess(Response<JHResponse<List<SearchResult>>> response) {
                                    List<SearchResult> list = response.body().getMsg();
                                    if (list != null) {
                                        searchResults = list;
                                        searchAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onError(Response<JHResponse<List<SearchResult>>> response) {
                                    T.showToast(mContext, "暂无搜索结果！");
                                    searchResults.clear();
                                    searchAdapter.notifyDataSetChanged();
                                }
                            });
                }
            }

        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(123);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEventAndData() {
        listView = mView.findViewById(R.id.list_view);
        tvCity = mView.findViewById(R.id.tv_city);
        mAdapter = new HomeListAdapter(mContext, mdatas);
        listView.setAdapter(mAdapter);
        headView = View.inflate(mContext, R.layout.view_home_head, null);
        View footView = View.inflate(mContext, R.layout.view_hoem_footer, null);
        footView.findViewById(R.id.tv_more).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, LabourWorkerList.class);
            startActivity(intent);
        });
        initHeadView();
        listView.addHeaderView(headView);
        listView.addFooterView(footView);
        listView.setOnItemClickListener(((parent, view, position, id) -> {
            Intent intent = new Intent(mContext, HiringDetailActivity.class);
            intent.putExtra("id", mdatas.get(position - 1).getId());
            startActivity(intent);
        }));
        getCiry("惠州市");
        searchAdapter = new SearchAdapter();
        searchList.setOnItemClickListener((parent, view, position, id) -> {
            SearchResult searchResult = searchResults.get(position);
            String type = searchResult.getType();
            Intent intent = new Intent();
            if (type != null) {
                if ("0".equals(type)) {
                    intent.setClass(mContext, QualityTeamDetailActivity.class);
                } else if ("1".equals(type)) {
                    intent.setClass(mContext, TenderDetailActivity.class);
                } else if ("2".equals(type)) {
                    intent.setClass(mContext, RentingDetailActivity.class);

                } else if ("3".equals(type)) {
                    intent.setClass(mContext, RentSeekingDetailActivity.class);

                } else if ("4".equals(type)) {
                    intent.setClass(mContext, QualitySupplierDetailActivity.class);

                } else if ("5".equals(type)) {
                    intent.setClass(mContext, PurchaseDetailActivity.class);

                } else if ("6".equals(type)) {
                    intent.setClass(mContext, RegistrationDetailActivity.class);

                } else if ("7".equals(type)) {
                    intent.setClass(mContext, QualificationHandleDetailActivity.class);

                } else if ("8".equals(type)) {
                    intent.setClass(mContext, HiringDetailActivity.class);

                } else if ("9".equals(type)) {
                    T.showToast(mContext, "无详情页面");
                    return;

                } else if ("10".equals(type)) {
                    T.showToast(mContext, "无详情页面");
                    return;

                } else if ("11".equals(type)) {
                    T.showToast(mContext, "无详情页面");
                    return;

                } else if ("12".equals(type)) {
                    intent.setClass(mContext, SiteMatchingDetailActivity.class);

                } else if ("13".equals(type)) {
                    intent.setClass(mContext, ProfessionalSkillsDetailActivity.class);
                }else {
                    T.showToast(mContext, "无详情页面");
                    return;
                }
            }
            llSearch.setVisibility(View.GONE);
            intent.putExtra("id", searchResult.getFid());
            startActivity(intent);
        });
        searchList.setAdapter(searchAdapter);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mHandler.removeCallbacks(mRunnable);
                //500毫秒没有输入认为输入完毕
                mHandler.postDelayed(mRunnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            int touch_flag = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touch_flag++;
                if (touch_flag == 2) {
                    //自己业务
                    llSearch.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        OkGo.<String>post("http://pv.sohu.com/cityjson?ie=utf-8")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        try {
                            if (!TextUtils.isEmpty(body)) {
                                int n = body.indexOf("{");
                                int m = body.indexOf("}");
                                if (n > -1 && m > n) {
                                    String substring = body.substring(n, m + 1);
                                    JSONObject jsonObject = new JSONObject(substring);
                                    String cip = jsonObject.getString("cip");
                                    if (!TextUtils.isEmpty(cip)) {
                                        String snkey = SnCalUtils.getSnkey(cip);
                                        Map paramsMap = new LinkedHashMap<String, String>();
                                        paramsMap.put("ip", cip);
                                        paramsMap.put("ak", "tKlirU8a30iVYnGyGh4ah9esteZRBkZy");
                                        paramsMap.put("sn", snkey);
                                        OkGo.<String>get("https://api.map.baidu.com/location/ip")
                                                .tag(this)
                                                .params(paramsMap)
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(Response response) {
                                                        String s = response.body().toString();
                                                        Gson gson = new Gson();
                                                        Address address = gson.fromJson(s, Address.class);
                                                        String city = address.getContent().getAddress_detail().getCity();
                                                        getCiry(city);
                                                    }

                                                    @Override
                                                    public void onError(Response response) {
                                                        super.onError(response);
                                                        T.showToast(mContext, "定位失败，无法通过外网IP定位当前城市");
                                                        Log.i("定位失败", response.getException().getMessage());
                                                    }
                                                });
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T.showToast(mContext, "定位失败，无法获取外网IP");
                        Log.i("定位失败", response.getException().getMessage());
                    }
                });
    }

    public void getCiry(String cityName) {
        tvCity.setText(cityName);
        OkGo.<JHResponse<City>>post(ReleaseConfig.baseUrl() + "index/getCity")
                .tag(this)
                .params("name", cityName)
                .execute(new JHCallback<JHResponse<City>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<City>> response) {

                        City city = response.body().getMsg();
                        cityId = city.getId();
                        SharedPreferenceUtil.setCityId(mContext, city.getId());
                        SharedPreferenceUtil.setCityName(mContext, city.getName());
                        getData();
                    }

                    @Override
                    public void onError(Response<JHResponse<City>> response) {
//                        T.showToast(mContext, response.getException().getMessage());
                    }
                });
    }

    public void getBannerImages() {
        OkGo.<JHResponse<BannerImages>>post(ReleaseConfig.baseUrl() + "index/Carousel")
                .tag(this)
                .params("id", 1)
                .params("port", 3)
                .execute(new JHCallback<JHResponse<BannerImages>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<BannerImages>> response) {
                        BannerImages bannerImages = response.body().getMsg();
                        images = bannerImages.getImages();
                        banner.setImages(images);
                        banner.start();
                    }

                    @Override
                    public void onError(Response<JHResponse<BannerImages>> response) {
//                        T.showToast(mContext, response.getException().getMessage());
                    }
                });

    }

    public void getData() {
        OkGo.<JHResponse<List<Hiring>>>post(ReleaseConfig.baseUrl() + "worker/workerList")
                .tag(this)
                .params("Cityid", cityId)
                .params("type", 0)
                .execute(new JHCallback<JHResponse<List<Hiring>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Hiring>>> response) {
                        List<Hiring> list = response.body().getMsg();
                        if (list.size() > 5) {
                            list = list.subList(0, 5);
                        }
                        mdatas = list;
                        mAdapter.setMdatas(mdatas);
                    }

                    @Override
                    public void onError(Response<JHResponse<List<Hiring>>> response) {
//                        T.showToast(mContext, response.getException().getMessage());
                    }
                });
    }

    public void initHeadView() {
        headView.findViewById(R.id.entrance1).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SubcontractList.class);
            mContext.startActivity(intent);
        });
        headView.findViewById(R.id.entrance2).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EquipmentList.class);
            mContext.startActivity(intent);
        });
        headView.findViewById(R.id.entrance3).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BuildMaterialList.class);
            mContext.startActivity(intent);
        });
        headView.findViewById(R.id.entrance4).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CertificateList.class);
            mContext.startActivity(intent);
        });
        headView.findViewById(R.id.entrance5).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProfessionalSkillsList.class);
            mContext.startActivity(intent);
        });
        headView.findViewById(R.id.entrance6).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, LabourWorkerList.class);
            mContext.startActivity(intent);
        });
        headView.findViewById(R.id.entrance7).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, TechnicalList.class);
            mContext.startActivity(intent);
        });
        headView.findViewById(R.id.entrance8).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddCardActivity.class);
            mContext.startActivity(intent);
        });
        headView.findViewById(R.id.entrance9).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SiteMatchingList.class);
            mContext.startActivity(intent);
        });
        headView.findViewById(R.id.entrance10).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BlackListActivity.class);
            mContext.startActivity(intent);
        });
        banner = headView.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setDelayTime(10000);
        getBannerImages();
    }

    @OnClick({R.id.ll_city, R.id.et_search, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_city:
                Intent intent = new Intent(mContext, CityListActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.et_search:
                llSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_search:
                llSearch.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                View currentFocus = ((Activity) mContext).getCurrentFocus();
                if (currentFocus != null) {
                    imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1002) {
            City city = (City) data.getExtras().get("city");
            SharedPreferenceUtil.setCityName(mContext, city.getName());
            SharedPreferenceUtil.setCityId(mContext, city.getId());
            tvCity.setText(city.getName());
            cityId = city.getId();
            getData();
        }
    }


    private class SearchAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return searchResults.size();
        }

        @Override
        public Object getItem(int position) {
            return searchResults.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_search_result, null);
            }
            TextView tvText = convertView.findViewById(R.id.tv_text);
            tvText.setText(searchResults.get(position).getName());
            return convertView;
        }
    }
}
