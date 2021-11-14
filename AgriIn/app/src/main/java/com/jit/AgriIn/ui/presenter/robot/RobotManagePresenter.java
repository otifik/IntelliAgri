package com.jit.AgriIn.ui.presenter.robot;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.RobotPageResponse;
import com.jit.AgriIn.ui.view.robot.RobotManageView;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/28.
 * Describe: 机器人管理的P层
 */
public class RobotManagePresenter extends BasePresenter<RobotManageView> {
    public RobotManagePresenter(BaseActivity context) {
        super(context);
    }


//    @SuppressLint("CheckResult")
//    public void queryAllRobot(){
//        getView().showLoadingDialog();
//        ApiRetrofit.getInstance().queryMyRobot()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(robotMainResponseBaseListResponse -> {
//                    getView().hideLoadingDialog();
//                    if (robotMainResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
//                        getView().queryMyRobotSuccess(robotMainResponseBaseListResponse.getData());
//                    }else {
//                        getView().queryMyRobotFailure(robotMainResponseBaseListResponse.getMsg());
//                    }
//                }, throwable -> {
//                    getView().hideLoadingDialog();
//                    getView().queryMyRobotFailure(throwable.getLocalizedMessage());
//                });
//
//    }

    @SuppressLint("CheckResult")
    public void queryAllRobotPage(int pageNum){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().queryMyRobotWithPage(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<RobotPageResponse>>() {
                    @Override
                    public void accept(BaseResponse<RobotPageResponse> robotPageResponseBaseResponse) throws Exception {
                        getView().hideLoadingDialog();
                        if (robotPageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                            getView().queryMyRobotSuccess(robotPageResponseBaseResponse.getData());
                        }else {
                            getView().queryMyRobotFailure(robotPageResponseBaseResponse.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().hideLoadingDialog();
                        getView().queryMyRobotFailure(throwable.getLocalizedMessage());
                    }
                });

    }


    @SuppressLint("CheckResult")
    public void deleteRobot(int robotID, int itemIndex){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().deleteRobot(robotID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().deleteRobotSuccess(itemIndex);
                    }else {
                        getView().deleteRobotFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().deleteRobotFailure(throwable.getLocalizedMessage());
                });

    }
}
