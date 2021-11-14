package com.jit.AgriIn.uinew.second.rizhi;

import android.annotation.SuppressLint;
import android.util.Log;

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
public class RizhiPresenter extends BasePresenter<RizhiView> {

    public RizhiPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getRizhi(int pageNum){
        getView().showLoadingDialog();
        Log.e("rizhi","处理日志");
        ApiRetrofit.getInstance().getRizhi(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getRizhiSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().getRizhiFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getRizhiFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void deleteRizhi(String ids){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteRizhi(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteRizhiSuccess();
                    }else {
                        getView().deleteRizhiFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteRizhiFailure(throwable.getLocalizedMessage());
                });
    }



}
