package com.jit.AgriIn.uinew.third;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/29.
 * Describe:
 */
public class AskQuestionPresenter extends BasePresenter<AskQuestionView> {

    public AskQuestionPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void askQuestionWithImage(String des, String img){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().askQuestionWithImage(des,img)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().AskSuccess();
                    }else {
                        getView().AskFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().AskFailure(throwable.getLocalizedMessage());
                });

    }


    @SuppressLint("CheckResult")
    public void askQuestion(String des){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().askQuestion(des)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().AskSuccess();
                    }else {
                        getView().AskFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().AskFailure(throwable.getLocalizedMessage());
                });

    }
}
