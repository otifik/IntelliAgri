package com.jit.AgriIn.uinew.first.data.query;

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
public class HisDataPresenter extends BasePresenter<HisDataView> {

    public HisDataPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getHisData(int cellId,String start_time,String end_time){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().getHisData(false,cellId,start_time,end_time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().hideLoadingDialog();
                        getView().getHisDataSuccess(pageResponseBaseResponse.getData());
                    }else {
                        getView().hideLoadingDialog();
                        getView().getHisDataFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable ->
                        getView().getHisDataFailure(throwable.getLocalizedMessage()));
    }

    @SuppressLint("CheckResult")
    public void getOrgData(int cellId){
        ApiRetrofit.getInstance().getOrgData(false,cellId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getOrgDataSuccess(pageResponseBaseResponse.getData());
                    }else {
                        getView().getOrgDataFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().getOrgDataFailure(throwable.getLocalizedMessage()));
    }
}
