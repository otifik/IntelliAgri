package com.jit.AgriIn.ui.presenter.user;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.jit.AgriIn.R;
import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.activity.user.RestPwdActivity;
import com.jit.AgriIn.ui.view.user.IRetrieveAtView;
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
 * @author zxl on 2018/8/28.
 *         discription: 找回密码的P层
 */

public class RetrieveAtPresenter  extends BasePresenter<IRetrieveAtView> {
    private MyCountTimer mMyCountTimer;
    private volatile boolean mVerifySuccess = false;
    private String mAccount = null;
    private String mConfirmAccount = null;
    private String mCode = null;
    private String mConfirmCode = null;

    public RetrieveAtPresenter(BaseActivity context) {
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
                                doRetrieveNext();
                            }
                        });
                    } else if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        mVerifySuccess = false;
                        UIUtils.postTaskSafely(new Runnable() {
                            @Override
                            public void run() {
                                getView().editCode().setText(null);
                                mContext.showSnackBar(getView().buttonNext(), ResHelper.getString(R.string.snack_bar,
                                        ResHelper.getString(R.string.success_sms_send)));
                            }
                        });
                    }
                }else  {
                    if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        UIUtils.postTaskSafely(() -> mContext.showSnackBar(getView().buttonNext(),String.format(ResHelper.getString(R.string.snack_bar),
                                ResHelper.getString(R.string.error_sms_code))));

                    } else if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码失败
                        Throwable throwable = (Throwable) o;
                        try {
                            JSONObject obj = new JSONObject(throwable.getMessage());
                            final String des = obj.optString("error");
                            if (!TextUtils.isEmpty(des)){
                                UIUtils.postTaskSafely(() -> {
                                    mContext.showSnackBar(getView().buttonNext(), ResHelper.getString(R.string.snack_bar,des));
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
     * 验证成功 活动跳转
     */
    private void doRetrieveNext() {
        recyclerTimer();
        Intent intent = new Intent(mContext, RestPwdActivity.class);
        intent.putExtra(AppConstant.UserExtra.PHONE.name(),mConfirmAccount);
        mContext.jumpToActivity(intent);
    }




    /**
     * 执行验证 进入重置密码界面
     */
    public void requestNext() {
        String mobile = getView().editPhone().getText().toString().trim();
        mAccount = mobile;
        if (TextUtils.isEmpty(mobile)){
            getView().rlPhone().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPhone().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonNext(), ResHelper.getString(R.string.snack_bar,
                    ResHelper.getString(R.string.input_phone_num)));
            return;
        }else if (!CheckUtils.isMobile(mobile)){
            getView().rlPhone().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPhone().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonNext(), ResHelper.getString(R.string.snack_bar,
                    ResHelper.getString(R.string.error_phone_num)));
            return;
        }
        if (!NetworkHelper.isNetworkConnected(mContext)){
            mContext.showSnackBar(getView().buttonNext(), ResHelper.getString
                    (R.string.snack_bar, ResHelper.getString(R.string.tip_network_error)));
            return;
        }

        String code = getView().editCode().getText().toString().trim();
        mCode = code;
        if (TextUtils.isEmpty(code)){
            getView().rlCode().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconCode().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonNext(), ResHelper.getString(R.string.snack_bar, ResHelper.getString(R.string.input_sms_code)));
        }else {
            // 用于测试
            // 如果账号和密码确实时已经匹配过了 那么直接进入-----
            if (mVerifySuccess && mobile.equals(mConfirmAccount) && code.equals(mConfirmCode)){
                doRetrieveNext();
            }else {
                SMSSDK.submitVerificationCode("86", mobile, code);
            }
        }
    }

    /**
     * 发送验证码
     *     在发送之前需要对该手机号码进行判断
     *          ---- 确实是已经注册过的账号 那么就返回true
     *               否则提示用户该用户未曾注册 -----
     */
    public void sendSMsCode() {
        String mobile = getView().editPhone().getText().toString().trim();
        if (TextUtils.isEmpty(mobile)){
            getView().rlPhone().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPhone().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonNext(), ResHelper.getString(R.string.snack_bar,
                    ResHelper.getString(R.string.input_phone_num)));
            return;
        }else if (!CheckUtils.isMobile(mobile)){
            getView().rlPhone().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPhone().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonNext(), ResHelper.getString(R.string.snack_bar,
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
                            doSendSms(mobile);
                        }

                    }else {
                        if (pondMainResponseBaseListResponse.getMsg().equals("用户不存在")){
                            mContext.showSnackBar(getView().buttonNext(),"用户不存在,请先注册");
                        }else {
                            mContext.showSnackBar(getView().buttonNext(),pondMainResponseBaseListResponse.getMsg());
                        }
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    mContext.showSnackBar(getView().buttonNext(),throwable.getLocalizedMessage());
                });
    }

    /**
     * 验证该手机号码是否已经注册
     *
     * 执行网络请求
     */
    private void doDefPhoneHasRegister(String mobile) {
        mContext.showSnackBar(getView().buttonNext(), ResHelper.getString(R.string.snack_bar,
                "您输入的手机号无效,尚未注册过!"));
        doSendSms(mobile);
    }

    /**
     * 执行发送验证码的过程
     */
    private void doSendSms(String mobile){
        if (mMyCountTimer == null) {
            mMyCountTimer = new MyCountTimer(60000, 1000);
        }
        mMyCountTimer.start();
        SMSSDK.getVerificationCode("86",mobile);
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
     * 回收mob的Handler
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
