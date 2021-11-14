package com.jit.AgriIn.db;
/**
 * Created by zxl on 2018/4/26.
 */

public class DBManager {
    private static DBManager mInstance;
    private DBManager(){
    }
    public static DBManager getInstance(){
        if (mInstance == null){
            synchronized (DBManager.class){
                if (mInstance == null) {
                    mInstance = new DBManager();
                }
            }
        }
        return mInstance;
    }
}
