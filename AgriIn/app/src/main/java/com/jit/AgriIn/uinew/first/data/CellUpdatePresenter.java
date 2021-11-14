package com.jit.AgriIn.uinew.first.data;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public class CellUpdatePresenter extends BasePresenter<CellUpdateView> {

    public CellUpdatePresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void updateFishPond(int id,String name,double length,double width,double depth,String orientation,double longitude,double latitude,String address,String product){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().updateFishPond(id,name,length,width,depth,orientation,longitude,latitude,address,product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateFishPondSuccess();
                    }else {
                        getView().updateFishPondFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateFishPondFailure(throwable.getLocalizedMessage());
                });
    }
}
