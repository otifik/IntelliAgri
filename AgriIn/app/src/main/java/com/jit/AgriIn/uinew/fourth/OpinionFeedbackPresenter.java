package com.jit.AgriIn.uinew.fourth;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class OpinionFeedbackPresenter extends BasePresenter<OpinionFeedbackView> {
    public OpinionFeedbackPresenter(BaseActivity context) {
        super(context);
    }


    @SuppressLint("CheckResult")
    public void addFeedBack( String description, String contact, String imagePath){
        ApiRetrofit.getInstance().opinionFeedback(description,contact,imagePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().feedbackAddSuccess();
                    }else {
                        getView().feedbackAddFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().feedbackAddFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void addFeedBackNoPic( String description, String contact){
        ApiRetrofit.getInstance().opinionFeedbackNoPic(description,contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().feedbackAddSuccess();
                    }else {
                        getView().feedbackAddFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().feedbackAddFailure(throwable.getLocalizedMessage());
                });
    }
}
