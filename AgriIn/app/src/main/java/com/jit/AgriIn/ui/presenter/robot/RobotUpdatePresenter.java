package com.jit.AgriIn.ui.presenter.robot;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.request.RobotActionRequest;
import com.jit.AgriIn.ui.view.robot.RobotUpdateView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/28.
 * Describe:
 */
public class RobotUpdatePresenter extends BasePresenter<RobotUpdateView> {

    public RobotUpdatePresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void updateRobot(int robotID, String robotNumber, String type){
        RobotActionRequest robotActionRequest = new RobotActionRequest();
        robotActionRequest.setType(type);
        robotActionRequest.setNumber(robotNumber);
        robotActionRequest.setCustomer_id(UserCache.getUserUser_id());
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().updateRobot(robotID,robotActionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().robotUpdateSuccess(baseResponse.getData());
                    }else {
                        getView().robotUpdateFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().robotUpdateFailure(throwable.getLocalizedMessage());
                });

    }
}
