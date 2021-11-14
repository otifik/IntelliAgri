package com.jit.AgriIn.ui.presenter.expert_baike;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.BaikeFeedBean;
import com.jit.AgriIn.ui.view.expert_baike.EBKUpdateFeedAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class EBKUpdateFeedAtPresenter extends BasePresenter<EBKUpdateFeedAtView> {
    public EBKUpdateFeedAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressWarnings("AlibabaMethodTooLong")
    @SuppressLint("CheckResult")
    public void queryBaikeDetail(int index) {

        getView().showLoadingDialog();
        ApiRetrofit.getInstance().queryDetailWeiBaikeInfo(index)
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
//    public void doFeedBaikeUpdateWithPic(int iD ,String name,String subKind, String price, String telPhone, String contact,
//                                  String company,
//                                  String manualInstruct,
//                                  String imagePath){
//        BaikeFeedBean baikeFeedBean = new BaikeFeedBean();
////        baikeFeedBean.setName(name);
////        baikeFeedBean.setSubKind(subKind);
////        baikeFeedBean.setPrice(price);
////        baikeFeedBean.setTelPhone(telPhone);
////        baikeFeedBean.setContact(contact);
////        baikeFeedBean.setCompany(company);
////        baikeFeedBean.setManualInstruct(manualInstruct);
////        baikeFeedBean.setImage(imagePath);
////        ApiRetrofit.getInstance().updateFeedBaikeWithPic(iD,baikeFeedBean)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(baseResponse -> {
////                    getView().hideLoadingDialog();
////                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
////                        getView().updateFeedSuccess(baseResponse.getData());
////                    }else {
////                        getView().updateFeedFailure(baseResponse.getMsg());
////                    }
////                }, throwable -> {
////                    getView().hideLoadingDialog();
////                    getView().updateFeedFailure(throwable.getLocalizedMessage());
////                });
//    }

    @SuppressLint("CheckResult")
    public void doFeedBaikeUpdateNoPic(int iD ,String name,String category, String source, String content){
        BaikeFeedBean baikeFeedBean = new BaikeFeedBean();
        baikeFeedBean.setName(name);
        baikeFeedBean.setCategory(category);
        baikeFeedBean.setSource(source);
        baikeFeedBean.setContent(content);
        ApiRetrofit.getInstance().updateFeedBaikeNoPic(iD,baikeFeedBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateFeedSuccess(baseResponse.getData());
                    }else {
                        getView().updateFeedFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateFeedFailure(throwable.getLocalizedMessage());
                });
    }
}
