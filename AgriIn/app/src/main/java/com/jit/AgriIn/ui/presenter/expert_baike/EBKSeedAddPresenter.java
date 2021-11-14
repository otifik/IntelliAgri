package com.jit.AgriIn.ui.presenter.expert_baike;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.BaikeSeedBean;
import com.jit.AgriIn.ui.view.expert_baike.EBKAddAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class EBKSeedAddPresenter extends BasePresenter<EBKAddAtView> {
    public EBKSeedAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void doSeedAddRequest(String name,
                                 String source,
                                 String content,
                                 String imgpath){
        BaikeSeedBean baikeSeedBean = new BaikeSeedBean();
        baikeSeedBean.setName(name);
        baikeSeedBean.setSource(source);
        baikeSeedBean.setContent(content);
        baikeSeedBean.setImage(imgpath);
        ApiRetrofit.getInstance().addSeedBaike(baikeSeedBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().ebkAddSuccess();
                    }else {
                        getView().ebkAddFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().ebkAddFailure(throwable.getLocalizedMessage());
                });
    }
}
