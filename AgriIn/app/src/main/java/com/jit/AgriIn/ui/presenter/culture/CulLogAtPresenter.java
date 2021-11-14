package com.jit.AgriIn.ui.presenter.culture;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.CultureLogResponse;
import com.jit.AgriIn.ui.view.culture.CulLogAtView;
import com.zxl.baselib.bean.response.BaseListResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/10.
 * Describe:
 */
public class CulLogAtPresenter extends BasePresenter<CulLogAtView> {
    public CulLogAtPresenter(BaseActivity context) {
        super(context);
    }

    /**
     * P层中按天去查询塘口历史日志
     * @param pondID
     * @param startDate
     * @param endDate
     */
    @SuppressLint("CheckResult")
    public void queryPondLogInfoByDate(int pondID, String startDate, String endDate){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().queryDiaryLogInfo(pondID,startDate,endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseListResponse<CultureLogResponse>>() {
                    @Override
                    public void accept(BaseListResponse<CultureLogResponse> cultureLogResponseBaseListResponse) throws Exception {
                        getView().hideLoadingDialog();
                        if (cultureLogResponseBaseListResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                            getView().queryLogSuccess(cultureLogResponseBaseListResponse.getData());
                        }else {
                            getView().queryLogFailure(cultureLogResponseBaseListResponse.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().hideLoadingDialog();
                        getView().queryLogFailure(throwable.getLocalizedMessage());
                    }
                });
    }
}
