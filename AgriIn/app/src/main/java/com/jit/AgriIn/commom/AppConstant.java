package com.jit.AgriIn.commom;
import com.zxl.baselib.commom.BaseAppConst;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxl on 2018/6/30.
 *         discription: 常量属性值测试
 */

public class AppConstant extends BaseAppConst{

    /**
     * mina请求的ip以及端口
     */
    public static final String MINA_IP = "115.28.154.41";
    public static final int MINA_PORT = 8009;

    public static final String TEST_EVENTBUS = "eventbus";
    public static String RECORD = "record"; // log日志保存的2级文件夹
    public static final int REQUEST_SUCCESS = 0;
    public static final String[] DIRECTION_ARRAYS = {"东","南","西","北"};
    public static final String[] RICHANG_TYPE_ARRAYS = {"饵料","药品"};
    public static final int MAX_FEED_NUM = 6;
    public static final int NUM_KEY_INPUT = 50;

    public static final String ROLE_CUSTOM = "ROLE_USER";
    public static final String ROLE_EXPERT = "ROLE_EXPERT";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String CAMERA_TOKEN = "accessToken";


    public static final int Role_User = 101;
    public static final int Role_Expert = 102;

    private static Map<String, Integer> gateIdMap = new HashMap<>();
    private static final String[] AQUAUSER = {"yuwang1", "yuwang2", "yuwang3"};
    private static final int[] GATEID = {2, 3, 4};
    static {
        for (int index = 0; index < AQUAUSER.length; index++) {
            gateIdMap.put(AQUAUSER[index], GATEID[index]);
        }
    }
    public static int getGateId(String user) {

//        if (Check.isEmpty(weather)) {
//            return R.mipmap.core_weather_none_available;
//        }

        if (!user.contains("yuwang")){
            return 0;
        }

        if (gateIdMap.get(user) != null) {
            return gateIdMap.get(user);
        }

        for (String gateKey : gateIdMap.keySet()) {
            if (gateKey.contains(user) || user.contains(gateKey)) {
                return gateIdMap.get(gateKey);
            }
        }

        return 0;
    }

    // ---------------------枚举类------------------------

    public enum UserExtra{

        /**
         * 传值 - 电话
         */
        PHONE
    }
    public enum PondDirection{
        /**
         * 西南
         */
        W_S("西南角"),
        /**
         * 西北
         */
        W_N("西北角"),
        /**
         * 东南
         */
        E_S("东南角"),
        /**
         * 东北
         */
        E_N("东北角");
        public String name;

        PondDirection(String str) {
            this.name = str;
        }

    }

    public static final String[] BAIKE_KIND = {"鱼","虾","蟹","其他"};



    public static final class FileSaveType{
        public static final String XLS = ".xls";
        public static final String CSV = ".csv";
        public static final String TXT = ".txt";
        public static final String DES_FOR_XLS ="极力推荐";
        public static final String DES_FOR_CVS ="效果一般";
        public static final String DES_FOR_TXT ="斟酌再三";
    }

    /**
     * 用户信息缓存对象值
     */
    public enum UserInfo{
        image,
        register_time,
        role,
        token,
        id,
        username,
        pwd
    }

    /**
     * 8个经纬度的传值
     */
    public enum DirectionExtra{
        LAT_W_S,
        LAT_W_N,
        LAT_E_S,
        LAT_E_N,
        LONG_E_S,
        LONG_E_N,
        LONG_W_S,
        LONG_W_N
    }

    // ----------------------状态返回码------------------------

    /**
     * 普通塘口信息的枚举类
     */
    public enum PondNormalConfig{
        POND_ID,
        POND_NAME,
        POND_LENGTH,
        POND_WIDTH,
        POND_DEPTH,
        POND_MAX_DEPTH,
        POND_PROVINCE,
        POND_CITY,
        POND_DISTRICT,
        POND_DETAIL_ADDRESS,
        POND_DIRECTION,
    }

    public static final String[] ROBOT_TYPE = {"金科智农"};

    public static final String IMAGE_URL_GLIDE = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2517753454.jpg";


    public static String[] WEATHER_SHOW = new String[]{
            "晴","多云转晴","阴转晴","晴转阴","阴转多云","多云转阴",
            "小雪","中雪","大雪","雨夹雪","多云转小雨","阵雨转多云","多云转阵雨",
            "阴","小雪转小雨","阴转阵雨","雷阵雨转多云","浮尘",
            "雾霾","多云","雷雨","台风","小雨","中雨",
            "大雨","雷阵雨","暴雨","其他"
    };

    public static String[] REPAIR_STATUS = new String[]{"待维修","维修中","已完成维修","损耗太大,无法维修"};

    /**
     * 添加经纬度
     */
    public static final int RECODE_ADD_LATITUDE = 0x001;

    /**
     * 修改经纬度
     */
    public static final int RECODE_MODIFY_LATITUDE = 0X002;
    /**
     * 从展示塘口配置 ----> 修改塘口配置回调
     */
    public static final int RECODE_MODIFY_POND_CONFIG = 0X003;

    // 从用法用量转向输入框的请求码
    public static final int RCODE_INPUT_FROM_TYPE_DEFINE= 0x004;
    // 从附加信息转向输入框的请求码
    public static final int RCODE_INPUT_FROM_REMARK_DETAIL= 0x005;
    // 单个条目的点击请求码
    public static final int RECODE_TO_SINGLE_ITEM_LIST = 0x006;

    public static final int RECODE_EDIT_FROM_LOG_FILE = 0x007;// 从文件转入




    public static final float LATITUDE_DEFAULT = -1f;



    // ---------------------- rx刷新控件 ------

