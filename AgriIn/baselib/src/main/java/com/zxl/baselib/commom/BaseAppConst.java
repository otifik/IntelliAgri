package com.zxl.baselib.commom;
/**
 * @author zxl
 *         基础的常量值 -.-
 *
 *        K_开头表示活动间数值传递的Key值
 *
 */

public class BaseAppConst {
    /**
     * 发布时： false
     */
    public static boolean isDebug = true;
    /**
     * sp 文件保存名
     */
    public static String SP_NAME = "config";
    public static boolean isTesting = false;
    /**
     * 文件保存Dir
     */
    public static String PS_SAVE_DIR = "JitRobot";
    /**
     * 文件临时保存的地点
     */
    public static String PS_SAVE_LEAST_DIR = "robot_least_cloud";
    /**
     * 应用名 针对不同应用修改
     */
    public static String APP_NAME = "应用名";

    public static final class MulPicShow{
        public static final String TYPE = "type";
        public static final String NOW_POSITION ="now_position";
        public static final String IMAGE_URIS = "image_uris";
    }

    /**
     * 单张图片资源传递的key值
     */
    public static final String K_SINGLE_PHOTO_URL = "key_single_photo_url";
    public static final String TRANSITION_ANIMATION_NEWS_PHOTOS = "transition_animation_news_photos";

    /**
     * 测试图片
     */
    public static final String T_IMAGE_URL = "https://img14.360buyimg.com/n7/jfs/t22456/22/1130676737/229109/53d3a22d/5b5071d2N9dc995d3.jpg";

    /**
     * 菜单展示或者隐藏
     */
    public static final String MENU_SHOW_HIDE="MENU_SHOW_HIDE";
}
