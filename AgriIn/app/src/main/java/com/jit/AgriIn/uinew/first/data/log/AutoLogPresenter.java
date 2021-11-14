package com.jit.AgriIn.uinew.first.data.log;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.uinew.first.data.query.HisDataView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/11/8.
 * Describe:
 */
public class AutoLogPresenter extends BasePresenter<AutoLogView> {

    public AutoLogPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void getAutoLog(int cellId,String start_time,String end_time){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().getAutoLog(cellId,start_time,end_time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageResponseBaseResponse -> {
                    if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().hideLoadingDialog();
                        getView().getAutoLogSuccess(pageResponseBaseResponse.getData());
                    }else {
                        getView().hideLoadingDialog();
                        getView().getCellAutoLogFailure(pageResponseBaseResponse.getMsg());
                    }
                }, throwable ->
                        getView().getCellAutoLogFailure(throwable.getLocalizedMessage()));
    }
}
