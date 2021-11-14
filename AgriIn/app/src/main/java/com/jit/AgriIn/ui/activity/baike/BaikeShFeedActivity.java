package com.jit.AgriIn.ui.activity.baike;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.BaikeFeedBean;
import com.jit.AgriIn.ui.presenter.baike.BaikeFeedShowAtPresenter;
import com.jit.AgriIn.ui.view.baike.BaikeFeedShowAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.time.TimeUtil;
import com.zxl.baselib.util.ui.UIUtils;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author crazyZhangxl on 2018-10-26 16:46:36.
 * Describe:
 */

public class BaikeShFeedActivity extends BaseActivity<BaikeFeedShowAtView, BaikeFeedShowAtPresenter> implements BaikeFeedShowAtView {


    @BindView(R.id.img_item_bg)
    ImageView mImgItemBg;
    @BindView(R.id.iv_one_photo)
    ImageView mIvOnePhoto;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvKind)
    TextView mTvKind;
    @BindView(R.id.tvTime)
    TextView mTvTime;
    @BindView(R.id.tvCompany)
    TextView mTvCompany;
    @BindView(R.id.ll_one_item)
    LinearLayout mLlOneItem;
    @BindView(R.id.fl_Header_view)
    FrameLayout mFlHeaderView;
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.llToolbarTitle)
    LinearLayout mLlToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.llAbstract)
    LinearLayout mLlAbstract;
    @BindView(R.id.tvDescription)
    TextView mTvDescription;
    private int mIndex;

    @Override
    protected void init() {
        if (getIntent() != null){
            mIndex = getIntent().getIntExtra(AppConstant.EXTRA_BAIKE_ID, -1);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_baike_dt_feed;
    }

    @Override
    protected BaikeFeedShowAtPresenter createPresenter() {
        return new BaikeFeedShowAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 加载背景
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mImgItemBg.setImageDrawable(resource);
            }
        };

        Glide.with(this).load(AppConstant.IMAGE_URL_GLIDE)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(23, 4)))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(simpleTarget);
    }

    @Override
    protected void initData() {
        mPresenter.queryBaikeDetail(mIndex);

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

    }

    @Override
    public void queryBaikeShowSuccess(BaikeFeedBean baikeSeedResponse) {
        if (baikeSeedResponse != null){
//            GlideLoaderUtils.display(this,mIvOnePhoto, baikeSeedResponse.getImage());
            mTvCompany.setText(getString(R.string.baike_source, baikeSeedResponse.getSource()));
            mTvKind.setText(getString(R.string.baike_kind,baikeSeedResponse.getCategory()));
            mTvToolbarTitle.setText(baikeSeedResponse.getName());
//            mTvContact.setText(getString(R.string.baike_phone, baikeSeedResponse.getTelPhone()));
            mTvDescription.setText(baikeSeedResponse.getContent());
//            mTvMan.setText(getString(R.string.baike_man, baikeSeedResponse.getContact()));
//            mTvPrice.setText(getString(R.string.baike_price, baikeSeedResponse.getPrice()));
//            mTvAddress.setText(getString(R.string.baike_address, baikeSeedResponse.getPrice()));
            mTvTime.setText(getString(R.string.baike_time, TimeUtil.getMyTime(baikeSeedResponse.getPublish_time())));
        }
    }

    @Override
    public void queryBaikeShowFailure(String error) {
        UIUtils.showToast(error);

    }

}
