package com.jit.AgriIn.uinew.role_admin;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public class EquipUpdatePresenter extends BasePresenter<EquipUpdateView> {

    public EquipUpdatePresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void updateEquip(int id,int cell_id,int term_id,int addr,int road,String equip_name){
        getView().showLoadingDialog();

        ApiRetrofit.getInstance().updateEquip(id,cell_id,term_id,addr,road,equip_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().updateEquipSuccess();
                    }else {
                        getView().updateEquipFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().updateEquipFailure(throwable.getLocalizedMessage());
                });
    }

}
