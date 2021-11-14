package com.jit.AgriIn.uinew.role_admin;

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
public class SensorAddPresenter extends BasePresenter<SensorAddView> {
    public SensorAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addSensor(int cell_id,int term_id,int addr,String sensor_type,String sensor_name){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addSensor(cell_id,term_id,addr,sensor_type,sensor_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addSensorSuccess(baseResponse.getData());
                    }else {
                        getView().addSensorFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addSensorFailure(throwable.getLocalizedMessage());
                });
    }




    @SuppressLint("CheckResult")
    public void getDefSensorType(int termtype){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getDefSensorType(termtype)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getDefSensorTypeSuccess(baseResponse.getData());
                    }else {
                        getView().getDefSensorTypeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getDefSensorTypeFailure(throwable.getLocalizedMessage());
                });
    }

}
