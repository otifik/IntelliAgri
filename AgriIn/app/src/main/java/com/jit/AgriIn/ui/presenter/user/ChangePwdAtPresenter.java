package com.jit.AgriIn.ui.presenter.user;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.ui.view.user.IChangePwdAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/31.
 * Describe:
 */
public class ChangePwdAtPresenter extends BasePresenter<IChangePwdAtView> {

    public ChangePwdAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void changePwd(String oldPwd, String newPwd) {
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().changePwd(oldPwd,newPwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(changePwdResponse -> {
                    getView().hideLoadingDialog();
                    if (changePwdResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        UserCache.setUserPwd(newPwd);
                        getView().changePwdSuccess();
                    }else {
                        getView().changePwdFailure(changePwdResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().changePwdFailure(throwable.getLocalizedMessage());
                });
    }
}
