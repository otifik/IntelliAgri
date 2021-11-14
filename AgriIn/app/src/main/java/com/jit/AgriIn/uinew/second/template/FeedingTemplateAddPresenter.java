package com.jit.AgriIn.uinew.second.template;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedingTemplateAddPresenter extends BasePresenter<FeedingTemplateAddView> {

    public FeedingTemplateAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addFeedingTemplate(String name,String batchNumber,String pond,String food,double amount,String unit,String time,String remarks){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addFeedingTemplate(name,batchNumber,pond,food,amount,unit,time,remarks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addFeedingTemplateSuccess(baseResponse);
                    }else {
                        getView().addFeedingTemplateFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addFeedingTemplateFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void getAllUserFishInput(String username){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getAllUserFishInput(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getAllUserFishInputSuccess(baseResponse.getData());
                    }else {
                        getView().getAllUserFishInputFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getAllUserFishInputFailure(throwable.getLocalizedMessage());
                });
    }

//    @SuppressLint("CheckResult")
//    public void getAllUserPonds(String username,int page){
//        getView().showLoadingDialog();
//        ApiRetrofit.getInstance().getAllUserPonds(username,page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(baseResponse -> {
//                    getView().hideLoadingDialog();
//                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
//                        getView().getAllUserPondSuccess(baseResponse.getData());
//                    }else {
//                        getView().getAllUserPondFailure(baseResponse.getMsg());
//                    }
//                }, throwable -> {
//                    getView().hideLoadingDialog();
//                    getView().getAllUserPondFailure(throwable.getLocalizedMessage());
//                });
//    }

    @SuppressLint("CheckResult")
    public void getAllUserInputs(String username){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getAllUserInputs(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getAllUserInputSuccess(baseResponse.getData());
                    }else {
                        getView().getAllUserInputFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getAllUserInputFailure(throwable.getLocalizedMessage());
                });
    }

}
