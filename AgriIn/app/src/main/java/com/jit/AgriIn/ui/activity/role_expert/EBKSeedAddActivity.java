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
import com.jit.AgriIn.ui.presenter.expert_baike.EBKSeedAddPresenter;
import com.jit.AgriIn.ui.view.expert_baike.EBKAddAtView;
import com.jit.AgriIn.util.PictureSHelper;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-29 15:37:32.
 * Describe:
 */

public class EBKSeedAddActivity extends BaseActivity<EBKAddAtView, EBKSeedAddPresenter> implements EBKAddAtView {


    public List<LocalMedia> mSingleSelectList = new ArrayList<>();
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
//    @BindView(R.id.tvSeedType)
//    TextView mTvSeedType;
//    @BindView(R.id.etFeedType)
//    EditText mEtFeedType;
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
//    @BindView(R.id.tvPrice)
//    TextView mTvPrice;
//    @BindView(R.id.etPrice)
//    EditText mEtPrice;
    @BindView(R.id.tvCompany)
    TextView mTvCompany;
    @BindView(R.id.etCompany)
    EditText mEtCompany;
//    @BindView(R.id.tvTelPhone)
//    TextView mTvTelPhone;
//    @BindView(R.id.etTeiLphone)
//    EditText mEtTeiLphone;
//    @BindView(R.id.tvPlace)
//    TextView mTvPlace;
//    @BindView(R.id.etPlace)
//    EditText mEtPlace;
//    @BindView(R.id.tvContact)
//    TextView mTvContact;
//    @BindView(R.id.etContact)
//    EditText mEtContact;
    @BindView(R.id.etDef)
    EditText mEtDef;
    private boolean isHeadPicAdd = false;

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ebkseed_add;
    }

    @Override
    protected EBKSeedAddPresenter createPresenter() {
        return new EBKSeedAddPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加种苗百科");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);
        mEtDef.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void initData() {

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
            //ShowBigImageActivity.startAction(mContext, mIvHeadPic, mSingleSelectList.get(0).getPath());
        });

        mDelete.setOnClickListener(v -> {
            mDelete.setVisibility(View.GONE);
            mFlAdd.setVisibility(View.GONE);
            mTvAddHeadPic.setVisibility(View.VISIBLE);
            isHeadPicAdd = false;
        });

        mTvPublishNow.setOnClickListener(v -> doCheckAndSubmit());
    }

    private void doCheckAndSubmit() {
        String feedName = mEtFeedName.getText().toString();
        if (TextUtils.isEmpty(feedName)) {
            UIUtils.showToast("请输入品种名称");
            return;
        }
//        String feedType = mEtFeedType.getText().toString();
//        if (TextUtils.isEmpty(feedType)) {
//            UIUtils.showToast(getString(R.string.drug_subkind));
//            return;
//        }
        if (!isHeadPicAdd) {
            UIUtils.showToast(getString(R.string.dis_pic));
            return;
        }

//        String price = mEtPrice.getText().toString();
//        if (TextUtils.isEmpty(price)) {
//            UIUtils.showToast(getString(R.string.pls_bk_price));
//            return;
//        }
        String company = mEtCompany.getText().toString();
        if (TextUtils.isEmpty(company)) {
            UIUtils.showToast("请输入信息来源");
            return;
        }

//        String telName = mEtTeiLphone.getText().toString();
//        if (TextUtils.isEmpty(telName)) {
//            UIUtils.showToast(getString(R.string.pls_bk_tel));
//            return;
//        }

//        String address = mEtPlace.getText().toString();
//        if (TextUtils.isEmpty(address)) {
//            UIUtils.showToast(getString(R.string.pls_bk_address));
//            return;
//        }
//        String contact = mEtContact.getText().toString();
//        if (TextUtils.isEmpty(contact)){
//            UIUtils.showToast(getString(R.string.pls_bk_contact));
//            return;
//        }

        String def = mEtDef.getText().toString();
        if (TextUtils.isEmpty(def)) {
            UIUtils.showToast(getString(R.string.drug_defination));
            return;
        }

        mPresenter.doSeedAddRequest(feedName,
                company,
                def,
                mSingleSelectList.get(0).getPath());
    }

    @Override
    public void ebkAddSuccess() {
        // 发送刷新疾病百科
        mRxManager.post(AppConstant.RX_ADD_SEED_BAIKE, AppConstant.RX_POST_SUCCESS);
        finish();
    }

    @Override
    public void ebkAddFailure(String msg) {
        UIUtils.showToast(msg);
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
