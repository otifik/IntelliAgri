package com.jit.AgriIn.ui.presenter.expert_baike;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.BaikeProductBean;
import com.jit.AgriIn.ui.view.expert_baike.EBKUpdateProductAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class EBKUpdateProductAtPresenter extends BasePresenter<EBKUpdateProductAtView> {
    public EBKUpdateProductAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressWarnings("AlibabaMethodTooLong")
    @SuppressLint("CheckResult")
    public void queryBaikeDetail(int index) {

        getView().showLoadingDialog();
        ApiRetrofit.getInstance().queryDetailBaikePinInfo(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baikeShowResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (baikeShowResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().queryBaikeShowSuccess(baikeShowResponseBaseResponse.getData());
                    }else {
                        getView().queryBaikeShowFailure(baikeShowResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().queryBaikeShowFailure(throwable.getLocalizedMessage());
                });
    }

//    @SuppressLint("CheckResult")
//    public void doProductBaikeUpdateWithPic(int iD,String name,String subKind,String description,String image){
//        BaikeProductBean baikeProductBean = new BaikeProductBean();
//        baikeProductBean.setName(name);
////        baikeProductBean.setSubKind(subKind);
////        baikeProductBean.setDescription(description);
////        baikeProductBean.setImage(image);
////        ApiRetrofit.getInstance().updateProductBaikeWithPic(iD,baikeProductBean)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(baseResponse -> {
////                    getView().hideLoadingDialog();
////                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
////                        getView().updateProductSuccess(baseResponse.getData());
////                    }else {
////                        getView().updateProductFailure(baseResponse.getMsg());
////                    }
////                }, throwable -> {
////                    getView().hideLoadingDialog();
////                    getView().updateProductFailure(throwable.getLocalizedMessage());
////                });
//    }

    @SuppressLint("CheckResult")
    public void doProductBaikeUpdateNoPic(int iD,BaikeProductBean baikeProductBean){
//        BaikeProductBean baikeProductBean = new BaikeProductBean();
//        baikeProductBean.setName(name);
//        baikeProductBean.setSubKind(subKind);
//        baikeProductBean.setDescription(description);
        ApiRetrofit.getInstance().updateProductBaikeNoPic(iD,baikeProductBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateProductSuccess(baseResponse.getData());
                    }else {
                        getView().updateProductFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateProductFailure(throwable.getLocalizedMessage());
                });
    }
}
