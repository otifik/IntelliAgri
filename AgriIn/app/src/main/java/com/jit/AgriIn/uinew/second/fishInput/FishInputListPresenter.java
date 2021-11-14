package com.jit.AgriIn.uinew.second.fishInput;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FishInputListPresenter extends BasePresenter<FishInputListView> {
    public FishInputListPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getUserFishInput(String username,int pageNum,int pageSize){
        ApiRetrofit.getInstance().getUserFishInput(username,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getFishInputSuccess(pageResponseBaseResponse.getData().getFishinputs());
                    }else {
                        getView().getFishInputFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().getFishInputFailure(throwable.getLocalizedMessage()));
    }

    @SuppressLint("CheckResult")
    public void deleteFishInput(int id){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteFishInput(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteFishInputSuccess();
                    }else {
                        getView().deleteFishInputFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteFishInputFailure(throwable.getLocalizedMessage());
                });
    }
}
