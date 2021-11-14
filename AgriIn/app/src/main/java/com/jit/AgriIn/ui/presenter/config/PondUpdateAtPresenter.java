package com.jit.AgriIn.ui.presenter.config;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.model.request.PondAddRequest;
import com.jit.AgriIn.ui.view.config.PondUpdateAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public class PondUpdateAtPresenter extends BasePresenter<PondUpdateAtView> {

    public PondUpdateAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void updatePond(int pondID, float eNLatitude, float eSLatitude, float wNLatitude, float wSLatitude,
                           float eNLongitude, float eSLongitude, float wNLongitude, float wSLongitude,
                           float width, float length, float depth, float maxDepth, String pondDetailAddress,String pondName,String mDirection) {
        getView().showLoadingDialog();
        PondAddRequest pondAddRequest = new PondAddRequest();
        pondAddRequest.setLatitude1(eNLatitude);
        pondAddRequest.setLatitude2(eSLatitude);
        pondAddRequest.setLatitude3(wNLatitude);
        pondAddRequest.setLatitude4(wSLatitude);
        pondAddRequest.setLongitude1(eNLongitude);
        pondAddRequest.setLongitude2(eSLongitude);
        pondAddRequest.setLongitude3(wNLongitude);
        pondAddRequest.setLongitude4(eSLongitude);
        pondAddRequest.setWidth(width);
        pondAddRequest.setLength(length);
        pondAddRequest.setMax_depth(maxDepth);
        pondAddRequest.setDepth(depth);
        pondAddRequest.setLocation(pondDetailAddress);
        pondAddRequest.setName(pondName);
        pondAddRequest.setToward(mDirection);
        ApiRetrofit.getInstance().requestUpdatePond(pondID,pondAddRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pondMainResponseBaseResponse -> {
                    getView().hideLoadingDialog();
                    if (pondMainResponseBaseResponse.getCode() == 1){
                        getView().updatePondSuccess(pondMainResponseBaseResponse.getData());
                    }else {
                        getView().updatePondFailure(pondMainResponseBaseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updatePondFailure(throwable.getLocalizedMessage());
                });
    }
}
