package com.jit.AgriIn.uinew.third;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jit.AgriIn.R;
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
 * @author crazyZhangxl on 2018-10-29 15:35:26.
 * Describe:添加疾病百科活动
 */

public class AskQuestionActivity extends BaseActivity<AskQuestionView, AskQuestionPresenter> implements AskQuestionView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
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
    @BindView(R.id.etDef)
    EditText mEtDef;

    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    public List<LocalMedia> mSingleSelectList = new ArrayList<>();
    private boolean isHeadPicAdd = false;

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_askquestion;
    }

    @Override
    protected AskQuestionPresenter createPresenter() {
        return new AskQuestionPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("提问");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);
        mEtDef.setMovementMethod(ScrollingMovementMethod.getInstance());
//        mEtComment.setMovementMethod(ScrollingMovementMethod.getInstance());
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
//            ShowBigImageActivity.startAction(mContext, mIvHeadPic, mSingleSelectList.get(0).getPath());
        });

        mDelete.setOnClickListener(v -> {
            mDelete.setVisibility(View.GONE);
            mFlAdd.setVisibility(View.GONE);
            mTvAddHeadPic.setVisibility(View.VISIBLE);
            isHeadPicAdd = false;
        });

        mTvPublishNow.setOnClickListener(v -> doCheckAndSubmit());
    }

    /**
     * 检查并且刷新
     */
    private void doCheckAndSubmit() {
//        String diseaseName = mEtDiseaseName.getText().toString();
//        if (TextUtils.isEmpty(diseaseName)){
//            UIUtils.showToast(getString(R.string.dis_name));
//            return;
//        }
//        String diseaseType = mEtDiseaseType.getText().toString();
//        if (TextUtils.isEmpty(diseaseType)){
//            UIUtils.showToast(getString(R.string.dis_subkind));
//            return;
//        }


//        String cause = mEtComment.getText().toString();
//        if (TextUtils.isEmpty(cause)){
//            UIUtils.showToast(getString(R.string.dis_cause));
//            return;
//        }
        String defination = mEtDef.getText().toString();
        if (TextUtils.isEmpty(defination)){
            UIUtils.showToast("请填写问题描述");
            return;
        }

        if (!isHeadPicAdd){
            Log.e("图片","没有图片");
            mPresenter.askQuestion(defination);
        }else {
            Log.e("图片","有图片");
            mPresenter.askQuestionWithImage(defination,mSingleSelectList.get(0).getPath());
        }
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


    @Override
    public void AskSuccess() {
        UIUtils.showToast("成功");
        finish();
    }

    @Override
    public void AskFailure(String msg) {
        UIUtils.showToast(msg);
    }
}
