package com.jit.AgriIn.uinew.second.feeding;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedingFoodPresenter extends BasePresenter<FeedingFoodView> {

    public FeedingFoodPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addFeedingFood(String templateName, String batchNumber, String pond, String food, double amount, String unit, String time, String remarks){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addFeedingFood(templateName, batchNumber, pond, food, amount, unit, time, remarks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addFeedingFoodSuccess(baseResponse);
                    }else {
                        getView().addFeedingFoodFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addFeedingFoodFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void getAllUserFeedingTemplate(String username){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getAllUserFeedingTemplate(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getAllUserFeedingTemplateSuccess(baseResponse.getData());
                    }else {
                        getView().getAllUserFeedingTemplateFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getAllUserFeedingTemplateFailure(throwable.getLocalizedMessage());
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

}
