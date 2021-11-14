package com.jit.AgriIn.ui.presenter.repair;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.view.repair.RepairListAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/11/8.
 * Describe:
 */
public class RepairListAtPresenter extends BasePresenter<RepairListAtView> {

    public RepairListAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void doQueryAllRepairList(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().queryRepairStateList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repairStateResponseBaseListResponse -> {
                    getView().hideLoadingDialog();
                    if (repairStateResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().queryRepairListSuccess(repairStateResponseBaseListResponse.getData());
                    }else {
                        getView().queryRepairListFailure(repairStateResponseBaseListResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().queryRepairListFailure(throwable.getLocalizedMessage());
                });
    }


}
