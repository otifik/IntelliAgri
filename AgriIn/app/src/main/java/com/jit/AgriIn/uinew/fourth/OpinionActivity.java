package com.jit.AgriIn.uinew.fourth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jit.AgriIn.R;
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
 * @author crazyZhangxl on 2018-11-8 11:09:56.
 * Describe:
 */

public class OpinionActivity extends BaseActivity<OpinionFeedbackView, OpinionFeedbackPresenter> implements OpinionFeedbackView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.etDescription)
    EditText mEtDescription;
    @BindView(R.id.etContact)
    EditText mEtContact;
    @BindView(R.id.ivImg)
    ImageView mIvImg;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    public List<LocalMedia> mSingleSelectList = new ArrayList<>();

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_opinion;
    }

    @Override
    protected OpinionFeedbackPresenter createPresenter() {
        return new OpinionFeedbackPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("意见反馈");

    }






    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        // 设置单张图片的点击事件
        mIvImg.setOnClickListener(view -> PictureSHelper.getInstance().
                chooseSinglePictureEvent(OpinionActivity.this, mSingleSelectList, PictureConfig.SINGLE));

        btnSubmit.setOnClickListener(v -> checkAndSubmit());
    }

    private void checkAndSubmit() {
        String description = mEtDescription.getText().toString().trim();
        if (TextUtils.isEmpty(description)){
            UIUtils.showToast("请输入意见反馈内容!");
            return;
        }

        String contact = mEtContact.getText().toString().trim();
        if (TextUtils.isEmpty(contact)){
            UIUtils.showToast("请输入联系方式!");
            return;
        }

        if (mSingleSelectList.isEmpty()){
            mPresenter.addFeedBackNoPic(description,contact);
        }else {
            mPresenter.addFeedBack(description,contact,mSingleSelectList.get(0).getPath());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.SINGLE:
                    mSingleSelectList = PictureSelector.obtainMultipleResult(data);
                    GlideLoaderUtils.display(this, mIvImg, mSingleSelectList.get(0).getPath());
                    break;
                default:
                    break;
            }
        }
    }



    @Override
    public void feedbackAddSuccess() {
        UIUtils.showToast("提交成功");
        mSingleSelectList.clear();
        finish();
    }

    @Override
    public void feedbackAddFailure(String msg) {
        UIUtils.showToast(msg);
    }
}
