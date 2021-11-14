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
import com.jit.AgriIn.model.bean.EBKProductUpdateBean;
import com.jit.AgriIn.model.response.BaikeProductBean;
import com.jit.AgriIn.ui.presenter.expert_baike.EBKUpdateProductAtPresenter;
import com.jit.AgriIn.ui.view.expert_baike.EBKUpdateProductAtView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-30 13:23:27.
 * Describe:
 */

public class EBKProductUpdateActivity extends BaseActivity<EBKUpdateProductAtView,EBKUpdateProductAtPresenter> implements EBKUpdateProductAtView {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.tvProductName)
    TextView mTvProductName;
    @BindView(R.id.etProductName)
    EditText mEtProductName;
    @BindView(R.id.tvProductType)
    TextView mTvProductType;
    @BindView(R.id.etFeedType)
    EditText mEtFeedType;
    @BindView(R.id.tvProductCompany)
    TextView mTvProductCompany;
    @BindView(R.id.etProdPuctCompany)
    EditText mEtProdPuctCompany;

    @BindView(R.id.etRange)
    EditText mEtRange;
    @BindView(R.id.etDef)
    EditText mEtDef;
    public List<LocalMedia> mSingleSelectList = new ArrayList<>();
    private boolean isHeadPicAdd = true;
    private boolean isHavingChanged = false;
    private int baikeID;
    private int mItemIndex;

    private BaikeProductBean mBaikeProductBean = new BaikeProductBean();
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
        return R.layout.activity_ebkproduct_add;
    }

    @Override
    protected EBKUpdateProductAtPresenter createPresenter() {
        return new EBKUpdateProductAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改产品百科");
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
        String productName = mEtProductName.getText().toString();
        if (TextUtils.isEmpty(productName)){
            UIUtils.showToast(getString(R.string.product_name));
            return;
        }
        String productType = mEtFeedType.getText().toString();
        if (TextUtils.isEmpty(productType)){
            UIUtils.showToast(getString(R.string.product_subkind));
            return;
        }

        String productCompany = mEtProdPuctCompany.getText().toString();
        if (TextUtils.isEmpty(productCompany)){
            UIUtils.showToast("请输入生产公司");
            return;
        }

        if (!isHeadPicAdd){
            UIUtils.showToast(getString(R.string.dis_pic));
            return;
        }


        String range = mEtRange.getText().toString();
        if (TextUtils.isEmpty(range)){
            UIUtils.showToast("请添加适用范围描述");
            return;
        }

        String def = mEtDef.getText().toString();
        if (TextUtils.isEmpty(def)){
            UIUtils.showToast(getString(R.string.feed_def));
            return;
        }


        mBaikeProductBean.setName(productName);
        mBaikeProductBean.setType(productType);
        mBaikeProductBean.setCompany(productCompany);
        mBaikeProductBean.setCrop_use(range);
        mBaikeProductBean.setContent(def);


//        if (isHavingChanged) {
//            mPresenter.doProductBaikeUpdateWithPic(baikeID, productName, productType, def, mSingleSelectList.get(0).getPath());
//        }else {
            mPresenter.doProductBaikeUpdateNoPic(baikeID, mBaikeProductBean);
//        }
    }

    @Override
    public void queryBaikeShowSuccess(BaikeProductBean baikeProductBean) {
//        mTvAddHeadPic.setVisibility(View.GONE);
//        mFlAdd.setVisibility(View.VISIBLE);
//        mDelete.setVisibility(View.VISIBLE);
        if (baikeProductBean != null){
//            GlideLoaderUtils.display(this,mIvHeadPic, baikeProductBean.getImage());
            mBaikeProductBean = baikeProductBean;
            mEtProductName.setText(baikeProductBean.getName());
            mEtProductName.setSelection(baikeProductBean.getName().length());
            mEtFeedType.setText(baikeProductBean.getType());
            mEtFeedType.setSelection(baikeProductBean.getType().length());
            mEtProdPuctCompany.setText(baikeProductBean.getCompany());
            mEtProdPuctCompany.setSelection(baikeProductBean.getCompany().length());
            mEtRange.setText(baikeProductBean.getCrop_use());
            mEtRange.setSelection(baikeProductBean.getCrop_use().length());
            mEtDef.setText(baikeProductBean.getContent());
            mEtDef.setSelection(baikeProductBean.getContent().length());
        }
    }

    @Override
    public void queryBaikeShowFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void updateProductSuccess(BaikeProductBean baikeProductBean) {
        EBKProductUpdateBean ebkProductUpdateBean = new EBKProductUpdateBean();
        ebkProductUpdateBean.setIndex(mItemIndex);
        ebkProductUpdateBean.setBaikeProductBean(baikeProductBean);
        mRxManager.post(AppConstant.RX_UPDATE_PRODUCT_BAIKE,ebkProductUpdateBean);
        finish();
    }

    @Override
    public void updateProductFailure(String error) {
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
