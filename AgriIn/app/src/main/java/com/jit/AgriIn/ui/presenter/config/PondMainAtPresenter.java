package com.jit.AgriIn.ui.presenter.config;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.view.config.PondMainAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 塘口管理的P层
 */
public class PondMainAtPresenter extends BasePresenter<PondMainAtView> {

    public PondMainAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void loadPondMainData() {
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().queryPondMainInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pondMainResponseBaseListResponse -> {
                    getView().hideLoadingDialog();
                    if (pondMainResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().queryPondSuccess(pondMainResponseBaseListResponse.getData());
                    }else {
                        getView().queryPondFailure(pondMainResponseBaseListResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().queryPondFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void delPondByID(int pondID, int itemPosition){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().requestDelPond(pondID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().delPondSuccess(itemPosition);
                    }else {
                        getView().delPondFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().delPondFailure(throwable.getLocalizedMessage());
                });
    }
}
