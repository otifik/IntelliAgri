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
 * Describe:
 */
public class RizhiUpdatePresenter extends BasePresenter<RizhiUpdateView> {

    public RizhiUpdatePresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void updateRizhi(String name,String content,int pondID,int Id){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().updateRizhi(name,content,pondID,Id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateRizhiSuccess(baseResponse.getData());
                    }else {
                        getView().updateRizhiFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateRizhiFailure(throwable.getLocalizedMessage());
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
