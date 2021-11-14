package com.jit.AgriIn.uinew.second.moban_d;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/11/8.
 * Describe:
 */
public class MobanPresenter extends BasePresenter<MobanView> {

    public MobanPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getMoban(int pageNum){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getTemplate(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getMobanSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().getMobanFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getMobanFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void deleteMoban(String ids){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteTemplate(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteMobanSuccess();
                    }else {
                        getView().deleteMobanFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteMobanFailure(throwable.getLocalizedMessage());
                });
    }



}
