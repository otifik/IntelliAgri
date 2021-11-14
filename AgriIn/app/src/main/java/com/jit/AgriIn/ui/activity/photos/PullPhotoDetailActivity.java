package com.jit.AgriIn.ui.activity.photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jit.AgriIn.R;
import com.zxl.baselib.commom.BaseAppConst;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.ui.StatusBarHelper;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.util.ui.ViewHelper;
import com.zxl.baselib.util.window.DisplayHelper;
import com.zxl.baselib.widget.PullBackLayout;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author zxl on 2018/08/16.
 *         discription: 仿微信单张图片拖拽的显示活动
 */
public class PullPhotoDetailActivity extends BaseActivity implements PullBackLayout.Callback {

    @BindView(R.id.photoView)
    PhotoView mPhotoView;
    @BindView(R.id.pullLayout)
    PullBackLayout mPullLayout;
    @BindView(R.id.rootView)
    View rootView;
    @BindView(R.id.loading)
    ProgressBar mLoading;
    private String mImageUrl;
    private boolean mIsStatusBarHidden = true;
    private ColorDrawable mBackground;

    /**
     * 显示图片活动跳转
     *
     * @param activity 上下文
     * @param url      url地址
     * @param view     过渡共用的ImageView
     */
    public static void startAction(Activity activity, String url, View view) {
        Intent intent = new Intent(activity, PullPhotoDetailActivity.class);
        intent.putExtra(BaseAppConst.K_SINGLE_PHOTO_URL, url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,
                            view,
                            BaseAppConst.TRANSITION_ANIMATION_NEWS_PHOTOS);
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view,
                            view.getWidth() / 2,
                            view.getHeight() / 2,
                            0,
                            0);
            ActivityCompat.startActivity(activity, intent, options.toBundle());

        }
    }


    @Override
    protected void init() {
        mImageUrl = getIntent().getStringExtra(BaseAppConst.K_SINGLE_PHOTO_URL);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pull_photo_detail;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        StatusBarHelper.hideStatusBar(this);
        initPhotoView();
        initBackground();
    }

    private void initBackground() {
        mBackground = new ColorDrawable(Color.BLACK);
        ViewHelper.getActivityRoot(this).setBackground(mBackground);
    }

    private void initPhotoView() {
        mLoading.setVisibility(View.VISIBLE);
        mLoading.setClickable(false);
        Glide.with(PullPhotoDetailActivity.this).load(mImageUrl)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(com.zxl.baselib.R.mipmap.ic_error_picture))
                .thumbnail(0.1f)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        UIUtils.showToast(getString(R.string.str_resource_load_exception));
                        mLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mLoading.setVisibility(View.GONE);
                        int height = mPhotoView.getHeight();
                        int wHeight = DisplayHelper.getScreenHeight(PullPhotoDetailActivity.this);
                        if (height > wHeight) {
                            mPhotoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        } else {
                            mPhotoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }
                        return false;
                    }
                })
                .into(mPhotoView);

    }

    @Override
    protected void initData() {

        // 单击事件退出

        mPhotoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                onBackPressed();
            }
        });

        // 长恩事件展示弹出框
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                UIUtils.showToast("弹出选择窗口!");
                return false;
            }
        });
    }

    @Override
    protected void initListener() {
        mPullLayout.setCallback(this);
    }

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            StatusBarHelper.showStatusBar(this);
        } else {
            StatusBarHelper.hideStatusBar(this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }


    @Override
    public void onPullStart() {

    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        mBackground.setAlpha((int) (0xff * (1f - progress)));
    }

    @Override
    public void onPullCancel() {

    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }


}
