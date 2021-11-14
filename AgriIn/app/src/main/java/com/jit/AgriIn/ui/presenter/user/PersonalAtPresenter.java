package com.jit.AgriIn.ui.presenter.user;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.ui.view.user.IPersonalAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 个人中心的P层
 */
public class PersonalAtPresenter extends BasePresenter<IPersonalAtView> {

    public PersonalAtPresenter(BaseActivity context) {
        super(context);
    }


    @SuppressLint("CheckResult")
    public void doPostHeadImage(String imagePath){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().postHeadImage(new File(imagePath))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == 1){
                        getView().postHeadSuccess(baseResponse.getData().getImage());
                    }else {
                       getView().postHeadFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().postHeadFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void doLogout() {
        getView().showLoadingDialog();
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            UserCache.clear();
            emitter.onNext(true);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        getView().hideLoadingDialog();
                        getView().logoutSuccess();
                    }
                });

    }
}
