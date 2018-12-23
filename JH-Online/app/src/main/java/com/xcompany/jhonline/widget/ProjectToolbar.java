package com.xcompany.jhonline.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;

/**
 * Created by xieliang on 2018/11/25 10:39
 */
public class ProjectToolbar extends RelativeLayout implements View.OnClickListener {
    private ImageView toolbarBack;
    private TextView toolbarTitle;
    private TextView toolbarRight;
    private View mToolbar;
    private String toolbar_title;
    private String toolbar_right;
    private int toolbar_right_mode;
    private OnRightClickListener onRightClickListener;

    public ProjectToolbar(Context context) {
        this(context, null);
    }

    public ProjectToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProjectToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mToolbar = View.inflate(context, R.layout.toolbar_base, this);
        toolbarBack = mToolbar.findViewById(R.id.toolbar_back);
        toolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        toolbarRight = mToolbar.findViewById(R.id.toolbar_right);
        toolbarBack.setOnClickListener(this);
        toolbarRight.setOnClickListener(this);

        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.ProjectToolbar);
        toolbar_title = attributes.getString(R.styleable.ProjectToolbar_toolbar_title);
        toolbar_right = attributes.getString(R.styleable.ProjectToolbar_toolbar_right);
        toolbar_right_mode = attributes.getInteger(R.styleable.ProjectToolbar_toolbar_right_mode, 1);
        init();
    }

    private void init() {
        if (toolbar_title != null) toolbarTitle.setText(toolbar_title);
        if (toolbar_right != null) toolbarRight.setText(toolbar_right);
        switch (toolbar_right_mode) {
            case 0://none
                toolbarBack.setVisibility(GONE);
                toolbarRight.setVisibility(GONE);
                break;
            case 1://black
                toolbarBack.setVisibility(VISIBLE);
                toolbarRight.setVisibility(VISIBLE);
                toolbarRight.setTextColor(getResources().getColor(R.color.color333333));
                break;
            case 2://blue
                toolbarBack.setVisibility(VISIBLE);
                toolbarRight.setVisibility(VISIBLE);
                toolbarRight.setTextColor(getResources().getColor(R.color.color0072FF));
                break;
            case 3://left-black
                toolbarBack.setVisibility(VISIBLE);
                toolbarRight.setVisibility(GONE);
                toolbarRight.setTextColor(getResources().getColor(R.color.color333333));
                break;
            case 4://left-blue
                toolbarBack.setVisibility(VISIBLE);
                toolbarRight.setVisibility(GONE);
                toolbarRight.setTextColor(getResources().getColor(R.color.color0072FF));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                ((Activity) getContext()).finish();
                break;
            case R.id.toolbar_right:
                if (onRightClickListener != null) onRightClickListener.onClick(v);
                break;
        }
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    public interface OnRightClickListener {
        void onClick(View v);
    }

    public String getToolbar_title() {
        return toolbar_title;
    }

    public void setToolbar_title(String toolbar_title) {
        this.toolbar_title = toolbar_title;
        if (toolbar_title != null) toolbarTitle.setText(toolbar_title);
    }

    public String getToolbar_right() {
        return toolbar_right;
    }

    public void setToolbar_right(String toolbar_right) {
        this.toolbar_right = toolbar_right;
        if (toolbar_right != null) toolbarRight.setText(toolbar_right);
    }

    public int getToolbar_button_mode() {
        return toolbar_right_mode;
    }

    public void setToolbar_button_mode(int toolbar_button_mode) {
        if (toolbar_button_mode < 0) toolbar_button_mode = 0;
        if (toolbar_button_mode > 2) toolbar_button_mode = 1;
        this.toolbar_right_mode = toolbar_button_mode;
        init();
    }
}
