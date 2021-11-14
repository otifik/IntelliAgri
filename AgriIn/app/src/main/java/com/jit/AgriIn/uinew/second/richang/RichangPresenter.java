package com.jit.AgriIn.uinew.second.richang;

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
public class RichangPresenter extends BasePresenter<RichangView> {

    public RichangPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getRicahng(int pageNum){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getThrow(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getRichangSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().getRichangFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getRichangFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void getRicahngByUser(int pageNum,String name){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getThrowByUser(pageNum,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getRichangSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().getRichangFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getRichangFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void deleteRichang(String ids){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteThrow(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteRichangSuccess();
                    }else {
                        getView().deleteRichangFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteRichangFailure(throwable.getLocalizedMessage());
                });
    }



}
