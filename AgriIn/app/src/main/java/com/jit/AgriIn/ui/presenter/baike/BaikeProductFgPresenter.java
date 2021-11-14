package com.jit.AgriIn.ui.presenter.baike;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.view.baike.BaikeProductFgView;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/12.
 * Describe: 百科类Fg P层
 */
public class BaikeProductFgPresenter extends BasePresenter<BaikeProductFgView> {

    public BaikeProductFgPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void queryKindBaike( int pageNum){
                ApiRetrofit.getInstance().queryKindPinBaike(pageNum)
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
        ApiRetrofit.getInstance().deleteProductBaikeByID(baikeID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        getView().hideLoadingDialog();
                        if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                            getView().deleteBaikeSuccess(itemPostion);
                        }else {
                            getView().deleteBaikeFailure(baseResponse.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().deleteBaikeFailure(throwable.getLocalizedMessage());
                    }
                });
    }
}
