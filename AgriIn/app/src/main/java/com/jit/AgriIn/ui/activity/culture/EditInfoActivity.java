package com.jit.AgriIn.ui.activity.culture;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.ui.UIUtils;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-11 16:01:06.
 * Describe: 编辑框
 */

public class EditInfoActivity extends BaseActivity {


    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.edContent)
    EditText mEdContent;
    @BindView(R.id.ivDelete)
    ImageView mIvDelete;

    private int mOrigin;
    private String mContent;
    private String mHint;
    @Override
    protected void init() {
        if (getIntent().hasExtra(AppConstant.CONTENT_FORM_FILE_LOG)){
            mOrigin = 1;
            mContent = getIntent().getStringExtra(AppConstant.CONTENT_FORM_FILE_LOG);
            mHint = getString(R.string.str_hint_file);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_edit_info;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (mOrigin == 1){
            mTvToolbarTitle.setText(R.string.edit_file);
        }
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(ResHelper.getString(R.string.save));
        mEdContent.setHint(mHint);
        if (!TextUtils.isEmpty(mContent)) {
            mEdContent.setText(mContent);
            mEdContent.setSelection(mContent.length());
            mIvDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mIvDelete.setOnClickListener(v -> mEdContent.setText(""));

        mEdContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(mEdContent.getText().toString().trim())){
                    mIvDelete.setVisibility(View.GONE);
                }else {
                    mIvDelete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTvPublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = mEdContent.getText().toString().trim();
                if (mOrigin == 1){
                    if (TextUtils.isEmpty(result)){
                        UIUtils.showToast(getString(R.string.str_toast_check_file));
                    }else {
                        Intent intent = new Intent();
                        intent.putExtra(AppConstant.RESULT_FROM_EDIT,result);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            }
        });
    }

}
