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
 * Describe: 增加塘口的P层
 */
public class EquipAddPresenter extends BasePresenter<EquipAddView> {
    public EquipAddPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void addEquip(int cell_id,int term_id,int addr,int road,String equip_type,String equip_name){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().addEquip(cell_id,term_id,road,addr,equip_type,equip_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().addEquipSuccess(baseResponse.getData());
                    }else {
                        getView().addEquipFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().addEquipFailure(throwable.getLocalizedMessage());
                });
    }




    @SuppressLint("CheckResult")
    public void getEquipType(){
        getView().showLoadingDialog();
        ApiRetrofit.getInstance().getEquipType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    getView().hideLoadingDialog();
                    if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().getEquipTypeSuccess(baseResponse.getData());
                    }else {
                        getView().getEquipTypeFailure(baseResponse.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().getEquipTypeFailure(throwable.getLocalizedMessage());
                });
    }

}
