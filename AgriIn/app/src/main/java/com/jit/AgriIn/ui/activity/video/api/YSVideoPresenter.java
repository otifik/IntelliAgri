package com.jit.AgriIn.ui.activity.video.api;

import android.annotation.SuppressLint;

import com.videogo.openapi.bean.EZDeviceInfo;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yuanyuan on 2018/12/5.
 */

public class YSVideoPresenter extends BasePresenter<YSVideoView> {
    List<EZDeviceInfo> result = null;

    public YSVideoPresenter(BaseActivity context) {
        super(context);
    }





    @SuppressLint("CheckResult")
    public void getToken(String phoneNum){
        getView().showLoadingDialog();
        TokenRetrofit.getInstance().getAccessToken(phoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getView().hideLoadingDialog();
                    if (response.getCode().contains("200")){
                        getView().getTokenSuccess(response.getMsg(),response.getData());
                    }else {
                        getView().getTokenFailure(response.getMsg());
                    }
                }, throwable ->{
                    getView().hideLoadingDialog();
                    getView().getTokenFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void getTokenBySms(String phoneNum,String smsStr){
        getView().showLoadingDialog();
        TokenBean tokenBean = new TokenBean();
        tokenBean.setPhone(phoneNum);
        tokenBean.setSmsCode(smsStr);
        TokenRetrofit.getInstance().getAccessTokenByCode(tokenBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getView().hideLoadingDialog();
                    if (response.getCode().contains("200")){
                        getView().getTokenSuccess(response.getMsg(),response.getData());
                    }else {
                        getView().getTokenFailure(response.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getTokenFailure(throwable.getLocalizedMessage());
                });
    }

}

