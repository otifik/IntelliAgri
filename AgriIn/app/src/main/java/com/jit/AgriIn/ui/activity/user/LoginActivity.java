package com.jit.AgriIn.ui.activity.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.jit.AgriIn.ui.presenter.user.LoginAtPresenter;
import com.jit.AgriIn.ui.view.user.ILoginAtView;
import com.jit.AgriIn.widget.FgPwdSpan;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.LoggerUtils;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.util.window.KeyboardHelper;
import com.zxl.baselib.widget.EditTextWithDel;
import com.zxl.open.constansts.OpenConstant;
import com.zxl.open.factory.OpenBuilder;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author zxl on 2018/08/19.
 *         discription:
 */
public class LoginActivity extends BaseActivity<ILoginAtView, LoginAtPresenter> implements ILoginAtView, IUiListener {

    @BindView(R.id.tvRegister)
    TextView mTvRegister;
    @BindView(R.id.accountIcon)
    ImageView mAccountIcon;
    @BindView(R.id.etAccount)
    EditTextWithDel mEtAccount;
    @BindView(R.id.rlAccount)
    RelativeLayout mRlAccount;
    @BindView(R.id.pwdIcon)
    ImageView mPwdIcon;
    @BindView(R.id.etPwd)
    EditTextWithDel mEtPwd;
    @BindView(R.id.rela_pass)
    RelativeLayout mRelaPass;
    @BindView(R.id.btLogin)
    Button mBtLogin;
    @BindView(R.id.tvForgetPwd)
    TextView mTvForgetPwd;
    @BindView(R.id.ll_login_layer)
    View mLlLoginLayer;
    @BindView(R.id.ib_login_qq)
    ImageView mIbLoginQq;
    @BindView(R.id.ib_login_wx)
    ImageView mIbLoginWx;
    @BindView(R.id.ib_login_zhifu)
    ImageView mIbLoginZhifu;
    @BindView(R.id.ll_login_options)
    LinearLayout mLlLoginOptions;
    @BindView(R.id.ll_login_pull)
    LinearLayout mLlLoginPull;
    @BindView(R.id.content)
    FrameLayout mContent;
    @BindView(R.id.ib_login_weibo)
    ImageView mIbLoginWeibo;
    @BindView(R.id.llBackground)
    LinearLayout mLlBackground;

    private boolean isShowBottom = false;
    private SsoHandler mWeiboSsoHandler;
    private int mOpenType;
    private Tencent mTencent;

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginAtPresenter createPresenter() {
        return new LoginAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        setFgPwd();
//        initBackGround();
        mLlLoginLayer.setVisibility(View.GONE);
    }

