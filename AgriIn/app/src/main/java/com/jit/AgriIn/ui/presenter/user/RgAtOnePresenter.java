package com.jit.AgriIn.ui.presenter.user;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;

import com.jit.AgriIn.R;
import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.activity.user.RegisterTwoActivity;
import com.jit.AgriIn.ui.view.user.IRgAtOneView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.CheckUtils;
import com.zxl.baselib.util.LoggerUtils;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.AnimationHelper;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.ui.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zxl on 2018/8/24.
 *         discription:
 */

public class RgAtOnePresenter extends BasePresenter<IRgAtOneView> {
    private MyCountTimer mMyCountTimer;
    private volatile boolean mVerifySuccess = false;
    private String mAccount = null;
    private String mConfirmAccount = null;
    private String mCode = null;
    private String mConfirmCode = null;
    public RgAtOnePresenter(BaseActivity context) {
        super(context);
    }

    public void initHandler() {
        SMSSDK.registerEventHandler(new EventHandler(){
            @Override
            public void afterEvent(int i, int i1, Object o) {
                if ((i1 == SMSSDK.RESULT_COMPLETE)) {
                    if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        mVerifySuccess = true;
                        mConfirmAccount = mAccount;
                        mConfirmCode = mCode;
                        LoggerUtils.logE("验证",mVerifySuccess+"------------");
                        UIUtils.postTaskSafely(new Runnable() {
                            @Override
                            public void run() {
                                recyclerTimer();
                                doRegisterNext();
                            }
                        });
                    } else if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        mVerifySuccess = false;
                        UIUtils.postTaskSafely(new Runnable() {
                            @Override
                            public void run() {
                                getView().editCode().setText(null);
                                mContext.showSnackBar(getView().buttonRegister(), ResHelper.getString(R.string.snack_bar,
                                        ResHelper.getString(R.string.success_sms_send)));
                            }
                        });
                    }
                }else  {
                    if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        UIUtils.postTaskSafely(() -> mContext.showSnackBar(getView().buttonRegister(),String.format(ResHelper.getString(R.string.snack_bar),
                                ResHelper.getString(R.string.error_sms_code))));

                    } else if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码失败
                        Throwable throwable = (Throwable) o;
                        Log.e("结果",throwable.getMessage()+" ");
                        try {
                            if (null == throwable.getMessage()){
                                UIUtils.postTaskSafely(() -> {
                                    mContext.showSnackBar(getView().buttonRegister(), "其他错误");
                                    recyclerTimer();
                                });
                                return;
                            }
                            JSONObject obj = new JSONObject(throwable.getMessage());
                            final String des = obj.optString("error");
                            if (!TextUtils.isEmpty(des)){
                                UIUtils.postTaskSafely(() -> {
                                    mContext.showSnackBar(getView().buttonRegister(), ResHelper.getString(R.string.snack_bar,des));
                                    recyclerTimer();
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }});
    }


    /**
     * 进行下一步的注册操作
     */
    private void doRegisterNext() {
        if (!getView().cbAgree().isChecked()){
            mContext.showSnackBar(getView().buttonRegister(), ResHelper.getString(R.string.snack_bar, ResHelper.getString(R.string.app_need_your_protocol)));
            return;
        }

        recyclerTimer();
        Intent intent = new Intent(mContext, RegisterTwoActivity.class);
        intent.putExtra(AppConstant.UserExtra.PHONE.name(),mConfirmAccount);
        mContext.jumpToActivity(intent);
    }

    /**
     * 发送验证码的处理
     */
    public void requestSms() {

        String mobile = getView().editPhone().getText().toString().trim();
        if (TextUtils.isEmpty(mobile)){
            getView().rlPhone().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPhone().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonRegister(), ResHelper.getString(R.string.snack_bar,
                    ResHelper.getString(R.string.input_phone_num)));
            return;
        }else if (!CheckUtils.isMobile(mobile)){
            getView().rlPhone().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPhone().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonRegister(), ResHelper.getString(R.string.snack_bar,
                    ResHelper.getString(R.string.error_phone_num)));
            return;
        }

        getView().showLoadingDialog();
        ApiRetrofit.getInstance().userCheck(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pondMainResponseBaseListResponse -> {
                    getView().hideLoadingDialog();
                    if (pondMainResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
//                        getView().querySelfInfoSuccess(pondMainResponseBaseListResponse.getData());
                        boolean isOld = (boolean) pondMainResponseBaseListResponse.getData();
                        if (isOld) {
                            mContext.showSnackBar(getView().buttonRegister(), "该用户已存在");
                        }

                    }else {
                        if (pondMainResponseBaseListResponse.getMsg().equals("用户不存在")){
                            if (mMyCountTimer == null) {
                                mMyCountTimer = new MyCountTimer(60000, 1000);
                            }
                            mMyCountTimer.start();
                            SMSSDK.getVerificationCode("86", mobile);
                        }else {
                            mContext.showSnackBar(getView().buttonRegister(),pondMainResponseBaseListResponse.getMsg());
                        }
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    mContext.showSnackBar(getView().buttonRegister(),throwable.getLocalizedMessage());
                });
    }

    public void requestRegister(){
        String mobile = getView().editPhone().getText().toString().trim();
        mAccount = mobile;
        if (TextUtils.isEmpty(mobile)){
            getView().rlPhone().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPhone().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonRegister(), ResHelper.getString(R.string.snack_bar,
                    ResHelper.getString(R.string.input_phone_num)));
            return;
        }else if (!CheckUtils.isMobile(mobile)){
            getView().rlPhone().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPhone().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonRegister(), ResHelper.getString(R.string.snack_bar,
                    ResHelper.getString(R.string.error_phone_num)));
            return;
        }
        if (!NetworkHelper.isNetworkConnected(mContext)){
            mContext.showSnackBar(getView().buttonRegister(), ResHelper.getString(R.string.snack_bar, ResHelper.getString(R.string.tip_network_error)));
            return;
        }

        String code = getView().editCode().getText().toString().trim();
        mCode = code;
        if (TextUtils.isEmpty(code)){
            getView().rlCode().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconCode().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonRegister(), ResHelper.getString(R.string.snack_bar, ResHelper.getString(R.string.input_sms_code)));
        }else {
            // 用于测试
            // 如果账号和密码确实时已经匹配过了 那么直接进入-----
            if (mVerifySuccess && mobile.equals(mConfirmAccount) && code.equals(mConfirmCode)){
                doRegisterNext();
            }else {
                SMSSDK.submitVerificationCode("86", mobile, code);
            }
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

    /**
     * 回收Handler
     */
    public void recyclerHandler() {
        SMSSDK.unregisterAllEventHandler();
    }


    public  class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            getView().paperButton().setText((millisUntilFinished / 1000) +"秒后重发");
            getView().paperButton().setCanDispatch(false);
        }
        @Override
        public void onFinish() {
            getView().paperButton().setText(ResHelper.getString(R.string.sms_send_again));
            getView().paperButton().setCanDispatch(true);
        }
    }

}
