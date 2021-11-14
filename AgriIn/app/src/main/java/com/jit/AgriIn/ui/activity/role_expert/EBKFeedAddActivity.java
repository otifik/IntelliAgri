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
import com.jit.AgriIn.ui.presenter.expert_baike.EBKFeedAddPresenter;
import com.jit.AgriIn.ui.view.expert_baike.EBKAddAtView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-29 15:36:41.
 * Describe:
 */

public class EBKFeedAddActivity extends BaseActivity<EBKAddAtView, EBKFeedAddPresenter> implements EBKAddAtView {
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
    private boolean isHeadPicAdd = false;


    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ebkfeed_add;
    }

    @Override
    protected EBKFeedAddPresenter createPresenter() {
        return new EBKFeedAddPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加投喂百科");
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
//        });

        mTvPublishNow.setOnClickListener(v -> doCheckAndSubmit());
    }

    // 价格 公司 电话 联系人
    private void doCheckAndSubmit() {
        String feedName = mEtFeedName.getText().toString();
        if (TextUtils.isEmpty(feedName)){
            UIUtils.showToast("请输入技术名称");
            return;
        }
        String feedType = mEtFeedType.getText().toString();
        if (TextUtils.isEmpty(feedType)){
            UIUtils.showToast("请输入技术类型");
            return;
        }

//        if (!isHeadPicAdd){
//            UIUtils.showToast(getString(R.string.dis_pic));
//            return;
//        }
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
            UIUtils.showToast("请输入技术详情");
            return;
        }

        mPresenter.doFeedAddRequest(feedName,feedType,company,def);

    }

    @Override
    public void ebkAddSuccess() {
        // 发送刷新疾病百科
        mRxManager.post(AppConstant.RX_ADD_FEED_BAIKE,AppConstant.RX_POST_SUCCESS);
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
