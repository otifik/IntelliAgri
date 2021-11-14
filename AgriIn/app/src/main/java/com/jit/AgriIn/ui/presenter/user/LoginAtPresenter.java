package com.jit.AgriIn.ui.presenter.user;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.jit.AgriIn.R;
import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.LoginResponse;
import com.jit.AgriIn.ui.activity.role_expert.RoleExpertActivity;
import com.jit.AgriIn.ui.view.user.ILoginAtView;
import com.jit.AgriIn.uinew.RobotMainActivity;
import com.jit.AgriIn.uinew.role_admin.RoleAdminActivity;
import com.zxl.baselib.http.load.LoaderStyle;
import com.zxl.baselib.http.load.PerfectLoader;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.ui.AnimationHelper;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.ui.UIUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zxl on 2018/8/24.
 *         discription:
 */

public class LoginAtPresenter extends BasePresenter<ILoginAtView> {
    public LoginAtPresenter(BaseActivity context) {
        super(context);
    }
    /**
     * 请求登录
     */
    public void loginRequest(){
        final String account = getView().editAccount().getText().toString();
        final String pwd = getView().editPwd().getText().toString();
        if (TextUtils.isEmpty(account)){
            getView().realAccount().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconMobile().startAnimation(AnimationHelper.shakeAnimation(2));
            // 提示 ----
            mContext.showSnackBar(getView().buttonLogin(),String.format(ResHelper.getString(R.string.snack_bar), ResHelper.getString(R.string.input_phone_extras)));
            return;
        }

        if (TextUtils.isEmpty(pwd)){
            getView().realPwd().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPwd().startAnimation(AnimationHelper.shakeAnimation(2));
            // 提示 -----
            mContext.showSnackBar(getView().buttonLogin(),String.format(ResHelper.getString(R.string.snack_bar), ResHelper.getString(R.string.input_password)));
            return;
        }

        requestLogin();

    }

    /**
     * 请求登录
     * 有网络就登录 ----
     */
    @SuppressLint("CheckResult")
    private void requestLogin(){
        String userName = getView().editAccount().getText().toString();
        String pwd = getView().editPwd().getText().toString();
        PerfectLoader.showLoading(mContext, LoaderStyle.BallScaleRippleMultipleIndicator);
        ApiRetrofit.getInstance().requestLogin(userName,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponseBaseResponse -> {
                    PerfectLoader.stopLoading();
                    if (loginResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        // 执行普通数据的存储----

                        LoginResponse loginResponse = loginResponseBaseResponse.getData();
                        UserCache.save(loginResponse.getImage(),
                                loginResponse.getRegister_time()
                        ,loginResponse.getRole(),
                                mContext.getString(R.string.token_suffix,loginResponse.getToken()),
                                loginResponse.getId(),
                                loginResponse.getUsername());
                        UserCache.setUserPwd(pwd);
                        if (AppConstant.ROLE_CUSTOM.equals(loginResponse.getRole()) ) {
                            mContext.jumpToActivity(RobotMainActivity.class);
                        }else if (AppConstant.ROLE_EXPERT.equals(loginResponse.getRole())){
                            mContext.jumpToActivity(RoleExpertActivity.class);
                        }else if (AppConstant.ROLE_ADMIN.equals(loginResponse.getRole())){
                            mContext.jumpToActivity(RoleAdminActivity.class);
                        }
                        mContext.finish();
                    }else {
                        loginFailure(loginResponseBaseResponse.getMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        PerfectLoader.stopLoading();
                        UIUtils.showToast(throwable.getLocalizedMessage());
                    }
                });
    }

    /**
     * 登录失败 -----
     */
    private void loginFailure(String msg){
        getView().realAccount().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
        getView().iconMobile().startAnimation(AnimationHelper.shakeAnimation(2));
        getView().realPwd().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
        getView().iconPwd().startAnimation(AnimationHelper.shakeAnimation(2));
        mContext.showSnackBar(getView().buttonLogin(),mContext.getString(R.string.snack_bar,msg));
    }
}
