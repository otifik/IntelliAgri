package com.jit.AgriIn.uinew.third;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/15.
 * Describe:
 */
public class ZhishiDetailPresenter extends BasePresenter<ZhishiDetailView> {

    public ZhishiDetailPresenter(BaseActivity context) {
        super(context);
    }


    @SuppressWarnings("AlibabaMethodTooLong")
    @SuppressLint("CheckResult")
    public void queryZhishiDetail(int index) {

                getView().showLoadingDialog();
                ApiRetrofit.getInstance().queryDetailZhishiInfo(index)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(baikeShowResponseBaseResponse -> {
                            getView().hideLoadingDialog();
                            if (baikeShowResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                                getView().queryBaikeSuccess(baikeShowResponseBaseResponse.getData());
                            }else {
                                getView().queryBaikeFailure(baikeShowResponseBaseResponse.getMsg());
                            }
                        }, throwable -> {
                            getView().hideLoadingDialog();
                            getView().queryBaikeFailure(throwable.getLocalizedMessage());
                        });



    }
}
