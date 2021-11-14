package com.jit.AgriIn.uinew.first.data.log;

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
public class WarnLogPresenter extends BasePresenter<WarnLogView> {

    public WarnLogPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getWarnLog(int cellId,String start_time,String end_time){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().getWarnLog(cellId,start_time,end_time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().hideLoadingDialog();
                        getView().getWarnLogSuccess(pageResponseBaseResponse.getData());
                    }else {
                        getView().hideLoadingDialog();
                        getView().getWarnLogFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable ->
                        getView().getWarnLogFailure(throwable.getLocalizedMessage()));
    }
}
