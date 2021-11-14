package com.jit.AgriIn.model.request;

/**
 * @author zxl crazyZhangxl on 2018/9/25.
 * Describe: 登陆请求体，暂时是账号，密码，电话 后续增加
 */
public class RegisterRequest {


    private String password;
    private String role;
    private String username;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
