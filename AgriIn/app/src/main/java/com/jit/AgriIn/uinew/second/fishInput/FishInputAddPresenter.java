package com.jit.AgriIn.uinew.second.fishInput;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FishInputAddPresenter extends BasePresenter<FishInputAddView> {

    public FishInputAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addFishInput(String type, double amount, String unit, String date, List<String> pond, String batchNumber){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addFishInput(type, amount, unit, date, pond, batchNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addFishInputSuccess(baseResponse);
                    }else {
                        getView().addFishInputFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addFishInputFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void getAllUserPonds(String username){
        ApiRetrofit.getInstance().getAllUserPonds(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getAllUserPondsSuccess(pageResponseBaseResponse.getData());
                    }else {
                        getView().getAllUserPondsFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().getAllUserPondsFailure(throwable.getLocalizedMessage()));
    }

}
