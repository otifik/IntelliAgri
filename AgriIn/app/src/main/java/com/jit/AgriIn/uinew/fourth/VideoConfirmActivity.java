package com.jit.AgriIn.uinew.fourth;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jit.AgriIn.R;
import com.jit.AgriIn.app.MyApp;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.activity.video.api.TokenResponse;
import com.jit.AgriIn.ui.activity.video.api.YSVideoPresenter;
import com.jit.AgriIn.ui.activity.video.api.YSVideoView;
import com.jit.AgriIn.ys.ui.cameralist.EZCameraListActivity;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.CheckUtils;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.app.SharePreferenceUtils;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.util.window.KeyboardHelper;
import com.zxl.baselib.widget.EditTextWithDel;
import com.zxl.baselib.widget.PaperButton;

import java.util.List;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/25.
 *         discription: 找回(重置)密码 ---
 */
public class VideoConfirmActivity extends BaseActivity<YSVideoView, YSVideoPresenter> implements YSVideoView {

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

    private MyCountTimer mMyCountTimer;
    @Override
    protected void init() {
        String tokenStr = SharePreferenceUtils.getInstance(this).getString(AppConstant.CAMERA_TOKEN, "");
        if (!tokenStr.isEmpty()){
            MyApp.getOpenSDK().setAccessToken(tokenStr);
            Intent toIntent = new Intent(this, EZCameraListActivity.class);
            toIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(toIntent);
            finish();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_retrieve;
    }

    @Override
    protected YSVideoPresenter createPresenter() {
        return new YSVideoPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {


        mPbSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = mEtAccount.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)){
                    UIUtils.showToast("请填写手机号码");
                    return;
                }else if (!CheckUtils.isMobile(mobile)){
                    UIUtils.showToast("请填写规范的手机号码");
                    return;
                }
                mPresenter.getToken(mobile);
            }
        });


        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = mEtAccount.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)){
                    UIUtils.showToast("请填写手机号码");
                    return;
                }else if (!CheckUtils.isMobile(mobile)){
                    UIUtils.showToast("请填写规范的手机号码");
                    return;
                }
                if (!NetworkHelper.isNetworkConnected(mContext)){
                    UIUtils.showToast(ResHelper.getString(R.string.tip_network_error));
                    return;
                }

                String code = mSmsCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    UIUtils.showToast(ResHelper.getString(R.string.input_sms_code));
                }else {
                    mPresenter.getTokenBySms(mobile,code);
                }
            }
        });

//        mContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (getCurrentFocus() != null) {
//                    KeyboardHelper.hideKeyboard(mBtnNext, getCurrentFocus().getWindowToken());
//                }
//            }
//        });
//
//        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());
//
//        mPbSmsCode.setOnClickListener(v -> mPresenter.sendSMsCode());
//
//        mBtnNext.setOnClickListener(v -> mPresenter.requestNext());
    }



    @Override
    protected void onDestroy() {
        recyclerTimer();
        if (KeyboardHelper.isKeyboardVisible(this)){
            KeyboardHelper.hideKeyboard(mBtnNext);
        }
        super.onDestroy();
    }

    /**
     * 执行发送验证码的过程
     */
    private void doSendSms(){
        if (mMyCountTimer == null) {
            mMyCountTimer = new MyCountTimer(60000, 1000);
        }
        mMyCountTimer.start();
    }

    public  class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            mPbSmsCode.setText((millisUntilFinished / 1000) +"秒后重发");
            mPbSmsCode.setCanDispatch(false);
        }
        @Override
        public void onFinish() {
            mPbSmsCode.setText(ResHelper.getString(R.string.sms_send_again));
            mPbSmsCode.setCanDispatch(true);
        }
    }


    /**
     * 回收定时器
     */
    public void recyclerTimer() {
        if (mMyCountTimer != null){
            mMyCountTimer.onFinish();
            mMyCountTimer.cancel();
        }
    }

    @Override
    public void getTokenSuccess(String msg, TokenResponse.DataEntity dataEntity) {
        Log.e("token msg",msg);
        if (msg.contains("成功")){
            MyApp.getOpenSDK().setAccessToken(dataEntity.getAccessToken());
            Intent toIntent = new Intent(this, EZCameraListActivity.class);
            toIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SharePreferenceUtils.getInstance(this).putString(AppConstant.CAMERA_TOKEN,dataEntity.getAccessToken());
            startActivity(toIntent);
            finish();
        }else if (msg.contains("请输入验证码")){
            doSendSms();
        }else {
            UIUtils.showToast("数据无效");
        }
    }

    @Override
    public void getTokenFailure(String msg) {
        UIUtils.showToast(msg);
    }

    @Override
    public void getCameraListSuccess(List<EZDeviceInfo> deviceInfos) {

    }

    @Override
    public void getCameraListFailure(String msg) {

    }

}
