package com.jit.AgriIn.uinew.first.data.autoctrl;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/11/8.
 * Describe:
 */
public class AutoTimeCtrlPresenter extends BasePresenter<AutoTimeCtrlView> {

    public AutoTimeCtrlPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getAutoTimeStatus(int id){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().getAutoTimeStatus(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getAutoTimeStatusSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().getAutoTimeStatusFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getAutoTimeStatusFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void CtrlAutoTime(int id,String env_type,String time,int opt,int ison_fg){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().ctrlAutoTime(id,env_type,time,opt,ison_fg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().ctrlAutoTimeSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().ctrlAutoTimeFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().ctrlAutoTimeFailure(throwable.getLocalizedMessage());
                });
    }

}
