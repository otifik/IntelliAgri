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
public class AutoCtrlPresenter extends BasePresenter<AutoCtrlView> {

    public AutoCtrlPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getAutoStatus(int id){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().getAutoEquipsStatus(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getAutoStatusSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().getAutoStatusFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getAutoStatusFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void CtrlAuto(int id,String env_type,int param_id,float wnup,float wndw,float actup,float actdw,int ison_fg){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().ctrlAuto(id,env_type,param_id,wnup,wndw,actup,actdw,ison_fg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().ctrlAutoSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().ctrlAutoFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().ctrlAutoFailure(throwable.getLocalizedMessage());
                });
    }

}
