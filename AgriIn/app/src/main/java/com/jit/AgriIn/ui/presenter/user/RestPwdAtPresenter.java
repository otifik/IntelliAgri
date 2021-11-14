package com.jit.AgriIn.ui.presenter.user;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.jit.AgriIn.R;
import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.ui.activity.user.LoginActivity;
import com.jit.AgriIn.ui.view.user.IRestPwdAtView;
import com.zxl.baselib.bean.response.BaseResponse;
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
 * @author zxl on 2018/8/28.
 *         discription:
 */

public class RestPwdAtPresenter extends BasePresenter<IRestPwdAtView> {
    private String phoneNum;
    public RestPwdAtPresenter(BaseActivity context, String phoneNum) {
        super(context);
        this.phoneNum = phoneNum;
    }


    /**
     * 确认修改密码
     */
    public void requestEnsure() {
        String pwd = getView().editPwd().getText().toString().trim();
        if (TextUtils.isEmpty(pwd)){
            getView().rlPwd().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPwd().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonEnsure(),String.format(ResHelper.getString(R.string.snack_bar)
                    , ResHelper.getString(R.string.input_password)));
            return;
        }
        String cfPwd = getView().editCfPwd().getText().toString().trim();
        if (cfPwd.isEmpty()){
            getView().rlCfPwd().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconCfPwd().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonEnsure(),String.format(ResHelper.getString(R.string.snack_bar)
                    , ResHelper.getString(R.string.input_confirm_password)));
            return;
        }

        if (!cfPwd.equals(pwd)){
            getView().rlPwd().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconPwd().startAnimation(AnimationHelper.shakeAnimation(2));
            getView().rlCfPwd().setBackground(ResHelper.getDrawable(R.drawable.bg_border_color_cutmaincolor));
            getView().iconCfPwd().startAnimation(AnimationHelper.shakeAnimation(2));
            mContext.showSnackBar(getView().buttonEnsure(),String.format(ResHelper.getString(R.string.snack_bar)
                    , ResHelper.getString(R.string.error_twice_pwd)));
            return;
        }
        doRegisterRequest(phoneNum,pwd);
    }

    /**
     * 这里可以考虑登陆界面未 singleTask 即任务栈复用模式
     * @param phoneNum
     * @param pwd
     */
    @SuppressLint("CheckResult")
    private void doRegisterRequest(String phoneNum, String pwd) {
        //进行重置密码的操作
        PerfectLoader.showLoading(mContext, LoaderStyle.BallScaleRippleMultipleIndicator);
        ApiRetrofit.getInstance().forgetPass(phoneNum,pwd,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        PerfectLoader.stopLoading();
                        if (baseResponse.getCode() == 1){
                            // 执行成功 --- 那么就是跳转了
                            boolean isReset = (boolean) baseResponse.getData();
                            if (isReset){
                                mContext.showSnackBar(getView().buttonEnsure(),"重置密码成功");
                                UserCache.setUserPwd(pwd);
                                mContext.jumpToActivity(LoginActivity.class);
                                mContext.finish();
                            }else {
                                mContext.showSnackBar(getView().buttonEnsure(),"重置失败");
                            }


                        }else {
                            mContext.showSnackBar(getView().buttonEnsure(),baseResponse.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        PerfectLoader.stopLoading();
                        UIUtils.showToast(throwable.getLocalizedMessage());
                    }
                });



    }
}
