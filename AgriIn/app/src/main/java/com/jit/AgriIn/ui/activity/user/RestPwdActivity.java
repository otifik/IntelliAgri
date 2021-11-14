package com.jit.AgriIn.ui.activity.user;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.presenter.user.RestPwdAtPresenter;
import com.jit.AgriIn.ui.view.user.IRestPwdAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.window.KeyboardHelper;
import com.zxl.baselib.widget.EditTextWithDel;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author zxl on 2018/08/28.
 *         discription: 重置密码的活动 ---
 */
public class RestPwdActivity extends BaseActivity<IRestPwdAtView, RestPwdAtPresenter> implements IRestPwdAtView {

    @BindView(R.id.pwdIcon)
    ImageView mPwdIcon;
    @BindView(R.id.etPwd)
    EditTextWithDel mEtPwd;
    @BindView(R.id.relaPass)
    RelativeLayout mRelaPass;
    @BindView(R.id.cfPwdIcon)
    ImageView mCfPwdIcon;
    @BindView(R.id.etCfPwd)
    EditTextWithDel mEtCfPwd;
    @BindView(R.id.rlCfPwd)
    RelativeLayout mRlCfPwd;
    @BindView(R.id.btnRegister)
    Button mBtnRegister;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    private String mPhoneNum;

    @BindView(R.id.tvBackToLogin)
    TextView tvBackToLogin;

    @Override
    protected void init() {
        if (getIntent() != null){
            mPhoneNum = getIntent().getStringExtra(AppConstant.UserExtra.PHONE.name());
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_rest_pwd;
    }

    @Override
    protected RestPwdAtPresenter createPresenter() {
        return new RestPwdAtPresenter(this,mPhoneNum);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        initBackGround();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        textChangeListener();
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    KeyboardHelper.hideKeyboard(mBtnRegister, getCurrentFocus().getWindowToken());
                }
            }
        });

        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mBtnRegister.setOnClickListener(v -> mPresenter.requestEnsure());

        tvBackToLogin.setOnClickListener(view -> jumpToActivity(LoginActivity.class));
    }

    private void initBackGround() {
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mContent.setBackground(resource);
            }
        };
        Glide.with(this).load(R.drawable.bg_login)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(23, 2)))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(simpleTarget);
    }

    /**
     * 设置EditText监听     */
    private void textChangeListener() {
        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mRelaPass.setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_black));
            }
        });

        mEtCfPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mRlCfPwd.setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_black));
            }
        });
    }

    @Override
    public EditText editPwd() {
        return mEtPwd;
    }

    @Override
    public EditText editCfPwd() {
        return mEtCfPwd;
    }

    @Override
    public ImageView iconPwd() {
        return mPwdIcon;
    }

    @Override
    public ImageView iconCfPwd() {
        return mCfPwdIcon;
    }


    @Override
    public RelativeLayout rlPwd() {
        return mRelaPass;
    }

    @Override
    public RelativeLayout rlCfPwd() {
        return mRlCfPwd;
    }

    @Override
    public Button buttonEnsure() {
        return mBtnRegister;
    }
}
