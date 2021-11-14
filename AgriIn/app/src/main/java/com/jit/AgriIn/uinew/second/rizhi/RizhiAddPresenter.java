package com.jit.AgriIn.uinew.second.rizhi;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 增加塘口的P层
 */
public class RizhiAddPresenter extends BasePresenter<RizhiAddView> {
    public RizhiAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addRizhi(String name,String content,String time,int pondId){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addRizhi(name,content,time,pondId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addRizhiSuccess(baseResponse.getData());
                    }else {
                        getView().addRizhiFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addRizhiFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void getObserveType(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getObserveType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getRizhiTypeSuccess(baseResponse.getData());
                    }else {
                        getView().getRizhiTypeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getRizhiTypeFailure(throwable.getLocalizedMessage());
                });
    }
}
