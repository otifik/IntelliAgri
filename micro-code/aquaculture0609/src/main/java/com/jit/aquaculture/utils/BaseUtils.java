package com.jit.aquaculture.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author croton
 * @create 2021/3/31 19:34
 */
public class BaseUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseUtils.class);

    private static final Date DATE = new Date();

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    private static boolean isOnTime;

    private static long timeGap = 0L;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Random random = new Random();




    /**
     * 获取UUID作为token
     * @return uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成盐值
     */
    public static String getSalt() {

        int val = new Random().nextInt() % 999999;
        val = Math.abs(val);
        String salt = String.format("%06d", val);
        salt += UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        return salt;
    }

    /**
     * List -> String[]
     */
    public static String restoreList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        list.stream().filter(p -> !"".equals(p)).forEach(p -> {sb.append(p).append(";");});
        return sb.toString();
    }

    /**
     * List -> String[]
     */
    public static String filerestoreList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        list.stream().filter(p -> !"".equals(p)).forEach(p -> {sb.append(p).append("-");});
        return sb.toString();
    }

    /**
     * String[] -> List
     */
    public static List<String> getListFromString(String str) {
        String[] strings = str.trim().split(";");
        return Arrays.stream(strings).filter(p -> !"".equals(p)).collect(Collectors.toList());
    }

    /**
     * String[] -> List
     */
    public static List<String> getFileListFromString(String str) {
        String[] strings = str.trim().split("_");
        return Arrays.stream(strings).filter(p -> !"".equals(p)).collect(Collectors.toList());
    }



    /**
     * 获取北京时间
     * @return 误差在1秒内的UTC+8时间
     */
    public static Long UTCUp8Time() {
        long time;
        if (!isOnTime) {
            time = System.currentTimeMillis() + timeGap;
        } else {
            time = System.currentTimeMillis();
        }
        // TODO 直接返回系统时间
        return System.currentTimeMillis();
    }

    /**
     * 时间格式转换 yyyy_MM_dd_hh_mm到毫秒
     */
    public static Long timeParser(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
        long t = 0;
        try {
            t = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 处理空串
     */
    private static <T> T dealNullStr(T str) {
        if ("".equals(str)) {
            return null;
        } else {
            return str;
        }
    }



//    public static void setDefaultValues(Object o) throws UnhandledException {
//        Field[] fields = o.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            if (Modifier.isFinal(field.getModifiers())) {
//                continue;
//            }
//            if (Modifier.isStatic(field.getModifiers())) {
//                continue;
//            }
//            boolean isAccess = field.canAccess(o);
//            if (!isAccess) {
//                field.setAccessible(true);
//            }
//            try {
//                if (field.get(o) != null) {
//                    continue;
//                }
//            } catch (IllegalAccessException e) {
//                ;
//            }
//            String fieldName = field.getName();
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(fieldName.charAt(0));
//            for (int i = 1, size = fieldName.length(); i < size; ++ i) {
//                char a = fieldName.charAt(i - 1);
//                char b = fieldName.charAt(i);
//                if (b >= 'A' && b <= 'Z') {
//                    stringBuilder.append("-")
//                            .append((char) (b - 'A' + 'a'));
//                } else {
//                    stringBuilder.append(b);
//                }
//            }
//            String newFieldName = stringBuilder.toString();
//            Class<?> clazz = field.getType();
//            try {
//                if (Long.class.getName().equals(clazz.getName())) {
//                    field.set(o, 0L);
//                } else if (String.class.getName().equals(clazz.getName())) {
//                    field.set(o, "no-" + newFieldName);
//                } else if (Double.class.getName().equals(clazz.getName())) {
//                    field.set(o, 0.0);
//                } else if (Integer.class.getName().equals(clazz.getName())) {
//                    field.set(o, 0);
//                } else if (Boolean.class.getName().equals(clazz.getName())) {
//                    field.set(o, false);
//                } else if (Short.class.getName().equals(clazz.getName())) {
//                    field.set(o, 0);
//                } else if (Float.class.getName().equals(clazz.getName())) {
//                    field.set(o, 0.0);
//                } else if (Date.class.getName().equals(clazz.getName())){
//                    field.set(o, new Date());
//                } else if (Gender.class.getName().equals(clazz.getName())) {
//                    field.set(o, Gender.X);
//                }
//            } catch (IllegalAccessException e) {
//                throw new UnhandledException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                        "设置对象域" + fieldName + "的默认值失败",
//                        BaseUtils.getRunLocation(Thread.currentThread().getStackTrace()[1]),
//                        new Date());
//            }
//            if (!isAccess) {
//                field.setAccessible(false);
//            }
//        }
//    }




}
