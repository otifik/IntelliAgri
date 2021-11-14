package com.jit.AgriIn.model.cache;

import com.jit.AgriIn.commom.AppConstant;
import com.zxl.baselib.util.app.SharePreferenceUtils;
import com.zxl.baselib.util.ui.UIUtils;

/**
 * @author zxl crazyZhangxl on 2018/9/25.
 * Describe: 用户信息缓存bean
 *
 *     private String image;
private String realname;
private String register_time;
private String role;
private String token;
private int user_id;
private String username;
 */
public class UserCache {

    public static String getUserImage(){
        return SharePreferenceUtils.getInstance(UIUtils.getContext()).getString(AppConstant.UserInfo.image.name(),"");
    }

//    public static String getUserRealname(){
//        return SharePreferenceUtils.getInstance(UIUtils.getContext()).getString(AppConstant.UserInfo.realname.name(),"");
//    }

    public static String getUserRegister_time(){
        return SharePreferenceUtils.getInstance(UIUtils.getContext()).getString(AppConstant.UserInfo.register_time.name(),"");
    }

    public static String getUserRole(){
        return SharePreferenceUtils.getInstance(UIUtils.getContext()).getString(AppConstant.UserInfo.role.name(),"");
    }

    public static String getUserToken(){
        return SharePreferenceUtils.getInstance(UIUtils.getContext()).getString(AppConstant.UserInfo.token.name(),"");
    }

    public static int getUserUser_id(){
        return SharePreferenceUtils.getInstance(UIUtils.getContext()).getInt(AppConstant.UserInfo.id.name(),-1);
    }

    public static String getUserUsername(){
        return SharePreferenceUtils.getInstance(UIUtils.getContext()).getString(AppConstant.UserInfo.username.name(),"");
    }

    public static String getUserPwd(){
        return SharePreferenceUtils.getInstance(UIUtils.getContext()).getString(AppConstant.UserInfo.pwd.name(),"");
    }


    /**
     * 缓存用户密码
     * @param pwd
     */
    public static void setUserPwd(String pwd){
        SharePreferenceUtils.getInstance(UIUtils.getContext()).putString(AppConstant.UserInfo.pwd.name(),pwd);
    }

    public static void setUserName(String username){
        SharePreferenceUtils.getInstance(UIUtils.getContext()).putString(AppConstant.UserInfo.username.name(),username);
    }

    /**
     * 缓存用户真实姓名
     * @param realName
     */
//    public static void setRealName(String realName){
//        SharePreferenceUtils.getInstance(UIUtils.getContext()).putString(AppConstant.UserInfo.realname.name(),realName);
//    }

    /**
     * 缓存用户头像
     * @param img
     */
    public static void setUserImage(String img){
        SharePreferenceUtils.getInstance(UIUtils.getContext()).putString(AppConstant.UserInfo.image.name(),img);
    }

    public static void setUserToken(String token){
        SharePreferenceUtils.getInstance(UIUtils.getContext()).putString(AppConstant.UserInfo.token.name(),token);
    }



    /**
     * 批量缓存
     */
    public static void save(String image,String register_time,String role,String token,int user_id,String username){
        SharePreferenceUtils.getInstance(UIUtils.getContext()).putString(AppConstant.UserInfo.image.name(),image);
        SharePreferenceUtils.getInstance(UIUtils.getContext()).putString(AppConstant.UserInfo.register_time.name(),register_time);
        SharePreferenceUtils.getInstance(UIUtils.getContext()).putString(AppConstant.UserInfo.role.name(),role);
        SharePreferenceUtils.getInstance(UIUtils.getContext()).putString(AppConstant.UserInfo.token.name(),token);
        SharePreferenceUtils.getInstance(UIUtils.getContext()).putInt(AppConstant.UserInfo.id.name(),user_id);
        SharePreferenceUtils.getInstance(UIUtils.getContext()).putString(AppConstant.UserInfo.username.name(),username);
    }

    /**
     * 批量清理
     */
    public static void clear(){
        SharePreferenceUtils.getInstance(UIUtils.getContext()).remove(AppConstant.UserInfo.image.name());
        SharePreferenceUtils.getInstance(UIUtils.getContext()).remove(AppConstant.UserInfo.register_time.name());
        SharePreferenceUtils.getInstance(UIUtils.getContext()).remove(AppConstant.UserInfo.role.name());
        SharePreferenceUtils.getInstance(UIUtils.getContext()).remove(AppConstant.UserInfo.token.name());
        SharePreferenceUtils.getInstance(UIUtils.getContext()).remove(AppConstant.UserInfo.id.name());
        SharePreferenceUtils.getInstance(UIUtils.getContext()).remove(AppConstant.UserInfo.username.name());
        SharePreferenceUtils.getInstance(UIUtils.getContext()).remove(AppConstant.UserInfo.pwd.name());

    }

}
