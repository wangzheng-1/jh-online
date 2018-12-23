package com.xcompany.jhonline.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.xcompany.jhonline.R;

import java.util.ArrayList;

import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;
import cn.bingoogolapple.baseadapter.BGAGridDivider;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGARecyclerViewHolder;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.photopicker.imageloader.BGAImage;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import cn.bingoogolapple.photopicker.widget.BGAImageView;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE;

public class CleanEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    public boolean hasFoucs;

    public CleanEditText(Context context) {
        this(context, null);
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        // 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public CleanEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            // throw new
            // NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.mipmap.clean_content_btn);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 当我们按下的位置 在 EditText的宽度 -
     * 图标到控件右边的间距 - 图标的宽度 和 EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    public void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public static class MyBGASortableNinePhotoLayout extends RecyclerView implements BGAOnItemChildClickListener, BGAOnRVItemClickListener {
        private PhotoAdapter mPhotoAdapter;
        private ItemTouchHelper mItemTouchHelper;
        private Delegate mDelegate;
        private GridLayoutManager mGridLayoutManager;

        private boolean mPlusEnable;
        private boolean mSortable;
        private int mDeleteDrawableResId;
        private boolean mDeleteDrawableOverlapQuarter;
        private int mPhotoTopRightMargin;
        private int mMaxItemCount;
        private int mItemSpanCount;
        private int mPlusDrawableResId;
        private int mItemCornerRadius;
        private int mItemWhiteSpacing;
        private int mOtherWhiteSpacing;
        private int mPlaceholderDrawableResId;
        private boolean mEditable;

        private int mItemWidth;

        public MyBGASortableNinePhotoLayout(Context context) {
            this(context, null);
        }

        public MyBGASortableNinePhotoLayout(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public MyBGASortableNinePhotoLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initDefaultAttrs();
            initCustomAttrs(context, attrs);
            afterInitDefaultAndCustomAttrs();
        }

        private void initDefaultAttrs() {
            mPlusEnable = true;
            mSortable = true;
            mEditable = true;
            mDeleteDrawableResId = cn.bingoogolapple.photopicker.R.mipmap.bga_pp_ic_delete;
            mDeleteDrawableOverlapQuarter = false;
            mMaxItemCount = 9;
            mItemSpanCount = 3;
            mItemWidth = 0;
            mItemCornerRadius = 0;
            mPlusDrawableResId = cn.bingoogolapple.photopicker.R.mipmap.bga_pp_ic_plus;
            mItemWhiteSpacing = BGABaseAdapterUtil.dp2px(4);
            mPlaceholderDrawableResId = cn.bingoogolapple.photopicker.R.mipmap.bga_pp_ic_holder_light;
            mOtherWhiteSpacing = BGABaseAdapterUtil.dp2px(100);
        }

        private void initCustomAttrs(Context context, AttributeSet attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout);
            final int N = typedArray.getIndexCount();
            for (int i = 0; i < N; i++) {
                initCustomAttr(typedArray.getIndex(i), typedArray);
            }
            typedArray.recycle();
        }

        private void initCustomAttr(int attr, TypedArray typedArray) {
            if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_plusEnable) {
                mPlusEnable = typedArray.getBoolean(attr, mPlusEnable);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_sortable) {
                mSortable = typedArray.getBoolean(attr, mSortable);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_deleteDrawable) {
                mDeleteDrawableResId = typedArray.getResourceId(attr, mDeleteDrawableResId);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_deleteDrawableOverlapQuarter) {
                mDeleteDrawableOverlapQuarter = typedArray.getBoolean(attr, mDeleteDrawableOverlapQuarter);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_maxItemCount) {
                mMaxItemCount = typedArray.getInteger(attr, mMaxItemCount);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_itemSpanCount) {
                mItemSpanCount = typedArray.getInteger(attr, mItemSpanCount);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_plusDrawable) {
                mPlusDrawableResId = typedArray.getResourceId(attr, mPlusDrawableResId);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_itemCornerRadius) {
                mItemCornerRadius = typedArray.getDimensionPixelSize(attr, 0);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_itemWhiteSpacing) {
                mItemWhiteSpacing = typedArray.getDimensionPixelSize(attr, mItemWhiteSpacing);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_otherWhiteSpacing) {
                mOtherWhiteSpacing = typedArray.getDimensionPixelOffset(attr, mOtherWhiteSpacing);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_placeholderDrawable) {
                mPlaceholderDrawableResId = typedArray.getResourceId(attr, mPlaceholderDrawableResId);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_editable) {
                mEditable = typedArray.getBoolean(attr, mEditable);
            } else if (attr == cn.bingoogolapple.photopicker.R.styleable.BGASortableNinePhotoLayout_bga_snpl_itemWidth) {
                mItemWidth = typedArray.getDimensionPixelSize(attr, mItemWidth);
            }
        }

        private void afterInitDefaultAndCustomAttrs() {
            if (mItemWidth == 0) {
                mItemWidth = (BGAPhotoPickerUtil.getScreenWidth() - mOtherWhiteSpacing) / mItemSpanCount;
            } else {
                mItemWidth += mItemWhiteSpacing;
            }

            setOverScrollMode(OVER_SCROLL_NEVER);
            mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
            mItemTouchHelper.attachToRecyclerView(this);

            mGridLayoutManager = new GridLayoutManager(getContext(), mItemSpanCount);
            setLayoutManager(mGridLayoutManager);
            addItemDecoration(BGAGridDivider.newInstanceWithSpacePx(mItemWhiteSpacing / 2));

            calculatePhotoTopRightMargin();

            mPhotoAdapter = new PhotoAdapter(this);
            mPhotoAdapter.setOnItemChildClickListener(this);
            mPhotoAdapter.setOnRVItemClickListener(this);
            setAdapter(mPhotoAdapter);
        }

        /**
         * 设置是否可拖拽排序，默认值为 true
         *
         * @param sortable
         */
        public void setSortable(boolean sortable) {
            mSortable = sortable;
        }

        /**
         * 获取是否可拖拽排序
         *
         * @return
         */
        public boolean isSortable() {
            return mSortable;
        }

        /**
         * 设置是否可编辑，默认值为 true
         *
         * @param editable
         */
        public void setEditable(boolean editable) {
            mEditable = editable;
            mPhotoAdapter.notifyDataSetChanged();
        }

        /**
         * 获取是否可编辑
         *
         * @return
         */
        public boolean isEditable() {
            return mEditable;
        }

        /**
         * 设置删除按钮图片资源id，默认值为
         *
         * @param deleteDrawableResId
         */
        public void setDeleteDrawableResId(@DrawableRes int deleteDrawableResId) {
            mDeleteDrawableResId = deleteDrawableResId;
            calculatePhotoTopRightMargin();
        }

        /**
         * 设置删除按钮是否重叠四分之一，默认值为 false
         *
         * @param deleteDrawableOverlapQuarter
         */
        public void setDeleteDrawableOverlapQuarter(boolean deleteDrawableOverlapQuarter) {
            mDeleteDrawableOverlapQuarter = deleteDrawableOverlapQuarter;
            calculatePhotoTopRightMargin();
        }

        /**
         * 计算图片右上角 margin
         */
        private void calculatePhotoTopRightMargin() {
            if (mDeleteDrawableOverlapQuarter) {
                int deleteDrawableWidth = BitmapFactory.decodeResource(getResources(), mDeleteDrawableResId).getWidth();
                int deleteDrawablePadding = getResources().getDimensionPixelOffset(cn.bingoogolapple.photopicker.R.dimen.bga_pp_size_delete_padding);
                mPhotoTopRightMargin = deleteDrawablePadding + deleteDrawableWidth / 2;
            } else {
                mPhotoTopRightMargin = 0;
            }
        }

        /**
         * 设置可选择图片的总张数,默认值为 9
         *
         * @param maxItemCount
         */
        public void setMaxItemCount(int maxItemCount) {
            mMaxItemCount = maxItemCount;
        }

        /**
         * 获取选择的图片的最大张数
         *
         * @return
         */
        public int getMaxItemCount() {
            return mMaxItemCount;
        }

        /**
         * 设置列数,默认值为 3
         *
         * @param itemSpanCount
         */
        public void setItemSpanCount(int itemSpanCount) {
            mItemSpanCount = itemSpanCount;
            mGridLayoutManager.setSpanCount(mItemSpanCount);
        }

        /**
         * 设置添加按钮图片，默认值为 R.mipmap.bga_pp_ic_plus
         *
         * @param plusDrawableResId
         */
        public void setPlusDrawableResId(@DrawableRes int plusDrawableResId) {
            mPlusDrawableResId = plusDrawableResId;
        }

        /**
         * 设置 Item 条目圆角尺寸，默认值为 0dp
         *
         * @param itemCornerRadius
         */
        public void setItemCornerRadius(int itemCornerRadius) {
            mItemCornerRadius = itemCornerRadius;
        }

        /**
         * 设置图片路径数据集合
         *
         * @param photos
         */
        public void setData(ArrayList<String> photos) {
            mPhotoAdapter.setData(photos);
        }

        /**
         * 在集合尾部添加更多数据集合
         *
         * @param photos
         */
        public void addMoreData(ArrayList<String> photos) {
            if (photos != null) {
                mPhotoAdapter.getData().addAll(photos);
                mPhotoAdapter.notifyDataSetChanged();
            }
        }

        /**
         * 在集合的尾部添加一条数据
         *
         * @param photo
         */
        public void addLastItem(String photo) {
            if (!TextUtils.isEmpty(photo)) {
                mPhotoAdapter.getData().add(photo);
                mPhotoAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int spanCount = mItemSpanCount;
            int itemCount = mPhotoAdapter.getItemCount();
            if (itemCount > 0 && itemCount < mItemSpanCount) {
                spanCount = itemCount;
            }
            mGridLayoutManager.setSpanCount(spanCount);

            int expectWidth = mItemWidth * spanCount;
            int expectHeight = 0;
            if (itemCount > 0) {
                int rowCount = (itemCount - 1) / spanCount + 1;
                expectHeight = mItemWidth * rowCount;
            }

            int width = resolveSize(expectWidth, widthMeasureSpec);
            int height = resolveSize(expectHeight, heightMeasureSpec);
            width = Math.min(width, expectWidth);
            height = Math.min(height, expectHeight);

            setMeasuredDimension(width, height);
        }

        /**
         * 获取图片路径数据集合
         *
         * @return
         */
        public ArrayList<String> getData() {
            return (ArrayList<String>) mPhotoAdapter.getData();
        }

        /**
         * 删除指定索引位置的图片
         *
         * @param position
         */
        public void removeItem(int position) {
            mPhotoAdapter.removeItem(position);
        }

        /**
         * 获取图片总数
         *
         * @return
         */
        public int getItemCount() {
            return mPhotoAdapter.getData().size();
        }

        @Override
        public void onItemChildClick(ViewGroup parent, View childView, int position) {
            if (mDelegate != null) {
                mDelegate.onClickDeleteNinePhotoItem(this, childView, position, mPhotoAdapter.getItem(position), getData());
            }
        }

        @Override
        public void onRVItemClick(ViewGroup parent, View itemView, int position) {
            if (mPhotoAdapter.isPlusItem(position)) {
                if (mDelegate != null) {
                    mDelegate.onClickAddNinePhotoItem(this, itemView, position, getData());
                }
            } else {
                if (mDelegate != null && ViewCompat.getScaleX(itemView) <= 1.0f) {
                    mDelegate.onClickNinePhotoItem(this, itemView, position, mPhotoAdapter.getItem(position), getData());
                }
            }
        }

        /**
         * 设置是否显示加号
         *
         * @param plusEnable
         */
        public void setPlusEnable(boolean plusEnable) {
            mPlusEnable = plusEnable;
            mPhotoAdapter.notifyDataSetChanged();
        }

        /**
         * 是否显示加号按钮
         *
         * @return
         */
        public boolean isPlusEnable() {
            return mPlusEnable;
        }

        public void setDelegate(Delegate delegate) {
            mDelegate = delegate;
        }

        private class PhotoAdapter extends BGARecyclerViewAdapter<String> {
            private int mImageSize;

            public PhotoAdapter(RecyclerView recyclerView) {
                super(recyclerView, cn.bingoogolapple.photopicker.R.layout.bga_pp_item_nine_photo);
                mImageSize = BGAPhotoPickerUtil.getScreenWidth() / (mItemSpanCount > 3 ? 8 : 6);
            }

            @Override
            protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
                helper.setItemChildClickListener(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_flag);
            }

            @Override
            public int getItemCount() {
                if (mEditable && mPlusEnable && super.getItemCount() < mMaxItemCount) {
                    return super.getItemCount() + 1;
                }

                return super.getItemCount();
            }

            @Override
            public String getItem(int position) {
                if (isPlusItem(position)) {
                    return null;
                }

                return super.getItem(position);
            }

            public boolean isPlusItem(int position) {
                return mEditable && mPlusEnable && super.getItemCount() < mMaxItemCount && position == getItemCount() - 1;
            }

            @Override
            protected void fillData(BGAViewHolderHelper helper, int position, String model) {
                MarginLayoutParams params = (MarginLayoutParams) helper.getView(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_photo).getLayoutParams();
                params.setMargins(0, mPhotoTopRightMargin, mPhotoTopRightMargin, 0);

                if (mItemCornerRadius > 0) {
                    BGAImageView imageView = helper.getView(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_photo);
                    imageView.setCornerRadius(mItemCornerRadius);
                }

                if (isPlusItem(position)) {
                    helper.setVisibility(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_flag, View.GONE);
                    helper.setImageResource(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_photo, mPlusDrawableResId);
                } else {
                    if (mEditable) {
                        helper.setVisibility(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_flag, View.VISIBLE);
                        helper.setImageResource(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_flag, mDeleteDrawableResId);
                    } else {
                        helper.setVisibility(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_flag, View.GONE);
                    }
                    BGAImage.display(helper.getImageView(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_photo), mPlaceholderDrawableResId, model, mImageSize);
                }
            }
        }

        private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

            @Override
            public boolean isLongPressDragEnabled() {
                return mEditable && mSortable && mPhotoAdapter.getData().size() > 1;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                if (mPhotoAdapter.isPlusItem(viewHolder.getAdapterPosition())) {
                    dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                }
                int swipeFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, ViewHolder source, ViewHolder target) {
                if (source.getItemViewType() != target.getItemViewType() || mPhotoAdapter.isPlusItem(target.getAdapterPosition())) {
                    return false;
                }
                mPhotoAdapter.moveItem(source.getAdapterPosition(), target.getAdapterPosition());
                if (mDelegate != null) {
                    mDelegate.onNinePhotoItemExchanged(MyBGASortableNinePhotoLayout.this, source.getAdapterPosition(), target.getAdapterPosition(), getData());
                }
                return true;
            }

            @Override
            public void onSwiped(ViewHolder viewHolder, int direction) {
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
                if (actionState != ACTION_STATE_IDLE) {
                    ViewCompat.setScaleX(viewHolder.itemView, 1.2f);
                    ViewCompat.setScaleY(viewHolder.itemView, 1.2f);
                    ((BGARecyclerViewHolder) viewHolder).getViewHolderHelper().getImageView(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_photo).setColorFilter(getResources().getColor(cn.bingoogolapple.photopicker.R.color.bga_pp_photo_selected_mask));
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
                ViewCompat.setScaleX(viewHolder.itemView, 1.0f);
                ViewCompat.setScaleY(viewHolder.itemView, 1.0f);
                ((BGARecyclerViewHolder) viewHolder).getViewHolderHelper().getImageView(cn.bingoogolapple.photopicker.R.id.iv_item_nine_photo_photo).setColorFilter(null);
                super.clearView(recyclerView, viewHolder);
            }
        }

        public interface Delegate {
            void onClickAddNinePhotoItem(MyBGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models);

            void onClickDeleteNinePhotoItem(MyBGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models);

            void onClickNinePhotoItem(MyBGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models);

            void onNinePhotoItemExchanged(MyBGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models);
        }
    }
}
