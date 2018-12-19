package com.xcompany.jhonline.model.report;

import android.os.Parcel;
import android.os.Parcelable;

import com.xcompany.jhonline.model.base.Model;

import java.util.ArrayList;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/7/10 上午1:12
 * 描述:
 */
public class Moment extends Model implements Parcelable {
    public String content;
    public String videoUrl;
    public ArrayList<String> photos;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeStringList(this.photos);
        dest.writeString(this.videoUrl);

    }

    public Moment() {
    }

    public Moment(String content, ArrayList<String> photos) {
        this.content = content;
        this.photos = photos;
    }

    public Moment(String content,String videoUrl) {
        this.content = content;
        this.videoUrl = videoUrl;
    }
    public Moment(String content,String videoUrl, ArrayList<String> photos) {
        this.content = content;
        this.videoUrl = videoUrl;
        this.photos = photos;
    }
    protected Moment(Parcel in) {
        this.content = in.readString();
        this.photos = in.createStringArrayList();
        this.videoUrl = in.readString();

    }

    public static final Creator<Moment> CREATOR = new Creator<Moment>() {
        @Override
        public Moment createFromParcel(Parcel source) {
            return new Moment(source);
        }

        @Override
        public Moment[] newArray(int size) {
            return new Moment[size];
        }
    };
}