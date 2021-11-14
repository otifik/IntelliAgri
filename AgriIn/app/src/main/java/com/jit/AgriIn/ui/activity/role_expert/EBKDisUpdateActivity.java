package com.jit.AgriIn.ui.activity.role_expert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.EBKDisUpdateBean;
import com.jit.AgriIn.model.response.BaikeDiseaseBean;
import com.jit.AgriIn.ui.presenter.expert_baike.EBKUpdateDisAtPresenter;
import com.jit.AgriIn.ui.view.expert_baike.EBKUpdateDiseaseAtView;
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
 * @author crazyZhangxl on 2018-10-30 13:21:29.
 * Describe:
 */

public class EBKDisUpdateActivity extends BaseActivity<EBKUpdateDiseaseAtView,EBKUpdateDisAtPresenter> implements EBKUpdateDiseaseAtView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tvDiseaseName)
    TextView mTvDiseaseName;
    @BindView(R.id.etDiseaseName)
    EditText mEtDiseaseName;
    @BindView(R.id.tvDiseaseType)
    TextView mTvDiseaseType;
    @BindView(R.id.etDiseaseType)
    EditText mEtDiseaseType;
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
    @BindView(R.id.etTreatment)
    EditText mEtTreatment;

    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    public List<LocalMedia> mSingleSelectList = new ArrayList<>();
    private boolean isHeadPicAdd = true;
    private boolean isHavingChanged = false;
    private int baikeID;
    private int mItemIndex;
    private BaikeDiseaseBean mBaikeDiseaseBean = new BaikeDiseaseBean();

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
        return R.layout.activity_ebkdis_add;
    }

    @Override
    protected EBKUpdateDisAtPresenter createPresenter() {
        return new EBKUpdateDisAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改疾病百科");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.save);
        if (baikeID == 0 || baikeID == -1){
            finish();
        }
        mEtTreatment.setMovementMethod(ScrollingMovementMethod.getInstance());
        mEtDef.setMovementMethod(ScrollingMovementMethod.getInstance());
//        mEtComment.setMovementMethod(ScrollingMovementMethod.getInstance());

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
            //ShowBigImageActivity.startAction(mContext, mIvHeadPic, mSingleSelectList.get(0).getPath());
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
        String diseaseName = mEtDiseaseName.getText().toString();
        if (TextUtils.isEmpty(diseaseName)){
            UIUtils.showToast(getString(R.string.dis_name));
            return;
        }
        String diseaseType = mEtDiseaseType.getText().toString();
        if (TextUtils.isEmpty(diseaseType)){
            UIUtils.showToast(getString(R.string.dis_subkind));
            return;
        }

        if (!isHeadPicAdd){
            UIUtils.showToast(getString(R.string.dis_pic));
            return;
        }
//        String cause = mEtComment.getText().toString();
//        if (TextUtils.isEmpty(cause)){
//            UIUtils.showToast(getString(R.string.dis_cause));
//            return;
//        }
        String defination = mEtDef.getText().toString();
        if (TextUtils.isEmpty(defination)){
            UIUtils.showToast(getString(R.string.dis_defination));
            return;
        }
        String treatMent = mEtTreatment.getText().toString();
        if (treatMent.isEmpty()){
            UIUtils.showToast(getString(R.string.dis_treatment));
            return;
        }

        mBaikeDiseaseBean.setBig_category(diseaseType);
        mBaikeDiseaseBean.setDiseaseName(diseaseName);
        mBaikeDiseaseBean.setSymptom(defination);
        mBaikeDiseaseBean.setTreatment(treatMent);

        if (isHavingChanged) {

            mPresenter.doDiseaseBaikeUpdateWithPic(baikeID, diseaseName, diseaseType,  defination, treatMent, mSingleSelectList.get(0).getPath());
        }else {
            mPresenter.doDiseaseBaikeUpdateNoPic(baikeID, diseaseName, diseaseType, defination, treatMent);
        }
    }

    @Override
    public void queryBaikeShowSuccess(BaikeDiseaseBean baikeDiseaseBean) {

        mBaikeDiseaseBean = baikeDiseaseBean;

        mTvAddHeadPic.setVisibility(View.GONE);
        mFlAdd.setVisibility(View.VISIBLE);
        mDelete.setVisibility(View.VISIBLE);
        //加载图片
        if (baikeDiseaseBean != null){
            GlideLoaderUtils.display(this,mIvHeadPic, baikeDiseaseBean.getImage());
            mEtDiseaseType.setText(baikeDiseaseBean.getBig_category());
            mEtDiseaseType.setSelection(baikeDiseaseBean.getBig_category().length());
            mEtDiseaseName.setText(baikeDiseaseBean.getDiseaseName());
            mEtDiseaseName.setSelection(baikeDiseaseBean.getDiseaseName().length());
//            mEtComment.setText(baikeDiseaseBean.getCause());
//            mEtComment.setSelection(baikeDiseaseBean.getCause().length());
            mEtDef.setText(baikeDiseaseBean.getSymptom());
            mEtDef.setSelection(baikeDiseaseBean.getSymptom().length());
            mEtTreatment.setText(baikeDiseaseBean.getTreatment());
            mEtTreatment.setSelection(baikeDiseaseBean.getTreatment().length());

        }
    }

    @Override
    public void queryBaikeShowFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void updateDisSuccess(BaikeDiseaseBean baikeDiseaseBean) {
        EBKDisUpdateBean ebkDisUpdateBean = new EBKDisUpdateBean();
        ebkDisUpdateBean.setIndex(mItemIndex);
        ebkDisUpdateBean.setBaikeDiseaseBean(baikeDiseaseBean);
        mRxManager.post(AppConstant.RX_UPDATE_DISEASE_BAIKE,ebkDisUpdateBean);
        finish();
    }

    @Override
    public void updateDisFailure(String error) {
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
