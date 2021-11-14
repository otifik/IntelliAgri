package com.jit.AgriIn.ui.presenter.culture;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.view.culture.LogDdListAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/11.
 * Describe:
 */
public class LogDdListAtPresenter extends BasePresenter<LogDdListAtView> {

    public LogDdListAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void queryAllLog(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getAllLogs().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(downLogResponse -> {
                    getView().hideLoadingDialog();
                    if (downLogResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().queryDdListSuccess(downLogResponse.getData());
                    }else {
                        getView().queryDdListFailure(downLogResponse.getMsg());
                    }
                }, (Throwable throwable) -> {
                    getView().hideLoadingDialog();
                    getView().queryDdListFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void deleleteSmLogs(String substring) {
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleleSmLogs(substring)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getView().hideLoadingDialog();
                    if (response.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().delDdListSuccess();
                    }else{
                        getView().delDdListFailure(response.getMsg());
                    }
                }, (Throwable throwable) -> {
                    getView().hideLoadingDialog();
                    getView().delDdListFailure(throwable.getLocalizedMessage());
                });
    }
}
