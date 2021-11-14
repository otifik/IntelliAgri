package com.jit.AgriIn.uinew.second.input;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class InputAddPresenter extends BasePresenter<InputAddView> {
    public InputAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addInput(String inputType, String inputName, String manufacturer, List<MultipartBody.Part> pictures, String remarks){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addInput(inputType,inputName,manufacturer,pictures,remarks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addInputSuccess(baseResponse);
                    }else {
                        getView().addInputFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addInputFailure(throwable.getLocalizedMessage());
                });
    }
}
