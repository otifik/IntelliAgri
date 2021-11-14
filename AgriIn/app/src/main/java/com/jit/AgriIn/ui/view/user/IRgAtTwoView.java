package com.jit.AgriIn.ui.view.user;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.zxl.baselib.ui.base.BaseView;

/**
 * @author zxl on 2018/8/27.
 *         discription:
 */

public interface IRgAtTwoView extends BaseView {

    /**
     * @return EditText 密码
     */
    EditText editPwd();

    /**
     * @return EditText确认密码
     */
    EditText editCfPwd();

    /**
     * @return EditText 用户名
     */
    EditText editAccount();

    /**
     * @return imageView 密码
     */
    ImageView iconPwd();

    /**
     * @return imageView 验证密码
     */
    ImageView iconCfPwd();

    /**
     * @return iamgeView 账号图标
     */
    ImageView iconAccount();

    /**
     * @return relative  密码
     */
    RelativeLayout rlPwd();

    /**
     * @return relative 验证密码
     */
    RelativeLayout rlCfPwd();

    /**
     * @return relative 用户名
     */
    RelativeLayout rlAccount();

    /**
     * @return 注册按钮
     */
    Button buttonRegister();

    /**
     * @return 角色选框
     */
    RadioGroup rgRole();


}
