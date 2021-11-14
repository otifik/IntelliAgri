package com.jit.AgriIn.uinew.second.richang;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.DailyThrowResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 增加塘口的P层
 */
public class RichangAddPresenter extends BasePresenter<RichangAddView> {
    public RichangAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addRichang(String type,int total,String addTme,String des,String richangName,String unit,int cellId){
        getView().showLoadingDialog();
        DailyThrowResponse dailyThrowResponse = new DailyThrowResponse();
        dailyThrowResponse.setType(type);
        dailyThrowResponse.setTotal(total);
        dailyThrowResponse.setSysTime(addTme);
        dailyThrowResponse.setDescription(des);
        dailyThrowResponse.setName(richangName);
        dailyThrowResponse.setCellId(cellId);
        dailyThrowResponse.setUnit(unit);
        ApiRetrofit.getInstance().addThrow(dailyThrowResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addRichangSuccess(baseResponse.getData());
                    }else {
                        getView().addRichangFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addRichangFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void getErliaoType(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getErliaoType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getErliaoTypeSuccess(baseResponse.getData());
                    }else {
                        getView().getErliaoTypeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getErliaoTypeFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void getMedicineType(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getMedicineType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getMedicineTypeSuccess(baseResponse.getData());
                    }else {
                        getView().getMedicineTypeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getMedicineTypeFailure(throwable.getLocalizedMessage());
                });
    }
}
