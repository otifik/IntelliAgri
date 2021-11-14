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
import com.jit.AgriIn.model.response.BaikeDrugBean;
import com.jit.AgriIn.ui.presenter.expert_baike.EBKDrugAddPresenter;
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
 * @author crazyZhangxl on 2018-10-29 15:38:29.
 * Describe:
 */

public class EBKDrugAddActivity extends BaseActivity<EBKAddAtView, EBKDrugAddPresenter> implements EBKAddAtView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.tvDiseaseName)
    TextView mTvDiseaseName;
    @BindView(R.id.etDiseaseName)
    EditText mEtDiseaseName;
    @BindView(R.id.tvDiseaseType)
    TextView mTvDiseaseType;
    @BindView(R.id.etDiseaseType)
    EditText mEtDiseaseType;
//    @BindView(R.id.ivHeadPic)
//    ImageView mIvHeadPic;
//    @BindView(R.id.cv)
//    CardView mCv;
//    @BindView(R.id.flAdd)
//    FrameLayout mFlAdd;
//    @BindView(R.id.delete)
//    ImageView mDelete;
//    @BindView(R.id.tvAddHeadPic)
//    TextView mTvAddHeadPic;
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
//    @BindView(R.id.tvAddress)
//    TextView mTvAddress;
//    @BindView(R.id.etAddress)
//    EditText mEtAddress;
    @BindView(R.id.etDef)
    EditText mEtDef;


    public List<LocalMedia> mSingleSelectList = new ArrayList<>();
    private boolean isHeadPicAdd = false;
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ebkdrug_add;
    }

    @Override
    protected EBKDrugAddPresenter createPresenter() {
        return new EBKDrugAddPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加药品百科");
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

    private void doCheckAndSubmit() {
        String drugName = mEtDiseaseName.getText().toString();
        if (TextUtils.isEmpty(drugName)){
            UIUtils.showToast(getString(R.string.drug_name));
            return;
        }
        String drugType = mEtDiseaseType.getText().toString();
        if (TextUtils.isEmpty(drugType)){
            UIUtils.showToast(getString(R.string.drug_subkind));
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
            UIUtils.showToast(getString(R.string.pls_bk_company));
            return;
        }

//        String telName = mEtTeiLphone.getText().toString();
//        if (TextUtils.isEmpty(telName)){
//            UIUtils.showToast(getString(R.string.pls_bk_tel));
//            return;
//        }

//        String address = mEtAddress.getText().toString();
//        if (TextUtils.isEmpty(address)){
//            UIUtils.showToast(getString(R.string.pls_bk_address));
//            return;
//        }

        String def = mEtDef.getText().toString();
        if (TextUtils.isEmpty(def)){
            UIUtils.showToast(getString(R.string.drug_defination));
            return;
        }

        BaikeDrugBean baikeDrugBean = new BaikeDrugBean();
        baikeDrugBean.setName(drugName);
        baikeDrugBean.setType(drugType);
        baikeDrugBean.setFromSource(company);
        baikeDrugBean.setContent(def);

        mPresenter.doDrugAddRequest(baikeDrugBean);

    }

    @Override
    public void ebkAddSuccess() {
        // 发送刷新疾病百科
        mRxManager.post(AppConstant.RX_ADD_DRUG_BAIKE,AppConstant.RX_POST_SUCCESS);
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
