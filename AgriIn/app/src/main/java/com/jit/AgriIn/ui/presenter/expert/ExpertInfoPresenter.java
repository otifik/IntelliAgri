package com.jit.AgriIn.ui.presenter.expert;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.view.expert.ExpertInfoView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/12.
 * Describe: 百科类Fg P层
 */
public class ExpertInfoPresenter extends BasePresenter<ExpertInfoView> {

    public ExpertInfoPresenter(BaseActivity context) {
        super(context);
    }

//    @SuppressLint("CheckResult")
//    public void queryExpertInfo(int pageNum){
//                ApiRetrofit.getInstance().queryExpertInfo(pageNum)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(pageResponseBaseResponse -> {
//                            if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
//                                getView().queryExpertInfoSuccess(pageResponseBaseResponse.getData());
//                            }else {
//                                getView().queryExpertInfoFailure(pageResponseBaseResponse.getMsg());
//                            }
//                        }, throwable -> getView().queryExpertInfoFailure(throwable.getLocalizedMessage()));
//
//    }

    @SuppressLint("CheckResult")
    public void queryExpertInfo(int pageNum){
        ApiRetrofit.getInstance().queryRoleExpertInfo(pageNum,3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().queryExpertInfoSuccess(pageResponseBaseResponse.getData());
                    }else {
                        getView().queryExpertInfoFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().queryExpertInfoFailure(throwable.getLocalizedMessage()));
    }
}
