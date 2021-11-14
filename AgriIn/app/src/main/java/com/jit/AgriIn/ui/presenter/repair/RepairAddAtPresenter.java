package com.jit.AgriIn.ui.presenter.repair;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.ui.view.repair.RepairAddAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/11/8.
 * Describe:
 */
public class RepairAddAtPresenter extends BasePresenter<RepairAddAtView> {

    public RepairAddAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void doAddRepairRobert(int robertID, String description){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addRobotNeededRepair(robertID,description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addRepairSuccess();
                    }else {
                        getView().addRepairFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addRepairFailure(throwable.getLocalizedMessage());
                });
    }

    @SuppressLint("CheckResult")
    public void queryAllRobotPage(int pageNum){
        ApiRetrofit.getInstance().queryMyRobotWithPage(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(robotPageResponseBaseResponse -> {
                    if (robotPageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().queryMyRobotSuccess(robotPageResponseBaseResponse.getData());
                    }else {
                        getView().queryMyRobotFailure(robotPageResponseBaseResponse.getMsg());
                    }
                }, throwable -> getView().queryMyRobotFailure(throwable.getLocalizedMessage()));
    }
}
