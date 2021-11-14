package com.jit.AgriIn.uinew.third;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/12.
 * Describe: 百科类Fg P层
 */
public class AnswerPresenter extends BasePresenter<AnswerView> {

    public AnswerPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void queryOneQuestion(int ID){
                ApiRetrofit.getInstance().queryOneQuestion(ID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(baseResponse -> {
                            if (baseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                                getView().queryQuestionSuccess(baseResponse.getData());
                            }else {
                                getView().queryQuestionFailure(baseResponse.getMsg());
                            }
                        }, throwable -> getView().queryQuestionFailure(throwable.getLocalizedMessage()));

    }


    @SuppressLint("CheckResult")
    public void answerQuestion(String content,int questionID){
        ApiRetrofit.getInstance().answerQuestion(content,questionID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(answerBeanBaseResponse -> {
                    if (answerBeanBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().answerSuccess(answerBeanBaseResponse.getData());
                    }else {
                        getView().answerFailure(answerBeanBaseResponse.getMsg());
                    }
                },throwable -> getView().answerFailure(throwable.getLocalizedMessage()));
    }



}
