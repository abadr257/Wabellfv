package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.clixifi.wabell.data.Response.ImageUrl;
import com.clixifi.wabell.utils.StaticMethods;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ImageAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<ImageUrl> images ;

    public ImageAdapter( Context mContext, ArrayList<ImageUrl> images) {
        this.mContext = mContext;
        this.images = images;
    }
    @Override
    public int getCount() {
        return images.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Log.e(TAG, "LoadImageI11: " + images.get(position).getFilePath());
        StaticMethods.LoadImage(mContext ,imageView ,images.get(position).getFilePath() , null );
        //imageView.setImageResource(/*images.get(position).getFilePath()*/);
        container.addView(imageView, 0);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}