package com.jit.AgriIn.ui.presenter.expert_baike;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.BaikeDrugBean;
import com.jit.AgriIn.ui.view.expert_baike.EBKUpdateDrugAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class EBKUpdateDrugAtPresenter  extends BasePresenter<EBKUpdateDrugAtView> {
    public EBKUpdateDrugAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressWarnings("AlibabaMethodTooLong")
    @SuppressLint("CheckResult")
    public void queryBaikeDetail(int index) {

        getView().showLoadingDialog();
        ApiRetrofit.getInstance().queryDetailYaoBaikeInfo(index)
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
//    public void  doDDrugBaikeUpdateWithPic(int iD,String name,String subKind,String price,String telPhone,String contact,String company,
//                                  String manualInstruct,String imagePath){
//        getView().showLoadingDialog();
//        BaikeDrugBean baikeDrugBean = new BaikeDrugBean();
//        baikeDrugBean.setName(name);
//        baikeDrugBean.setSubKind(subKind);
//        baikeDrugBean.setPrice(price);
//        baikeDrugBean.setTelPhone(telPhone);
//        baikeDrugBean.setContact(contact);
//        baikeDrugBean.setCompany(company);
//        baikeDrugBean.setManualInstruct(manualInstruct);
//        baikeDrugBean.setImage(imagePath);
//        ApiRetrofit.getInstance().updateDrugBaikeWithPic(iD,baikeDrugBean)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(baseResponse -> {
//                    getView().hideLoadingDialog();
//                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
//                        getView().updateDrugSuccess(baseResponse.getData());
//                    }else {
//                        getView().updateDrugFailure(baseResponse.getMsg());
//                    }
//                }, throwable -> {
//                    getView().hideLoadingDialog();
//                    getView().updateDrugFailure(throwable.getLocalizedMessage());
//                });
//    }

    /**
     * 不含图片的更新
     * @param iD
     */
    @SuppressLint("CheckResult")
    public void  doDDrugBaikeUpdateNoPic(int iD,BaikeDrugBean baikeDrugBean){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().updateDrugBaikeNoPic(iD,baikeDrugBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateDrugSuccess(baseResponse.getData());
                    }else {
                        getView().updateDrugFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateDrugFailure(throwable.getLocalizedMessage());
                });
    }
}
