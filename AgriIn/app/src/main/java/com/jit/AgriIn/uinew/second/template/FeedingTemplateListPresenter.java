package com.jit.AgriIn.uinew.second.template;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedingTemplateListPresenter extends BasePresenter<FeedingTemplateListView> {
    public FeedingTemplateListPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getUserFeedingTemplate(String username,int pageNum,int pageSize){
        ApiRetrofit.getInstance().getUserFeedingTemplate(username,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getFeedingTemplateSuccess(pageResponseBaseResponse.getData().getFeedTemplates());
                    }else {
                        getView().getFeedingTemplateFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().getFeedingTemplateFailure(throwable.getLocalizedMessage()));
    }

    @SuppressLint("CheckResult")
    public void deleteFeedingTemplate(int id){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteFeedingTemplate(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteFeedingTemplateSuccess();
                    }else {
                        getView().deleteFeedingTemplateFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteFeedingTemplateFailure(throwable.getLocalizedMessage());
                });
    }
}
