package com.jit.AgriIn.uinew.second.richang;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.DailyThrowResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public class RichangUpdatePresenter extends BasePresenter<RichangUpdateView> {

    public RichangUpdatePresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void updateRichang(String type,int total,String addTme,String des,String richangName,String unit,int cellId,int richangID){
        getView().showLoadingDialog();

        DailyThrowResponse dailyThrowResponse = new DailyThrowResponse();
        dailyThrowResponse.setId(richangID);

        dailyThrowResponse.setType(type);
        dailyThrowResponse.setTotal(total);
        dailyThrowResponse.setSysTime(addTme);
        dailyThrowResponse.setDescription(des);
        dailyThrowResponse.setName(richangName);
        dailyThrowResponse.setCellId(cellId);
        dailyThrowResponse.setUnit(unit);


        ApiRetrofit.getInstance().updateThrow(dailyThrowResponse,richangID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateRichangSuccess(baseResponse.getData());
                    }else {
                        getView().updateRichangFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateRichangFailure(throwable.getLocalizedMessage());
                });
    }
}
