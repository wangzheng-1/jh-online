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
public class MenuButton extends LinearLayout {
    private TextView tvTitle;
    private ImageView ivImage;
    private String title;
    private int image_normal;
    private int image_press;
    private int color_normal;
    private int color_press;
    private Boolean checked;

    public MenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }


    public MenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.menu_button);
        image_normal = array.getResourceId(R.styleable.menu_button_menu_button_image_normal, R.drawable.down);
        image_press = array.getResourceId(R.styleable.menu_button_menu_button_image_press, R.drawable.up);
        color_normal = array.getColor(R.styleable.menu_button_menu_button_color_normal, getContext().getResources().getColor(R.color.color333333));
        color_press = array.getColor(R.styleable.menu_button_menu_button_color_press, getContext().getResources().getColor(R.color.color0072FF));
        checked = array.getBoolean(R.styleable.menu_button_menu_button_checked, false);

        title = array.getString(R.styleable.menu_button_menu_button_title);
        int resource = array.getResourceId(R.styleable.menu_button_menu_button_resource, R.layout.view_menu_button);
        array.recycle();

        View.inflate(context, resource, this);
        tvTitle = findViewById(R.id.title);
        ivImage = findViewById(R.id.image);
        tvTitle.setText(title);
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
        tvTitle.setTextColor(color_normal);
        ivImage.setImageResource(image_normal);
    }

    private void setPress() {
        tvTitle.setTextColor(color_press);
        ivImage.setImageResource(image_press);
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
        if (checked) {
            setPress();
        } else {
            setNormal();
        }
    }
}
