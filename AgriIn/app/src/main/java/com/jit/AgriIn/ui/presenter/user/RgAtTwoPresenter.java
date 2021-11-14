package com.jit.AgriIn.ui.presenter.user;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.jit.AgriIn.R;
import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.request.RegisterRequest;
import com.jit.AgriIn.ui.activity.user.LoginActivity;
import com.jit.AgriIn.ui.view.user.IRgAtTwoView;
import com.zxl.baselib.http.load.LoaderStyle;
import com.zxl.baselib.http.load.PerfectLoader;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.AnimationHelper;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.ui.UIUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zxl on 2018/8/27.
 *         discription:
 */

public class RgAtTwoPresenter extends BasePresenter<IRgAtTwoView> {
    private String mPhone;
    public RgAtTwoPresenter(BaseActivity context, String phone) {
        super(context);
        mPhone = phone;
    }


    /**
     * 请求注册先判断网络
     */
    public void requestRegister() {
        if (!NetworkHelper.isNetworkAvailable(mContext)){
            mContext.showSnackBar(getView().buttonRegister(),String.format(ResHelper.getString(R.string.snack_bar)
                    , ResHelper.getString(R.string.input_password)));
            return;
        }
        defData();
    }

    /**
     * 判断数据是否为空 或者设置的2次密码是否一致
     */
    private void defData() {
        String userName = getView().editAccount().getText().toString().trim();
        if (TextUtils.isEmpty(userName)){
            getView().rlAccount().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconAccount().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonRegister(),String.format(ResHelper.getString(R.string.snack_bar)
                    , ResHelper.getString(R.string.input_username)));
            return;
        }
        String pwd = getView().editPwd().getText().toString().trim();
        if (TextUtils.isEmpty(pwd)){
            getView().rlPwd().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPwd().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonRegister(),String.format(ResHelper.getString(R.string.snack_bar)
                    , ResHelper.getString(R.string.input_password)));
            return;
        }
        String cfPwd = getView().editCfPwd().getText().toString().trim();
        if (cfPwd.isEmpty()){
            getView().rlCfPwd().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconCfPwd().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonRegister(),String.format(ResHelper.getString(R.string.snack_bar)
                    , ResHelper.getString(R.string.input_confirm_password)));
            return;
        }

        if (!cfPwd.equals(pwd)){
            getView().rlPwd().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPwd().startAnimation(AnimationHelper.shakeAnimation(2));
            getView().rlCfPwd().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconCfPwd().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonRegister(),String.format(ResHelper.getString(R.string.snack_bar)
                    , ResHelper.getString(R.string.error_twice_pwd)));
            return;
        }

        String roleString = "";


        switch (getView().rgRole().getCheckedRadioButtonId()){
            case R.id.rbUser:
                roleString = "ROLE_USER";
                break;
            case R.id.rbExpert:
                roleString = "ROLE_EXPERT";
                break;
        }


        doRegisterRequest(userName,pwd,roleString);
    }

    /**
     * 更具电话 - 用户名 - 密码 进行注册请求
     * @param userName
     * @param pwd
     */
    @SuppressLint("CheckResult")
    private void doRegisterRequest(String userName, String pwd,String role) {
        PerfectLoader.showLoading(mContext, LoaderStyle.BallScaleRippleMultipleIndicator);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(userName);
        registerRequest.setPassword(pwd);
        registerRequest.setRole(role);
        ApiRetrofit.getInstance().requestRegister(registerRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    PerfectLoader.stopLoading();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        mContext.jumpToActivity(LoginActivity.class);
                    }else {
                        UIUtils.showToast(baseResponse.getMsg());
                    }
                }, throwable -> {
                    PerfectLoader.stopLoading();
                    UIUtils.showToast(throwable.getLocalizedMessage());
                });

        // 注册成功:
    }
}
