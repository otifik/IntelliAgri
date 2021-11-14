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
 * Describe: 增加塘口的P层
 */
public class CellAddPresenter extends BasePresenter<CellAddView> {
    public CellAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addCell(double length,double width,double longitude,double latitude,String product,String prod,String cell_name,String user_name){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addCell(length,width,longitude,latitude,product,prod,cell_name,user_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addCellSuccess(baseResponse.getData());
                    }else {
                        getView().addCellFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addCellFailure(throwable.getLocalizedMessage());
                });
    }




    @SuppressLint("CheckResult")
    public void getCellProType(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getCellProType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getCellProTypeSuccess(baseResponse.getData());
                    }else {
                        getView().getCellProTypeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getCellProTypeFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void getCellPro(String celltype){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getCellPro(celltype)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getCellProSuccess(baseResponse.getData());
                    }else {
                        getView().getCellProFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getCellProFailure(throwable.getLocalizedMessage());
                });
    }

}
