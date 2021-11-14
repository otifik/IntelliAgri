package com.jit.AgriIn.ui.view.culture;

import com.jit.AgriIn.model.response.PondMainResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/30.
 * Describe:
 */
public interface CultureSubAtView extends BaseView {

    void cultureSubmitSuccess();

    void cultureSubmitFailure(String error);

    void getPondInfoSuccess(List<PondMainResponse> pondBeanList, List<String> pondStrList);

    void getPondInfoFailure(String error);
}
