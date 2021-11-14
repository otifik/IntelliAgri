package com.jit.AgriIn.uinew.first.data;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FishPondAddPresenter extends BasePresenter<FishPondAddView> {
    public FishPondAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addFishPond(String pondName, double length, double width, double depth, String orientation, double longitude, double latitude, String address,String product) {
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addFishPond(pondName,length,width,depth,orientation,longitude,latitude,address,product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addFishPondSuccess(baseResponse);
                    }else {
                        getView().addFishPondFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addFishPondFailure(throwable.getLocalizedMessage());
                });
    }
}
