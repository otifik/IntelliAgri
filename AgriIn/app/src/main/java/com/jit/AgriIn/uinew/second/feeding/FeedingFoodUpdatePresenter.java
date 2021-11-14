package com.jit.AgriIn.uinew.second.feeding;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedingFoodUpdatePresenter extends BasePresenter<FeedingFoodUpdateView> {

    public FeedingFoodUpdatePresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void updateFeedingFood(int id,String name,String batchNumber,String pond,String food,double amount,String unit,String time,String remarks){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().updateFeedingFood(id, name, batchNumber, pond, food, amount, unit, time, remarks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateFeedingFoodSuccess();
                    }else {
                        getView().updateFeedingFoodFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateFeedingFoodFailure(throwable.getLocalizedMessage());
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