    private void initBackGround() {
                SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mLlBackground.setBackground(resource);
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

    private void setFgPwd() {
        SpannableString forgetPwd = new SpannableString(getString(R.string.forget_pwd));
        forgetPwd.setSpan(new FgPwdSpan(),
                0,
                forgetPwd.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvForgetPwd.setText(forgetPwd);
    }

    @Override
    protected void onDestroy() {
        if (KeyboardHelper.isKeyboardVisible(this)){
            KeyboardHelper.hideKeyboard(mBtLogin);
        }
        super.onDestroy();
    }


    @Override
    protected void initData() {


    }

    private void tencentLogin(){
        mOpenType = OpenConstant.TENCENT;
        mTencent = OpenBuilder.with(this)
                .useTencent(OpenConstant.QQ_APP_ID)
                .login(this, new OpenBuilder.Callback() {
                    @Override
                    public void onFailed() {

                    }

                    @Override
                    public void onSuccess() {

                    }
                });

    }

    private void tencentOnActivityResult(Intent data) {
        if (mOpenType == OpenConstant.TENCENT) {
            // 对于tencent
            // 注：在某些低端机上调用登录后，由于内存紧张导致APP被系统回收，登录成功后无法成功回传数据。
            if (mTencent != null) {
                Tencent.handleResultData(data,this);
            }
        }
    }

    /**
     * QQ回调成功
     * 信息主要在Object对象里面
     * @param o
     */
    @Override
    public void onComplete(Object o) {
    }

    /**
     * QQ回调 错误
     * @param uiError
     */
    @Override
    public void onError(UiError uiError) {

    }

    /**
     * QQ回调 取消授权
     */
    @Override
    public void onCancel() {

    }

    private void weiBoLogin(){
        mOpenType = OpenConstant.SINA;
        // 这个赢应该在子线程中
        mWeiboSsoHandler = OpenBuilder.with(this)
                .useWeibo(OpenConstant.WB_APP_KEY)
                .login(new WbAuthListener() {
                    @Override
                    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                        if (oauth2AccessToken.isSessionValid()) {
                            // 这个赢应该在子线程中
                            LoggerUtils.logE("微博返回成功了!");
                        }
                    }

                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 微博
        weiBoOnActivityResult(requestCode,requestCode,data);
        tencentOnActivityResult(data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * weiBo Activity 回调
     *
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */

    private void weiBoOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (mOpenType == OpenConstant.SINA) {
            // SSO 授权回调
            // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
            if (mWeiboSsoHandler != null) {
                // SSO 授权回调
                // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
                mWeiboSsoHandler .authorizeCallBack(requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected void initListener() {
        textListener();

        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    KeyboardHelper.hideKeyboard(mBtLogin, getCurrentFocus().getWindowToken());
                }else {
                    LoggerUtils.logE("软键盘","为空");
                }
            }
        });

        mLlLoginPull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOptions();
            }
        });

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(RegisterTwoActivity.class);
            }
        });

        mIbLoginQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("QQ");
            }
        });

        mIbLoginWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("WeiBo");
            }
        });

        mIbLoginWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("WeiXin");
            }
        });

        mIbLoginZhifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("AliPay");
            }
        });

        mLlLoginLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOptions();
            }
        });

        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loginRequest();
            }
        });

        mTvForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(RetrieveActivity.class);
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (isShowBottom){
            mLlLoginPull.animate().cancel();
            mLlLoginLayer.animate().cancel();
            // 登录选择
            int height = mLlLoginOptions.getHeight();
            float progress = (mLlLoginLayer.getTag() != null
                    && mLlLoginLayer.getTag() instanceof Float) ?
                    (float) mLlLoginLayer.getTag() : 1;
            int time = (int) (360 * progress);
            if (mLlLoginPull.getTag() != null) {
                isShowBottom = false;
                mLlLoginPull.setTag(null);
                glide(height, progress, time);
            }
        }else {
            super.onBackPressedSupport();
        }
    }

    /**
     * EditText布局变动监听
     * 设置文字输入时,背景变为黑色
     */
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
                mRlAccount.setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_black));
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
    }

    /**
     * 知识点 直接可通过 view.animate( )来驱动动画效果
     */
    private void loginOptions(){
        mLlLoginPull.animate().cancel();
        mLlLoginLayer.animate().cancel();
        // 登录选择
        int height = mLlLoginOptions.getHeight();
        float progress = (mLlLoginLayer.getTag() != null
                && mLlLoginLayer.getTag() instanceof Float) ?
                (float) mLlLoginLayer.getTag() : 1;
        int time = (int) (360 * progress);

        if (mLlLoginPull.getTag() != null) {
            // 隐藏底部
            isShowBottom = false;
            mLlLoginPull.setTag(null);
            glide(height, progress, time);
        } else {
            // 展示底部
            mLlLoginPull.setTag(true);
            isShowBottom = true;
            upGlide(height, progress, time);
        }

    }


    /**
     * menu up glide
     *
     * @param height   height
     * @param progress progress
     * @param time     time
     *
     * ViewPropertyAnimator是不需要知道起始点，也不需要过程，它要的仅仅是结果，而且这个结果是一个 绝对结果
     * ViewPropertyAnimator是从控件本身获取到的 ---- 通过 view.animate()
     *
     */
    private void upGlide(int height, float progress, int time) {
    mLlLoginPull.animate()
            .translationYBy(height * progress)
            .translationY(0)
            .setDuration(time)
            .start();
        mLlLoginLayer.animate()
                .alphaBy(1 - progress)
                .alpha(1)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mLlLoginLayer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }
                })
                .start();
    }

    /**
     * menu glide
     *
     * @param height   height
     * @param progress progress
     * @param time     time
     */
    private void glide(int height, float progress, int time) {
        // translationY 终点
        // translationYBy 过程
        mLlLoginPull.animate()
                .translationYBy(height - height * progress)
                .translationY(height)
                .setDuration(time)
                .start();

        mLlLoginLayer.animate()
                .alphaBy(1 * progress)
                .alpha(0)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                        mLlLoginLayer.setVisibility(View.GONE);
                    }
                })
                .start();
    }


    @Override
    public ImageView iconMobile() {
        return mAccountIcon;
    }

    @Override
    public ImageView iconPwd() {
        return mPwdIcon;
    }

    @Override
    public EditText editAccount() {
        return mEtAccount;
    }

    @Override
    public EditText editPwd() {
        return mEtPwd;
    }

    @Override
    public RelativeLayout realAccount() {
        return mRlAccount;
    }

    @Override
    public RelativeLayout realPwd() {
        return mRelaPass;
    }

    @Override
    public Button buttonLogin() {
        return mBtLogin;
    }
}
