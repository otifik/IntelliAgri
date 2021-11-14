package com.jit.AgriIn.uinew.second.reagent;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReagentInputAddPresenter extends BasePresenter<ReagentInputAddView> {
    public ReagentInputAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addReagentInput(String batchNumber,String pond,String reagent,double amount,String unit,String time,String remarks){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addReagentInput(batchNumber, pond, reagent, amount, unit, time, remarks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addReagentSuccess(baseResponse);
                    }else {
                        getView().addReagentFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addReagentFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void getAllUserInputs(String username){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getAllUserInputs(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getAllUserInputsSuccess(baseResponse.getData());
                    }else {
                        getView().getAllUserInputsFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getAllUserInputsFailure(throwable.getLocalizedMessage());
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

    @SuppressLint("CheckResult")
    public void getAllUserFishInput(String username){
        ApiRetrofit.getInstance().getAllUserFishInput(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getAllUserFishInputSuccess(pageResponseBaseResponse.getData());
                    }else {
                        getView().getAllUserFishInputFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().getAllUserFishInputFailure(throwable.getLocalizedMessage()));
    }
}
