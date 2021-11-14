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
public class QuestionListPresenter extends BasePresenter<QuestionListView> {

    public QuestionListPresenter(BaseActivity context) {
        super(context);
    }

    @SuppressLint("CheckResult")
    public void queryQuestionList(int pageNum){
                ApiRetrofit.getInstance().queryQuestionList(pageNum)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pageResponseBaseResponse -> {
                            if (pageResponseBaseResponse.getCode() == AppConstant.REQUEST_SUCCESS){
                                getView().queryQuestionSuccess(pageResponseBaseResponse.getData());
                            }else {
                                getView().queryQuestionFailure(pageResponseBaseResponse.getMsg());
                            }
                        }, throwable -> getView().queryQuestionFailure(throwable.getLocalizedMessage()));

    }
}
