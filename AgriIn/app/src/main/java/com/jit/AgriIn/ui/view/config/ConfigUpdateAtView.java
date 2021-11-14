package com.jit.AgriIn.ui.view.config;

import com.jit.AgriIn.model.response.ConfigMainResponse;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.model.response.RobotPageResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author zxl on 2018/9/5.
 *         discription:
 */

public interface ConfigUpdateAtView extends BaseView {

    void updateConfigSuccess(ConfigMainResponse configMainResponse);

    void updateConfigFailure(String error);


    void getPondInfoSuccess(List<PondMainResponse> pondBeanList, List<String> pondStrList);

    void getPondInfoFailure(String error);


    void queryMyRobotSuccess(RobotPageResponse robotPageResponse);

    void queryMyRobotFailure(String error);

}
