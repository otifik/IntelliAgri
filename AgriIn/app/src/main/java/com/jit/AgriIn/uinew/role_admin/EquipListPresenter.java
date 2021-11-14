package com.jit.AgriIn.uinew.role_admin;

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
public class EquipListPresenter extends BasePresenter<EquipListView> {

    public EquipListPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getAllEquips(int id){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().getAllEquips(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(typeIncomeResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (typeIncomeResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getEquipSuccess(typeIncomeResponseBaseResponse.getData());
                    }else {
                        getView().getEquipFailure(typeIncomeResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getEquipFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void deleteEquip(String id){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteEquip(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteEquipSuccess();
                    }else {
                        getView().deleteEquipFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteEquipFailure(throwable.getLocalizedMessage());
                });
    }



}
