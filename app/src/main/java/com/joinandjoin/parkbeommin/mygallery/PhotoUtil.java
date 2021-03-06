package com.joinandjoin.parkbeommin.mygallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PhotoUtil {

    public ArrayList<Photo> getAllPhotoPathList(Context context) {
        ArrayList<Photo> photos = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.MediaColumns.DATA,
        };

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        // 사진 아이디
        int columnIndexId = cursor.getColumnIndex(MediaStore.Images.Media._ID);

        // 사진 경로
        int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            Photo Photo = new Photo(cursor.getString(columnIndexId), cursor.getString(columnIndexData));
            photos.add(Photo);
        }

        cursor.close();

        // 최근 순으로 정렬
        Collections.sort(photos, new DescendingId());

        return photos;
    }

    class DescendingId implements Comparator<Photo> {
        @Override
        public int compare(Photo Photo, Photo t1) {
            return ((Integer)Integer.parseInt(t1.getId())).compareTo((Integer)Integer.parseInt(Photo.getId()));
        }
    }

}
