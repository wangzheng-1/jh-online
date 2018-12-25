package com.xcompany.jhonline.module.report.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.report.MediaBaseBean;
import com.xcompany.jhonline.model.report.MediaBaseBeanSerial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/14.
 */

public class PhotoSelectActivity extends BaseActivity {

    public static final String EXTRA_SELECTED_PHOTOS = "EXTRA_SELECTED_PHOTOS";


    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.okSelectText)
    TextView okSelectText;
    @BindView(R.id.mEasyRecylerView)
    XRecyclerView mEasyRecylerView;
    private boolean flag = true;

    private List<MediaBaseBean> list = new ArrayList<>();
    public static String IMAGE_NUM = "imageNum";
    public static String DATA = "data";
    /**
     * 需要选择照片的张数
     */
    private int intExtra;

    final List<String> imdata = new ArrayList<>();

    private int selectMediaType;  // 0 视频照片皆可， 1只能照片。

    public static String SELECT_MEDIA_TYPE = "selectMediaType";

    private HashMap<Integer, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photoselect);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        intExtra = intent.getIntExtra(IMAGE_NUM, 0);
        selectMediaType = intent.getIntExtra(SELECT_MEDIA_TYPE, 0);

        getPath();
        for (int i = 0; i < list.size(); i++) {
            map.put(i, "false");
        }
        PhotoSelectAdapter adapter = new PhotoSelectAdapter(this);
        mEasyRecylerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        mEasyRecylerView.setAdapter(adapter);
        adapter.setOnVideoClickLinstener(new OnVideoClickLinstener() {
            @Override
            public void OnVideoClick(int position) {
                if (flag) {
                    MediaBaseBeanSerial mediaBaseBeanSerial = new MediaBaseBeanSerial();
                    List<MediaBaseBean> mediaBaseBeanList = new ArrayList<>();
                    mediaBaseBeanList.add(list.get(position - 1));
                    mediaBaseBeanSerial.setMediaBaseBeanList(mediaBaseBeanList);
                    Intent intent1 = new Intent();
                    intent1.putExtra(EXTRA_SELECTED_PHOTOS, mediaBaseBeanSerial);
                    setResult(Activity.RESULT_OK, intent1);
                    finish();
                } else {
                    Toast.makeText(PhotoSelectActivity.this, "您已选择图片", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initViewAndListener();
    }

    private void initViewAndListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        okSelectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imdata.clear();
                List<MediaBaseBean> mediaBaseBeanList = new ArrayList<>();
                for (int i = 0; i < map.size(); i++) {
                    String s = map.get(i);
                    if (s.equals("true")) {
                        MediaBaseBean bean = list.get(i);
                        imdata.add(bean.getUrl());
                        mediaBaseBeanList.add(bean);
                    }
                }
                if (imdata.size() == 0) {
                    Toast.makeText(PhotoSelectActivity.this, "请选择图片", Toast.LENGTH_SHORT).show();
                } else {
                    MediaBaseBeanSerial mediaBaseBeanSerial = new MediaBaseBeanSerial();
                    mediaBaseBeanSerial.setMediaBaseBeanList(mediaBaseBeanList);
                    Intent intent1 = new Intent();
                    intent1.putExtra(EXTRA_SELECTED_PHOTOS, mediaBaseBeanSerial);
                    setResult(Activity.RESULT_OK, intent1);
                    finish();
                }
            }
        });
    }


    private void getPath() {

        if (selectMediaType == 0) {
            /*获取视频列表*/
            String[] projections = {
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.DATE_ADDED,
                    MediaStore.Video.Media.SIZE
            };
            String orderBys = MediaStore.Video.Media.DISPLAY_NAME;
            Uri uris = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            getVideoContentProvider(uris, projections, orderBys);
        }

        /*获取图片列表*/
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
        };
        String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        getImageContentProvider(uri, projection, orderBy);

        Collections.sort(list,new Comparator<MediaBaseBean>(){
            @Override
            public int compare(MediaBaseBean o1, MediaBaseBean o2) {
                // TODO Auto-generated method stub
                return -(o1.getCreateTime().compareTo(o2.getCreateTime()));
            }
        });

    }

    /*获取手机所有图片地址*/
    public void getImageContentProvider(Uri uri, String[] projection, String orderBy) {
        // TODO Auto-generated method stub
        Cursor cursor = getContentResolver().query(uri, projection, null,
                null, orderBy);
        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {
            MediaBaseBean bean = new MediaBaseBean();
            bean.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
            String createTimeStr = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)); // 路径
            bean.setCreateTime(Long.parseLong(createTimeStr));

            bean.setIsvideo(false);
            list.add(bean);

        }
    }

    /*获取手机所有视频地址和视频时长*/
    public void getVideoContentProvider(Uri uri, String[] projection, String orderBy) {
        // TODO Auto-generated method stub
        Cursor cursor = getContentResolver().query(uri, projection, null,
                null, orderBy);
        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {
            long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
            if (size < 500 * 1024 * 1024) { //视频小于500M
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
                String createTimeStr = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)); // 路径
                MediaBaseBean bean = new MediaBaseBean();
                bean.setIsvideo(true);
                bean.setUrl(path);
                bean.setVideosize(duration + "");
                bean.setCreateTime(Long.parseLong(createTimeStr));
                list.add(bean);
            }

        }


    }

    public interface OnVideoClickLinstener {
        void OnVideoClick(int position);
    }

    public interface OnImageClickLinstener {
        void OnImageClick(int position);
    }

    public interface OnImageSelectClickLinstener {
        void OnImageSelectClick(int position, boolean b, CheckBox box);
    }

    class PhotoSelectAdapter extends XRecyclerView.Adapter {
        private Context context;
        private OnVideoClickLinstener onVideoClickLinstener;
        private OnImageClickLinstener onImageClickLinstener;
        private OnImageSelectClickLinstener onImageSelectClickLinstener;

        public PhotoSelectAdapter(Context context) {
            this.context = context;
        }

        public void setOnImageSelectClickLinstener(OnImageSelectClickLinstener onImageSelectClickLinstener) {
            this.onImageSelectClickLinstener = onImageSelectClickLinstener;
        }

        public void setOnVideoClickLinstener(OnVideoClickLinstener onVideoClickLinstener) {
            this.onVideoClickLinstener = onVideoClickLinstener;
        }

        public void setOnImageClickLinstener(OnImageClickLinstener onImageClickLinstener) {
            this.onImageClickLinstener = onImageClickLinstener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {        //视频地址
                View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
                return new PhotoSelectVideoHolder(view);
            } else {                  //图片地址
                View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
                return new PhotoSelectImageHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            int type = getItemViewType(position);
            MediaBaseBean baseBean = list.get(position);
            if (type == 0) {
                PhotoSelectVideoHolder videoHolder = (PhotoSelectVideoHolder) holder;
                Glide.with(context).load(baseBean.getUrl()).into(videoHolder.video_iv);
                videoHolder.video_time.setText(baseBean.getVideosize());
            } else {
                final PhotoSelectImageHolder imageHolder = (PhotoSelectImageHolder) holder;
                Glide.with(context).load(baseBean.getUrl()).into(imageHolder.imageSelectView);
                String s = map.get(position);
                if (s.equals("true")) {
                    imageHolder.imageCheckBox.setImageResource(R.mipmap.select_ok);
                } else {
                    imageHolder.imageCheckBox.setImageResource(R.mipmap.select_no);
                }
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            MediaBaseBean bean = list.get(position);
            if (bean.isvideo()) {
                return 0;
            } else {
                return 1;
            }
        }

        class PhotoSelectVideoHolder extends XRecyclerView.ViewHolder {
            TextView video_time;
            ImageView video_iv;
            LinearLayout video_ll;

            public PhotoSelectVideoHolder(View itemView) {
                super(itemView);
                video_time = (TextView) itemView.findViewById(R.id.video_time);
                video_iv = (ImageView) itemView.findViewById(R.id.video_iv);
                video_ll = (LinearLayout) itemView.findViewById(R.id.video_ll);
                video_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onVideoClickLinstener.OnVideoClick(getAdapterPosition());
                    }
                });
            }
        }

        class PhotoSelectImageHolder extends XRecyclerView.ViewHolder {
            ImageView imageCheckBox;
            LinearLayout imageLayout;
            ImageView imageSelectView;

            public PhotoSelectImageHolder(View itemView) {
                super(itemView);
                imageCheckBox = (ImageView) itemView.findViewById(R.id.image_mCheckBox);
                imageLayout = (LinearLayout) itemView.findViewById(R.id.image_ll);
                imageSelectView = (ImageView) itemView.findViewById(R.id.image_iv);
                imageLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition() - 1;
                        String s = map.get(position);
                        if (s.equals("true")) {
                            imageCheckBox.setImageResource(R.mipmap.select_no);
                            map.put(position, "false");
                        } else {
                            if (getmapsize() < intExtra) {
                                imageCheckBox.setImageResource(R.mipmap.select_ok);
                                map.put(position, "true");
                            } else {
                                imageCheckBox.setImageResource(R.mipmap.select_no);
                                Toast.makeText(context, "您已选择" + intExtra + "张图片", Toast.LENGTH_SHORT).show();
                                map.put(position, "false");
                            }
                        }
                        if (getmapsize() == 0) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                });
                imageCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition() - 1;
                        String s = map.get(position);
                        if (s.equals("true")) {
                            imageCheckBox.setImageResource(R.mipmap.select_no);
                            map.put(position, "false");
                        } else {
                            if (getmapsize() < intExtra) {
                                imageCheckBox.setImageResource(R.mipmap.select_ok);
                                map.put(position, "true");
                            } else {
                                imageCheckBox.setImageResource(R.mipmap.select_no);
                                Toast.makeText(context, "您已选择" + intExtra + "张图片", Toast.LENGTH_SHORT).show();
                                map.put(position, "false");
                            }
                        }
                        if (getmapsize() == 0) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                });
            }

            private int getmapsize() {
                int count = 0;
                for (int i = 0; i < map.size(); i++) {
                    String s = map.get(i);
                    if (s.equals("true")) {
                        count++;
                    }
                }
                return count;
            }
        }
    }

}
