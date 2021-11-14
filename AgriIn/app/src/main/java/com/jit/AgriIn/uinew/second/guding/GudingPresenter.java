package com.jit.AgriIn.uinew.second.guding;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.IncomeResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/11/8.
 * Describe:
 */
public class GudingPresenter extends BasePresenter<GudingView> {

    public GudingPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getIncome(int type,int pageNum){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getIncome(type,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getIncomeSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().getIncomeFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getIncomeFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void addIncome(IncomeResponse incomeResponse){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addIncome(incomeResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addIncomeSuccess();
                    }else {
                        getView().addIncomeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addIncomeFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void deleteIncome(String ids){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteIncome(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteIncomeSuccess();
                    }else {
                        getView().deleteIncomeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteIncomeFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void updateIncome(IncomeResponse incomeResponse){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().updateIncome(incomeResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateIncomeSuccess();
                    }else {
                        getView().updateIncomeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateIncomeFailure(throwable.getLocalizedMessage());
                });
    }


}
