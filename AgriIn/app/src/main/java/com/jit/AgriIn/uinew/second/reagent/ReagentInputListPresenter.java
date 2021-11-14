package com.jit.AgriIn.uinew.second.reagent;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReagentInputListPresenter extends BasePresenter<ReagentInputListView> {
    public ReagentInputListPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getUserReagentInput(String username,int pageNum,int pageSize){
        ApiRetrofit.getInstance().getUserReagentInput(username,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getReagentInputSuccess(pageResponseBaseResponse.getData().getReagents());
                    }else {
                        getView().getReagentInputFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().getReagentInputFailure(throwable.getLocalizedMessage()));
    }

    @SuppressLint("CheckResult")
    public void deleteReagentInput(int id){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteReagentInput(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteReagentInputSuccess();
                    }else {
                        getView().deleteReagentInputFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteReagentInputFailure(throwable.getLocalizedMessage());
                });
    }
}
