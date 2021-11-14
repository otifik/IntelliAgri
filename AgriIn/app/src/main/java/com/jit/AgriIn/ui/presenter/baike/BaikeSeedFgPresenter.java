package com.jit.AgriIn.ui.presenter.baike;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.view.baike.BaikeCustomFgView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/12.
 * Describe: 百科类Fg P层
 */
public class BaikeSeedFgPresenter extends BasePresenter<BaikeCustomFgView> {

    public BaikeSeedFgPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void queryKindBaike(int pageNum){
                ApiRetrofit.getInstance().queryKindBaike(pageNum)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pageResponseBaseResponse -> {
                            if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                                getView().queryBaikeSuccess(pageResponseBaseResponse.getData());
                            }else {
                                getView().queryBaikeFailure(pageResponseBaseResponse.getMsg());
                            }
                        }, throwable -> getView().queryBaikeFailure(throwable.getLocalizedMessage()));

    }

    @SuppressLint("CheckResult")
    public void deleteBaikeByID(int baikeID, int itemPostion){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteSeedBaikeByID(baikeID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteBaikeSuccess(itemPostion);
                    }else {
                        getView().deleteBaikeFailure(baseResponse.getMsg());
                    }
                }, throwable -> getView().deleteBaikeFailure(throwable.getLocalizedMessage()));
    }
}
