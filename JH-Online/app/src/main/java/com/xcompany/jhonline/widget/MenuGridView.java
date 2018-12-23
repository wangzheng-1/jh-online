package com.xcompany.jhonline.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.Dictionary;
import com.xcompany.jhonline.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/12/01 20:56
 */
public class MenuGridView extends GridView {
    private Myadapter adapter;
    private List<Dictionary> dictionaries = new ArrayList<>();
    private int currentIndex = 0;

    public MenuGridView(Context context) {
        super(context);
        init();

    }

    public MenuGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MenuGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private void init() {
        setCacheColorHint(getResources().getColor(android.R.color.transparent));
        setHorizontalSpacing(DensityUtils.dp2px(getContext(), 15));
        setVerticalSpacing(DensityUtils.dp2px(getContext(), 20));
        setNumColumns(3);
        setStretchMode(STRETCH_COLUMN_WIDTH);
        setSelector(R.color.transparent);
        adapter = new Myadapter();
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentIndex = position;
                adapter.notifyDataSetChanged();
                if (mListener != null) mListener.onCheckChange(dictionaries.get(position));
            }
        });
        setAdapter(adapter);
    }

    public void setDictionaries(List<Dictionary> dictionaries, int index) {
        this.dictionaries = dictionaries;
        this.currentIndex = index;
        adapter.notifyDataSetChanged();
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        adapter.notifyDataSetChanged();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    private class Myadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return dictionaries.size();
        }

        @Override
        public Object getItem(int position) {
            return dictionaries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_menu_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvTitle.setText(dictionaries.get(position).getName());
            if (currentIndex == position) {
                holder.menuItem.setBackground(getResources().getDrawable(R.drawable.bg_menu2));
                holder.tvTitle.setTextColor(getResources().getColor(R.color.white));
            } else {
                holder.menuItem.setBackground(getResources().getDrawable(R.drawable.bg_menu));
                holder.tvTitle.setTextColor(getResources().getColor(R.color.color333333));
            }
            return convertView;
        }

    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.menu_item)
        LinearLayout menuItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setCheckChangeListener(OnCheckChangeListener mListener) {
        this.mListener = mListener;
    }

    private OnCheckChangeListener mListener;

    public interface OnCheckChangeListener {
        void onCheckChange(Dictionary dictionary);
    }
}
