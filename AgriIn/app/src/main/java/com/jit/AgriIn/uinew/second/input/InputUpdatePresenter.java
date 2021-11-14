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

public class InputUpdatePresenter extends BasePresenter<InputUpdateView> {
    public InputUpdatePresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void updateInput(int id,String inputType, String inputName, String manufacturer, List<MultipartBody.Part> pictures, String remarks){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().updateInput(id,inputType,inputName,manufacturer,pictures,remarks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateInputSuccess();
                    }else {
                        getView().updateInputFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateInputFailure(throwable.getLocalizedMessage());
                });
    }
}
