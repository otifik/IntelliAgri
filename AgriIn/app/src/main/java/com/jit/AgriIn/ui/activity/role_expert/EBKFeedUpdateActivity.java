package com.jit.AgriIn.ui.activity.role_expert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.EBKFeedUpdateBean;
import com.jit.AgriIn.model.response.BaikeFeedBean;
import com.jit.AgriIn.ui.presenter.expert_baike.EBKUpdateFeedAtPresenter;
import com.jit.AgriIn.ui.view.expert_baike.EBKUpdateFeedAtView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-30 13:22:48.
 * Describe:
 */

public class EBKFeedUpdateActivity extends BaseActivity<EBKUpdateFeedAtView,EBKUpdateFeedAtPresenter> implements EBKUpdateFeedAtView {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.tvFeedName)
    TextView mTvFeedName;
    @BindView(R.id.etFeedName)
    EditText mEtFeedName;
    @BindView(R.id.tvFeedType)
    TextView mTvFeedType;
    @BindView(R.id.etFeedType)
    EditText mEtFeedType;
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

    private BaikeFeedBean mBaikeFeedBean = new BaikeFeedBean();

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
        return R.layout.activity_ebkfeed_add;
    }

    @Override
    protected EBKUpdateFeedAtPresenter createPresenter() {
        return new EBKUpdateFeedAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改投喂百科");
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

//        // 点击添加封面图 =======
//        mTvAddHeadPic.setOnClickListener(v -> {
//            mSingleSelectList.clear();
//            PictureSHelper.getInstance().chooseSinglePictureEvent(this, mSingleSelectList, PictureConfig.SINGLE);
//        });
//
//        mIvHeadPic.setOnClickListener(v -> {
//            // 这里就是展示图片咯
//            //ShowBigImageActivity.startAction(mContext, mIvHeadPic, mSingleSelectList.get(0).getPath());
//        });
//
//        mDelete.setOnClickListener(v -> {
//            mDelete.setVisibility(View.GONE);
//            mFlAdd.setVisibility(View.GONE);
//            mTvAddHeadPic.setVisibility(View.VISIBLE);
//            isHeadPicAdd = false;
//            isHavingChanged = true;
//        });

        mTvPublishNow.setOnClickListener(v -> doCheckAndSubmit());
    }

    private void doCheckAndSubmit() {
        String feedName = mEtFeedName.getText().toString();
        if (TextUtils.isEmpty(feedName)){
            UIUtils.showToast(getString(R.string.feed_name));
            return;
        }
        String feedType = mEtFeedType.getText().toString();
        if (TextUtils.isEmpty(feedType)){
            UIUtils.showToast(getString(R.string.feed_subkind));
            return;
        }

        if (!isHeadPicAdd){
            UIUtils.showToast(getString(R.string.dis_pic));
            return;
        }
//        String price = mEtPrice.getText().toString();
//        if (TextUtils.isEmpty(price)){
//            UIUtils.showToast(getString(R.string.pls_bk_price));
//            return;
//        }
        String company = mEtCompany.getText().toString();
        if (TextUtils.isEmpty(company)){
            UIUtils.showToast("请输入信息来源");
            return;
        }
//        String telName = mEtTeiLphone.getText().toString();
//        if (TextUtils.isEmpty(telName)){
//            UIUtils.showToast(getString(R.string.pls_bk_tel));
//            return;
//        }

//        String contact = mEtContact.getText().toString();
//        if (TextUtils.isEmpty(contact)){
//            UIUtils.showToast(getString(R.string.pls_bk_contact));
//            return;
//        }

        String def = mEtDef.getText().toString();
        if (TextUtils.isEmpty(def)){
            UIUtils.showToast(getString(R.string.feed_def));
            return;
        }

        mBaikeFeedBean.setName(feedName);
        mBaikeFeedBean.setCategory(feedType);
        mBaikeFeedBean.setSource(company);
        mBaikeFeedBean.setContent(def);

//        if (isHavingChanged) {
//            mPresenter.doFeedBaikeUpdateWithPic(baikeID,feedName, feedType, price, telName, contact, company, def, mSingleSelectList.get(0).getPath());
//        }else {
            mPresenter.doFeedBaikeUpdateNoPic(baikeID,feedName,feedType,company,def);
//        }
    }

    @Override
    public void queryBaikeShowSuccess(BaikeFeedBean baikeFeedBean) {
//        mTvAddHeadPic.setVisibility(View.GONE);
//        mFlAdd.setVisibility(View.VISIBLE);
//        mDelete.setVisibility(View.VISIBLE);
        if (baikeFeedBean != null){
            mBaikeFeedBean = baikeFeedBean;
//            GlideLoaderUtils.display(this,mIvHeadPic, baikeFeedBean.getImage());
            mEtFeedName.setText(baikeFeedBean.getName());
            mEtFeedName.setSelection(baikeFeedBean.getName().length());
            mEtFeedType.setText(baikeFeedBean.getCategory());
            mEtFeedType.setSelection(baikeFeedBean.getCategory().length());
            mEtCompany.setText(baikeFeedBean.getSource());
            mEtCompany.setSelection(baikeFeedBean.getSource().length());
//            mEtContact.setText(baikeFeedBean.getContact());
//            mEtContact.setSelection(baikeFeedBean.getContact().length());
//            mEtPrice.setText(baikeFeedBean.getPrice());
//            mEtPrice.setSelection(baikeFeedBean.getPrice().length());
            mEtDef.setText(baikeFeedBean.getContent());
            mEtDef.setSelection(baikeFeedBean.getContent().length());
//            mEtTeiLphone.setText(baikeFeedBean.getTelPhone());
//            mEtTeiLphone.setSelection(baikeFeedBean.getTelPhone().length());
        }
    }

    @Override
    public void queryBaikeShowFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void updateFeedSuccess(BaikeFeedBean baikeFeedBean) {
        EBKFeedUpdateBean ebkFeedUpdateBean = new EBKFeedUpdateBean();
        ebkFeedUpdateBean.setIndex(mItemIndex);
        ebkFeedUpdateBean.setBaikeFeedBean(baikeFeedBean);
        mRxManager.post(AppConstant.RX_UPDATE_FEED_BAIKE,ebkFeedUpdateBean);
        finish();
    }

    @Override
    public void updateFeedFailure(String error) {
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
//                    mTvAddHeadPic.setVisibility(View.GONE);
//                    mFlAdd.setVisibility(View.VISIBLE);
//                    mDelete.setVisibility(View.VISIBLE);
//                    Glide.with(mContext).load(filePath).into(mIvHeadPic);
                    break;
                default:
                    break;
            }
        }
    }
}
