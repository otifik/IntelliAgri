package com.jit.AgriIn.uinew.second.moban_d;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.TemplateResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 增加塘口的P层
 */
public class MobanAddPresenter extends BasePresenter<MobanAddView> {
    public MobanAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addMoban(String templateName,String startDate,String endDate,String throwTime,int throwCount){
        getView().showLoadingDialog();
        TemplateResponse templateResponse = new TemplateResponse();
        templateResponse.setTemplateName(templateName);
        templateResponse.setStartDate(startDate);
        templateResponse.setEndDate(endDate);
        templateResponse.setThrowTime(throwTime);
        templateResponse.setThrowCount(throwCount);
        templateResponse.setCount(1);
        ApiRetrofit.getInstance().addTemplate(templateResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addMobanSuccess(baseResponse.getData());
                    }else {
                        getView().addMobanFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addMobanFailure(throwable.getLocalizedMessage());
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
}
