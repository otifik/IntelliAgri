package com.jit.AgriIn.ui.activity.user;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.jit.AgriIn.ui.presenter.user.RgAtTwoPresenter;
import com.jit.AgriIn.ui.view.user.IRgAtTwoView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.window.DisplayHelper;
import com.zxl.baselib.util.window.KeyboardHelper;
import com.zxl.baselib.widget.EditTextWithDel;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author zxl on 2018/08/27.
 *         discription:
 */
public class RegisterTwoActivity extends BaseActivity<IRgAtTwoView, RgAtTwoPresenter> implements IRgAtTwoView {
    @BindView(R.id.accountIcon)
    ImageView mAccountIcon;
    @BindView(R.id.etAccount)
    EditTextWithDel mEtAccount;
    @BindView(R.id.account)
    RelativeLayout mAccount;
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

    @BindView(R.id.tvBackToLogin)
    TextView tvBackToLogin;
    @BindView(R.id.rgRole)
    RadioGroup rgRole;
    @BindView(R.id.rbUser)
    RadioButton rbUser;
    @BindView(R.id.rbExpert)
    RadioButton rbExpert;

    private boolean isFirst = true;
    private int mButtonHeight;
    private ViewTreeObserver mTreeObserver;
    private ViewTreeObserver.OnGlobalLayoutListener mListener;
    /**
     * 用户的密码
     */
    private String mPhone;

    @Override
    protected void init() {
        if (getIntent()!= null){
            mPhone = getIntent().getStringExtra(AppConstant.UserExtra.PHONE.name());
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_two;
    }

    @Override
    protected RgAtTwoPresenter createPresenter() {
        return new RgAtTwoPresenter(this,mPhone);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        buttonBeyondKeyboardLayout(mContent,mBtnRegister);
//        initBackGround();
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

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        textListener();
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    KeyboardHelper.hideKeyboard(mBtnRegister, getCurrentFocus().getWindowToken());
                }
            }
        });

        mIvToolbarNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterTwoActivity.this.onBackPressed();
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.requestRegister();
            }
        });

        tvBackToLogin.setOnClickListener(view -> jumpToActivity(LoginActivity.class));
    }

    private void textListener() {
        mEtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAccount.setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_black));
            }
        });

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
    public EditText editAccount() {
        return mEtAccount;
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
    public ImageView iconAccount() {
        return mAccountIcon;
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
    public RelativeLayout rlAccount() {
        return mAccount;
    }

    @Override
    public Button buttonRegister() {
        return mBtnRegister;
    }

    @Override
    public RadioGroup rgRole() {
        return rgRole;
    }


    private void buttonBeyondKeyboardLayout(final View root, final View button) {
        mListener = () -> {
            Rect rect = new Rect();
            root.getWindowVisibleDisplayFrame(rect);
            int rootInvisibleHeight = root.getHeight() - rect.bottom;
            if (rootInvisibleHeight > 100) {
                int[] location = new int[2];
                button.getLocationInWindow(location);
                int buttonHeight = button.getHeight() + location[1];
                if (rect.bottom > buttonHeight){
                    if (mListener != null && mTreeObserver.isAlive()) {
                        mTreeObserver.removeOnGlobalLayoutListener(mListener);
                    }
                    mListener = null;
                } else {
                    if (isFirst) {
                        mButtonHeight = (buttonHeight - rect.bottom + DisplayHelper.px2Dp(20));
                        isFirst = false;
                    }
                    root.scrollTo(0, mButtonHeight);
                }


            } else {
                root.scrollTo(0, 0);
                isFirst = true;
            }
        };
        mTreeObserver = root.getViewTreeObserver();
        mTreeObserver.addOnGlobalLayoutListener(mListener);
    }

    @Override
    public void onBackPressedSupport() {
        showMaterialDialog(getString(R.string.info_tips),
                getString(R.string.register_content),
                getString(R.string.dialog_ensure),
                getString(R.string.dialog_cancel),
                (dialog, which) -> {
                    dialog.dismiss();
                    RegisterTwoActivity.super.onBackPressedSupport();
                }, (dialog, which) -> dialog.dismiss());
    }

    @Override
    protected void onDestroy() {
        if (KeyboardHelper.isKeyboardVisible(this)){
            KeyboardHelper.hideKeyboard(mBtnRegister);
        }
        if (mListener != null && mTreeObserver != null && mTreeObserver.isAlive()) {
            mTreeObserver.removeOnGlobalLayoutListener(mListener);
        }
        super.onDestroy();
    }
}
