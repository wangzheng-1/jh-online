package com.xcompany.jhonline.widget;

import android.content.Context;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Category;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.ReleaseConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieliang on 2018/12/31 10:53
 */
public class TypeGridView {
    public View mView;
    private Context mContext;
    private List<Category> categories = new ArrayList<>();
    private String curCategory = "";
    private JHGridView grid;
    private boolean isCheckFirst;

    public TypeGridView(Context context, int type, int pid,boolean isCheckFirst) {
        mContext = context;
        this.isCheckFirst = isCheckFirst;
        mView = View.inflate(context, R.layout.view_type_grid, null);
        getMenuData(type, pid);
    }

    private void getMenuData(int type, int pid) {
        OkGo.<JHResponse<List<Category>>>post(ReleaseConfig.baseUrl() + "class/classList")
                .tag(this)
                .params("type", type)
                .params("pid", pid)
                .execute(new JHCallback<JHResponse<List<Category>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Category>>> response) {
                        categories = response.body().getMsg();
                        initView();
                    }
                });
    }

    public String getCurCategory() {
        return curCategory;
    }

    private void initView() {
        grid = mView.findViewById(R.id.grid);
        grid.setMdatas(categories, curCategory);
        grid.setCheckChangeListener(category -> {
            curCategory = category.getId();
            grid.setCurrentId(curCategory);
            if (mListener != null) mListener.onItemClick(category);
        });
        if(isCheckFirst){
            Category category = categories.get(0);
            curCategory = category.getId();
            grid.setCurrentId(curCategory);
            if (mListener != null) mListener.onItemClick(category);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Category category);
    }
}
