package com.jit.AgriIn.model.response;

/**
 * @author zxl crazyZhangxl on 2018/9/25.
 * Describe:
 */
public class LoginResponse {

    /**
     * id : 0
     * image : string
     * password : string
     * register_time : 2019-05-10T07:22:35.280Z
     * role : string
     * token : string
     * username : string
     */

    private int id;
    private String image;
    private String password;
    private String register_time;
    private String role;
    private String token;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
