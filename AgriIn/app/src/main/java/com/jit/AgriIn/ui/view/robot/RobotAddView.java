package com.jit.AgriIn.ui.view.robot;

import com.jit.AgriIn.model.response.RobotMainResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/28.
 * Describe:
 */
public interface RobotAddView extends BaseView {

    void addRobotSuccess(RobotMainResponse robotMainResponse);

    void addRobotFailure(String error);
}
