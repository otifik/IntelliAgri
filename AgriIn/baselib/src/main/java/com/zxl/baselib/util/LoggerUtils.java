package com.zxl.baselib.util;

import com.orhanobut.logger.Logger;
import com.zxl.baselib.BuildConfig;

/**
 * @author zxl
 * 日志管理工具类 ------
 */
public class LoggerUtils {
    /**
     * 是否调试模式
     */
    private static final boolean DEBUG_ENABLE = BuildConfig.LOG_DEBUG;

    public static void logD(String tag, String message) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).d(message);
        }
    }

    public static void logD( String message) {
        if (DEBUG_ENABLE) {
            Logger.d(message);
        }
    }

    public static void logE(String tag,Throwable throwable, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).e(throwable, message, args);
        }
    }

    public static void logE(Throwable throwable, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.e(throwable, message, args);
        }
    }

    public static void logE(String tag,String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).e(message, args);
        }
    }
    public static void logE(String tag,String message) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).e(message);
        }
    }

    public static void logE(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.e(message, args);
        }
    }


    public static void logI(String tag,String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).i(message, args);
        }
    }

    public static void logI(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.i(message, args);
        }
    }
    public static void logV(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.v(message, args);
        }
    }

    public static void logV(String tag,String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).v(message, args);
        }
    }


    public static void logW(String tag,String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).v(message, args);
        }
    }

    public static void logW(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.v(message, args);
        }
    }

    public static void logWtf(String tag,String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).wtf(message, args);
        }
    }

    public static void logWtf(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.wtf(message, args);
        }
    }

    public static void logJson(String tag,String message) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).json(message);
        }
    }

    public static void logJson(String message) {
        if (DEBUG_ENABLE) {
            Logger.json(message);
        }
    }

    public static void logXml(String tag,String message) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).xml(message);
        }
    }

    public static void logXml(String message) {
        if (DEBUG_ENABLE) {
            Logger.xml(message);
        }
    }
}
