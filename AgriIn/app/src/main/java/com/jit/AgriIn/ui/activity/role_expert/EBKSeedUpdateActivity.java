package com.jit.AgriIn.ui.activity.role_expert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.EBKSeedUpdateBean;
import com.jit.AgriIn.model.response.BaikeSeedBean;
import com.jit.AgriIn.ui.presenter.expert_baike.EBKUpdateSeedAtPresenter;
import com.jit.AgriIn.ui.view.expert_baike.EBKUpdateSeedAtView;
import com.jit.AgriIn.util.PictureSHelper;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-30 13:24:11.
 * Describe:
 */

public class EBKSeedUpdateActivity extends BaseActivity<EBKUpdateSeedAtView,EBKUpdateSeedAtPresenter> implements EBKUpdateSeedAtView {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.iv_save)
    ImageView mIvSave;
    @BindView(R.id.tvFrontStep)
    TextView mTvFrontStep;
    @BindView(R.id.tvNextStep)
    TextView mTvNextStep;
    @BindView(R.id.llTwoSteps)
    LinearLayout mLlTwoSteps;
    @BindView(R.id.flToolBar)
    FrameLayout mFlToolBar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tvSeedName)
    TextView mTvSeedName;
    @BindView(R.id.etFeedName)
    EditText mEtFeedName;
    @BindView(R.id.ivHeadPic)
    ImageView mIvHeadPic;
    @BindView(R.id.cv)
    CardView mCv;
    @BindView(R.id.flAdd)
    FrameLayout mFlAdd;
    @BindView(R.id.delete)
    ImageView mDelete;
    @BindView(R.id.tvAddHeadPic)
    TextView mTvAddHeadPic;
    @BindView(R.id.tvCompany)
    TextView mTvCompany;
    @BindView(R.id.etCompany)
    EditText mEtCompany;
    @BindView(R.id.etDef)
    EditText mEtDef;
    public List<LocalMedia> mSingleSelectList = new ArrayList<>();
    private boolean isHeadPicAdd = true;
    private boolean isHavingChanged = false;
    private int baikeID;
    private int mItemIndex;
    @Override
    protected void init() {
        // 从上一个活动接受数据
        if (getIntent() != null){
            baikeID =  getIntent().getIntExtra(AppConstant.EXTRA_BAIKE_ID,-1);
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX, -1);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ebkseed_add;
    }

    @Override
    protected EBKUpdateSeedAtPresenter createPresenter() {
        return new EBKUpdateSeedAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改种苗百科");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.save);
        if (baikeID == 0 || baikeID == -1){
            finish();
        }
        mEtDef.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void initData() {
        mPresenter.queryBaikeDetail(baikeID);
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        // 点击添加封面图 =======
        mTvAddHeadPic.setOnClickListener(v -> {
            mSingleSelectList.clear();
            PictureSHelper.getInstance().chooseSinglePictureEvent(this, mSingleSelectList, PictureConfig.SINGLE);
        });

        mIvHeadPic.setOnClickListener(v -> {
            // 这里就是展示图片咯
//            ShowBigImageActivity.startAction(mContext, mIvHeadPic, mSingleSelectList.get(0).getPath());
        });

        mDelete.setOnClickListener(v -> {
            mDelete.setVisibility(View.GONE);
            mFlAdd.setVisibility(View.GONE);
            mTvAddHeadPic.setVisibility(View.VISIBLE);
            isHeadPicAdd = false;
            isHavingChanged = true;
        });

        mTvPublishNow.setOnClickListener(v -> doCheckAndSubmit());
    }

    private void doCheckAndSubmit() {
        String feedName = mEtFeedName.getText().toString();
        if (TextUtils.isEmpty(feedName)) {
            UIUtils.showToast(getString(R.string.drug_name));
            return;
        }
        if (!isHeadPicAdd) {
            UIUtils.showToast(getString(R.string.dis_pic));
            return;
        }

        String company = mEtCompany.getText().toString();
        if (TextUtils.isEmpty(company)) {
            UIUtils.showToast(getString(R.string.pls_bk_company));
            return;
        }


        String def = mEtDef.getText().toString();
        if (TextUtils.isEmpty(def)) {
            UIUtils.showToast(getString(R.string.drug_defination));
            return;
        }
        if (isHavingChanged) {
            mPresenter.doSeedBaikeUpdateWithPic(baikeID, feedName,
                    company,
                    def,
                    mSingleSelectList.get(0).getPath());
        }else {
            mPresenter.doSeedBaikeUpdateNoPic(baikeID, feedName,
                    company,
                    def);
        }
    }

    @Override
    public void queryBaikeShowSuccess(BaikeSeedBean baikeSeedBean) {
        mTvAddHeadPic.setVisibility(View.GONE);
        mFlAdd.setVisibility(View.VISIBLE);
        mDelete.setVisibility(View.VISIBLE);
        if (baikeSeedBean != null){
            GlideLoaderUtils.display(this,mIvHeadPic, baikeSeedBean.getImage());
            mEtCompany.setText(baikeSeedBean.getSource());
            mEtCompany.setSelection(baikeSeedBean.getSource().length());
//            mEtContact.setText(baikeSeedBean.getContact());
//            mEtContact.setSelection(baikeSeedBean.getContact().length());
//            mEtTeiLphone.setText(baikeSeedBean.getTelPhone());
//            mEtTeiLphone.setSelection(baikeSeedBean.getTelPhone().length());
            mEtDef.setText(baikeSeedBean.getContent());
            mEtDef.setSelection(baikeSeedBean.getContent().length());
//            mEtPrice.setText(baikeSeedBean.getPrice());
//            mEtPrice.setSelection(baikeSeedBean.getPrice().length());
            mEtFeedName.setText(baikeSeedBean.getName());
            mEtFeedName.setSelection(baikeSeedBean.getName().length());
//            mEtFeedType.setText(baikeSeedBean.getSubKind());
//            mEtFeedType.setSelection(baikeSeedBean.getSubKind().length());
//            mEtPlace.setText(baikeSeedBean.getProductPlace());
//            mEtPlace.setSelection(baikeSeedBean.getProductPlace().length());
        }
    }

    @Override
    public void queryBaikeShowFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void updateSeedSuccess(BaikeSeedBean baikeSeedBean) {
        EBKSeedUpdateBean ebkSeedUpdateBean = new EBKSeedUpdateBean();
        ebkSeedUpdateBean.setIndex(mItemIndex);
        ebkSeedUpdateBean.setBaikeSeedBean(baikeSeedBean);
        mRxManager.post(AppConstant.RX_UPDATE_SEED_BAIKE,baikeSeedBean);
        finish();
    }

    @Override
    public void updateSeedFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.SINGLE:
                    mSingleSelectList = PictureSelector.obtainMultipleResult(data);
                    String filePath = mSingleSelectList.get(0).getPath();
                    isHeadPicAdd = true; // 改变图片选择状态
                    mTvAddHeadPic.setVisibility(View.GONE);
                    mFlAdd.setVisibility(View.VISIBLE);
                    mDelete.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(filePath).into(mIvHeadPic);
                    break;
                default:
                    break;
            }
        }
    }
}
