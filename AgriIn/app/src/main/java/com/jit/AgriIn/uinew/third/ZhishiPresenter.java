package com.jit.AgriIn.uinew.third;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/12.
 * Describe: 百科类Fg P层
 */
public class ZhishiPresenter extends BasePresenter<ZhishiView> {

    public ZhishiPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void queryZhishi(int pageNum) {
        ApiRetrofit.getInstance().queryZhishi(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS) {
                        getView().queryBaikeSuccess(pageResponseBaseResponse.getData());
                    } else {
                        getView().queryBaikeFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().queryBaikeFailure(throwable.getLocalizedMessage()));
    }

}
