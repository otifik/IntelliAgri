package com.jit.AgriIn.uinew.first.data;

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
public class CellListPresenter extends BasePresenter<CellListView> {

    public CellListPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getAllUserPonds(String username,int pageNum,int pageSize){
        ApiRetrofit.getInstance().getUserPonds(username,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getFishPondSuccess(pageResponseBaseResponse.getData().getPonds());
                    }else {
                        getView().getFishPondFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().getFishPondFailure(throwable.getLocalizedMessage()));
    }


    @SuppressLint("CheckResult")
    public void deleteFishpond(int id){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteFishPond(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteFishPondSuccess();
                    }else {
                        getView().deleteFishPondFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteFishPondFailure(throwable.getLocalizedMessage());
                });
    }



}
