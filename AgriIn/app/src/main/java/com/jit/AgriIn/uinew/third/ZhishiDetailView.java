package com.jit.AgriIn.uinew.third;

import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/15.
 * Describe:
 */
public interface ZhishiDetailView extends BaseView {

    void  queryBaikeSuccess(ZhishiBean baikeSeedBean);

    void queryBaikeFailure(String error);
}
