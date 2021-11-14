package com.jit.AgriIn.ui.presenter.config;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.request.ConfigActionRequest;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.model.response.RobotPageResponse;
import com.jit.AgriIn.ui.view.config.ConfigAddAtView;
import com.zxl.baselib.bean.response.BaseListResponse;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/29.
 * Describe: 配置增加的P层
 */
public class ConfigAddAtPresenter extends BasePresenter<ConfigAddAtView> {

    public ConfigAddAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addConfig(ConfigActionRequest configActionRequest){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().requestAddConfig(configActionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(configMainResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (configMainResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addConfigSuccess(configMainResponseBaseResponse.getData());
                    }else {
                        getView().addConfigFailure(configMainResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addConfigFailure(throwable.getLocalizedMessage());
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
                .flatMap((Function<BaseListResponse<PondMainResponse>, ObservableSource<Boolean>>) pondMainResponseBaseListResponse -> {
                    if (pondMainResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        List<String> pondStrList = new ArrayList<>();
                        if (pondMainResponseBaseListResponse.getData() != null){
                            for (PondMainResponse pondMainResponse : pondMainResponseBaseListResponse.getData()){
                                pondStrList.add(pondMainResponse.getName());
                            }
                        }
                        getView().getPondInfoSuccess(pondMainResponseBaseListResponse.getData(),pondStrList);
                        return Observable.just(true);
                    }else {
                        getView().getPondInfoFailure(pondMainResponseBaseListResponse.getMsg());
                        return Observable.just(false);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Boolean, ObservableSource<BaseResponse<RobotPageResponse>>>() {
                    @Override
                    public ObservableSource<BaseResponse<RobotPageResponse>> apply(Boolean aBoolean) throws Exception {
                        return ApiRetrofit.getInstance().queryMyRobotWithPage(1);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(robotMainResponseBaseListResponse -> {
                    getView().hideLoadingDialog();
                    if (robotMainResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getRobotInfoSuccess(robotMainResponseBaseListResponse.getData());
                    }else {
                        getView().getRobotInfoFailure(robotMainResponseBaseListResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getRobotInfoFailure(throwable.getLocalizedMessage());
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
