package com.jit.AgriIn.ui.presenter.expert_baike;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.BaikeDiseaseBean;
import com.jit.AgriIn.ui.view.expert_baike.EBKUpdateDiseaseAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class EBKUpdateDisAtPresenter extends BasePresenter<EBKUpdateDiseaseAtView> {
    public EBKUpdateDisAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressWarnings("AlibabaMethodTooLong")
    @SuppressLint("CheckResult")
    public void queryBaikeDetail(int index) {
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().queryDetailBingBaikeInfo(index)
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

    @SuppressLint("CheckResult")
    public void doDiseaseBaikeUpdateWithPic(int baikeID,
                                            String diseaseName,
                                            String subKind,
                                            String symptom,
                                            String treatment,
                                            String imagePath){
        getView().showLoadingDialog();
        BaikeDiseaseBean baikeDiseaseBean = new BaikeDiseaseBean();
        baikeDiseaseBean.setDiseaseName(diseaseName);
        baikeDiseaseBean.setBig_category(subKind);
        baikeDiseaseBean.setSymptom(symptom);
        baikeDiseaseBean.setTreatment(treatment);
        baikeDiseaseBean.setImage(imagePath);
        ApiRetrofit.getInstance().updateDiseaseBaikeWithPic(baikeID,baikeDiseaseBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateDisSuccess(baseResponse.getData());
                    }else {
                        getView().updateDisFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateDisFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void doDiseaseBaikeUpdateNoPic(int baikeID,
                                            String diseaseName,
                                            String subKind,
                                            String symptom,
                                            String treatment){
        getView().showLoadingDialog();
        BaikeDiseaseBean baikeDiseaseBean = new BaikeDiseaseBean();
        baikeDiseaseBean.setDiseaseName(diseaseName);
        baikeDiseaseBean.setBig_category(subKind);
        baikeDiseaseBean.setSymptom(symptom);
        baikeDiseaseBean.setTreatment(treatment);
        ApiRetrofit.getInstance().updateDiseaseBaikeNoPic(baikeID,baikeDiseaseBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateDisSuccess(baseResponse.getData());
                    }else {
                        getView().updateDisFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateDisFailure(throwable.getLocalizedMessage());
                });
    }
}