    //-------------- 普通值 -----------
    public static final String EXTRA_IS_CHANGE_LATITUDES = "extra_is_change_latitudes";

    public static final int NORMAL_PAGE_SIZE = 8;

    public static final String BUNDLE_POND_BEAN = "bundle_pond_bean";
    public static final String BUNDLE_FISH_INPUT_BEAN = "bundle_fish_input_bean";
    public static final String BUNDLE_FEEDING_FOOD_BEAN = "bundle_feeding_food_bean";
    public static final String BUNDLE_INPUT_BEAN = "bundle_input_bean";
    public static final String BUNDLE_REAGENT_INPUT_BEAN = "bundle_reagent_input_bean";
    public static final String BUNDLE_FEEDING_TEMPLATE_BEAN = "bundle_feeding_template_bean";
    public static final String BUNDLE_ROBOT_BEAN = "bundle_robot_bean" ;
    public static final String BUNDLE_CONFIG_BEAN = "bundle_config_bean" ;
    public static final String EXTRA_ITEM_INDEX = "extra_item_index";
    public static final String EXTRA_IS_FROM_POND_MAIN = "extra_is_from_pond_main";
    public static final String EXTRA_IS_FROM_INPUT_MAIN = "extra_is_from_input_main";
    public static final String EXTRA_IS_FROM_FISH_INPUT_MAIN = "extra_is_from_fish_input_main";
    public static final String EXTRA_IS_FROM_FEEDING_FOOD_MAIN = "extra_is_from_feeding_food_main";
    public static final String EXTRA_IS_FROM_REAGENT_INPUT_MAIN = "extra_is_from_reagent_input_main";
    public static final String EXTRA_IS_FROM_FEEDING_TEMPLATE_MAIN = "extra_is_from_feeding_template_main";
    public static final String EXTRA_IS_FROM_CONFIG_MAIN = "extra_is_from_config_main";
    public static final String EXTRA_WEATHER_STATE = "extra_weather_state";

    // 打开输入框的标题
    public static final String KEY_INPUT_TITLE = "key_input_title";
    //   从输入框界面的返回值
    public static final String KEY_FROM_INPUT = "key_from_input";
    // 打开界面传递的内容
    public static final String KEY_TO_INPUT_CONTENT = "key_to_input_content";
    // 单个条目的选择
    public static final String SINGLE_ITEM_SELECTED =  "single_item_selected";
    // 单个塘口的id选择
    public static final String POND_ID_SELECTED =  "pond_id_selected";

    public static final String POND_NAME_SELECTED =  "pond_name_selected";

    public static final String POND_ADDRESS_SELECTED =  "pond_address_selected";

    public static final String EXTRA_BAIKE_ID = "extra_baike_id";
    public static final String EXTRA_BAIKE_FLAG = "extra_baike_flag";

    public static final String QUESTION_ID = "question_id";

    //--------------- rxBus 刷新监听值 ----------
    public static final String RX_ADD_POND = "rx_add_pond";
    public static final String RX_UPDATE_POND = "rx_update_pond";
    public static final String RX_ADD_ROBOT = "rx_add_robot";
    public static final String RX_UPDATE_ROBOT = "rx_update_robot";
    public static final String RX_ADD_CONFIG = "rx_add_config";
    public static final String RX_UPDATE_CONFIG = "rx_update_config";
    public static final String RX_ADD_INPUT = "rx_add_input";
    public static final String RX_UPDATE_INPUT = "rx_update_input";
    public static final String RX_ADD_FISH_INPUT = "rx_add_fish_input";
    public static final String RX_UPDATE_FISH_INPUT = "rx_update_fish_input";
    public static final String RX_ADD_FEEDING_FOOD = "rx_add_feeding_food";
    public static final String RX_UPDATE_FEEDING_FOOD = "rx_update_feeding_food";
    public static final String RX_ADD_REAGENT_INPUT = "rx_add_reagent_input";
    public static final String RX_UPDATE_REAGENT_INPUT = "rx_update_reagent_input";
    public static final String RX_ADD_FEEDING_TEMPLATE = "rx_add_feeding_template";
    public static final String RX_UPDATE_FEEDING_TEMPLATE = "rx_update_feeding_template";

    public static final String RX_UPDATE_DISEASE_BAIKE = "rx_update_disease_baike";
    public static final String RX_UPDATE_PRODUCT_BAIKE = "rx_update_product_baike";
    public static final String RX_UPDATE_FEED_BAIKE = "rx_update_feed_baike";
    public static final String RX_UPDATE_SEED_BAIKE = "rx_update_seed_baike";
    public static final String RX_UPDATE_DRUG_BAIKE = "rx_update_drug_baike";

    public static final String RX_ADD_DISEASE_BAIKE = "rx_add_disease_baike";
    public static final String RX_ADD_PRODUCT_BAIKE = "rx_add_product_baike";
    public static final String RX_ADD_FEED_BAIKE = "rx_add_feed_baike";
    public static final String RX_ADD_SEED_BAIKE = "rx_add_seed_baike";
    public static final String RX_ADD_DRUG_BAIKE = "rx_add_drug_baike";
    public static final String RX_ADD_REPAIR_INFO = "rx_add_repair_info";


    public static final String RX_POST_SUCCESS = "成功";
    public static final String RX_ON_SUCCESS = "成功";


    public static final String EXTRA_DOWNLOAD_LIST = "extra_download_list";
    public static final String EXTRA_DOWNLOAD_NAME  = "extra_download_name";
    public static final String CONTENT_FORM_FILE_LOG = "content_form_file_log";
    public static final String RESULT_FROM_EDIT = "result_from_edit";

}
