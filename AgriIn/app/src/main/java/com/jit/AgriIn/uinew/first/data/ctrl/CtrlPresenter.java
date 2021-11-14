package com.jit.AgriIn.uinew.first.data.ctrl;

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
public class CtrlPresenter extends BasePresenter<CtrlView> {

    public CtrlPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getCellEquips(int id){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().getCellEquipsStatus(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getCellEquipStatusSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().getCellEquipStatusFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getCellEquipStatusFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void ctrlEquip(int appusrid, int equip_id,int ison_fg){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().ctrlEquip(appusrid,equip_id,ison_fg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().ctrlEquipSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().ctrlEquipFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().ctrlEquipFailure(throwable.getLocalizedMessage());
                });
    }

}
