package com.jit.AgriIn.uinew.second.input;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InputListPresenter extends BasePresenter<InputListView> {

    public InputListPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getUserInputs(String username,int pageNum,int pageSize){
        ApiRetrofit.getInstance().getUserInputs(username,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getInputSuccess(pageResponseBaseResponse.getData().getInputs());
                    }else {
                        getView().getInputFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().getInputFailure(throwable.getLocalizedMessage()));
    }

    @SuppressLint("CheckResult")
    public void deleteInput(int id){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteInput(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteInputSuccess();
                    }else {
                        getView().deleteInputFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteInputFailure(throwable.getLocalizedMessage());
                });
    }
}
