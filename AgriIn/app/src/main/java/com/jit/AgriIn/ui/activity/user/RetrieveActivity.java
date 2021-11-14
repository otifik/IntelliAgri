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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.jit.AgriIn.R;
import com.jit.AgriIn.ui.presenter.user.RetrieveAtPresenter;
import com.jit.AgriIn.ui.view.user.IRetrieveAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.window.KeyboardHelper;
import com.zxl.baselib.widget.EditTextWithDel;
import com.zxl.baselib.widget.PaperButton;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author zxl on 2018/08/25.
 *         discription: 找回(重置)密码 ---
 */
public class RetrieveActivity extends BaseActivity<IRetrieveAtView, RetrieveAtPresenter> implements IRetrieveAtView {

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
    @BindView(R.id.btnNext)
    Button mBtnNext;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_retrieve;
    }

    @Override
    protected RetrieveAtPresenter createPresenter() {
        return new RetrieveAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        initBackGround();
        mPresenter.initHandler();
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
                    KeyboardHelper.hideKeyboard(mBtnNext, getCurrentFocus().getWindowToken());
                }
            }
        });

        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mPbSmsCode.setOnClickListener(v -> mPresenter.sendSMsCode());

        mBtnNext.setOnClickListener(v -> mPresenter.requestNext());
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
    protected void onDestroy() {
        mPresenter.recyclerTimer();
        mPresenter.recyclerHandler();
        if (KeyboardHelper.isKeyboardVisible(this)){
            KeyboardHelper.hideKeyboard(mBtnNext);
        }
        super.onDestroy();
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
    public Button buttonNext() {
        return mBtnNext;
    }

    @Override
    public PaperButton paperButton() {
        return mPbSmsCode;
    }
}
