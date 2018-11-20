package com.xcompany.jhonline.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;


/**
 * Created by xieliang on 2018/11/20 22:56
 */
public class HomeRadioButtonView extends RelativeLayout {

    private TextView tv_title;//标题
    private ImageView imageView;//图标
    private TextView tv_cicle;

    private int image_normal;
    private int image_press;
    private int color_normal;
    private int colro_press;
    private String title;
    private Boolean checked;
    private String figure;
    private Boolean showCicle;

    public HomeRadioButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    /**
     * 带有两个参数的构造方法，布局文件使用的时候调用
     *
     * @param context
     * @param attrs
     */
    public HomeRadioButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.home_radio_button);
        image_normal = array.getResourceId(R.styleable.home_radio_button_image_normal, -1);
        image_press = array.getResourceId(R.styleable.home_radio_button_image_press, -1);
        color_normal = array.getColor(R.styleable.home_radio_button_color_normal, Color.TRANSPARENT);
        colro_press = array.getColor(R.styleable.home_radio_button_color_press, Color.TRANSPARENT);
        title = array.getString(R.styleable.home_radio_button_button_title);
        figure = array.getString(R.styleable.home_radio_button_figure);
        showCicle = array.getBoolean(R.styleable.home_radio_button_showCicle, false);
        checked = array.getBoolean(R.styleable.home_radio_button_checked, false);
        tv_title.setText(title == null ? "" : title);
        if (checked) {
            setPress();
        } else {
            setNormal();
        }
    }

    public HomeRadioButtonView(Context context) {
        super(context);
        initView(context);//初始化布局
    }

    /**
     * 初始化布局文件
     *
     * @param context
     */
    private void initView(Context context) {
        View.inflate(context, R.layout.view_home_radio_button, this);
        tv_title = findViewById(R.id.title);
        imageView = findViewById(R.id.image);
        tv_cicle = findViewById(R.id.tv_cicle);
        tv_cicle.setVisibility(GONE);
    }

    public void setNormal() {
        if (image_normal != -1) imageView.setImageResource(image_normal);
        tv_title.setTextColor(color_normal);
    }

    public void setPress() {
        if (image_press != -1) imageView.setImageResource(image_press);
        tv_title.setTextColor(colro_press);
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setCicleText(String text) {
        figure = text;
        tv_cicle.setText(text);
    }

    public void showCicle(Boolean show) {
        showCicle = show;
        tv_cicle.setVisibility(show ? VISIBLE : GONE);
    }

    public void setChecked(Boolean flag) {
        checked = flag;
        if (checked) {
            setPress();
        } else {
            setNormal();
        }
    }

}