package com.jit.AgriIn.uinew.second.guding;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.IncomeResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 增加塘口的P层
 */
public class GudingAddPresenter extends BasePresenter<GudingAddView> {
    public GudingAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addIncome(int type,String name,int price,String time,String des,String count,String unit){
        getView().showLoadingDialog();
        IncomeResponse incomeResponse = new IncomeResponse();
        incomeResponse.setName(name);
        incomeResponse.setType(type);
        incomeResponse.setSysTime(time);
        incomeResponse.setRemark(des);
        if (!unit.isEmpty()){
            incomeResponse.setCount(Integer.valueOf(count));
            incomeResponse.setPrice(price);
        }
        if (!count.isEmpty()){
            incomeResponse.setUnit(unit);
        }else {
            incomeResponse.setTotal(price);
        }
        ApiRetrofit.getInstance().addIncome(incomeResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addGudingSuccess(baseResponse.getData());
                    }else {
                        getView().addGudingFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addGudingFailure(throwable.getLocalizedMessage());
                });
    }



    @SuppressLint("CheckResult")
    public void getGudingType(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getGudingType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getGudingTypeSuccess(baseResponse.getData());
                    }else {
                        getView().getGudingTypeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getGudingTypeFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void getGoumaiType(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getGoumaiType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getGudingTypeSuccess(baseResponse.getData());
                    }else {
                        getView().getGudingTypeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getGudingTypeFailure(throwable.getLocalizedMessage());
                });
    }


    @SuppressLint("CheckResult")
    public void getXiaoshouType(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getXiaoshouType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getGudingTypeSuccess(baseResponse.getData());
                    }else {
                        getView().getGudingTypeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getGudingTypeFailure(throwable.getLocalizedMessage());
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
