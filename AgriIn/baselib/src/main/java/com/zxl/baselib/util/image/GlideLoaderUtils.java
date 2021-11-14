package com.zxl.baselib.util.image;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.zxl.baselib.R;

import java.io.File;

/**
 * @author zxl on 2018/8/2.
 *         discription: Glide还有许多神奇的东西要学习呢 后面好得花点时间去研究 0-0-0-0-0-0
 */

public class GlideLoaderUtils {
    /**
     * 自己设置展位图和错误的图片
     * @param context
     * @param imageView
     * @param url
     * @param placeholder
     * @param error
     */
    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        //.transition(DrawableTransitionOptions.withCrossFade())
        Glide.with(context).load(url).apply(
                new RequestOptions().placeholder(placeholder).error(error))
                .into(imageView);
    }



    /**
     * 默认加载网页图片
     * @param context
     * @param imageView
     * @param url
     */
    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }

        if (TextUtils.isEmpty(url)){
            Glide.with(context).load(R.mipmap.default_head).into(imageView);
        }else {
            Glide.with(context).load(url)
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_image_loading)
                            .error(R.mipmap.ic_empty_picture)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(imageView);
        }
    }

    /**
     * 加载文本图片
     * @param context
     * @param imageView
     * @param file
     */
    public static void display(Context context, ImageView imageView,File file) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(file)
                .apply(new RequestOptions().placeholder(R.mipmap.ic_image_loading)
                        .error(R.mipmap.ic_empty_picture)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
        // .transition(DrawableTransitionOptions.withCrossFade())

    }

    /**
     * 加载图片资源
     * @param context
     * @param imageView
     * @param imageSource
     */
    public static void display(Context context, ImageView imageView,int imageSource) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(imageSource)
                .apply(new RequestOptions().placeholder(R.mipmap.ic_image_loading)
                        .error(R.mipmap.ic_empty_picture)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 无效了啊 ------
     * @param context
     * @param imageView
     * @param url
     */
    public static void displayRound(Context context,ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransformUtil())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.default_head)
                        .centerCrop())
                .into(imageView);
    }
    public static void displayRound(Context context,ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(resId)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransformUtil()).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.default_head)
                        .centerCrop())
                .into(imageView);
    }

}
