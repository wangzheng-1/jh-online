package com.xcompany.jhonline.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;

/**
 * Created by xieliang on 2018/12/1 20:33
 */
public class TypeButton2 extends LinearLayout {
    TextView tvTitle;
    ImageView ivOpt;
    LinearLayout llChild;
    LinearLayout llParent;

    private String title;
    private Boolean checked;
    private int image_normal = R.drawable.arrow4;
    private int image_press = R.drawable.arrow3;

    public TypeButton2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }


    public TypeButton2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.type_button);
        checked = array.getBoolean(R.styleable.type_button_type_button_checked, false);
        title = array.getString(R.styleable.type_button_type_button_title);
        array.recycle();

        View.inflate(context, R.layout.view_publish_type2, this);
        tvTitle = findViewById(R.id.tv_title);
        ivOpt = findViewById(R.id.iv_opt);
        llChild = findViewById(R.id.ll_child);
        llParent = findViewById(R.id.ll_parent);
        tvTitle.setText(title);
        llParent.setOnClickListener(v -> {
            if (checked) {
                setChecked(false);
            } else {
                setChecked(true);
            }
        });
        if (checked) {
            setPress();
        } else {
            setNormal();
        }
    }

    public void setTitle(String title) {
        this.title = title;
        tvTitle.setText(title);
    }

    private void setNormal() {
        ivOpt.setImageResource(image_normal);
        llChild.setVisibility(GONE);
    }

    private void setPress() {
        ivOpt.setImageResource(image_press);
        llChild.setVisibility(VISIBLE);
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
        if (checked) {
            setPress();
        } else {
            setNormal();
        }
    }

    public void addChild(View view) {
        llChild.addView(view);
    }
}
