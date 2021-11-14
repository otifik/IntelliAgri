package com.jit.AgriIn.ui.activity.video.api;

/**
 * Created by yuanyuan on 2018/12/5.
 */

public class TokenResponse {

    /**
     * msg : 操作成功!
     * code : 200
     * data : {"accessToken":"at.b2lkqr0l90ufisxl3zgeubm27ivaf981-3ush3y1ks1-0mik543-qptj2tswf","userId":"0e3b5021eb30f1b6"}
     */
    private String msg;
    private String code;
    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        /**
         * accessToken : at.b2lkqr0l90ufisxl3zgeubm27ivaf981-3ush3y1ks1-0mik543-qptj2tswf
         * userId : 0e3b5021eb30f1b6
         */
        private String accessToken;
        private String userId;

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getUserId() {
            return userId;
        }
    }
}
