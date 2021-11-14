package com.jit.AgriIn.uinew.fourth;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 塘口管理的P层
 */
public class SelfInfoPresenter extends BasePresenter<SelfInfotView> {

    public SelfInfoPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void requestSelfInfo(String uerName,int Role) {
        getView().showLoadingDialog();
        if (Role == AppConstant.Role_User){
            ApiRetrofit.getInstance().queryUserfInfo(uerName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(pondMainResponseBaseListResponse -> {
                        getView().hideLoadingDialog();
                        if (pondMainResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                            getView().querySelfInfoSuccess(pondMainResponseBaseListResponse.getData());
                        }else {
                            getView().querySelfInfoFailure(pondMainResponseBaseListResponse.getMsg());
                        }
                    }, throwable -> {
                        getView().hideLoadingDialog();
                        getView().querySelfInfoFailure(throwable.getLocalizedMessage());
                    });
        }else if (Role == AppConstant.Role_Expert){
            ApiRetrofit.getInstance().queryExpertfInfo(uerName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(pondMainResponseBaseListResponse -> {
                        getView().hideLoadingDialog();
                        if (pondMainResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                            getView().querySelfInfoSuccess(pondMainResponseBaseListResponse.getData());
                        }else {
                            getView().querySelfInfoFailure(pondMainResponseBaseListResponse.getMsg());
                        }
                    }, throwable -> {
                        getView().hideLoadingDialog();
                        getView().querySelfInfoFailure(throwable.getLocalizedMessage());
                    });
        }

    }

    @SuppressLint("CheckResult")
    public void updateUserInfo(int userId,String realname, String username, String tel, String company,
                        String email, String major,int Role) {
        getView().showLoadingDialog();
        SelfInfoBean selfInfoBean = new SelfInfoBean();
        selfInfoBean.setId(userId);
        selfInfoBean.setRealname(realname);
        selfInfoBean.setCompany(company);
        selfInfoBean.setUsername(username);
        selfInfoBean.setTel(tel);
        selfInfoBean.setEmail(email);
        selfInfoBean.setMajor(major);
        if (Role == AppConstant.Role_User){
            ApiRetrofit.getInstance().updateUserInfo(username,selfInfoBean)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseResponse>() {
                        @Override
                        public void accept(BaseResponse pondMainResponseBaseResponse) throws Exception {
                            getView().hideLoadingDialog();
                            if (pondMainResponseBaseResponse.getCode() == 1){
                                getView().updateInfoSuccess(pondMainResponseBaseResponse);
                            }else {
                                getView().updateInfoFailure(pondMainResponseBaseResponse.getMsg());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getView().hideLoadingDialog();
                            getView().updateInfoFailure(throwable.getLocalizedMessage());
                        }
                    });
        }else if (Role == AppConstant.Role_Expert){
            ApiRetrofit.getInstance().updateExpertInfo(username,selfInfoBean)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseResponse>() {
                        @Override
                        public void accept(BaseResponse pondMainResponseBaseResponse) throws Exception {
                            getView().hideLoadingDialog();
                            if (pondMainResponseBaseResponse.getCode() == 1){
                                getView().updateInfoSuccess(pondMainResponseBaseResponse);
                            }else {
                                getView().updateInfoFailure(pondMainResponseBaseResponse.getMsg());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getView().hideLoadingDialog();
                            getView().updateInfoFailure(throwable.getLocalizedMessage());
                        }
                    });
        }

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
}
