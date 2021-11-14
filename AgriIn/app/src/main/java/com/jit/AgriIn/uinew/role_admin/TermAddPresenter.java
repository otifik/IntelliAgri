package com.jit.AgriIn.uinew.role_admin;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 增加塘口的P层
 */
public class TermAddPresenter extends BasePresenter<TermAddView> {
    public TermAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addTerm(int type,String deveui,String manu,String prod,String name,String user){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addTerm(type,deveui,manu,prod,name,user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addTermSuccess(baseResponse.getData());
                    }else {
                        getView().addTermFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addTermFailure(throwable.getLocalizedMessage());
                });
    }




    @SuppressLint("CheckResult")
    public void getTermManus(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getAllManus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getManuSuccess(baseResponse.getData());
                    }else {
                        getView().getManuFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getManuFailure(throwable.getLocalizedMessage());
                });
    }




    @SuppressLint("CheckResult")
    public void queryRoleUserInfo(int pageNum){
        ApiRetrofit.getInstance().queryRoleUserInfo(pageNum,2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().queryCustomersSuccess(pageResponseBaseResponse.getData());
                    }else {
                        getView().queryCustomerFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().queryCustomerFailure(throwable.getLocalizedMessage()));
    }
}
