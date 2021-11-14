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
 * Describe:
 */
public class MobanUpdatePresenter extends BasePresenter<MobanUpdateView> {

    public MobanUpdatePresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void updateMoban(String templateName,String startDate,String endDate,String throwTime,int throwCount,int mobanId){
        getView().showLoadingDialog();

        TemplateResponse templateResponse = new TemplateResponse();
        templateResponse.setTemplateName(templateName);
        templateResponse.setStartDate(startDate);
        templateResponse.setEndDate(endDate);
        templateResponse.setThrowTime(throwTime);
        templateResponse.setThrowCount(throwCount);
        templateResponse.setId(mobanId);
        templateResponse.setCount(1);

        ApiRetrofit.getInstance().updateTemplate(templateResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateMobanSuccess(baseResponse.getData());
                    }else {
                        getView().updateMobanFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateMobanFailure(throwable.getLocalizedMessage());
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
