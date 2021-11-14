package com.jit.AgriIn.ui.activity.baike;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
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
import com.jit.AgriIn.model.response.BaikeSeedBean;
import com.jit.AgriIn.ui.presenter.baike.BaikeSeedShowAtPresenter;
import com.jit.AgriIn.ui.view.baike.BaikeShowAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.time.TimeUtil;
import com.zxl.baselib.util.ui.UIUtils;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author crazyZhangxl on 2018-10-15 11:08:47.
 * Describe:
 */

public class BaikeShSeedActivity extends BaseActivity<BaikeShowAtView, BaikeSeedShowAtPresenter> implements BaikeShowAtView {

    @BindView(R.id.img_item_bg)
    ImageView mImgItemBg;
    @BindView(R.id.iv_one_photo)
    ImageView mIvOnePhoto;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvTime)
    TextView mTvTime;
    @BindView(R.id.tvMan)
    TextView mTvMan;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tvDescription)
    TextView mTvDescription;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvNavigation;
    private int mIndex;


    @Override
    protected void init() {
        if (getIntent() != null){
            mIndex = getIntent().getIntExtra(AppConstant.EXTRA_BAIKE_ID, -1);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_baike_show;
    }

    @Override
    protected BaikeSeedShowAtPresenter createPresenter() {
        return new BaikeSeedShowAtPresenter(this);
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
        mIvNavigation.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void queryBaikeShowSuccess(BaikeSeedBean baikeSeedBean) {
        if (baikeSeedBean != null){
            GlideLoaderUtils.display(this,mIvOnePhoto, baikeSeedBean.getImage());
//            mTvCompany.setText(getString(R.string.baike_company, baikeSeedBean.getCompany()));
//            mTvKind.setText(getString(R.string.baike_kind, baikeSeedBean.getSubKind()));
            mTvToolbarTitle.setText(baikeSeedBean.getName());
            mTvMan.setText(getString(R.string.baike_source, baikeSeedBean.getSource()));
//            mTvContact.setText(getString(R.string.baike_phone, baikeSeedBean.getTelPhone()));
            mTvDescription.setText(baikeSeedBean.getContent());
//            mTvMan.setText(getString(R.string.baike_man, baikeSeedBean.getContact()));
//            mTvPrice.setText(getString(R.string.baike_price, baikeSeedBean.getPrice()));
//            mTvAddress.setText(getString(R.string.baike_address, baikeSeedBean.getProductPlace()));
            mTvTime.setText(getString(R.string.baike_time, TimeUtil.getMyTime(baikeSeedBean.getPublish_time())));
        }
    }

    @Override
    public void queryBaikeShowFailure(String error) {
        UIUtils.showToast(error);
    }
}
