package com.jit.AgriIn.ui.presenter.config;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.model.request.PondAddRequest;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.ui.view.config.PondAddAtView;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 增加塘口的P层
 */
public class PondAddAtPresenter extends BasePresenter<PondAddAtView> {
    public PondAddAtPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addPond(float eNLatitude, float eSLatitude, float wNLatitude, float wSLatitude,
                        float eNLongitude, float eSLongitude, float wNLongitude, float wSLongitude,
                        float width, float length, float depth, float maxDepth, String pondDetailAddress,String pondName,String pondDirection) {
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
        pondAddRequest.setToward(pondDirection);
        ApiRetrofit.getInstance().requestAddPond(pondAddRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<PondMainResponse>>() {
                    @Override
                    public void accept(BaseResponse<PondMainResponse> pondMainResponseBaseResponse) throws Exception {
                        getView().hideLoadingDialog();
                        if (pondMainResponseBaseResponse.getCode() == 1){
                            getView().addPondSuccess(pondMainResponseBaseResponse.getData());
                        }else {
                            getView().addPondFailure(pondMainResponseBaseResponse.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().hideLoadingDialog();
                        getView().addPondFailure(throwable.getLocalizedMessage());
                    }
                });
    }
}
