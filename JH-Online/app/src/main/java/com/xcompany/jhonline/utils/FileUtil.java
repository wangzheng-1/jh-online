package com.xcompany.jhonline.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class FileUtil {

    /**
     * 将bitmap 保存到系统图片中
     *
     * @param bitmap
     * @param absFilename
     * @throws IOException
     */
    public static boolean saveBitmap(Bitmap bitmap, String absFilename) {

        boolean success = false;
        File file = new File(absFilename);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
                success = true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            success = false;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static Uri getImageContentUri(Context context, String filePath) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            File imageFile = new File(filePath);
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public static Uri bitMapToMediaAndGetUri(Context context, Bitmap bitmap, String filePath) {
        Uri uri = null;
        try {
            saveBitmap(bitmap, filePath);
            uri = getImageContentUri(context, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }

    public static  Bitmap getVideoThumbnail(String url) {

        File file = new File(url);
        boolean fileExist;

        if(file.exists()){
            fileExist = true;
        }else{
            fileExist = false;
        }
        Bitmap bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //（）根据文件路径获取缩略图
            retriever.setDataSource(url);
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}