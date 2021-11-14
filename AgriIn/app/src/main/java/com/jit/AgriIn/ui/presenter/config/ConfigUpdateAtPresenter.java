package com.jit.AgriIn.ui.presenter.config;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.request.ConfigActionRequest;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.model.response.RobotPageResponse;
import com.jit.AgriIn.ui.view.config.ConfigUpdateAtView;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zxl on 2018/9/5.
 *         discription:
 */

public class ConfigUpdateAtPresenter extends BasePresenter<ConfigUpdateAtView> {
    public ConfigUpdateAtPresenter(BaseActivity context) {
        super(context);
    }


    @SuppressLint("CheckResult")
    public void updateConfig(int configID,ConfigActionRequest configActionRequest){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().requestUpdateConfig(configID,configActionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(configMainResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (configMainResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateConfigSuccess(configMainResponseBaseResponse.getData());
                    }else {
                        getView().updateConfigFailure(configMainResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateConfigFailure(throwable.getLocalizedMessage());
                });

    }


    /**
     * 拉取塘口以及机器人信息
     */
    @SuppressLint("CheckResult")
    public void loadPondWithRobot() {
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().queryPondMainInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pondMainResponseBaseListResponse -> {
                    getView().hideLoadingDialog();
                    if (pondMainResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        List<String> pondStrList = new ArrayList<>();
                        if (pondMainResponseBaseListResponse.getData() != null){
                            for (PondMainResponse pondMainResponse : pondMainResponseBaseListResponse.getData()){
                                pondStrList.add(pondMainResponse.getName());
                            }
                        }
                        getView().getPondInfoSuccess(pondMainResponseBaseListResponse.getData(),pondStrList);
                    }else {
                        getView().getPondInfoFailure(pondMainResponseBaseListResponse.getMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().hideLoadingDialog();
                        getView().getPondInfoFailure(throwable.getLocalizedMessage());
                    }
                });
    }


    @SuppressLint("CheckResult")
    public void queryAllRobotPage(int pageNum){
        ApiRetrofit.getInstance().queryMyRobotWithPage(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<RobotPageResponse>>() {
                    @Override
                    public void accept(BaseResponse<RobotPageResponse> robotPageResponseBaseResponse) throws Exception {
                        if (robotPageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                            getView().queryMyRobotSuccess(robotPageResponseBaseResponse.getData());
                        }else {
                            getView().queryMyRobotFailure(robotPageResponseBaseResponse.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().queryMyRobotFailure(throwable.getLocalizedMessage());
                    }
                });
    }
}
