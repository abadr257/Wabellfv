package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;

import com.clixifi.wabell.data.Response.ImageUrl;
import com.clixifi.wabell.utils.StaticMethods;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    ArrayList<ImageUrl> images ;

    public ImageAdapter(FragmentManager fm, Context mContext, ArrayList<ImageUrl> images) {
        super(fm, mContext);
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
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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