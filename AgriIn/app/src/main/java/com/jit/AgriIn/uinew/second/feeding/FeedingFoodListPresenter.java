package com.jit.AgriIn.uinew.second.feeding;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedingFoodListPresenter extends BasePresenter<FeedingFoodListView> {
    public FeedingFoodListPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getUserFeedingFood(String username,int pageNum,int pageSize){
        ApiRetrofit.getInstance().getAllUserFeedingFood(username,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getFeedingFoodSuccess(pageResponseBaseResponse.getData().getAddFoods());
                    }else {
                        getView().getFeedingFoodFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().getFeedingFoodFailure(throwable.getLocalizedMessage()));
    }

    @SuppressLint("CheckResult")
    public void deleteFeedingFood(int id){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteFeedingFood(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteFeedingFoodSuccess();
                    }else {
                        getView().deleteFeedingFoodFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteFeedingFoodFailure(throwable.getLocalizedMessage());
                });
    }
}
