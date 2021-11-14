package com.jit.AgriIn.uinew.role_admin;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/11/1.
 * Describe:
 */
public class CellListPresenter extends BasePresenter<CellListView> {
    public CellListPresenter(BaseActivity context) {
        super(context);
    }


    @SuppressLint("CheckResult")
    public void getAllUserCells(String username,int pageNum){
        ApiRetrofit.getInstance().getAllUserCells(username,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().queryCellSuccess(pageResponseBaseResponse.getData());
                    }else {
                        getView().queryCellFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().queryCellFailure(throwable.getLocalizedMessage()));
    }
}
