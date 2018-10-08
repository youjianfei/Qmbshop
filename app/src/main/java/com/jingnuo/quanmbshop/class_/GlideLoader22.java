package com.jingnuo.quanmbshop.class_;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jingnuo.quanmbshop.R;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2016/6/6.
 */
public class GlideLoader22 extends ImageLoader {


    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).error(R.mipmap.shouyelunbo).into(imageView);
    }
}
