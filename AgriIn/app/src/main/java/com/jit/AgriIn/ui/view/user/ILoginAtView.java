package com.jit.AgriIn.ui.view.user;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zxl.baselib.ui.base.BaseView;


/**
 * @author zxl on 2018/8/24.
 *         discription: 登录View层
 */

public interface ILoginAtView extends BaseView {
    /**
     * @return 电话图标
     */
    ImageView iconMobile();

    /**
     * @return 密码图标
     */
    ImageView iconPwd();

    /**
     * @return 账号EditText
     */
    EditText editAccount();

    /**
     * @return 密码EditText
     */
    EditText editPwd();

    /**
     * @return 账户的相对布局
     */
    RelativeLayout realAccount();

    /**
     * @return 密码布局
     */
    RelativeLayout realPwd();

    /**
     * @return 登陆按钮
     */
    Button buttonLogin();

}
