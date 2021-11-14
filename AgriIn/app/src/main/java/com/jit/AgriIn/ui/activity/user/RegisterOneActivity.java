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
import android.widget.CheckBox;
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
import com.jit.AgriIn.ui.presenter.user.RgAtOnePresenter;
import com.jit.AgriIn.ui.view.user.IRgAtOneView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.window.DisplayHelper;
import com.zxl.baselib.util.window.KeyboardHelper;
import com.zxl.baselib.widget.EditTextWithDel;
import com.zxl.baselib.widget.PaperButton;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author zxl on 2018/08/24.
 *         discription: 注册第一步 ----
 *         在通过手机号码注册之前  需要校验该手机号是否已被注册
 */
public class RegisterOneActivity extends BaseActivity<IRgAtOneView, RgAtOnePresenter> implements IRgAtOneView {
    @BindView(R.id.accountIcon)
    ImageView mAccountIcon;
    @BindView(R.id.etAccount)
    EditTextWithDel mEtAccount;
    @BindView(R.id.account)
    RelativeLayout mAccount;
    @BindView(R.id.keyIcon)
    ImageView mKeyIcon;
    @BindView(R.id.smsCode)
    EditTextWithDel mSmsCode;
    @BindView(R.id.rlRecode)
    RelativeLayout mRlRecode;
    @BindView(R.id.pbSmsCode)
    PaperButton mPbSmsCode;
    @BindView(R.id.cbProtocol)
    CheckBox mCbProtocol;
    @BindView(R.id.tvProtocol)
    TextView mTvProtocol;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;

    private boolean isFirst = true;
    private int mButtonHeight;
    private ViewTreeObserver mTreeObserver;
    private ViewTreeObserver.OnGlobalLayoutListener mListener;
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_one;
    }

    @Override
    protected RgAtOnePresenter createPresenter() {
        return new RgAtOnePresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPresenter.initHandler();
        buttonBeyondKeyboardLayout(mContent,mBtnNext);
//        initBackGround();
    }

    @Override
    protected void initData() {

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
    protected void initListener() {
        textChangeListener();

        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    KeyboardHelper.hideKeyboard(mBtnNext, getCurrentFocus().getWindowToken());
                }
            }
        });

        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        /* 发送验证码点击事件*/
        mPbSmsCode.setOnClickListener(v -> mPresenter.requestSms());

        /* 注册点击事件*/
        mBtnNext.setOnClickListener(v -> mPresenter.requestRegister());

//        /* 服务条款点击事件*/
//        mTvProtocol.setOnClickListener(v ->
//                jumpToActivity(RegisterProtocolActivity.class));
    }

    private void textChangeListener() {
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

        mSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mRlRecode.setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_black));
            }
        });

    }

    @Override
    public EditText editPhone() {
        return mEtAccount;
    }

    @Override
    public EditText editCode() {
        return mSmsCode;
    }


    @Override
    public ImageView iconPhone() {
        return mAccountIcon;
    }

    @Override
    public ImageView iconCode() {
        return mKeyIcon;
    }


    @Override
    public RelativeLayout rlPhone() {
        return mAccount;
    }

    @Override
    public RelativeLayout rlCode() {
        return mRlRecode;
    }


    @Override
    public Button buttonRegister() {
        return mBtnNext;
    }

    @Override
    public CheckBox cbAgree() {
        return mCbProtocol;
    }

    @Override
    public PaperButton paperButton() {
        return mPbSmsCode;
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
    protected void onDestroy() {
        mPresenter.recyclerTimer();
        mPresenter.recyclerHandler();
        if (KeyboardHelper.isKeyboardVisible(this)){
            KeyboardHelper.hideKeyboard(mBtnNext);
        }
        if (mListener != null && mTreeObserver != null && mTreeObserver.isAlive()) {
            mTreeObserver.removeOnGlobalLayoutListener(mListener);
        }
        super.onDestroy();
    }
}
