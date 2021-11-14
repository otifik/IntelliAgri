package com.jit.AgriIn.ui.presenter.robot;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.request.RobotActionRequest;
import com.jit.AgriIn.ui.view.robot.RobotAddView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/28.
 * Describe:
 */
public class RobotAddPresenter extends BasePresenter<RobotAddView> {
    public RobotAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void doAddRobot(String robotNumber,String type){
        RobotActionRequest robotActionRequest = new RobotActionRequest();
        robotActionRequest.setCustomer_id(UserCache.getUserUser_id());
        robotActionRequest.setNumber(robotNumber);
        robotActionRequest.setType(type);
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().requestAddRobot(robotActionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(robotMainResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (robotMainResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addRobotSuccess(robotMainResponseBaseResponse.getData());
                    }else {
                        getView().addRobotFailure(robotMainResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                   getView().hideLoadingDialog();
                   getView().addRobotFailure(throwable.getLocalizedMessage());
                });
    }
}
