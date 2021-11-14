package com.jit.AgriIn.ui.presenter.config;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.view.config.ConfigMainAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/29.
 * Describe: 机器人配置 P层
 */
public class ConfigMainAtPresenter extends BasePresenter<ConfigMainAtView> {

    public ConfigMainAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void queryAllConfig(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().queryAllConfig()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(configMainResponseBaseListResponse -> {
                    getView().hideLoadingDialog();
                    if (configMainResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().queryConfigSuccess(configMainResponseBaseListResponse.getData());
                    }else {
                        getView().queryConfigFailure(configMainResponseBaseListResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().queryConfigFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void deleteConfigByID( int configID,int itemIndex){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().requestDelConfig(configID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteConfigSuccess(itemIndex);
                    }else {
                        getView().deleteConfigFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteConfigFailure(throwable.getLocalizedMessage());
                });
    }
}
